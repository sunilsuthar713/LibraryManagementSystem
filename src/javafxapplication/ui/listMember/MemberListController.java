
package javafxapplication.ui.listMember;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxapplication.database.DatabaseHandler;

public class MemberListController implements Initializable {
    
    ObservableList<MemberListController.Student> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> idCol;
    @FXML
    private TableColumn<Student, String> mobileCol;
    @FXML
    private TableColumn<Student, String> emailCol;
    @FXML
    private TableColumn<Student, String> fineCol;
    @FXML
    private TableColumn<Student, String> borrowedCol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initCol();
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void initCol() {
    
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        fineCol.setCellValueFactory(new PropertyValueFactory<>("fine"));
        borrowedCol.setCellValueFactory(new PropertyValueFactory<>("borrowed"));
    }
    
    private void loadData() throws SQLException {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM STUDENT";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()){
                String name = rs.getString("name");
                String id = rs.getString("id");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String fine = rs.getString("fine");
                String borrowed = rs.getString("borrowed");

                list.add(new Student(name, id, mobile, email,fine,borrowed) {});
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
    
    public static class Student
    {
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
        private final SimpleStringProperty fine;
        private final SimpleStringProperty borrowed;
        
        Student(String name, String id, String mobile, String email, String fine,String borrowed) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
            this.fine = new SimpleStringProperty(fine);
            this.borrowed = new SimpleStringProperty(borrowed);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }
        
        public String getFine() {
            return fine.get();
        }
        
        public String getBorrowed() {
            return borrowed.get();
        }
    }
}
