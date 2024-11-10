package store.domain.util.receipt;

import store.domain.util.discount.EventDiscount;
import store.domain.util.discount.MembershipDiscount;

import java.text.NumberFormat;
import java.util.Map;

import static store.domain.util.Formatting.*;

public class ResultReceipt {

    private int totalDiscount = 0;

    public String create(Map<String, Integer> giftProduct, int totalQuantity, int totalPrice, int generalTotalPrice) {
        StringBuilder sb = new StringBuilder();
        sb.append(createString(totalQuantity, totalPrice));
        sb.append(createString(giftProduct));
        sb.append(createString(generalTotalPrice));
        sb.append(createGoodSumOfMoney(totalPrice));
        return sb.toString();
    }

    private String createString(int totalQuantity, int totalPrice) {
        return formatting("총구매액", String.valueOf(totalQuantity), totalPrice, false);
    }

    private String createString(Map<String, Integer> giftProduct) {
        int discount = EventDiscount.discount(giftProduct);
        totalDiscount -= discount;
        if (giftProduct.isEmpty()) {
            discount = 0;
        }
        return formatting("행사할인", "", discount, true);
    }

    private String createString(int totalPrice) {
        int discount = MembershipDiscount.discount(totalPrice);
        totalDiscount -= discount;
        return formatting("멤버십할인", "", discount,true);
    }

    private String createGoodSumOfMoney(int totalPrice) {
        int money = totalPrice + totalDiscount;
        totalDiscount = 0;
        return formatting("내실돈", "", money, false);
    }

    private String formatting(String name, String quantity, int price, boolean discount) {
        String formatNumber = NumberFormat.getInstance().format(price);
        if (discount) {
            formatNumber = "-" + formatNumber;
        }
        return String.format(String.valueOf(FORMATTING), name, quantity, formatNumber);
    }
}
