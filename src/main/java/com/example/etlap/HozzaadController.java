package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HozzaadController extends Controller {
    @FXML
    private ChoiceBox inputKategoria;
    @FXML
    private TextArea inputLeiras;
    @FXML
    private TextField inputNev;
    @FXML
    private Spinner inputAr;

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
        System.out.println(ar);
        String kategoria = (String) inputKategoria.getValue();

        try {
            EtlapDb db = new EtlapDb();
            int siker = db.etlapHozzaadasa(nev,leiras, kategoria,ar);
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
