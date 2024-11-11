package store.error;

public enum ErrorMessage {
    IS_INCORRECT_FORM("올바르지 않은 형식으로 입력했습니다."),
    IS_EXCESS_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다."),
    IS_DOESNT_EXIST("존재하지 않는 상품입니다."),
    IS_INVALID_INPUT("잘못된 입력입니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
