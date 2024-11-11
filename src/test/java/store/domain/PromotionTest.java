package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionTest {

    @Test
    @DisplayName("프로모션 기간에 포함되어 있는지 확인한다.")
    void checkDateTest() {
        Promotion promotion = setUp();

        boolean result = promotion.checkData(LocalDateTime.of(2024,1,31,0,0));

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("프로모션 혜택을 맞기 위해 최소 구매 수량을 구입하였는지 확인한다.")
    void checkGiftTest() {
        Promotion promotion = setUp();

        boolean result = promotion.checkGift(1);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("프로모션 혜택으로 증정 받는 상품의 갯수를 반환한다.")
    void giftCountTest() {
        Promotion promotion = setUp();

        int giftCount = promotion.giftCount(10);

        assertThat(giftCount).isEqualTo(3);
    }

    @Test
    @DisplayName("프로모션 혜택으로도 몇 개가 부족한지 갯수를 반환한다.")
    void remainCountTest() {
        Promotion promotion = setUp();

        int remainCount = promotion.remainCount(7);

        assertThat(remainCount).isEqualTo(1);
    }

    public Promotion setUp() {
        String name = "탄산2+1";
        int buy = 2;
        int get = 1;
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1,0,0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31,0,0);
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
