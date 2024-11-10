package store.controlller;

import store.model.Store;
import store.validator.YesOrNoValidator;
import store.view.InputView;
import store.view.OutputView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StoreController {

    private final InputView input;
    private final OutputView output;

    public StoreController() {
        input = new InputView();
        output = new OutputView();
    }

    public void run() {
        while (true) {
            Store store = execute();
            if (retry().equals("n")) {
//                store.returnProduct();
                return;
            }
        }
    }

    private String retry() {
        while (true) {
            try {
                String userInput = input.readRetry();
                return YesOrNoValidator.validate(userInput);
            } catch (IllegalArgumentException e) {
                output.printExceptionMessage(e.getMessage());
            }
        }
    }

    private Store execute() {
        Store store = init();
        output.printProducts(store.getProducts());
        while (true) {
            try {
                store.sendOrder(input.readOrder());
                String result = store.calculateTotal();
                output.printOrderReceipt(result);
                return store;
            } catch (IllegalArgumentException e) {
                output.printExceptionMessage(e.getMessage());
            }
        }
    }

    private Store init() {
        try {
            return new Store();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
