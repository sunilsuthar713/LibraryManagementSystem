package javafxapplication.ui.main;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxapplication.database.DatabaseHandler;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/ui/login/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ADMIN LOGIN");
        stage.show();
        DatabaseHandler.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}


