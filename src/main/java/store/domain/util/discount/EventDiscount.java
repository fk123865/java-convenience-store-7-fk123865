package store.domain.util.discount;

import store.domain.Items;
import store.domain.util.ItemsMap;

import java.util.Map;

public class EventDiscount {

    private EventDiscount(){}

    public static int discount(Map<String, Integer> giftProduct) {
        if (giftProduct == null) {
            return 0;
        }
        return calculateDiscountPrice(giftProduct);
    }

    private static int calculateDiscountPrice(Map<String, Integer> giftProduct) {
        int discountPrice = 0;
        Map<String, String> items = ItemsMap.create();
        for (String product : giftProduct.keySet()) {
            Items item = Items.valueOf(items.get(product));
            int totalPrice = item.totalPrice(giftProduct.get(product));
            discountPrice += totalPrice;
        }
        return discountPrice;
    }
}
