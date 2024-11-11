package store.model;

import store.domain.InventoryManagement;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.util.file.ProductData;
import store.domain.util.file.PromotionData;
import store.domain.util.file.WriteProductData;
import store.validator.OrderValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Store {

    private List<Product> products;
    private final Map<String, Promotion> promotions;
    private Map<String, Integer> order;

    public Store() throws FileNotFoundException {
        promotions = PromotionData.create("src/main/resources/promotions.md");
        products = ProductData.create("src/main/resources/products.md", promotions);
    }

    public void sendOrder(String order) {
        this.order = OrderValidator.validate(order);
    }

    public String calculateTotal() {
        InventoryManagement inventory = new InventoryManagement(products, order);
        inventory.calculate();
        try {
            WriteProductData.write(products, "src/main/resources/products.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inventory.writeReceipt();
    }

    public List<Product> getProducts() {
        return products;
    }
}
