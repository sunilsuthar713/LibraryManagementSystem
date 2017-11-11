package javafxapplication.ui.addMember;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafxapplication.database.DatabaseHandler;

public class AddMemberController implements Initializable 
{
    
    DatabaseHandler handler;
    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        handler = DatabaseHandler.getInstance();
    }    

    @FXML
    private void addStudent(ActionEvent event)
    {
        String mName = name.getText();
        String mStudentID = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();
        
        if(mName.isEmpty() || mStudentID.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty() ) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        
        String qu = "INSERT INTO STUDENT VALUES ("
                + "'" + mStudentID + "',"
                + "'" + mName + "',"
                + "'" + mMobile + "',"
                + "'" + mEmail + "',"
                + "" + "0" + ","
                + " 0 )";
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
    private void cancel(ActionEvent event) {
    }
    
}
