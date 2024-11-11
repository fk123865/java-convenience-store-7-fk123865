package store.domain.util.discount;

public class MembershipDiscount {

    private MembershipDiscount() {}

    public static int discount(int totalPrice) {
        if (totalPrice == 0) {
            return 0;
        }
        double discountPrice = totalPrice * 0.3;
        discountPrice = Math.min(discountPrice, 8000.0);
        return (int) discountPrice;
    }
}
