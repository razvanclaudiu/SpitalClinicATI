package com.example.ati;

import com.example.ati.Service.Service;
import com.example.ati.domain.Pacient;
import com.example.ati.repository.BedDBRepository;
import com.example.ati.repository.PacientDBRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        PacientDBRepository pacientDBRepository = new PacientDBRepository("jdbc:postgresql://localhost:5432/ati", "postgres", "razvan");
        BedDBRepository bedDBRepository = new BedDBRepository("jdbc:postgresql://localhost:5432/ati", "postgres", "razvan");

        Service service = new Service(bedDBRepository, pacientDBRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startBed.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setScene(scene);
        stage.show();

        ControllerBed bedcontroller = fxmlLoader.getController();
        bedcontroller.setService(service, stage);

        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("startPacient.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load(), 1000, 600);
        Stage stage1 = new Stage();
        stage1.setScene(scene1);
        stage1.show();

        ControllerPacient pacientcontroller = fxmlLoader1.getController();
        pacientcontroller.setService(service, stage1);
    }

    public static void main(String[] args) {
        launch();
    }
}