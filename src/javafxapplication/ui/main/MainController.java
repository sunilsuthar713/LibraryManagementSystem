package javafxapplication.ui.main;

import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafxapplication.database.DatabaseHandler;

public class MainController implements Initializable {
    
    DatabaseHandler databasehandler;
    @FXML
    private TextField bookIDInput;
    @FXML
    private Text bookName;
    @FXML
    private Text bookStatus;
    @FXML
    private TextField memberIDInput;
    @FXML
    private Text memberName;
    @FXML
    private Text contact;
    @FXML
    private TextField bookID;
    @FXML
    private ListView<String> issueDataList;
    Boolean isReadyForSubmission;
    @FXML
    private TextField studentID;
    @FXML
    private HBox book_info;
    @FXML
    private HBox stud_info;
    @FXML
    private Button issue;
    @FXML
    private HBox bookInfo;
    @FXML
    private Button renewButton;
    @FXML
    private Button submitButton;
    @FXML
    private TextField updateBookID;
    @FXML
    private TextField updateBookCopies;
    @FXML
    private Button updateButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(book_info,1);
        JFXDepthManager.setDepth(stud_info,1);
        JFXDepthManager.setDepth(bookInfo,1);
        JFXDepthManager.setDepth(issueDataList,1);
        JFXDepthManager.setDepth(renewButton,2);
        JFXDepthManager.setDepth(submitButton,2);
        databasehandler = DatabaseHandler.getInstance();
    }    

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/javafxapplication/ui/addBook/add_book.fxml","Add New Book");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        loadWindow("/javafxapplication/ui/listBook/book_list.fxml","Book Table");
    }

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/javafxapplication/ui/addMember/add_member.fxml","Add New Student");

    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        loadWindow("/javafxapplication/ui/listMember/member_list.fxml","Student Table");
    }

    @FXML
    private void loadAddAuthor(ActionEvent event) {
        loadWindow("/javafxapplication/ui/addAuthor/add_author.fxml","Add New Author");

    }

    private void loadAuthorTable(ActionEvent event) {
        loadWindow("/javafxapplication/ui/listAuthor/author_list.fxml","Author Table");
    }

    @FXML
    private void loadAddPublisher(ActionEvent event) {
        loadWindow("/javafxapplication/ui/addPublisher/add_publisher.fxml","Add New Publisher");

    }

    private void loadPublisherTable(ActionEvent event) {
        loadWindow("/javafxapplication/ui/listPublisher/publisher_list.fxml","Publisher Table");
    }
    
    @FXML
    private void loadIssueTable(ActionEvent event) {
        loadWindow("/javafxapplication/ui/listIssue/issueList.fxml","Issue Table");
    }
    
    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        String id = bookIDInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id = '" + id + "'";
        ResultSet rs = databasehandler.execQuery(qu);
        Boolean flag = false;
        try {
            while(rs.next())
            {
                String bName = rs.getString("title");
                Boolean bStatus = rs.getBoolean("isAvail");
                
                bookName.setText(bName);
                String status = (bStatus)?"Available":"Not Available";
                bookStatus.setText(status);
                flag = true;
            }
            if(!flag)
            {
                bookName.setText("No Such Book Available");
                bookStatus.setText("NULL");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        String id = memberIDInput.getText();
        String qu = "SELECT * FROM STUDENT WHERE id = '" + id + "'";
        ResultSet rs = databasehandler.execQuery(qu);
        Boolean flag = false;
        try {
            while(rs.next())
            {
                String mName = rs.getString("name");
                String mContact = rs.getString("email");
                memberName.setText(mName);
                contact.setText(mContact);
                flag = true; 
            }
            if(!flag)
            {
                memberName.setText("Not Registered");
                contact.setText("NULL");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) 
    { 
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            Calendar call = Calendar.getInstance();
            call.add(Calendar.DATE, 14);
            String studentId = memberIDInput.getText();
            String bookId = bookIDInput.getText();
            int copies=1;
            String qu = "SELECT copies FROM BOOK WHERE id = '" + bookId + "'";
            ResultSet rs = databasehandler.execQuery(qu);
            try{
            while(rs.next())
            {
                copies = rs.getInt("copies");
                System.out.println(" Inside : " + copies);
            } 
            } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(" Copies : " + copies);
            
            databasehandler.IssueError(copies);
            
            int borrowed=0;
            qu = "SELECT borrowed FROM STUDENT WHERE id = '" + studentId + "'";
            rs = databasehandler.execQuery(qu);
            try{
            while(rs.next())
            {
                borrowed = rs.getInt("borrowed");
                System.out.println(" Inside borrowed : " + borrowed);
            } 
            } catch (SQLException ex) 
            {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(" Borrowed : " + borrowed);
            if( borrowed == 3)
                databasehandler.OverIssue(studentId,borrowed);
            System.out.println("Procedure executed");
            if(copies == 0)
            {
                String trigger = "UPDATE PUBLISHER SET phone = 'Not available' WHERE name='NOBODY'";
                databasehandler.Trigger(trigger);
            }
            
            if(copies != 0 & borrowed <3)
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Issue Operation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to issue the book " + bookName.getText() + "\n to " + memberName.getText() + " ?");
            
            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                String str = "INSERT INTO ISSUE VALUES ("
                        + "'" + bookId + "',"
                        + "'" + studentId + "',"
                        + "'" + dateFormat.format(cal.getTime()) + "',"
                        + "'" + dateFormat.format(call.getTime()) + "')";
                String str2 = "UPDATE BOOK SET copies=copies-1 WHERE id = '" + bookId + "' AND copies > 0";
                String str3 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookId + "' AND copies <= 0 ";
                String str4 = "UPDATE STUDENT SET borrowed = borrowed + 1 WHERE id = '" + studentId + "'";
                  
                if (databasehandler.execAction(str) && databasehandler.execAction(str2) && databasehandler.execAction(str4) && databasehandler.execAction(str3)) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Success");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book Issue Completed");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("FAILED");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Issue Operation Failed");
                    alert1.showAndWait();
                }
            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Cancelled");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue Operation cancelled");
                alert1.showAndWait();
            }
            }
        } 
    

    @FXML
    private void loadBookInfo2(ActionEvent event) {
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission = false;

        String id = bookID.getText();
        String studentId = studentID.getText();
        System.out.println(id + "\t" + studentId);
        String qu = "SELECT * FROM ISSUE WHERE bookID = '" + id + "' AND studentID = '" + studentId + "'";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            while (rs.next()) {
                String mBookID = id;
                String mStudentID = rs.getString("studentID");
                Date mIssueDate = rs.getDate("issueDate");
                Date mReturnDate = rs.getDate("returnDate");
                issueData.add("Issue Date :" + mIssueDate);
                issueData.add("Return Date :" + mReturnDate);

                issueData.add("Book Information:-");
                qu = "SELECT * FROM BOOK, AUTHOR WHERE BOOK.ID = AUTHOR.ID AND BOOK.ID = '" + mBookID + "'";
                ResultSet r1 = databasehandler.execQuery(qu);

                while (r1.next()) {
                    issueData.add("\tBook ID        :" + r1.getString("id"));
                    issueData.add("\tBook Name      :" + r1.getString("title"));
                    issueData.add("\tBook Publisher :" + r1.getString("publisherName"));
                    issueData.add("\tBook Author    :" + r1.getString("name"));
                    issueData.add("\tBook Category  :" + r1.getString("category"));
                }
                qu = "SELECT * FROM STUDENT WHERE ID = '" + mStudentID + "'";
                r1 = databasehandler.execQuery(qu);
                issueData.add("Student Information:-");

                while (r1.next()) {
                    issueData.add("\tName           :" + r1.getString("name"));
                    issueData.add("\tMobile         :" + r1.getString("mobile"));
                    issueData.add("\tEmail          :" + r1.getString("email"));
                }
                
                issueData.add("Publisher Information:-");
                qu = "SELECT * FROM PUBLISHER, BOOK WHERE PUBLISHER.name=BOOK.publisherName AND id = '" + mBookID + "'";
                r1 = databasehandler.execQuery(qu);
                
                while(r1.next())
                {
                    issueData.add("\tName           :" + r1.getString("name"));     
                    issueData.add("\tAddress        :" + r1.getString("address"));     
                    issueData.add("\tPhone          :" + r1.getString("phone"));     
                }

                isReadyForSubmission = true;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        issueDataList.getItems().setAll(issueData);
    }

    @FXML
    private void loadSubmissionOp(ActionEvent event) throws ParseException {
        if(!isReadyForSubmission)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to submit");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Submission Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to return the book ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance(); 
            String id = bookID.getText();
            String studentId = studentID.getText();
            int days=1;
            String qu = "SELECT returnDate FROM ISSUE WHERE bookID = '" + id + "' AND studentID = '" + studentId + "'";
            ResultSet rs = databasehandler.execQuery(qu);
            try {
                while(rs.next()) {
                    String dt1 = dateFormat.format(cal.getTime());
                    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dt1);
                    System.out.println(date1);
                    Date d2 = rs.getDate("returnDate");
                    long diff = date1.getTime() - d2.getTime();
                    days = (int) ( diff / (1000 * 60 * 60 * 24));
                    System.out.println(" format : " + d2);
                    System.out.println("Days : " + days);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Days outside : " + days);
            {
                databasehandler.fineCalculate(days, studentId);
                System.out.println("Procedure executed");
            }
            
            String ac1 = "DELETE FROM ISSUE WHERE bookID = '" + id + "' AND studentID = '" + studentID.getText() + "'";
            String ac2 = "UPDATE BOOK SET ISAVAIL = TRUE, copies=copies+1 WHERE ID = '" + id + "'";
            String ac3 = "UPDATE STUDENT SET borrowed=borrowed-1 WHERE ID = '" + studentID.getText() + "'";
            
            if (databasehandler.execAction(ac1) && databasehandler.execAction(ac2) && databasehandler.execAction(ac3) ) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Submitted");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed");
                alert.setHeaderText(null);
                alert.setContentText("Submission Has Been Failed");
                alert.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelled");
            alert1.setHeaderText(null);
            alert1.setContentText("Submission Operation cancelled");
            alert1.showAndWait();
        }
    }

    @FXML
    private void loadRenewOp(ActionEvent event) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Calendar call = Calendar.getInstance();
        call.add(Calendar.DATE, 14);
        if (!isReadyForSubmission) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to renew");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Renew Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to renew the book ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String ac = "UPDATE ISSUE SET issueDate = '"+ dateFormat.format(cal.getTime()) +"', returnDate = '" +dateFormat.format(call.getTime()) + "' WHERE BOOKID = '" + bookID.getText() + "'";
            System.out.println(ac);
            if (databasehandler.execAction(ac)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Renewed");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Renew  Failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelled");
            alert1.setHeaderText(null);
            alert1.setContentText("Renew Operation cancelled");
            alert1.showAndWait();
        }
    }

    @FXML
    private void searchBook(ActionEvent event) {
        loadWindow("/javafxapplication/ui/searchBook/bookSearch.fxml","Search Book");
    }

    @FXML
    private void searchStudent(ActionEvent event) {
        loadWindow("/javafxapplication/ui/searchStudent/studentSearch.fxml","Search Student");
    }

    @FXML
    private void updateCopies(ActionEvent event) {
        String bookId = updateBookID.getText();
        int num = Integer.parseInt(updateBookCopies.getText());
        String query = "UPDATE BOOK SET copies = copies+" + num + " WHERE id = '" + bookId + "'";
        databasehandler.execAction(query);
    }


    
}
