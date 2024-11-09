package store.domain.util;

import store.domain.Items;

import java.util.HashMap;
import java.util.Map;

public class ItemsMap {

    private ItemsMap() {}

    public static Map<String, String> create() {
        Map<String, String> items = new HashMap<>();
        for (Items item : Items.values()) {
            items.put(item.getName(), item.name());
        }
        return items;
    }
}
