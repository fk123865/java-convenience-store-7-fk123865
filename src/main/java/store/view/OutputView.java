package store.view;

import store.domain.Product;

import java.util.List;

public class OutputView {

    public void printProducts(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();
    }

    public void printOrderReceipt(String receipt) {
        System.out.println(receipt);
    }

    public void printExceptionMessage(String message) {
        System.out.print(message);
        System.out.println(" 다시 입력해 주세요.");
    }
}
