package controller;

import java.io.*;

/**
 * Class to help the saving and loading of data
 */
public class SerializationController {
    
    public static final String ANI_LIST_FILE = "src/main/java/savedAniLists/savedAniList.ser";
    
    /**
     * Writes an object to the specified file path
     * @param filePath = the path to write to
     * @param object   = the object to write
     * @param <T>      = the type of the object
     */
    public static <T extends Serializable> void serialize (String filePath, T object) {
        
        try {
            
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            
            objectOutputStream.close();
            fileOutputStream.close();
            
        } catch (IOException|IllegalArgumentException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Stores a java object of type T from a serialized file
     * @param filePath = the path to the file to read from
     * @param <T>      = the type of object to be read in
     * @return An object of the specified type
     */
    public static <T extends Serializable> T deserialize (String filePath) {
        
        try {
            
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            
            T object = (T) objectInputStream.readObject();
            
            objectInputStream.close();
            fileInputStream.close();
            
            return object;
            
        } catch (IOException|IllegalArgumentException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
        
    }
    
}
