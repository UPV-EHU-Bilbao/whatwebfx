package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.WhatWebDBKud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerKud implements Initializable {

    @FXML
    private Button btnEguneratu;

    @FXML
    private ListView<String> listHistoriala;

    @FXML
    void onClick(ActionEvent event) {
        eguneratu();
    }

    public void initialize(URL location, ResourceBundle resources) {
        eguneratu();
    }

    public void eguneratu() {
        ArrayList<String> urlList = WhatWebDBKud.getWhatWebDBKud().lortuUrl();
        listHistoriala.getItems().clear();
        listHistoriala.getItems().addAll(urlList);
    }
}
