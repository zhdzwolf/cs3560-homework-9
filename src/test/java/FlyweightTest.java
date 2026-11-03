import FlyweightFiles.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FlyweightTest {

    private TextDocument document;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        CharacterPropertiesFactory.clearForTesting();
        document = new TextDocument();
    }

    // CHARACTER PROPERTIES TESTS

    @Test
    public void testGetFont() {
        CharacterProperties properties =
                new CharacterProperties("Arial", "Red", 12);

        assertEquals("Arial", properties.getFont());
    }

    @Test
    public void testGetColor() {
        CharacterProperties properties =
                new CharacterProperties("Arial", "Red", 12);

        assertEquals("Red", properties.getColor());
    }

    @Test
    public void testGetSize() {
        CharacterProperties properties =
                new CharacterProperties("Arial", "Red", 12);

        assertEquals(12, properties.getSize());
    }

    @Test
    public void testToString() {
        CharacterProperties properties =
                new CharacterProperties("Arial", "Red", 12);

        assertEquals(
                "Font: Arial, Color: Red, Size: 12",
                properties.toString()
        );
    }

    // FLYWEIGHT FACTORY TESTS

    @Test
    public void testSamePropertiesReuseSameObject() {
        CharacterProperties first =
                CharacterPropertiesFactory.getCharacterProperties(
                        "Arial", "Red", 12);

        CharacterProperties second =
                CharacterPropertiesFactory.getCharacterProperties(
                        "Arial", "Red", 12);

        assertSame(first, second);
    }

    @Test
    public void testDifferentPropertiesCreateDifferentObjects() {
        CharacterProperties first =
                CharacterPropertiesFactory.getCharacterProperties(
                        "Arial", "Red", 12);

        CharacterProperties second =
                CharacterPropertiesFactory.getCharacterProperties(
                        "Calibri", "Blue", 14);

        assertNotSame(first, second);
    }

    @Test
    public void testUniquePropertyCount() {
        CharacterPropertiesFactory.getCharacterProperties(
                "Arial", "Red", 12);

        CharacterPropertiesFactory.getCharacterProperties(
                "Calibri", "Blue", 14);

        CharacterPropertiesFactory.getCharacterProperties(
                "Verdana", "Black", 16);

        CharacterPropertiesFactory.getCharacterProperties(
                "Arial", "Green", 18);

        assertEquals(
                4,
                CharacterPropertiesFactory.getTotalProperties()
        );
    }

    // CHARACTER TESTS

    @Test
    public void testGetCharacterValue() {
        CharacterProperties properties =
                new CharacterProperties("Arial", "Red", 12);

        FlyweightFiles.Character character =
                new FlyweightFiles.Character('H', properties);

        assertEquals('H', character.getValue());
    }

    @Test
    public void testSetCharacterValue() {
        CharacterProperties properties =
                new CharacterProperties("Arial", "Red", 12);

        FlyweightFiles.Character character =
                new FlyweightFiles.Character('H', properties);

        character.setValue('A');

        assertEquals('A', character.getValue());
    }

    @Test
    public void testGetCharacterProperties() {
        CharacterProperties properties =
                new CharacterProperties("Arial", "Red", 12);

        FlyweightFiles.Character character =
                new FlyweightFiles.Character('H', properties);

        assertSame(properties, character.getProperties());
    }

    @Test
    public void testSetCharacterProperties() {
        CharacterProperties original =
                new CharacterProperties("Arial", "Red", 12);

        CharacterProperties updated =
                new CharacterProperties("Calibri", "Blue", 14);

        FlyweightFiles.Character character =
                new FlyweightFiles.Character('H', original);

        character.setProperties(updated);

        assertSame(updated, character.getProperties());
    }

    // TEXT DOCUMENT TESTS

    @Test
    public void testAddCharacter() {
        document.addCharacter('H', "Arial", "Red", 12);

        assertEquals(1, document.getCharacters().size());
    }

    @Test
    public void testAddMultipleCharacters() {
        document.addCharacter('H', "Arial", "Red", 12);
        document.addCharacter('e', "Calibri", "Blue", 14);
        document.addCharacter('l', "Verdana", "Black", 16);

        assertEquals(3, document.getCharacters().size());
    }

    @Test
    public void testEditCharacterValue() {
        document.addCharacter('H', "Arial", "Red", 12);

        document.editCharacter(0, 'A', "Arial", "Red", 12);

        assertEquals(
                'A',
                document.getCharacters().get(0).getValue()
        );
    }

    @Test
    public void testEditCharacterProperties() {
        document.addCharacter('H', "Arial", "Red", 12);

        document.editCharacter(0, 'H', "Calibri", "Blue", 14);

        assertEquals(
                "Calibri",
                document.getCharacters().get(0)
                        .getProperties().getFont()
        );
    }

    @Test
    public void testHelloWorldDocument() {
        String word = "HelloWorld";

        for (int i = 0; i < word.length(); i++) {
            document.addCharacter(
                    word.charAt(i), "Arial", "Red", 12);
        }

        StringBuilder result = new StringBuilder();

        for (FlyweightFiles.Character character : document.getCharacters()) {
            result.append(character.getValue());
        }

        assertEquals("HelloWorld", result.toString());
    }

    // FILE SAVE AND LOAD TESTS

    @Test
    public void testSaveDocument() throws Exception {
        document.addCharacter('H', "Arial", "Red", 12);

        String filename =
                tempDir.resolve("testDocument.dat").toString();

        DocumentFileManager.saveDocument(document, filename);

        assertTrue(Files.exists(Path.of(filename)));
    }

    @Test
    public void testLoadDocument() {
        document.addCharacter('H', "Arial", "Red", 12);

        String filename =
                tempDir.resolve("testDocument.dat").toString();

        DocumentFileManager.saveDocument(document, filename);

        TextDocument loaded =
                DocumentFileManager.loadDocument(filename);

        assertNotNull(loaded);
    }

    @Test
    public void testLoadedDocumentContents() {
        document.addCharacter('H', "Arial", "Red", 12);
        document.addCharacter('e', "Calibri", "Blue", 14);
        document.addCharacter('l', "Verdana", "Black", 16);

        String filename =
                tempDir.resolve("testDocument.dat").toString();

        DocumentFileManager.saveDocument(document, filename);

        TextDocument loaded =
                DocumentFileManager.loadDocument(filename);

        StringBuilder result = new StringBuilder();

        for (FlyweightFiles.Character character : loaded.getCharacters()) {
            result.append(character.getValue());
        }

        assertEquals("Hel", result.toString());
    }

}
