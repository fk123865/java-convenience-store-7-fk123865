package store.domain.util.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class EventDiscountTest {

    @Test
    @DisplayName("증점품이 1개 일 때 할인 금액을 계산한다.")
    void discountTest1() {
        Map<String, Integer> giftProduct = new HashMap<>();
        giftProduct.put("콜라", 1);

        int discountPrice = EventDiscount.discount(giftProduct);

        assertThat(discountPrice).isEqualTo(1000);
    }

    @Test
    @DisplayName("증점품이 2개 일 때 할인 금액을 계산한다.")
    void discountTest2() {
        Map<String, Integer> giftProduct = new HashMap<>();
        giftProduct.put("콜라", 1);
        giftProduct.put("사이다", 2);

        int discountPrice = EventDiscount.discount(giftProduct);

        assertThat(discountPrice).isEqualTo(3000);
    }

}