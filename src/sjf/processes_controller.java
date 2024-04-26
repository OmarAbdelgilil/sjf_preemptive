/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sjf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class processes_controller {

    private TextField[][] text;
    @FXML
    private GridPane gr;
    @FXML
    private ScrollPane scroll;
    Stage stage1;
    int[] arrivalTime;
    int[] burstTime;

    //Creates the text fields to enter arrival and burst times for each process
    public void initiate_grid(Stage stage, int processes_no) {
        stage1 = stage;
        text = new TextField[processes_no][2];
        for (int i = 0; i < processes_no; i++) {
            for (int j = 1; j < 3; j++) {
                TextField t = new TextField();
                gr.add(t, j, i);
                text[i][j - 1] = t;
            }
            Label l = new Label();
            String s = "Process " + (i + 1);
            l.setText(s);
            l.setFont(new Font(16));
            gr.add(l, 0, i);
        }
    }

    //Clears the input fields
    public void clear(ActionEvent e) {
        for (int i = 0; i < text.length; i++) {
            for (int j = 0; j < text[i].length; j++) {
                text[i][j].clear();
            }
        }
    }

    public void Exit(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit !");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage1.close();
        }

    }

    //Get the entered Arrival and Burst time from the processes window when clicking submit button.
    public void submit(ActionEvent e) throws Exception {
        try {
            arrivalTime = new int[text.length];

            burstTime = new int[text.length];
            for (int i = 0; i < text.length; i++) {
                int arrival = Integer.parseInt(text[i][0].getText());
                if(arrival<0)throw new Exception();
                arrivalTime[i] = arrival;
                int burst = Integer.parseInt(text[i][1].getText());
                if(burst<=0)throw new Exception("Burst time can't be 0 or negative!");
                burstTime[i] = burst;
            }

            //Pass the Arrival and Burst time arrays to the  processes_e_controller class and open the window.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("processes_e.fxml"));
            Parent root = (Parent) loader.load();

            processes_e_controller pscont1 = loader.getController();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);
            Image icon = new Image("images1.png");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setX(900);
            stage.setY(200);
            stage.setResizable(false);
            pscont1.sjf_equation(stage, arrivalTime, burstTime);
            stage.show();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Only positive numbers and can't leave the fields empty (No zeros in burst time) !");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            alert.showAndWait();
        }
    }

}
