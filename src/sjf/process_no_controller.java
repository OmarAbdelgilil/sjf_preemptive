/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/ Templates/Classes/Class.java to edit this template
 */
package sjf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class process_no_controller {

    @FXML
    private TextField pro;
    private Stage stage;
    private Parent root;
    private Scene scene;
    public int processes;

    //when clicking submit button it sends number of processes to the next window
    public void get_no(ActionEvent e) throws Exception {
        try {
            processes = Integer.parseInt(pro.getText());
            if(processes<=0)throw new Exception();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("processes.fxml"));
            root = loader.load();
            processes_controller pscont = loader.getController();

            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);

            stage.setX(400);
            stage.setY(100);
            pscont.initiate_grid(stage, processes);

            stage.show();
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Only positive numbers and can't leave the field empty!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            alert.showAndWait();
        }
    }

}
