package store.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.error.ErrorMessage;
import store.validator.OrderValidator;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderValidatorTest {

    @Test
    @DisplayName("주문이 2개인 경우를 테스트한다.")
    void orderTest1() {
        assertOrderTest("[콜라-10],[사이다 - 3]", 2);
    }

    @Test
    @DisplayName("주문이 3개인 경우를 테스트한다.")
    void orderTest2() {
        assertOrderTest("[콜라 - 10],[사이다-3],[오렌지주스-1]", 3);
    }

    @ParameterizedTest
    @DisplayName("주문의 형식이 잘못되면 예외를 발생한다.")
    @ValueSource(strings = {"[콜라-10][사이다-3]", "콜라-10,사이다-3", "[콜라10],[사이다3]", "[콜라-10,사이다3]","[콜라]-10",""})
    void orderExceptionTest1(String input) {
        assertExceptionTest(input, ErrorMessage.IS_INCORRECT_FORM);
    }

    @ParameterizedTest
    @DisplayName("주문 갯수가 숫자가 아니면 예외가 발생한다.")
    @ValueSource(strings = {"[콜라-?]","[ - ]", "[콜라-a]", "[콜라-가]"})
    void orderExceptionTest2(String input) {
        assertExceptionTest(input, ErrorMessage.IS_INCORRECT_FORM);
    }

    void assertOrderTest(String input, int size) {
        // give
        //when
        Map<String, Integer> order = OrderValidator.validate(input);

        //then
        assertThat(order).hasSize(size);
    }

    void assertExceptionTest(String input, ErrorMessage message) {
        // when, then
        assertThatThrownBy(() -> OrderValidator.validate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(message.toString());
    }
}
