/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text.editor;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *This is a custom text area class that extends
 * the Java FX text area
 * 
 * @author 455085
 */
public class CustomTextArea extends TextArea{

    /**
     *This stores text that is currently being copied
     */
    protected String tempString = "";

    /**
     *This stores the search term for the find and find next methods
     */
    protected String searchTerm = "";

    /**
     * This is used to keep track of the current index
     * for use in the find and find next methods
     */
    protected int index = -1;
    
    //This copies the selected text
    @Override
    public void copy(){
        
        //Gets the selected text and places it in the tempString variable
        tempString = this.getSelectedText();
    }
    
    //This is used to cut the selected text
    @Override
    public void cut(){
        
        //Gets the selected text and places it in the tempString variable
        tempString = this.getSelectedText();
        
        //Removes the highlighted text
        this.replaceText(this.getSelection().getStart(), this.getSelection().getEnd(), "");
    }
    
    //This is used to paste the text into the text area
    @Override
    public void paste(){
       
    //Paste the string stored in tempString
    this.insertText(this.getCaretPosition(), tempString);
    
    }
    
    /**
     *This is used to search the text area and
     * highlight a word based on the users search
     * term
     * 
     * @param searchInput this is the search term passed in from the user interface
     */
    public void find(String searchInput){
    
        //Sets the search term equal to the users input
        searchTerm = searchInput.toLowerCase();
        
        //sets the index of the first instance of the search term it finds
        index = this.getText().toLowerCase().indexOf(searchTerm, index);
        
        //Checks that the search tesrm is found
        if(index > -1){
            //Sets the caret to start of found word
            this.positionCaret(index);
            //Selects to the end of the found word
            this.selectEndOfNextWord();
            //Advances the index to the next character so it can move pass the current word
            index = index + 1;
        } else {
            //If there is no word found create an alert dialog box
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Find");
            alert.setHeaderText("The term you searched for was not found");
            alert.showAndWait();
            
            //Clears the search term
            searchTerm = "";
        }//end if
    }
    
    /**
     *This is used to find and highlight the next word
     * that the user has searched for
     */
    public void findNext(){
        
        System.out.println("Find Next Triggered");
        
        //Gets the index of the next word found
        index = this.getText().toLowerCase().indexOf(searchTerm, index);
        
        //If no next word found
        if(index == -1){
            
            // Sets the index back to the first word found
            index = this.getText().toLowerCase().indexOf(searchTerm, index);
            //Sets the caret to start of found word
            this.positionCaret(index);
            //Selects to the end of the found word
            this.selectEndOfNextWord();
            //Advances the index to the next character so it can move pass the current word
            index = index + 1;
        } else {
            //Sets the caret to start of found word
            this.positionCaret(index);
            //Selects to the end of the found word
            this.selectEndOfNextWord();
            //Advances the index to the next character so it can move pass the current word
            index = index + 1;
        }//end if
        
    }
    
    /**
     *This is used to change the text areas font
     * to the one that the user has selected
     * 
     * @param font this is the font passed in from the user interface 
     */
    public void changeFont(String font){
         
        //Sets the font to the one passed in by the User Interface
        this.setFont(Font.font(font, this.getFont().getSize()));
        
    }
    
    /**
     *This is used to change the size of the text
     * that the text in the text area displays
     * 
     * @param size this is the text size passed in from the user interface
     */
    public void changeFontSize(int size){
        
        //Gets the font and stores it in a temp variable
        String tempFont = this.getFont().getFamily();
        
        //Sets a new font using the tempFont variable and the size passed in
        this.setFont(Font.font(tempFont, size));
    }

    /**
     *This is used to change the colour of the text in the text area
     * 
     * @param colour this is the colour value passed in from the user interface
     */
    public void changeFontColour(Color colour) {
        //Creates a rgb string to be used with the css style
        String rgb = String.format("#%02X%02X%02X", (int)(colour.getRed() * 255 ), (int)(colour.getGreen() * 255 ), (int)(colour.getBlue() * 255 ) );
        
        //Styles the text using css
        this.setStyle("-fx-text-fill: " + rgb);
    }
    
}
