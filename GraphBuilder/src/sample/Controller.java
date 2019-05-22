package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private TextField parametr;

    @FXML
    private TextField step;

    @FXML
    private LineChart<Number, Number> lineChar;

    @FXML
    private Button build;

    @FXML
    private TextField lowLimit;

    @FXML
    private TextField upLimit;

    @FXML
    private Button saveButton;




    private static double y (double x,double a){
        return (8*Math.pow(a,3))/((Math.pow(x,2))+4*Math.pow(a,2));
    }


    @FXML
    void buildHandle(ActionEvent event) {
        double a;
        double step ;
        double lowLimit;
        double upLimit;
        if(parametr.getText().equals("")||this.step.getText().equals("") || this.lowLimit.getText().equals("") || this.upLimit.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Enter valid values");
            alert.showAndWait();
            return;
        }
        try{
            upLimit = Double.valueOf(this.upLimit.getText());
            lowLimit = Double.valueOf(this.lowLimit.getText());
            if(upLimit <= lowLimit)throw new IllegalArgumentException();
         a = Double.valueOf(parametr.getText());
        step = Double.valueOf(this.step.getText());
        if(a>100 || a <-100)throw new Exception();
        if(step<0.01)throw new Exception();
        if(upLimit > 100 || lowLimit<-100)throw new Exception();
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Lower limit is higher or equals Higher limit");
            alert.showAndWait();
            return;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Enter valid values");
            alert.showAndWait();
            return;
        }


        lineChar.getData().clear();
        XYChart.Series<Number,Number> series = new XYChart.Series<Number, Number>();
        for (double i = lowLimit; i <=upLimit ; i+=step) {
            series.getData().add(new XYChart.Data<>(i,y(i,a)));
        }

        series.setName("Графік заданої функції!");
        lineChar.getData().add(series);
    }


    @FXML
    void saveButtonHandle(ActionEvent event) {
        WritableImage image = lineChar.snapshot(new SnapshotParameters(), null);

        File file = new File("chart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
        }

    }

}
