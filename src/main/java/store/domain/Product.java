package store.domain;

import store.domain.util.ItemsMap;
import store.validator.YesOrNoValidator;
import store.view.InputView;
import store.view.OutputView;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Product {

    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean paymentAvailable(int orderQuantity) {
        return this.quantity >= orderQuantity;
    }

    public int generalAvailableForBuy(String productName, int orderQuantity) {
        Map<String, String> items = ItemsMap.create();
        String product = items.get(productName);
        Items item = Items.valueOf(product);

        this.quantity -= orderQuantity;
        return item.totalPrice(orderQuantity);
    }

    public void promotionAvailableForBuy(String productName, int orderQuantity,
                                         Map<String, Integer> giftProducts, Map<String, Integer> order) {
        if (promotion.checkGift(orderQuantity)) {
            String answer = addProduct(productName);
            if (answer.equals("n")) {
                return;
            }
            if (answer.equals("y")) {
                order.put(productName, order.get(productName) + 1);
                orderQuantity += 1;
            }
        }
        int giftCount = promotion.giftCount(orderQuantity);
        if (promotion.minBuy(orderQuantity) ) {
            giftProducts.put(productName, giftCount);
        }
        this.quantity -= orderQuantity;
    }

    public String addProduct(String productName) {
        while (true) {
            try {
                InputView inputView = new InputView();
                return YesOrNoValidator.validate(inputView.readFreeGift(productName));
            } catch (IllegalArgumentException e) {
                OutputView outputView = new OutputView();
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public int nonPaymentCount(String productName, int orderQuantity,
                               Map<String, Integer> giftProducts, Map<String, Integer> order) {
        if (this.quantity == 0) {
            String input = sendGeneralProduct(productName, orderQuantity);
            if (input.equals("n")) {
                order.put(productName, 0);
                return 0;
            }
            return orderQuantity;
        }

        int remainQuantity = orderQuantity - this.quantity; // 12- 1 = 11
        int useQuantity = orderQuantity - remainQuantity; // 12 - 11 = 1
        // 일단 이거는 입력이 y 또는 n 이든 둘 다 실행해야되는 것
        int giftCount = promotion.giftCount(useQuantity);
        if (giftCount != 0) {
            giftProducts.put(productName, giftCount);
        }
        int remainCount = 0;
        if (useQuantity != 1) {
             remainCount = promotion.remainCount(useQuantity);
        }
        int generalCount = remainCount + remainQuantity;
        String input = sendGeneralProduct(productName, generalCount);
        if (input.equals("n")) {
            order.put(productName, useQuantity);
        }
        if (input.equals("y")) {
            this.quantity -= useQuantity;
            return remainQuantity;
        }
        this.quantity -= useQuantity;
        return 0;
    }

    private String sendGeneralProduct(String productName, int generalCount) {
        while (true) {
            try {
                InputView inputView = new InputView();
                String input = inputView.readNoPromotionDiscount(productName, generalCount);
                return YesOrNoValidator.validate(input);
            } catch (IllegalArgumentException e) {
                OutputView outputView = new OutputView();
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public boolean checkDate(LocalDate now) {
        return promotion.checkData(now);
    }

    public boolean isPromotion() {
        return promotion != null;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String writeString() {
        String promotion = "null";
        if (this.promotion != null) {
            promotion = this.promotion.getName();
        }
        return name + "," + price + "," + quantity + "," + promotion;
    }

    @Override
    public String toString() {
        String promotionName = "";
        if (promotion != null) {
            promotionName = promotion.getName();
        }
        String quantity = this.quantity + "개";
        if (quantity.equals("0개")) {
            quantity = "재고 없음";
        }
        return "- " + name + " " +
                NumberFormat.getNumberInstance().format(price) + "원 " +
                quantity + " " + promotionName;
    }
}
