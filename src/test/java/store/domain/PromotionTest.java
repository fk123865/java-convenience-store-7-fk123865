package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionTest {

    @Test
    @DisplayName("프로모션 객체는 프로모션 이름, 구매 수량, 증정 수량, 시작 일, 종료 일을 가진다.")
    void promotionTest1() {
        Promotion promotion = setUp();

        assertThat(promotion).isNotNull();
    }

    @Test
    @DisplayName("오늘 날짜가 해당 프로모션 기간 내에 포함되어 있으면 True를 반환한다.")
    void promotionTest2() {
        Promotion promotion = setUp();

        boolean result = promotion.checkData(LocalDate.of(2024,1,31));

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("오늘 날짜가 해당 프로모션 기간 내에 포함되어 있으면 false를 반환한다.")
    void promotionTest3() {
        Promotion promotion = setUp();

        boolean result = promotion.checkData(LocalDate.of(2023,12,31));

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("프로모션 상품을 2개 구매하면 1개를 증정한다.")
    void promotionTest4() {
        Promotion promotion = setUp();

        int giftCount = promotion.checkGift(2);

        assertThat(giftCount).isEqualTo(1);
    }

    @Test
    @DisplayName("프로모션 상품을 최소 주문 개수보다 작게 구매하면 증정 상품이 없다.")
    void promotionTest5() {
        Promotion promotion = setUp();

        int giftCount = promotion.checkGift(1);

        assertThat(giftCount).isEqualTo(0);
    }

    @Test
    @DisplayName("프로모션 상품을 4개 구매하면 2개를 증정한다.")
    void promotionTest6() {
        Promotion promotion = setUp();

        int giftCount = promotion.checkGift(4);

        assertThat(giftCount).isEqualTo(2);
    }


    public Promotion setUp() {
        String name = "탄산2+1";
        int buy = 2;
        int get = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
