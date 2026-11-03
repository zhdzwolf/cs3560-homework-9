package FlyweightFiles;

import java.util.HashMap;
import java.util.Map;

public class CharacterPropertiesFactory {

    private static final Map<String, CharacterProperties> propertiesMap =
            new HashMap<>();

    public static CharacterProperties getCharacterProperties(
            String font, String color, int size) {
        String key = font + "--" + color + "--" + size;

        if (!propertiesMap.containsKey(key)) {
            propertiesMap.put(
                    key, new CharacterProperties(font, color, size)
            );

            System.out.println("Created new properties: " + key);
        } else {
            System.out.println("Existing properties: " + key);
        }

        return propertiesMap.get(key);
    }

    public static int getTotalProperties() {
        return propertiesMap.size();
    }

    public static void clearForTesting() {
        propertiesMap.clear();
    }

}
