package com.example.ati;

import com.example.ati.Service.Service;
import com.example.ati.domain.Pacient;
import com.example.ati.domain.Pat;
import com.example.ati.domain.Type;
import com.example.ati.repository.RepositoryException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.List;

public class ControllerPacient {

    ObservableList<Pacient> modelPacient = FXCollections.observableArrayList();

    private Service service;

    private Stage stage;

    @FXML
    ListView<Pacient> pacientList;

    @FXML
    Button timButton;

    @FXML
    Button ticButton;

    @FXML
    Button tiipButton;

    @FXML
    CheckBox checkVen;


    @FXML
    public void initialize()
    {
        pacientList.setItems(modelPacient);

        timButton.setOnAction(event -> {
            try {
                handleTIM();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });

        ticButton.setOnAction(event -> {
            try {
                handleTIC();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });

        tiipButton.setOnAction(event -> {
            try {
                handleTIIP();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });
    }

    public void handleTIC() throws RepositoryException {
        Pacient pacient = pacientList.getSelectionModel().getSelectedItem();
        Pat bed;
        if(checkVen.isSelected())
            bed = service.getOneUnoccupiedBedByTypeAndVentilation(Type.TIC);
        else
            bed = service.getOneUnoccupiedBedByType(Type.TIC);
        bed.setPacient(pacient);
        service.updateBed(bed);
        pacient.setInBed(true);
        service.updatePacient(pacient);
        pacientList.setItems(FXCollections.observableArrayList(getAllPacientsSortedByGravityAndNotInBed()));
    }

    public void handleTIM() throws RepositoryException {
        Pacient pacient = pacientList.getSelectionModel().getSelectedItem();
        Pat bed;
        if(checkVen.isSelected())
            bed = service.getOneUnoccupiedBedByTypeAndVentilation(Type.TIM);
        else
            bed = service.getOneUnoccupiedBedByType(Type.TIM);
        bed.setPacient(pacient);
        service.updateBed(bed);
        pacient.setInBed(true);
        service.updatePacient(pacient);
        pacientList.setItems(FXCollections.observableArrayList(getAllPacientsSortedByGravityAndNotInBed()));
    }

    public void handleTIIP() throws RepositoryException {
        Pacient pacient = pacientList.getSelectionModel().getSelectedItem();
        Pat bed;
        if(checkVen.isSelected())
            bed = service.getOneUnoccupiedBedByTypeAndVentilation(Type.TIIP);
        else
            bed = service.getOneUnoccupiedBedByType(Type.TIIP);
        bed.setPacient(pacient);
        service.updateBed(bed);
        pacient.setInBed(true);
        service.updatePacient(pacient);
        pacientList.setItems(FXCollections.observableArrayList(getAllPacientsSortedByGravityAndNotInBed()));
    }

    public List<Pacient> getAllPacientsSortedByGravityAndNotInBed()
    {
        return service.getAllPacientsSortedByGravityAndNotInBed();
    }

    public void setService(Service service, Stage stage) {
        this.service = service;
        this.stage = stage;
        modelPacient.setAll(getAllPacientsSortedByGravityAndNotInBed());
    }
}
