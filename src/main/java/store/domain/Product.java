package store.domain;

import store.domain.util.ItemsMap;
import store.validator.YesOrNoValidator;
import store.view.InputView;
import store.view.OutputView;

import java.text.NumberFormat;
import java.time.LocalDateTime;
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

        deductQuantity(orderQuantity);
        return item.totalPrice(orderQuantity);
    }

    public void promotionAvailableForBuy(String productName, int orderQuantity,
                                         Map<String, Integer> giftProducts, Map<String, Integer> order) {
        if (this.quantity == orderQuantity) {
            sameQuantity(productName, orderQuantity, giftProducts);
            return;
        }
        if (promotion.checkGift(orderQuantity)) {
            int gift = addGift(productName, orderQuantity, order);
            if (gift == 0) return;
            orderQuantity = gift;
        }
        deductQuantity(orderQuantity);
        addGiftProduct(productName, orderQuantity, giftProducts);
    }

    // todo 메서드 10줄 이하로 줄이기
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

    private int addGift(String productName, int orderQuantity, Map<String, Integer> order) {
        String answer = addProduct(productName);
        if (answer.equals("y")) {
            order.put(productName, order.get(productName) + 1);
            return orderQuantity + 1;
        }
        deductQuantity(orderQuantity);
        return 0;
    }

    private void sameQuantity(String productName, int orderQuantity, Map<String, Integer> giftProducts) {
        int giftCount = promotion.giftCount(orderQuantity);
        if (giftCount > 0) {
            giftProducts.put(productName, giftCount);
        }
        deductQuantity(orderQuantity);
    }

    private void addGiftProduct(String productName, int orderQuantity, Map<String, Integer> giftProducts) {
        int giftCount = promotion.giftCount(orderQuantity);
        if (promotion.minBuy(orderQuantity) ) {
            giftProducts.put(productName, giftCount);
        }
    }

    // 프로모션 상품의 재고와 일반 상품의 재고를 함께 사용하는 메서드
    // 여기서 먼저 프로모션 상품의 재로를 사용한다.
    // 여기서 0을 리턴한다는 것은 일반 상품의 재고를 사용하지 않겠다는 의미.
    public int nonPaymentCount(String productName, int orderQuantity,
                               Map<String, Integer> giftProducts, Map<String, Integer> order) {
        if (this.quantity == 0) {
            return promotionQuantityIsNotting(productName, orderQuantity, order);
        }
        int remainQuantity = orderQuantity - this.quantity;
        int useQuantity = orderQuantity - remainQuantity; 
        int giftCount = promotion.giftCount(useQuantity);
        putGiftProduct(productName, giftProducts, giftCount);
        int remainCount = 0;
        remainCount = getRemainCount(useQuantity, remainCount);
        int generalCount = remainCount + remainQuantity;
        String input = sendGeneralProduct(productName, generalCount);
        if (input.equals("n")) {
            order.put(productName, useQuantity);
        }
        if (input.equals("y")) {
            deductQuantity(useQuantity);
            return remainQuantity;
        }
        deductQuantity(useQuantity);
        return 0;
    }

    private static void putGiftProduct(String productName, Map<String, Integer> giftProducts, int giftCount) {
        if (giftCount != 0) {
            giftProducts.put(productName, giftCount);
        }
    }

    private int getRemainCount(int useQuantity, int remainCount) {
        if (useQuantity != 1 && useQuantity != 2) {
             remainCount = promotion.remainCount(useQuantity);
        }
        return remainCount;
    }

    private int promotionQuantityIsNotting(String productName, int orderQuantity, Map<String, Integer> order) {
        String input = sendGeneralProduct(productName, orderQuantity);
        if (input.equals("n")) {
            order.put(productName, 0);
            return 0;
        }
        return orderQuantity;
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

    private void deductQuantity(int orderQuantity) {
        this.quantity -= orderQuantity;
    }

    public boolean checkDate(LocalDateTime now) {
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
        promotionName = hasPromotion(promotionName);
        String quantity = hasQuantity();
        return "- " + name + " " +
                NumberFormat.getNumberInstance().format(price) + "원 " +
                quantity + " " + promotionName;
    }

    private String hasPromotion(String promotionName) {
        if (promotion != null) {
            promotionName = promotion.getName();
        }
        return promotionName;
    }

    private String hasQuantity() {
        String quantity = this.quantity + "개";
        if (quantity.equals("0개")) {
            quantity = "재고 없음";
        }
        return quantity;
    }
}
