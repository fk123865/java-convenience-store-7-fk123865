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

    public void printOrderReceipt(String orderReceipt) {
        System.out.println("==============W 편의점================");
        System.out.println(orderReceipt);
    }

    public void printGiftReceipt(String giftReceipt) {
        System.out.println("=============증\t정===============");
        System.out.println(giftReceipt);
    }

    public void printResultReceipt(String resultReceipt) {
        System.out.println("====================================");
        System.out.println(resultReceipt);
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }
}
