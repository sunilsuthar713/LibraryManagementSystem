package javafxapplication.ui.listPublisher;

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

public class PublisherListController implements Initializable {
    
    ObservableList<Publisher> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Publisher> tableView;
    @FXML
    private TableColumn<Publisher, String> nameCol;
    @FXML
    private TableColumn<Publisher, String> addressCol;
    @FXML
    private TableColumn<Publisher, String> phoneCol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initCol();
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void initCol() {
    
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private void loadData() throws SQLException {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM PUBLISHER";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                
                list.add(new Publisher(name, address, phone));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublisherListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
    
    public static class Publisher {
        private final SimpleStringProperty name;
        private final SimpleStringProperty address;
        private final SimpleStringProperty phone;
        
        Publisher(String name, String address, String phone) {
            this.name = new SimpleStringProperty(name);
            this.address = new SimpleStringProperty(address);
            this.phone = new SimpleStringProperty(phone);
        }

        public String getName() {
            return name.get();
        }

        public String getAddress() {
            return address.get();
        }

        public String getPhone() {
            return phone.get();
        }
    }
}
