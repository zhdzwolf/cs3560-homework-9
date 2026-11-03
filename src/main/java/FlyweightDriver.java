import FlyweightFiles.*;

public class FlyweightDriver {

    public static void main(String[] args) {

        TextDocument document = new TextDocument();

        System.out.println("Creating Text Document...");

        document.addCharacter('H', "Arial", "Red", 12);
        document.addCharacter('e', "Calibri", "Blue", 14);
        document.addCharacter('l', "Verdana", "Black", 16);
        document.addCharacter('l', "Arial", "Red", 12);
        document.addCharacter('o', "Calibri", "Blue", 14);

        document.addCharacter('W', "Verdana", "Black", 16);
        document.addCharacter('o', "Arial", "Red", 12);
        document.addCharacter('r', "Calibri", "Blue", 14);
        document.addCharacter('l', "Verdana", "Black", 16);
        document.addCharacter('d', "Arial", "Green", 18);

        document.addCharacter('C', "Verdana", "Black", 16);
        document.addCharacter('S', "Verdana", "Black", 16);
        document.addCharacter('3', "Arial", "Red", 12);
        document.addCharacter('5', "Arial", "Red", 12);
        document.addCharacter('6', "Calibri", "Blue", 14);
        document.addCharacter('0', "Calibri", "Blue", 14);

        document.displayDocument();

        document.displayCharacterDetails();

        System.out.println("\nTotal unique property objects: " +
                CharacterPropertiesFactory.getTotalProperties());

        DocumentFileManager.saveDocument(
                document, "quake3arena.txt");

        TextDocument loadedDocument =
                DocumentFileManager.loadDocument("quake3arena.txt");

        if (loadedDocument != null) {

            System.out.println("\nLoaded Document:");

            loadedDocument.displayDocument();

            loadedDocument.displayCharacterDetails();
        }
    }
}
