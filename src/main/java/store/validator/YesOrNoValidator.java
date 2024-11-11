package store.validator;

import static store.error.Error.*;
import static store.error.ErrorMessage.*;

public class YesOrNoValidator {

    private static final String NOT_ALPHABET = "[^a-zA-Z]";
    private static final int INPUT_LENGTH = 1;
    private YesOrNoValidator() {
    }

    public static String validate(String userInput) {
        String input = removeBlank(userInput);
        incorrectFormat(input);
        String inputToLowerCase = input.toLowerCase();
        if (inputToLowerCase.equals("y") || inputToLowerCase.equals("n")) {
            return inputToLowerCase;
        }
        throw new IllegalArgumentException(ERROR.toString() + IS_INVALID_INPUT);
    }

    private static void incorrectFormat(String input) {
        if (input.length() > INPUT_LENGTH) {
            throw new IllegalArgumentException(ERROR.toString() + IS_INVALID_INPUT);
        }
        if (input.matches(NOT_ALPHABET)) {
            throw new IllegalArgumentException(ERROR.toString() + IS_INVALID_INPUT);
        }
    }

    private static String removeBlank(String input) {
        return input.trim();
    }
}