package store.error;

public enum Error {

    ERROR("[ERROR] ");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
