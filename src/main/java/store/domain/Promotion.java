package store.domain;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean checkData(LocalDate now) {
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    public int checkGift(int buy) {
        if (this.buy > buy) {
            return 0;
        }
        return buy / this.buy;
    }

    public String getName() {
        return name;
    }
}