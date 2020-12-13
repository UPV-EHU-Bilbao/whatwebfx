package ehu.isad.controllers.ui;

import ehu.isad.WhatWeb;
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

    private WhatWeb mainApp;

    @FXML
    private Button btnEguneratu;

    @FXML
    private ListView<String> listHistoriala;

    public ServerKud(WhatWeb main) {
        mainApp = main;
    }

    @FXML
    void onClick(ActionEvent event) {
        desaktibatuFuntzionalitateak();
        eguneratu();
        aktibatuFuntzionalitateak();
    }

    public void initialize(URL location, ResourceBundle resources) {
        eguneratu();
    }

    public void eguneratu() {
        ArrayList<String> urlList = WhatWebDBKud.getWhatWebDBKud().lortuUrl();
        listHistoriala.getItems().clear();
        listHistoriala.getItems().addAll(urlList);
    }

    private void aktibatuFuntzionalitateak() {
        aktibatuServer();
        mainApp.aktibatuCMS();
        mainApp.aktibatuWhatWeb();
    }

    public void aktibatuServer() {
        btnEguneratu.setDisable(false);
    }

    private void desaktibatuFuntzionalitateak() {
        desaktibatuServer();
        mainApp.desaktibatuCMS();
        mainApp.desaktibatuWhatWeb();
    }

    public void desaktibatuServer() {
        btnEguneratu.setDisable(true);
    }
}
