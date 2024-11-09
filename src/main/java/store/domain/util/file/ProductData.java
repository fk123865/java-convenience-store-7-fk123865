package store.domain.util.file;

import store.domain.Product;
import store.domain.Promotion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductData {

    private static final String DATA_DELIMITER = ",";

    private ProductData(){}


    public static List<Product> create(String filePath, Map<String, Promotion> promotionData) throws FileNotFoundException {
        List<String> fileData = getFileData(filePath);
        return createPromotionProductData(fileData, promotionData);
    }

    private static List<String> getFileData(String filePath) throws FileNotFoundException {
        List<String> fileData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));

        while (scanner.hasNextLine()) {
            fileData.add(scanner.nextLine());
        }

        return fileData;
    }

    private static List<Product> createPromotionProductData(List<String> fileData, Map<String, Promotion> promotionData) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < fileData.size(); i++) {
            if (i == 0) {
                continue;
            }
            String data = fileData.get(i);
            String[] materials = data.split(DATA_DELIMITER);
            Promotion promotion = getPromotion(materials[3], promotionData);
            products.add(createProduct(materials, promotion));
        }
        return products;
    }

    private static Product createProduct(String[] materials, Promotion promotion) {
        String name = materials[0];
        int price = Integer.parseInt(materials[1]);
        int quantity = Integer.parseInt(materials[2]);
        return new Product(name, price, quantity, promotion);
    }

    private static Promotion getPromotion(String hasPromotion, Map<String, Promotion> promotionData) {
        if (hasPromotion.equals("null")) {
            return null;
        }
        return promotionData.get(hasPromotion);
    }
}
