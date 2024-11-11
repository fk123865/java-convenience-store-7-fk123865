package store.domain.util.receipt;

import store.domain.Items;
import store.domain.util.Formatting;
import store.domain.util.ItemsMap;

import java.text.NumberFormat;
import java.util.Map;

import static store.domain.util.Formatting.*;

public class OrderReceipt {

    public String create(Map<String, Integer> order){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(String.valueOf(FORMATTING), "상품명", "수량", "금액"));
        Map<String, String> items = ItemsMap.create();
        for (String orderProduct : order.keySet()) {
            createOrderReceipt(order, orderProduct, items, sb);
        }
        return sb.toString();
    }

    private void createOrderReceipt(Map<String, Integer> order, String orderProduct, Map<String, String> items, StringBuilder sb) {
        String ItemName = items.get(orderProduct);
        Items item = Items.valueOf(ItemName);
        int totalPrice = item.totalPrice(order.get(orderProduct));
        String receipt = String.format(String.valueOf(FORMATTING), orderProduct, order.get(orderProduct), formatting(totalPrice));
        sb.append(receipt);
    }

    private String formatting(int totalPrice) {
        return NumberFormat.getInstance().format(totalPrice);
    }
}
