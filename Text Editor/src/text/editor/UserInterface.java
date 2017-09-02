/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text.editor;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *This class handles the user interface 
 * 
 * @author 455085
 */
public class UserInterface{
    
    //Create the variables for the user interface
    private Stage primaryStage;
    private Scene scene;
    private MenuBar menu;
    private Driver driver;
    private Boolean hasSaved = false;
    
    /**
     *This creates the user interface and also
     * handles events such as keyboard shortcuts
     * and menu items being clicked on
     * 
     * @param primaryStage this is the stage on which the user interface shall be built
     * @param driver this is the driver to allow the user interface to interact with it
     */
    public void CreateUI(Stage primaryStage, Driver driver) {
               
        //sets the stage
        this.primaryStage = primaryStage;
        this.driver = driver;
        
        //create the menubar
        menu = new MenuBar();
        
        //Create the input dialog box for the find feature
        TextInputDialog searchInput = new TextInputDialog("");
        searchInput.setTitle("Find");
        searchInput.setContentText("Please enter your search term: ");
      
        //Create the text area
        CustomTextArea textArea = new CustomTextArea();
        textArea.setWrapText(true);
        
        //Create the controls to populate the menubar
        Menu fileMenu = new Menu("File");
        Menu fontMenu = new Menu("Font");
        Menu fontSizeMenu = new Menu("Font Size");
        Menu editMenu = new Menu("Edit");
        
        //Menu items for the file menu option
        MenuItem newFile = new MenuItem("New File");
        
        //Clears the text area when the new file menu option is selected 
        newFile.setOnAction((ActionEvent e ) -> {
            textArea.setText("");
            hasSaved = false;
        });
        
        //Create the save file menu option
        MenuItem saveFile = new MenuItem("Save");
        
        //Triggers the save function when the menu item is selected
        saveFile.setOnAction((ActionEvent e) -> {
            try {
                driver.save(textArea.getText());
                hasSaved = true;
            }catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save Error");
                alert.setHeaderText("There was a problem saving your file, Please try again.");
                alert.showAndWait();
            }
        });
        
        //Create the load file menu item
        MenuItem loadFile = new MenuItem("Load");
        
        //Triggers the load function when the menu item is selected
        loadFile.setOnAction((ActionEvent e) -> {
            textArea.setText(driver.load());
        });
        
        //Create te print file menu item
        MenuItem printFile = new MenuItem("Print");
        
        //Create the file statistics menu item
        MenuItem fileStats = new MenuItem("Statistics");
        
        //create the quit menu item
        MenuItem quit = new MenuItem("Quit");
        
