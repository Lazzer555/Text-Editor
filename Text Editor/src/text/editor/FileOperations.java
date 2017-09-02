/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;

/**
 *This class handles all of the file related methods
 * 
 * @author 455085
 */
public class FileOperations {

    /**
     * This is the currently open file
     */
    protected File file;

    /**
     *This is a filechooser for saving and loading files
     */
    protected FileChooser fileChooser = new FileChooser();
    
    /**
     *This method takes the text passed in by
     * the driver and saves it to a file using 
     * the file chooser.
     * 
     * @param text This is the text taken from the text area
     * @throws IOException if there is an error with the file
     */
    public void saveFile(String text) throws IOException{
        
        //Create the file chooser 
        JFileChooser Chooser = new JFileChooser();
        Chooser.setDialogTitle("Specify a file to save");   
 
        //Disaplay the file chooser
        int userSelection = Chooser.showSaveDialog(null);
 
        //When the user approves the file write the text to the file
        //using a file writer
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            this.file = Chooser.getSelectedFile();
            
            try (FileWriter fw = new FileWriter(this.file + ".txt")) {
                fw.write(text);
            }
        }
    }
    
    /**
     *This gets the text from as file using
     *a filechooser and returns it
     * 
     * @return string 
     */
    public String loadFile(){
        
        //Create an empty string
        String contents = "";
        
        //Display the file chooser
        file = fileChooser.showOpenDialog(null);
        
        //Create a file reader to read text from the file
        //Create a scanner to read from the file readers stream
        try {
            FileReader fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            
            //Fill the string with each line from the text file
            do{
                contents = contents + sc.nextLine();
            }while(sc.hasNextLine());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contents;
    }
    
    /**
     *This saves the file with the text passed
     * in from the driver class
     * 
     * @param text This is the text taken from the text area
     * @throws IOException if there is an error with the file
     */
    public void autoSave(String text) throws IOException {
       
        //Writes the text to a file already created
        try (FileWriter fw = new FileWriter(this.file + ".txt")) {
                fw.write(text);
        }

        
    } 
    
}
