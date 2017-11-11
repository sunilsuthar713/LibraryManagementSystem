package javafxapplication.ui.listAuthor;

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

public class AuthorListController implements Initializable {
    
     ObservableList<Author> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Author> tableView;
    @FXML
    private TableColumn<Author, String> nameCol;
    @FXML
    private TableColumn<Author, String> idCol;
    @FXML
    private TableColumn<Author, String> categoryCol;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
             initCol();
             loadData();
         } catch (SQLException ex) {
             Logger.getLogger(AuthorListController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }    
    
    public void initCol() {
    
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
    
    private void loadData() throws SQLException {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM AUTHOR";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()){
                String name = rs.getString("name");
                String id = rs.getString("id");
                String category = rs.getString("category");
                
                list.add(new Author(name, id, category));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
    
    public static class Author {
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty category;
        
        Author(String name, String id, String category) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.category = new SimpleStringProperty(category);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getCategory() {
            return category.get();
        }
    }
    
}
