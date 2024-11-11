package store.domain.util.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MembershipDiscountTest {

    @Test
    @DisplayName("할인된 금액을 계산한다.")
    void discountTest1() {
        int discount = MembershipDiscount.discount(1000);

        assertThat(discount).isEqualTo(300);
    }

    @Test
    @DisplayName("최대 할인 금액은 8000원을 넘을 수 없다.")
    void discountTest2() {
        int discount = MembershipDiscount.discount(1_000_000);

        assertThat(discount).isEqualTo(8000);
    }
}