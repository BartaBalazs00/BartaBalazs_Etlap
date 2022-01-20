package com.example.etlap.controllerek;

import com.example.etlap.Controller;
import com.example.etlap.Etlap;
import com.example.etlap.EtlapDb;
import com.example.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MainController extends Controller {

    @FXML
    private TableColumn colNev;
    @FXML
    private Spinner inputSzazalek;
    @FXML
    private Spinner inputForint;
    @FXML
    private TableView etlapTable;
    @FXML
    private TableView kategoriaTable;
    @FXML
    private TableColumn colAr;
    @FXML
    private TableColumn colKategoria;
    @FXML
    private TableColumn colKategoriaNev;

    private EtlapDb db;

    public void initialize(){
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        colKategoriaNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        try {
            db = new EtlapDb();
            etlapListaFeltolt();
            kategoriaListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
    @FXML
    public void onEtelTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = etlapTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Etlap torlendoEtel = (Etlap) etlapTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos hogy törölni szeretné az alábbi ételt: "+torlendoEtel.getNev())){
            return;
        }
        try {
            db.etelTorlese(torlendoEtel.getId());
            alert("Sikeres törlés");
            etlapListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
    public void onKategoriaTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = kategoriaTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Kategoria torlendoKategoria = (Kategoria) kategoriaTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos hogy törölni szeretné az alábbi ételt: "+torlendoKategoria.getNev())){
            return;
        }
        try {
            db.kategoriaTorlese(torlendoKategoria.getId());
            alert("Sikeres törlés");
            kategoriaListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
    @FXML
    public void onHozzadasButtonClick(ActionEvent actionEvent) {
        try {
            Controller hozzaadas = ujAblak("hozzaad-view.fxml", "Étel hozzáadása", 400, 500);
            hozzaadas.getStage().setOnCloseRequest(event -> etlapListaFeltolt());
            hozzaadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
    public void onSzazalekEmelesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = etlapTable.getSelectionModel().getSelectedIndex();
        int szazalek = 0;
        try {
            szazalek = (int) inputSzazalek.getValue();
        } catch (Exception e){
            alert("A mező kitöltése kötelező, és csak számot fogad el");
            return;
        }
        if(szazalek < 5 || szazalek > 50){
            alert("A százaléknak nagyobbnak kell lennie mint 5 és kissebbnek mint 50");
            return;
        }
        if (!confirm("Biztos hogy emelni szeretné az árat?")){
            return;
        }
        if(selectedIndex == -1){
            try {
                db.szazalekosEmelesMindenre(szazalek);
                alert("Sikeres emelés");
                etlapListaFeltolt();
            } catch (SQLException e) {
                hibaKiir(e);
            }
        } else {
            Etlap emelendoEtel = (Etlap) etlapTable.getSelectionModel().getSelectedItem();
            try {
                db.szazalekosEmelesEgyEtelre(szazalek, emelendoEtel.getId());
                alert("Sikeres Emelés");
                etlapListaFeltolt();
            } catch (SQLException e) {
                hibaKiir(e);
            }
        }


    }

    public void onForintEmelesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = etlapTable.getSelectionModel().getSelectedIndex();
        int forint = 0;
        try {
            forint = (int) inputForint.getValue();
        } catch (Exception e){
            alert("A mező kitöltése kötelező, és csak számot fogad el");
            return;
        }
        if(forint < 50 || forint > 3000){
            alert("Az értéknek nagyobbnak kell lennie mint 50 és kissebbnek mint 3000");
            return;
        }
        if (!confirm("Biztos hogy emelni szeretné az árat?")){
            return;
        }
        if(selectedIndex == -1){
            try {
                db.fixOsszeguEmelesMindenre(forint);
                alert("Sikeres emelés");
                etlapListaFeltolt();
            } catch (SQLException e) {
                hibaKiir(e);
            }
        } else {
            Etlap emelendoEtel = (Etlap) etlapTable.getSelectionModel().getSelectedItem();
            try {
                db.fixOsszeguEmelesEgyEtelre(forint, emelendoEtel.getId());
                alert("Sikeres Emelés");
                etlapListaFeltolt();
            } catch (SQLException e) {
                hibaKiir(e);
            }
        }
    }

    public void onKategoriaHozzáadásaButtonClick(ActionEvent actionEvent) {
        try {
            Controller hozzaadas = ujAblak("kategoria-view.fxml", "Kategoria hozzáadása", 200, 100);
            hozzaadas.getStage().setOnCloseRequest(event -> kategoriaListaFeltolt());
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
    private void kategoriaListaFeltolt(){
        try {
            List<Kategoria> kategoriaList = db.getKategoria();
            kategoriaTable.getItems().clear();
            for(Kategoria kategoria: kategoriaList){
                kategoriaTable.getItems().add(kategoria);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }


}