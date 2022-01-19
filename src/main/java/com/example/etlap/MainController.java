package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MainController extends Controller{

    @FXML
    private TableColumn colNev;
    @FXML
    private Spinner inputSzazalek;
    @FXML
    private TableView etlapTable;
    @FXML
    private TableColumn colAr;
    @FXML
    private TableColumn colKategoria;
    @FXML
    private Spinner inputForint;
    private EtlapDb db;

    public void initialize(){
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try {
            db = new EtlapDb();
            etlapListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onHozzadasButtonClick(ActionEvent actionEvent) {
        try {
            Controller hozzaadas = ujAblak("hozzaad-view.fxml", "Étel hozzáadása", 320, 400);
            hozzaadas.getStage().setOnCloseRequest(event -> etlapListaFeltolt());
            hozzaadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
    private void etlapListaFeltolt(){
        try {
            List<Etlap> etlapList = db.getEtlap();
            etlapTable.getItems().clear();
            for(Etlap etlap: etlapList){
                etlapTable.getItems().add(etlap);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

}