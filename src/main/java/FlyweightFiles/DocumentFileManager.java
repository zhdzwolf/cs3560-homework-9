package FlyweightFiles;

import java.io.*;

public class DocumentFileManager {

    public static void saveDocument(TextDocument document,
                                    String filename) {
        try (ObjectOutputStream output =
                new ObjectOutputStream(
                        new FileOutputStream(filename))) {
            output.writeObject(document);

            System.out.println("File saved to: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static TextDocument loadDocument(String filename) {
        try (ObjectInputStream input =
                new ObjectInputStream(
                        new FileInputStream(filename))) {
            TextDocument document = (TextDocument) input.readObject();

            System.out.println("File loaded from: " + filename);

            return document;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());

            return null;
        }
    }

}
