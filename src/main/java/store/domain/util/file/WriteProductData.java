package store.domain.util.file;

import store.domain.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class WriteProductData {

    private WriteProductData() {}

    public static void write(List<Product> products, String filePath) throws IOException {
        File file = new File(filePath);

        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        pw.println("name,price,quantity,promotion");
        for (Product product : products) {
            pw.println(product.writeString());
        }
        pw.close();
    }
}
