/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sjf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class processes_e_controller {

    @FXML
    private GridPane sjf_gr;
    @FXML
    private AnchorPane gantt;
    @FXML
    private Label avgT;
    @FXML
    private Label avgW;
    @FXML
    private Label avgR;
    Stage stage;

    int[] completionTime;
    int[] turnaroundTime;
    int[] waitingTime;
    int[] responseTime;
    int[] arrivalTime;
    int[] burstTime;
    ArrayList<Integer> startn = new ArrayList<>();
    double avarageWaiting;
    double averageTurn;
    double averageResponse;
    ArrayList<Integer> in_pro = new ArrayList<>();
    int arrive_first = 0;
    Color[] colors = {Color.ORANGE, Color.YELLOW, Color.PINK, Color.CYAN};
    Random rand = new Random();
    int in_process = -1;

    void sjf_equation(Stage stage1, int[] arrivaltime, int[] bursttime) {
        stage = stage1;
        arrivalTime = arrivaltime;
        burstTime = bursttime;
        int n = arrivalTime.length;
        int[] remainingTime = new int[n];
        completionTime = new int[n];
        turnaroundTime = new int[n];
        waitingTime = new int[n];
        responseTime = new int[n];
        Arrays.fill(responseTime, -1);
        boolean[] isCompleted = new boolean[n];
        int currentTime = 0;
        int completed = 0;

        for (int i = 0; i < n; i++) {
            remainingTime[i] = burstTime[i];
        }
        Integer[] processOrder = new Integer[n];

        // Sort the processes based on arrival time and remaining time
        for (int i = 0; i < n; i++) {
            processOrder[i] = i;
        }
        Arrays.sort(processOrder, (a, b) -> remainingTime[a] - remainingTime[b]);

        int flag = 0;
        // Run until all processes are completed
        while (completed != n) {
            int shortest = -1;
            int minBurstTime = Integer.MAX_VALUE;

            // Find the process with the shortest remaining time among the processes that have arrived
            for (int i = 0; i < n; i++) {
                int j = processOrder[i];
                if (arrivalTime[j] <= currentTime && !isCompleted[j]) {
                    if (remainingTime[j] < minBurstTime
                            || (remainingTime[j] == minBurstTime && arrivalTime[j] < arrivalTime[shortest])) {
                        minBurstTime = remainingTime[j];
                        shortest = j;
                        if (flag == 0) {
                            arrive_first = arrivalTime[shortest];
                            flag = 1;
                        }
                    }

                }
            }
            int in = shortest;
            if (shortest == -1) {
                currentTime++;
                continue;
            }
            if (in_process != in) {
                in_process = in;
                startn.add(currentTime);
            }

            // Decrement remaining time of the selected process
            remainingTime[shortest]--;

            in_pro.add(shortest);

            if (responseTime[shortest] == -1) { // response time is  calculated
                responseTime[shortest] = currentTime - arrivalTime[shortest];
            }
            // Check if the selected process has completed
            if (remainingTime[shortest] == 0) {
                isCompleted[shortest] = true;
                completed++;

                // Calculate completion time, turnaround time, and waiting time
                completionTime[shortest] = currentTime + 1;
                turnaroundTime[shortest] = completionTime[shortest] - arrivalTime[shortest];
                waitingTime[shortest] = turnaroundTime[shortest] - burstTime[shortest];

            }

            currentTime++;
        }

        //calculate average waiting,turnaround and response time
        double sumw = 0;
        double sumt = 0;
        double sumr = 0;
        double waitlen = waitingTime.length;
        for (int i = 0; i < waitlen; i++) {
            sumw += waitingTime[i];
            sumt += turnaroundTime[i];
            sumr += responseTime[i];
        }

        avarageWaiting = sumw / waitlen;
        averageTurn = sumt / waitlen;
        averageResponse = sumr / waitlen;

        initiate_sjf_grid();
    }

    public void initiate_sjf_grid() {
        startn.remove(0);
        //Create the sjf output table
        for (int i = 0; i < arrivalTime.length; i++) {
            Label l = new Label();
            String s = "Process " + (i + 1);
            l.setText(s);
            l.setFont(new Font("System", 12));
            sjf_gr.add(l, 0, i);

            Label l_1 = new Label();
            l_1.setFont(new Font("System", 12));
            String k = Integer.toString(arrivalTime[i]);
            l_1.setText(k);
            sjf_gr.add(l_1, 1, i);

            Label l_2 = new Label();
            k = Integer.toString(burstTime[i]);
            l_2.setText(k);
            sjf_gr.add(l_2, 2, i);

            Label l_3 = new Label();
            k = Integer.toString(waitingTime[i]);
            l_3.setText(k);
            sjf_gr.add(l_3, 3, i);

            Label l_4 = new Label();
            k = Integer.toString(responseTime[i]);
            l_4.setText(k);
            sjf_gr.add(l_4, 4, i);

            Label l_5 = new Label();
            k = Integer.toString(turnaroundTime[i]);
            l_5.setText(k);
            sjf_gr.add(l_5, 5, i);

        }

        String t = String.format("%.2f", averageTurn);
        avgT.setText(t);

        String w = String.format("%.2f", avarageWaiting);
        avgW.setText(w);

        String rv = String.format("%.2f", averageResponse);
        avgR.setText(rv);
        //Draw gantt chart
        int start = 10;
        String k;
        Label no = new Label();
        k = Integer.toString(arrive_first);
        no.setText(k);
        no.setLayoutX(start);
        no.setLayoutY(80);
        gantt.getChildren().add(no);
        int count = 0;
        int in = -1;
        int c = 0;
        int f_n=0;
        for (int i = 0; i < in_pro.size(); i++) {
            if (in == -1) {
                in = in_pro.get(i);
            }
            if (in_pro.get(i) == in) {
                count++;
            } else {
                int x = count * 30;
                int index = rand.nextInt(colors.length);
                Color randomColor = colors[index];
                Rectangle r = new Rectangle();
                r.setX(start);
                r.setY(50);
                r.setWidth(x);
                r.setHeight(20);
                r.setFill(randomColor);
                start += x;
                int center = (int) (r.getX() + (r.getWidth() / 2));
                String m;
                Label no1 = new Label();
                m = Integer.toString(startn.get(c));
                no1.setText(m);
                no1.setLayoutX(start);
                no1.setLayoutY(80);
                Label p_name = new Label();
                p_name.setText("p" + (in + 1));
                p_name.setLayoutX(center);
                p_name.setLayoutY(80);
                in = in_pro.get(i);
                count = 1;
                gantt.getChildren().add(r);
                gantt.getChildren().add(no1);
                gantt.getChildren().add(p_name);
                f_n=startn.get(c);
                c++;
            }

        }
        String m;
        if(startn.size()==0)f_n+=arrive_first;
        int index = rand.nextInt(colors.length);
        Color randomColor = colors[index];
        Rectangle r = new Rectangle();
        int x = count * 20;
        r.setX(start);
        r.setY(50);
        r.setWidth(x);
        r.setHeight(20);
        r.setFill(randomColor);
        start += x;
        int center = (int) (r.getX() + (r.getWidth() / 2));
        Label p_name = new Label();
        p_name.setText("p" + (in + 1));
        p_name.setLayoutX(center);
        p_name.setLayoutY(80);
        gantt.getChildren().add(p_name);

        gantt.getChildren().add(r);

        Label no2 = new Label();
        m = Integer.toString(f_n+ count);
        no2.setText(m);
        no2.setLayoutX(start);
        no2.setLayoutY(80);
        gantt.getChildren().add(no2);

    }

    @FXML
    public void Ok(ActionEvent e) throws Exception {
        stage.close();
    }

}
