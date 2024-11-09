package store.domain.util;

public enum Formatting {

    FORMATTING("%s              %d          %s\n");

    private final String format;

    Formatting(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
