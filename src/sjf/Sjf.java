/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package sjf;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class Sjf extends Application {

    @Override
    public void start(Stage stage) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("no_of_processes.fxml"));
         Scene scene = new Scene(root);
         String css = this.getClass().getResource("application.css").toExternalForm();
         Image icon = new  Image("images1.png");
         stage.getIcons().add(icon);
         scene.getStylesheets().add(css);
          stage.setTitle("SJF");
          stage.setResizable(false);
           stage.setScene(scene);
           stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
