package javafxapplication.ui.searchBook;

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

public class BookSearchController implements Initializable {

    
    DatabaseHandler databasehandler;
    @FXML
    private TextField bookID;
    @FXML
    private ListView<String> idBookList;
    @FXML
    private TextField bookTitle;
    @FXML
    private ListView<String> titleBookList;
    @FXML
    private TextField bookCategory;
    @FXML
    private ListView<String> categoryBookList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databasehandler = DatabaseHandler.getInstance();
    }    

    @FXML
    private void idBookInfo(ActionEvent event) {
        ObservableList<String> bookData = FXCollections.observableArrayList();
        String bookId = bookID.getText();
        String qu = "SELECT DISTINCT BOOK.id, title, publisherName, copies, category"
                + " FROM BOOK, AUTHOR WHERE BOOK.id = '" +bookId + "' AND BOOK.id = AUTHOR.id";
        ResultSet rs = databasehandler.execQuery(qu);
        
        try {
            while(rs.next())
            {
                bookData.add("**BOOK DETAILS**");
                bookData.add("Book ID \t\t\t: " + bookId);
                bookData.add("Title   \t\t\t: " + rs.getString("title"));
                bookData.add("Category \t\t\t: " + rs.getString("category"));
                bookData.add("Publisher \t\t\t: " + rs.getString("publisherName"));
                bookData.add("Copies \t\t\t: " + rs.getString("copies"));
                
                String query = "SELECT name FROM BOOK, AUTHOR WHERE BOOK.id = '" +bookId + "' AND BOOK.id = AUTHOR.id";
                ResultSet rs1 = databasehandler.execQuery(query);
                while(rs1.next())
                {
                   bookData.add("**Authors** ");
                  bookData.add("Name  \t\t\t: " + rs1.getString("name"));
                }
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BookSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        idBookList.getItems().setAll(bookData);
    }

    @FXML
    private void titleBookInfo(ActionEvent event) {
        ObservableList<String> bookData = FXCollections.observableArrayList();
        String bookName = bookTitle.getText();
        String qu = "SELECT DISTINCT BOOK.id, title, publisherName, copies, category"
                + " FROM BOOK, AUTHOR WHERE BOOK.title = '" +bookName + "' AND BOOK.id = AUTHOR.id";
        ResultSet rs = databasehandler.execQuery(qu);
        
        try {
            while(rs.next())
            {
                bookData.add("**BOOK DETAILS**");
                bookData.add("Book ID \t\t\t: " + rs.getString("id"));
                bookData.add("Title   \t\t\t: " + rs.getString("title"));
                bookData.add("Category \t\t\t: " + rs.getString("category"));
                bookData.add("Publisher \t\t\t: " + rs.getString("publisherName"));
                bookData.add("Copies \t\t\t: " + rs.getString("copies"));
                String query = "SELECT name FROM BOOK, AUTHOR WHERE BOOK.title = '" +bookName + "' AND BOOK.id = AUTHOR.id";
                ResultSet rs1 = databasehandler.execQuery(query);
                while(rs1.next())
                {
                   bookData.add("**Authors** ");
                   bookData.add("Name  \t\t\t: " + rs1.getString("name"));
                }
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BookSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        titleBookList.getItems().setAll(bookData);
    }

    @FXML
    private void bookCategoryInfo(ActionEvent event) {
        ObservableList<String> bookData = FXCollections.observableArrayList();
        String bookType = bookCategory.getText();
        String qu = "SELECT DISTINCT BOOK.id, title, publisherName, copies, category"
                + " FROM BOOK, AUTHOR WHERE category = '" +bookType + "' AND BOOK.id = AUTHOR.id";
        ResultSet rs = databasehandler.execQuery(qu), rs1;
        
        try {
            while(rs.next())
            {
                bookData.add("**BOOK DETAILS**");
                bookData.add("Book ID \t\t\t: " + rs.getString("id"));
                bookData.add("Title   \t\t\t: " + rs.getString("title"));
                bookData.add("Category \t\t\t: " + rs.getString("category"));
                bookData.add("Publisher \t\t\t: " + rs.getString("publisherName"));
                bookData.add("Copies \t\t\t: " + rs.getString("copies"));
                
                String query = "SELECT name FROM BOOK, AUTHOR WHERE category = '" +bookType + "' AND BOOK.id = AUTHOR.id";
                rs1 = databasehandler.execQuery(query);
                while(rs1.next())
                {
                   bookData.add("Authors: ");
                  bookData.add("Name  \t\t\t: " + rs1.getString("name"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        categoryBookList.getItems().setAll(bookData);
    }
    
}
