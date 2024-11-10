package store.domain.util.receipt;

import store.domain.util.Formatting;

import java.util.Map;

public class GiftReceipt {

    public String create(Map<String, Integer> giftProduct) {
        StringBuilder sb = new StringBuilder();
        for (String product : giftProduct.keySet()) {;
            sb.append(String.format(String.valueOf(Formatting.FORMATTING), product,giftProduct.get(product), ""));
        }
        return sb.toString();
    }
}
