package store.domain.util.discount;

import store.validator.YesOrNoValidator;
import store.view.InputView;
import store.view.OutputView;

public class MembershipDiscount {

    private MembershipDiscount() {}

    public static int discount(int totalPrice) {
        String input = discountInput();
        if (input.equals("n") || totalPrice == 0) {
            return 0;
        }
        double discountPrice = totalPrice * 0.3;
        if (discountPrice > 8000) {
            discountPrice = 8000.0;
        }
        return (int) discountPrice;
    }

    private static String discountInput() {
        InputView input = new InputView();
        OutputView output = new OutputView();
        while (true) {
            try {
                String userInput = input.readMembership();
                return YesOrNoValidator.validate(userInput);
            } catch (IllegalArgumentException e) {
                output.printExceptionMessage(e.getMessage());
            }
        }
    }
}
