package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.util.receipt.CreateReceipt;
import store.error.Error;
import store.error.ErrorMessage;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static store.error.Error.*;
import static store.error.ErrorMessage.*;

public class InventoryManagement {

    private final List<Product> products;
    private final Map<String, Integer> order;
    private final Map<String, Product> promotionProduct;
    private final Map<String, Product> generalProduct;
    private final Map<String, Integer> giftProduct;
    private int generalTotalPrice = 0;

    public InventoryManagement(List<Product> products,  Map<String, Integer> order) {
        this.products = products;
        this.order = order;
        promotionProduct = splitPromotion();
        generalProduct = splitGeneral();
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
            if (promotionProduct.containsKey(orderProduct)) {
                promotionCalculate(orderProduct, order.get(orderProduct));
                continue;
            }
            if (generalProduct.containsKey(orderProduct)) {
                generalCalculate(orderProduct, order.get(orderProduct));
                continue;
            }
            throw new IllegalArgumentException(ERROR.toString() + IS_DOESNT_EXIST);
        }
    }

    private void promotionCalculate(String productName, int orderQuantity) {
        Product product = promotionProduct.get(productName);
        LocalDate now = DateTimes.now().toLocalDate();
        if (product.checkDate(now)) {
            if (product.paymentAvailable(orderQuantity)) { // 프로모션 재고로만 가능할 때
                product.promotionAvailableForBuy(productName, orderQuantity, giftProduct, order);
                return;
            }
            // 프로모션 재고로만 불가능할 때
            int count = product.nonPaymentCount(productName, orderQuantity, giftProduct, order);
            if (count == 0){
                return;
            }
            generalCalculate(productName, count);
            return;
        }
        // 프로모션 기간이 아닐 땐 바로 일반 재고로 넘긴다.
        generalCalculate(productName, orderQuantity);
    }

    private void generalCalculate(String productName, int orderQuantity) {
        Product product = generalProduct.get(productName);
        if (product.paymentAvailable(orderQuantity)) {
            generalTotalPrice += product.generalAvailableForBuy(productName, orderQuantity);
            return;
        }
        throw new IllegalArgumentException(ERROR.toString() + IS_EXCESS_QUANTITY);
    }

    public String writeReceipt() {
        return CreateReceipt.create(order,giftProduct, generalTotalPrice);
    }
}