        //Closes the application when the quit menu item is selected
        quit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });
        
        //Create the undo menu item
        MenuItem undo = new MenuItem("Undo");
        
        //Create the redo menu item
        MenuItem redo = new MenuItem("Redo");
        
        //Create the cut menu item
        MenuItem cut = new MenuItem("Cut");
        
        //Trigger the cut feature when cut menu item is selected
        cut.setOnAction((ActionEvent e) -> {
           textArea.cut();
        });
        
        //Create the copy menu item
        MenuItem copy = new MenuItem("Copy");
        
        //Trigger the copy feature when the copy menu item is selected
        copy.setOnAction((ActionEvent e) -> {
           textArea.copy();
        });
        
        //Create the paste menu item
        MenuItem paste = new MenuItem("Paste");
        
        //Trigger the paste feature when the paste menu item is selected
        paste.setOnAction((ActionEvent e) -> {
           textArea.paste();
        });
        
        //Create the select all menu item
        MenuItem selectAll = new MenuItem("Select All");
        
        //Trigger the select all menu item
        selectAll.setOnAction((ActionEvent e) -> {
           textArea.selectAll();
        });
        
        //Create the find menu item
        MenuItem find = new MenuItem("Find");
        
        //Triggers the find feature when find menu item is selected
        find.setOnAction((ActionEvent e) -> {
            String result = searchInput.showAndWait().get();
            textArea.find(result);
            searchInput.close();
        });
        
        //Create the find next menu Item
        MenuItem findNext = new MenuItem("Find Next");
        
        //Triggers the find next feature when find menu item is selected
        findNext.setOnAction((ActionEvent e) -> {
            textArea.findNext();
        });
        
        //Populate the file mnenu
        fileMenu.getItems().addAll(newFile, saveFile, loadFile, printFile, fileStats, quit);
        
        //Populate the edit menu
        editMenu.getItems().addAll(undo, redo, cut, copy, paste, selectAll, find, findNext);
        
        //Create and populate fonts menu items for font menu option
        final String availableFonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        
        //Sets the array to store all the menuitems
        MenuItem[] fontNameArray = new MenuItem[availableFonts.length];
        
        //Populates the menu items array
        for(int i = 0; i < availableFonts.length; i++){
            //for use with event handler
            String tempFont = availableFonts[i];
            
            //Create the menu item
            fontNameArray[i] = new MenuItem(tempFont);
            
            //Changes the font when a user clicks on a menu item
            fontNameArray[i].setOnAction((ActionEvent e) -> {
                textArea.changeFont(tempFont);
                fontMenu.setText("Font: " + tempFont);
            });
            
            //adds menu item to font menu
            fontMenu.getItems().add(fontNameArray[i]);
        }
        
        //Create and populate menu items for font size
        MenuItem[] fontSizeArray = new MenuItem[60];
        
        //Used to populate the font size menu
        for(int i = 1; i < 60; i+=2){
            
            //used for the error handler
            int tempSize = i+1;
            
            //creates the menu item
            fontSizeArray[i] = new MenuItem("" + (i+1));
            
            //Changes the font size when the user selects a menu item
            fontSizeArray[i].setOnAction((ActionEvent e) -> {
               textArea.changeFontSize(tempSize);
               fontSizeMenu.setText("Size: " + tempSize);
            });
            
            //Adds menu item to the font size menu
            fontSizeMenu.getItems().add(fontSizeArray[i]);
        }

        //Populate the menubar with all the menus
        menu.getMenus().addAll(fileMenu,editMenu,fontMenu, fontSizeMenu);
        
        //Creates a borderpane layout to be used as the main container
        BorderPane layout = new BorderPane();
         
        //Creates a new scene and adds the layout to it
        this.scene = new Scene(layout, 700, 500);    

        //Create the colorpicker to be used for font colour selection
        ColorPicker colourPicker = new ColorPicker();
        colourPicker.setOnAction((ActionEvent e) -> {
            textArea.changeFontColour(colourPicker.getValue());
        });
        
        textArea.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            
            if(hasSaved == true){
                try {
                    driver.autoSave(textArea.getText());
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Auto Save Error");
                    alert.setHeaderText("There was a problem saving your file.");
                    alert.showAndWait();
                }
            }
            
            if(e.isShortcutDown() && e.getCode() == KeyCode.F){
               String result = searchInput.showAndWait().get();
               textArea.find(result);
               searchInput.close();
            }else if(e.getCode() == KeyCode.F3){
                textArea.findNext();
            }else if(e.isShortcutDown() && e.getCode() == KeyCode.O){
                textArea.setText(driver.load());
            }else if(e.isShortcutDown() && e.getCode() == KeyCode.N){
                textArea.setText("");
                hasSaved = false;
            }else if(e.isShortcutDown() && e.getCode() == KeyCode.S){
                try {
                    driver.save(textArea.getText());
                    hasSaved = true;
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Save Error");
                    alert.setHeaderText("There was a problem saving your file, Please try again.");
                    alert.showAndWait();
                }
            }
        });
    
        //This is used to add the colour picker next to the menu bar
        GridPane topMenu = new GridPane();
        topMenu.addColumn(0, menu);
        topMenu.addColumn(1, colourPicker);
        
        //Add the components to the boarder pane
        layout.setTop(topMenu);
        layout.setCenter(textArea);
        
        //Set the title of the window
        primaryStage.setTitle("Text Editor");
        
        //Sets the primary stage to be resizeable
        primaryStage.setMinHeight(520);
        primaryStage.setMinWidth(700);
        primaryStage.setResizable(true);
        
        //Adds the scene to the stage and then displays
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
}
