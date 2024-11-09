package store.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.error.ErrorMessage;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static store.error.ErrorMessage.*;

class YesOrNoValidatorTest {

    @Test
    @DisplayName("입력이 두 글자 이상이면 예외를 발생한다.")
    void validateTest1() {
        String input = "yn";

        assertExceptionTest(input, IS_NOT_OWN_LENGTH);
    }

    @DisplayName("알파벳이 아니면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "?", "가"})
    void validateTest2(String input) {
        assertExceptionTest(input, IS_NOT_ALPHABET);
    }

    @Test
    @DisplayName("입력이 y와 n이 아니면 예외를 발생한다.")
    void validateTest3() {
        String input = "a";

        assertExceptionTest(input, IS_NOT_YES_OR_NO);
    }
    void assertExceptionTest(String input, ErrorMessage message) {
        assertThatThrownBy(() -> YesOrNoValidator.validate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(message.toString());
    }

}