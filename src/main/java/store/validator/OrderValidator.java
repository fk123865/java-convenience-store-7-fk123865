package store.validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static store.error.Error.ERROR;
import static store.error.ErrorMessage.*;

public class OrderValidator {

    private static final String ORDER_DELIMITER = ",";
    private static final String ITEM_START = "[";
    private static final String ITEM_END = "]";
    private static final String ITEM_DELIMITER = "-";
    private static final String REMOVE = "";

    private OrderValidator() {}

    public static Map<String, Integer> validate(String input) throws IllegalArgumentException {
        metItemSpecialCharacter(input);
        metFormatting(input);
        return createOrder(input);
    }

    private static void metItemSpecialCharacter(String input) {
        if (hasNotItemSpecialCharacter(input)) {
            throw new IllegalArgumentException(ERROR.toString() + IS_INCORRECT_FORM);
        }
    }

    private static boolean hasNotItemSpecialCharacter(String input) {
        return !input.contains(ITEM_START) || !input.contains(ITEM_END) || !input.contains(ITEM_DELIMITER);
    }

    private static void metFormatting(String input) {
        List<String> specialCharacterOrder = List.of(ITEM_START,ITEM_DELIMITER,ITEM_END,ORDER_DELIMITER);
        int metCount = 0;
        for (String string : input.split("")) {
            if (hasNotSpecialCharacter(string, specialCharacterOrder)) {
                continue;
            }
            validateFormatting(string, metCount, specialCharacterOrder);
            metCount++;
            if (metCount == specialCharacterOrder.size()) {
                metCount = 0;
            }
        }
    }

    private static boolean hasNotSpecialCharacter(String string, List<String> specialCharacterOrder) {
        return !specialCharacterOrder.contains(string);
    }

    private static void validateFormatting(String input, int metCount, List<String> specialCharacterOrder) {
        if (isOutOfOrder(input, metCount, specialCharacterOrder)) {
            throw new IllegalArgumentException(ERROR.toString() + IS_INCORRECT_FORM);
        }
    }

    private static boolean isOutOfOrder(String input, int metCount, List<String> specialCharacterOrder) {
        return !input.equals(specialCharacterOrder.get(metCount));
    }

    private static Map<String, Integer> createOrder(String input) {
        Map<String, Integer> order = new LinkedHashMap<>();
        for (String orders : input.split(ORDER_DELIMITER)) {
            String[] items = orders.replace(ITEM_START, REMOVE).replace(ITEM_END, REMOVE).split(ITEM_DELIMITER);
            putOrder(order, items);
        }
        return order;
    }

    private static void putOrder(Map<String, Integer> order, String[] items) {
        try {
            int orderQuantity = Integer.parseInt(items[1].trim());
            checkPositive(orderQuantity);
            order.put(items[0].trim(), orderQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR.toString() + IS_INCORRECT_FORM);
        }
    }

    private static void checkPositive(int orderQuantity) {
        if (orderQuantity <= 0) {
            throw new IllegalArgumentException(ERROR.toString() + IS_INCORRECT_FORM);
        }
    }
}