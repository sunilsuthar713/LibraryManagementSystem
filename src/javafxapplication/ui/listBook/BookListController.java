
package javafxapplication.ui.listBook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafxapplication.database.DatabaseHandler;

public class BookListController implements Initializable {

    
    ObservableList<Book> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availabilityCol;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> copiesCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initCol();
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initCol() {
    
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<>("copies"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisherName"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    private void loadData() throws SQLException {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM BOOK";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()){
                String titlex = rs.getString("title");
                String id = rs.getString("id");
                String publisher = rs.getString("publisherName");
                String copies = rs.getString("copies");
                Boolean avail = rs.getBoolean("isAvail");
                
                list.add(new Book(id, titlex, publisher, copies, avail));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
    
    public static class Book {
        private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty copies;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;
        
        Book(String id, String title, String publisher, String copies, Boolean avail) {
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.publisher = new SimpleStringProperty(publisher);
            this.copies = new SimpleStringProperty(copies);
            this.availability = new SimpleBooleanProperty(avail);
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getPublisher() {
            return publisher.get();
        }
        
        public String getCopies() {
            return copies.get();
        }

        public Boolean getAvailability() {
            return availability.get();
        }
    }
    
}
