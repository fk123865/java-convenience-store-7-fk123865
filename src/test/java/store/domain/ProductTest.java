package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.error.ErrorMessage;
import store.validator.OrderValidator;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @Test
    @DisplayName("각 상품의 재고 수량을 고려하여 결제 가능하면 현재 상품의 재고에서 차감하여 수량을 관리한다.")
    void productsTest2() {
        Product product = getProduct();
        Map<String, Integer> order = OrderValidator.validate("[콜라-10]");

        int quantity = product.availableForBuy(order);

        assertThat(quantity).isEqualTo(0);
    }

    @Test
    @DisplayName("각 상품의 재고 수량을 고려하여 결제가 불가능하면 예외를 발생시킨다.")
    void productsTest3() {
        Product product = getProduct();
        Map<String, Integer> order = OrderValidator.validate("[콜라-100]");

        assertThatThrownBy(() -> product.availableForBuy(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.IS_EXCESS_QUANTITY.toString());
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
