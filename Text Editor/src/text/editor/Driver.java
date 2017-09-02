
package text.editor;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *The main class that controls the running of the program
 * 
 * @author 455085
 */
public class Driver extends Application{

    /**
     * This is used to allow the user interface to interact with the driver
     */
    protected UserInterface UI = new UserInterface();

    /**
     *This is used to allow the driver to access the file operations
     */
    protected FileOperations FO = new FileOperations();

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        initalize(primaryStage);
         
    }
    
    /**
     *This initalises the user interface when the program is launched
     * 
     * @param primaryStage this is the stage on which the user interface shall be built
     */
    public void initalize(Stage primaryStage){
        
       UI.CreateUI(primaryStage, this);
        
    }
    
    /**
     * 
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     *This returns the text from a file to the user interface
     * 
     * @return string
     */
    public String load(){
        return FO.loadFile();
    }
  
    /**
     *This passes the users text to the file operations object to be saved
     * 
     * @param text this is the text form the text area
     * @throws IOException if there is an error with text area 
     */
    public void save(String text) throws IOException{
        FO.saveFile(text);
    }
    
    /**
     *This passes the users text to the file operations object to be saved as the user types
     * 
     * @param text this is the text from the text area
     * @throws IOException if there is an error with the file
     */
    public void autoSave(String text) throws IOException{
        FO.autoSave(text);
    }
}
