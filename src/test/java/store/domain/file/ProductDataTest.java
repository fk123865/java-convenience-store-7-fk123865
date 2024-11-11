package store.domain.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.util.file.ProductData;
import store.domain.util.file.PromotionData;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class ProductDataTest {

    @Test
    void createProductDataTest() throws FileNotFoundException {
        Map<String, Promotion> promotionData = PromotionData.create("src/main/resources/promotions.md");

        List<Product> products = ProductData.create("src/main/resources/products.md", promotionData);

        Assertions.assertThat(products).hasSize(18);
    }
}
