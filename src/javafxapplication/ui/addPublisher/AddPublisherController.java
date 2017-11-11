package javafxapplication.ui.addPublisher;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafxapplication.database.DatabaseHandler;

public class AddPublisherController implements Initializable 
{

    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
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
    private void addPublisher(ActionEvent event) 
    {
        String pName = name.getText();
        String pAddress = address.getText();
        String pPhone = phone.getText();
        
        if(pName.isEmpty() || pAddress.isEmpty() || pPhone.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        
        String qu = "INSERT INTO PUBLISHER VALUES ("
                + "'" + pName + "',"
                + "'" + pAddress + "',"
                + "'" + pPhone + "'"
                + ")";
        System.out.println(qu);
        
        if (handler.execAction(qu)) 
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
    private void cancel(ActionEvent event) 
    {
    }
    
}
