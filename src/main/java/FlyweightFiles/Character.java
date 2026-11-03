package FlyweightFiles;

import java.io.Serializable;

public class Character implements Serializable {

    private char value;
    private CharacterProperties properties;

    public Character(char value, CharacterProperties properties) {
        this.value = value;
        this.properties = properties;
    }

    public char getValue() {
        return value;
    }

    public CharacterProperties getProperties() {
        return properties;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public void setProperties(CharacterProperties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return value + " [" + properties + "]";
    }

}
