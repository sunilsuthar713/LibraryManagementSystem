package javafxapplication.ui.addBook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafxapplication.database.DatabaseHandler;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.xml.transform.Result;

public class BookAddController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextField id;
    @FXML
    private TextField publisher;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    DatabaseHandler databasehandler;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField copies;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
            databasehandler = DatabaseHandler.getInstance();
        
    }    

    @FXML
    private void addBook(ActionEvent event) 
    {
        Result rs = null;
        String bookID = id.getText();
        String bookNumber = copies.getText();
        int bookCopies = Integer.valueOf(bookNumber);
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        
        if(bookID.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty() ) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        
        String qu = "INSERT INTO BOOK VALUES ("
                + "'" + bookID + "',"
                + "'" + bookName + "',"
                + "'" + bookPublisher + "',"
                + "" + bookCopies + ","
                + "" + "true" + ""
                + ")";
        System.out.println(qu);
        if (databasehandler.execAction(qu)) {
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
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
