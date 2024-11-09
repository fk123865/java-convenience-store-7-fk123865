package store.domain.util.receipt;

import store.domain.Items;
import store.domain.util.Formatting;
import store.domain.util.ItemsMap;

import java.text.NumberFormat;
import java.util.Map;

import static store.domain.util.Formatting.*;

public class OrderReceipt {

    private OrderReceipt(){}

    public static String create(Map<String, Integer> order){
        StringBuilder sb = new StringBuilder();
        Map<String, String> items = ItemsMap.create();

        for (String orderProduct : order.keySet()) {
            String ItemName = items.get(orderProduct);
            Items item = Items.valueOf(ItemName);
            int totalPrice = item.totalPrice(order.get(orderProduct));
            String receipt = String.format(String.valueOf(FORMATTING), orderProduct, order.get(orderProduct), formatting(totalPrice));
            sb.append(receipt);
        }
        return sb.toString();
    }

    private static String formatting(int totalPrice) {
        return NumberFormat.getInstance().format(totalPrice);
    }
}
