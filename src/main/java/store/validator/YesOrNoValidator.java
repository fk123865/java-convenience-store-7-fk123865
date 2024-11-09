package store.validator;

import static store.error.Error.*;
import static store.error.ErrorMessage.*;

public class YesOrNoValidator {

    private static final String NOT_ALPHABET = "[^a-zA-Z]";

    private YesOrNoValidator() {
    }

    public static String validate(String userInput) throws IllegalArgumentException {
        String input = removeBlank(userInput);
        if (input.length() > 1) {
            throw new IllegalArgumentException(ERROR.toString() + IS_NOT_OWN_LENGTH);
        }
        if (input.matches(NOT_ALPHABET)) {
            throw new IllegalArgumentException(ERROR.toString() + IS_NOT_ALPHABET);
        }
        String inputLowerCase = input.toLowerCase();
        if (inputLowerCase.equals("y") || inputLowerCase.equals("n")) {
            return inputLowerCase;
        }
        throw new IllegalArgumentException(ERROR.toString() + IS_NOT_YES_OR_NO);
    }

    private static String removeBlank(String input) {
        return input.trim();
    }
}