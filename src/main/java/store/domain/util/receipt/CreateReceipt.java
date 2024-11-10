package store.domain.util.receipt;

import store.domain.Items;
import store.domain.util.ItemsMap;

import java.util.Map;

public class CreateReceipt {

    private final static OrderReceipt orderReceipt = new OrderReceipt();
    private final static GiftReceipt giftReceipt = new GiftReceipt();
    private final static ResultReceipt resultReceipt = new ResultReceipt();

    private CreateReceipt() {}

    public static String create(Map<String, Integer> order, Map<String, Integer> giftProduct, int generalTotalPrice) {
        StringBuilder sb = new StringBuilder();
        sb.append("==============W 편의점================\n");
        sb.append(orderReceipt.create(order));
        if (!giftProduct.isEmpty()) {
            sb.append("=============증\t정===============\n");
            sb.append(giftReceipt.create(giftProduct));
        }
        sb.append("====================================\n");
        sb.append(resultReceipt.create(giftProduct, calculateTotalQuantity(order), calculateTotalPrice(order), generalTotalPrice));
        return sb.toString();
    }

    private static int calculateTotalQuantity(Map<String, Integer> order) {
        int totalQuantity = 0;
        for (Integer quantity : order.values()) {
            totalQuantity += quantity;
        }
        return totalQuantity;
    }

    private static int calculateTotalPrice(Map<String, Integer> order) {
        int totalPrice = 0;
        Map<String, String> items = ItemsMap.create();
        for (String orderProduct : order.keySet()) {
            String ItemName = items.get(orderProduct);
            Items item = Items.valueOf(ItemName);
            totalPrice += item.totalPrice(order.get(orderProduct));
        }
        return totalPrice;
    }
}
