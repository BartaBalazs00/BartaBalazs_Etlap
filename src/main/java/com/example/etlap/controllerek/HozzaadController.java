package com.example.etlap.controllerek;

import com.example.etlap.Controller;
import com.example.etlap.EtlapDb;
import com.example.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HozzaadController extends Controller {
    @FXML
    private ComboBox inputKategoria;
    @FXML
    private TextArea inputLeiras;
    @FXML
    private TextField inputNev;
    @FXML
    private Spinner inputAr;
    private EtlapDb db;
    private List<Kategoria> kategoriaList;
    public void initialize(){
        kategoriaList = new ArrayList<>();
        try {
            db = new EtlapDb();
        } catch (SQLException e) {
            hibaKiir(e);
        }
        try {
            kategoriaList = db.getKategoria();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Kategoria kategoria : kategoriaList){
            inputKategoria.getItems().add(kategoria.getNev());
        }
        inputKategoria.getSelectionModel().selectFirst();
    }
    @FXML
    public void onHozzadButtonClick(ActionEvent actionEvent) {
        String nev = inputNev.getText().trim();
        String leiras = inputLeiras.getText().trim();
        int ar = 0;
        int kategoriaIndex = inputKategoria.getSelectionModel().getSelectedIndex();
        if (nev.isEmpty()){
            alert("Név megadása kötelező");
            return;
        }
        if (leiras.isEmpty()){
            alert("Leírás megadása kötelező");
            return;
        }
        try {
            ar = (int) inputAr.getValue();
        } catch (NullPointerException ex){
            alert("Az ár megadása kötelező");
            return;
        } catch (Exception ex){
            System.out.println(ex);
            alert("Az ár csak 1 és 1000000 közötti szám lehet");
            return;
        }
        if (ar < 1 || ar > 1000000) {
            alert("Az ár csak 1 és 1000000 közötti szám lehet");
            return;
        }
        if (kategoriaIndex == -1){
            alert("Kategória kiválasztása köztelező");
            return;
        }

        String kategoriaString = (String) inputKategoria.getSelectionModel().getSelectedItem();
        int kategoriaInt = -1;
        try {
            EtlapDb db = new EtlapDb();
            for (Kategoria kategoria : kategoriaList){
                if(kategoria.getNev().equals(kategoriaString)){
                    kategoriaInt = kategoria.getId();
                    break;
                }
            }
            int siker = db.etlapHozzaadasa(nev,leiras, kategoriaInt,ar);
            if (siker == 1){
                alert("Az étel hozzáadása sikeres");
            } else {
                alert("Az étel hozzáadása sikeretelen");
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
}
