package javafxapplication.ui.addAuthor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafxapplication.database.DatabaseHandler;

public class AddAuthorController implements Initializable
{

    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private TextField category;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    
    DatabaseHandler handler;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        handler = DatabaseHandler.getInstance();
    }    

    @FXML
    private void addAuthor(ActionEvent event)
    {
        String aName = name.getText();
        String aID = id.getText();
        String aCategory = category.getText();
        
        
        if(aName.isEmpty() || aID.isEmpty() || aCategory.isEmpty() ) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        
        String query = "INSERT INTO AUTHOR VALUES ("
                + "'" + aName + "',"
                + "'" + aID + "',"
                + "'" + aCategory + "'"
                + ")";
        System.out.println(query);
        
        if (handler.execAction(query)) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        } else //Error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
    }
    
}
