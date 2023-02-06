package com.example.ati;

import com.example.ati.Service.Service;
import com.example.ati.domain.Pat;
import com.example.ati.repository.RepositoryException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ControllerBed {

    public class MyRunnableTask implements Runnable {
        public void run() {

            Platform.runLater(() -> {
                List<Integer> occupied_TIC_TIIP_TIM_Beds = getOccupied_TIC_TIIP_TIM_Beds();
                ocupatLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(0).toString());
                ticLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(1).toString());
                tiipLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(2).toString());
                timLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(3).toString());
            });
        }
    }

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> future = executor.scheduleAtFixedRate(new MyRunnableTask(), 0, 1, TimeUnit.SECONDS);


    private Service service;

    private Stage stage;

    @FXML
    Label ocupatLabel;

    @FXML
    Label ticLabel;

    @FXML
    Label tiipLabel;

    @FXML
    Label timLabel;

    @FXML
    TextField cnpText;

    @FXML
    Button eliminaButton;

    public void Initialize()
    {
        List<Integer> occupied_TIC_TIIP_TIM_Beds = getOccupied_TIC_TIIP_TIM_Beds();
        ocupatLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(0).toString());
        ticLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(1).toString());
        tiipLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(2).toString());
        timLabel.setText(occupied_TIC_TIIP_TIM_Beds.get(3).toString());

        eliminaButton.setOnAction(event -> {
            try {
                handleElimina();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Integer> getOccupied_TIC_TIIP_TIM_Beds()
    {
        return service.getOccupied_TIC_TIIP_TIM_Beds();
    }

    public void handleElimina() throws RepositoryException {
        System.out.println(cnpText.getText());
        Pat bed = getBedByCNP(Long.parseLong(cnpText.getText()));
        bed.setPacient(null);
        service.updateBed(bed);
    }

    public Pat getBedByCNP(Long cnp)
    {
        return service.getBedByCNP(cnp);
    }

    public void setService(Service service, Stage stage) {
        this.service = service;
        this.stage = stage;
        Initialize();
    }
}
