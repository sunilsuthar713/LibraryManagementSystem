
package javafxapplication.ui.searchStudent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafxapplication.database.DatabaseHandler;

public class StudentSearchController implements Initializable {

    DatabaseHandler databasehandler;
    @FXML
    private TextField studentID;
    @FXML
    private ListView<String> studentList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databasehandler = DatabaseHandler.getInstance();
    }    

    @FXML
    private void idBookInfo(ActionEvent event) {
        ObservableList<String> studentData = FXCollections.observableArrayList();
        String studentId = studentID.getText();
        String qu = "SELECT * FROM STUDENT WHERE id='" + studentId + "'";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            while(rs.next())
            {
                studentData.add("**STUDENT DETAILS**");
                studentData.add("ID   \t\t\t\t : " + rs.getString("id"));
                studentData.add("Name \t\t\t : " + rs.getString("name"));
                studentData.add("Email \t\t\t : " + rs.getString("email"));
                studentData.add("Contact \t\t\t : " + rs.getString("mobile"));
                studentData.add("Books borrowed \t : " + rs.getString("borrowed"));
                studentData.add("Fine \t\t\t\t : " + rs.getString("fine"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        studentList.getItems().setAll(studentData);
    }
    
}
