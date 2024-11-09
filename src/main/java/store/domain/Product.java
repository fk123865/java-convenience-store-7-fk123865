package store.domain;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Map;

import static store.error.Error.ERROR;
import static store.error.ErrorMessage.IS_EXCESS_QUANTITY;

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

    public int availableForBuy(Map<String, Integer> order) {
        Integer orderQuantity = order.get(name);
        if (promotion == null || orderQuantity > quantity) {
            throw new IllegalArgumentException(ERROR.toString() + IS_EXCESS_QUANTITY);
        }
        this.quantity -= orderQuantity;
        return this.quantity;
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
