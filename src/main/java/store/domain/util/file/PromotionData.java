package store.domain.util.file;

import store.domain.Promotion;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class PromotionData {

    private static final String DATA_DELIMITER = ",";
    private static final String DATE_DELIMITER = "-";

    private PromotionData() {}

    public static Map<String, Promotion> create(String filePath) throws FileNotFoundException {
        List<String> fileData = getFileData(filePath);
        return createPromotionData(fileData);
    }

    private static List<String> getFileData(String filePath) throws FileNotFoundException {
        List<String> fileData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));

        while (scanner.hasNextLine()) {
            fileData.add(scanner.nextLine());
        }

        return fileData;
    }

    private static Map<String, Promotion> createPromotionData(List<String> fileData) {
        Map<String, Promotion> promotions = new HashMap<>();
        for (int i = 0; i < fileData.size(); i++) {
            if (i == 0) {
                continue;
            }
            String data = fileData.get(i);
            String[] materials = data.split(DATA_DELIMITER);
            promotions.put(materials[0], createPromotion(materials));
        }
        return promotions;
    }

    private static Promotion createPromotion(String[] materials) {
        String name = materials[0];
        int buy = Integer.parseInt(materials[1]);
        int get = Integer.parseInt(materials[2]);
        LocalDateTime startDate = parseDate(materials[3]);
        LocalDateTime endDate = parseDate(materials[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }

    private static LocalDateTime parseDate(String materials) {
        String[] dates = materials.split(DATE_DELIMITER);
        return LocalDateTime.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]), 0,0);
    }
}
