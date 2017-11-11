package javafxapplication.ui.listIssue;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import javafxapplication.ui.listBook.BookListController;

public class IssueListController implements Initializable {

    ObservableList<Issue> list = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Issue> tableView;
    @FXML
    private TableColumn<Issue,String> returnCol;
    @FXML
    private TableColumn<Issue,String> bookIDCol;
    @FXML
    private TableColumn<Issue,String> studentIDCol;
    @FXML
    private TableColumn<Issue,String> issueDateCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initCol();
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(IssueListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initCol() {
    
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        studentIDCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        issueDateCol.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        returnCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    private void loadData() throws SQLException {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM ISSUE";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()){
                String bookID = rs.getString("bookID");
                String studentID = rs.getString("studentID");
                Date issue = rs.getDate("issueDate");
                Date returns = rs.getDate("returnDate");
                String issueDate = issue.toString();
                String returnDate = returns.toString();
                
                list.add(new Issue(bookID, studentID, issueDate, returnDate));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
    
    public static class Issue {
        private final SimpleStringProperty bookID;
        private final SimpleStringProperty studID;
        private final SimpleStringProperty issueDate;
        private final SimpleStringProperty returnDate;        
        Issue(String bookID, String studID, String issueDate, String returnDate)
        {
            this.bookID = new SimpleStringProperty(bookID);
            this.studID = new SimpleStringProperty(studID);
            this.issueDate = new SimpleStringProperty(issueDate);
            this.returnDate = new SimpleStringProperty(returnDate);
        }
        
        public String getBookID() {
            return bookID.get();
        }
        
        public String getStudentID(){
            return studID.get();
        }
        
        public String getissueDate(){
            return issueDate.get();
        }

        public String getreturnDate(){
            return returnDate.get();
        }
        
    }
    
}
