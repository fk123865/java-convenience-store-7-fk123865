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
            Store store = init();
            execute(store);
            if (retry().equals("n")) {
                return;
            }
        }
    }

    private void execute(Store store) {
        output.printProducts(store.getProducts());
        while (true) {
            try {
                store.sendOrder(input.readOrder());
                String receipt = store.calculateTotal();
                output.printOrderReceipt(receipt);
                return;
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
}
