package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.error.ErrorMessage;
import store.validator.OrderValidator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @Test
    @DisplayName("프로모션 재고인지 확인한다.")
    void isPromotionTest() {
        Product product = getProduct();

        boolean result = product.isPromotion();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("계산이 가능한지 여부를 테스트한다.")
    void paymentAvailableTest() {
        Product product = getProduct();

        boolean result = product.paymentAvailable(3);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("프로모션 날짜에 해당하는지 테스트한다.")
    void checkDateTest() {
        Product product = getProduct();

        boolean result = product.checkDate(LocalDate.of(2025, 1, 1));

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("일반 상품의 재고를 사용하면 일반 상품의 결제 금액을 반환한다.")
    void generalAvailableTest() {
        Product product = getProduct();

        int totalPrice = product.generalAvailableForBuy("콜라", 3);

        assertThat(totalPrice).isEqualTo(3000);
    }

    @Test
    @DisplayName("프로모션 재고를 사용하면 증정 상품에 등록된다.")
    void promotionAvailableTest() {
        Product product = getProduct();
        Map<String, Integer> giftProducts = new HashMap<>();
        Map<String, Integer> order = OrderValidator.validate("[콜라-3]");

        product.promotionAvailableForBuy("콜라", 3, giftProducts, order);

        assertThat(giftProducts.get("콜라")).isEqualTo(1);
    }


    private Product getProduct() {
        String name = "콜라";
        int price = 1000;
        int quantity = 10;
        Promotion promotion = getPromotion();
        return new Product(name, price, quantity, promotion);
    }

    private Promotion getPromotion() {
        PromotionTest promotionTest = new PromotionTest();
        return promotionTest.setUp();
    }
}
