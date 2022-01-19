package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HozzaadController extends Controller {
    @javafx.fxml.FXML
    private ChoiceBox inputKategoria;
    @javafx.fxml.FXML
    private TextArea inputLeiras;
    @javafx.fxml.FXML
    private TextField inputNev;
    @javafx.fxml.FXML
    private Spinner inputAr;

    @javafx.fxml.FXML
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
                alert("Film hozzáadása sikeres");
            } else {
                alert("Film hozzáadása sikeretelen");
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
}
