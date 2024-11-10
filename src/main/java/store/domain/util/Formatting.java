package store.domain.util;

public enum Formatting {

    // todo 위치 포맷팅 하기
    FORMATTING("%-15s  %-7s %-15s\n");

    private final String format;

    Formatting(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
