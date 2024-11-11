package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.util.receipt.CreateReceipt;
import store.validator.YesOrNoValidator;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static store.error.Error.*;
import static store.error.ErrorMessage.*;

public class InventoryManagement {

    private final List<Product> products;
    private final Map<String, Integer> order;
    private final Map<String, Product> promotionProducts;
    private final Map<String, Product> generalProducts;
    private final Map<String, Integer> giftProduct;
    private int generalTotalPrice = 0;

    public InventoryManagement(List<Product> products,  Map<String, Integer> order) {
        this.products = products;
        this.order = order;
        promotionProducts = splitPromotion();
        generalProducts = splitGeneral();
        giftProduct = new HashMap<>();
    }

    private Map<String, Product> splitPromotion() {
        Map<String, Product> promotionProduct = new HashMap<>();
        for (Product product : products) {
            if (product.isPromotion()) {
                promotionProduct.put(product.getName(), product);
            }
        }
        return promotionProduct;
    }

    private Map<String, Product> splitGeneral() {
        Map<String, Product> generalProduct = new HashMap<>();
        for (Product product : products) {
            if (!product.isPromotion()) {
                generalProduct.put(product.getName(), product);
            }
        }
        return generalProduct;
    }

    public void calculate() {
        for (String orderProduct : order.keySet()) {
            if (promotionProducts.containsKey(orderProduct)) {
                promotionCalculate(orderProduct, order.get(orderProduct));
                continue;
            }
            if (generalProducts.containsKey(orderProduct)) {
                generalCalculate(orderProduct, order.get(orderProduct));
                continue;
            }
            throw new IllegalArgumentException(ERROR.toString() + IS_DOESNT_EXIST);
        }
    }

    private void promotionCalculate(String productName, int orderQuantity) {
        Product product = promotionProducts.get(productName);
        LocalDateTime now = DateTimes.now();
        if (product.checkDate(now)) {
            if (product.paymentAvailable(orderQuantity)) {
                product.promotionAvailableForBuy(productName, orderQuantity, giftProduct, order);
                return;
            }
            outOfStock(productName, orderQuantity, product);
            return;
        }
        generalCalculate(productName, orderQuantity);
    }

    private void outOfStock(String productName, int orderQuantity, Product product) {
        int totalQuantity = getTotalQuantity(productName);
        if (totalQuantity < orderQuantity) {
            throw new IllegalArgumentException(ERROR.toString() + IS_EXCESS_QUANTITY);
        }
        int count = product.nonPaymentCount(productName, orderQuantity, giftProduct, order);
        if (count == 0){
            return;
        }
        generalCalculate(productName, count);
    }

    private int getTotalQuantity(String productName){
        Product promotionProduct = promotionProducts.get(productName);
        Product generalProduct = generalProducts.get(productName);
        return promotionProduct.getQuantity() + generalProduct.getQuantity();
    }

    private void generalCalculate(String productName, int orderQuantity) {
        Product product = generalProducts.get(productName);
        if (product.paymentAvailable(orderQuantity)) {
            generalTotalPrice += product.generalAvailableForBuy(productName, orderQuantity);
            return;
        }
        throw new IllegalArgumentException(ERROR.toString() + IS_EXCESS_QUANTITY);
    }

    public String writeReceipt() {
        int totalPrice = hopeMembershipDiscount();
        return CreateReceipt.create(order,giftProduct, totalPrice);
    }

    private int hopeMembershipDiscount() {
        String input = membershipInput();
        if (input.equals("n")) {
            return 0;
        }
        return generalTotalPrice;
    }

    private String membershipInput() {
        while (true) {
            try {
                InputView input = new InputView();
                return YesOrNoValidator.validate(input.readMembership());
            } catch (IllegalArgumentException e) {
                OutputView output = new OutputView();
                output.printExceptionMessage(e.getMessage());
            }
        }
    }
}
