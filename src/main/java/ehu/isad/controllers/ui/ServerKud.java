package ehu.isad.controllers.ui;

import ehu.isad.WhatWeb;
import ehu.isad.controllers.db.WhatWebDBKud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerKud implements Initializable {

    private WhatWeb mainApp;

    @FXML
    private AnchorPane panela;

    @FXML
    private Button btnEguneratu;

    @FXML
    private ListView<String> listHistoriala;

    public ServerKud(WhatWeb main) {
        mainApp = main;
    }

    @FXML
    void onClick(ActionEvent event) {
        mainApp.urlEguneratu(listHistoriala.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onClickListHistoriala(MouseEvent event) {
        if (listHistoriala.getSelectionModel().getSelectedItem() != null) {
            btnEguneratu.setDisable(false);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        eguneratu();
        btnEguneratu.setDisable(true);
    }

    public void gaituListHistorialaEventFilter() {
        WhatWeb.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (!WhatWeb.inHierarchy(evt.getPickResult().getIntersectedNode(), listHistoriala)) {
                listHistoriala.getSelectionModel().clearSelection();
                btnEguneratu.setDisable(true);
                panela.requestFocus();
            }
        });
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
