package javafxapplication.ui.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafxapplication.database.DatabaseHandler;

public class FXMLController implements Initializable {
    public String user="admin";
    public String pass="admin";
    Stage stage;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void login(ActionEvent event) {
        String userName = username.getText();
        String passWord = password.getText();
        if( userName.equals(user) && passWord.equals(pass))
        {
            closeStage();
            loadMain();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid Password or Username");
            alert.showAndWait();
            return;
        }
    }
    
    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }
    
    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/javafxapplication/ui/main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library Assistant");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
