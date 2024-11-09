package store.domain.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;
import store.domain.util.file.PromotionData;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionDataTest {

    @Test
    @DisplayName("promotions.md 파일을 가지고 Promotion 객체를 보관하는 리스트를 생성한다.")
    void createPromotionDataTest() throws FileNotFoundException {
        // when
        Map<String, Promotion> promotions = PromotionData.create("src/main/resources/promotions.md");

        //then
        assertThat(promotions).hasSize(3);
    }
}
