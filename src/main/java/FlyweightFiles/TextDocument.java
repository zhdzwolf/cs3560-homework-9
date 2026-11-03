package FlyweightFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TextDocument implements Serializable {

    private final List<Character> characters;

    public TextDocument() {
        characters = new ArrayList<>();
    }

    public void addCharacter(char value, String font,
                             String color, int size) {
        CharacterProperties properties = new CharacterProperties(font, color, size);
        CharacterPropertiesFactory.getCharacterProperties(font, color, size);

        characters.add(new Character(value, properties));
    }

    public void editCharacter(int index, char newValue,
                              String font, String color, int size) {
        if (index >= 0 && index < characters.size()) {
            CharacterProperties properties =
                    CharacterPropertiesFactory.getCharacterProperties(
                            font, color, size);

            Character character = characters.get(index);

            character.setValue(newValue);
            character.setProperties(properties);

            System.out.println("Edit successful.");
        } else {
            System.out.println("Invalid character index.");
        }
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void displayDocument() {
        System.out.println("\nDocument:");

        for (Character character : characters) {
            System.out.print(character.getValue());
        }

        System.out.println();
    }

    public void displayCharacterDetails() {
        System.out.println("\nCharacter Details:");

        for (int i = 0; i < characters.size(); i++) {
            System.out.println(
                    "Index " + i + ": " +
                    characters.get(i)
            );
        }
    }

}
