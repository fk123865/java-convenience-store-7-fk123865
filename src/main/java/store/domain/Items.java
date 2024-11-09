package store.domain;

import java.text.NumberFormat;

public enum Items {

    COKE("콜라", 1_000),
    SPRITE("사이다", 1_000),
    ORANGE_JUICE("오렌지주스", 1_800),
    SPARKLING_WATER("탄산수",1_200),
    WATER("물", 500),
    VITAMIN_WATER("비타민워터", 1_500),
    POTATO_CHIP("감자칩", 1_500),
    CHOCOLATE_BAR("초코바",1_200),
    ENERGY_BAR("에너지바", 2_000),
    JUNGSICK_LUNCH("정식도시락", 6_400),
    CUP_RAMEN("컵라면", 1_700);

    private final String name;
    private final int price;

    Items(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String totalPrice(int orderQuantity) {
        return NumberFormat.getInstance().format((long) orderQuantity * price);
    }

    public String getName() {
        return name;
    }
}
