package store.domain;

import java.time.LocalDateTime;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean checkData(LocalDateTime now) {
        return startDate.isBefore(now) && endDate.isAfter(now) || startDate.isEqual(now) || endDate.isEqual(now);
    }

    public boolean checkGift(int orderQuantity) {
        return orderQuantity == this.buy || orderQuantity % (this.buy + this.get) == this.buy;
    }

    public int giftCount(int orderQuantity) {
        int giftCount = 0;
        while (orderQuantity > this.buy) {
            giftCount += get;
            orderQuantity -= (this.buy + this.get);
        }
        return giftCount;
    }

    public boolean minBuy(int orderQuantity) {
        return orderQuantity >= this.buy;
    }

    public int remainCount(int orderQuantity) {
        while (orderQuantity > this.buy) {
            orderQuantity -= (this.buy + this.get);
        }
        return orderQuantity;
    }

    public String getName() {
        return name;
    }
}
