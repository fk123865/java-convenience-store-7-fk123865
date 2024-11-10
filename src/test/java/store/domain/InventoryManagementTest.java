package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.error.ErrorMessage;
import store.validator.OrderValidator;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static store.error.ErrorMessage.*;

class InventoryManagementTest {

    @Test
    @DisplayName("재고에 없는 상품을 입력하면 예외를 발생한다.")
    void exceptionTest() {
        List<Product> products = List.of(new Product("콜라", 1000, 10, null));
        Map<String, Integer> order = OrderValidator.validate("[사이다-1]");
        InventoryManagement inventory = new InventoryManagement(products, order);

        assertThatThrownBy(() -> inventory.calculate())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(IS_DOESNT_EXIST.toString());
    }
}