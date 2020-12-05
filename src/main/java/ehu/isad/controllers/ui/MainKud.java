package ehu.isad.controllers.ui;

//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.WhatWeb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainKud implements Initializable {

    @FXML
    private Button btnCMS;

    @FXML
    private Button btnServer;

    @FXML
    private Button btnWhatWeb;

    /*@FXML
    private FontAwesomeIconView btnItxi;*/

    private WhatWeb mainApp;

    public void setMainApp(WhatWeb main) {
        mainApp = main;
    }

    @FXML
    void onClick(ActionEvent event) {
        if (event.getSource() == btnCMS) {
            //TODO CMS botoia klikatzean gertatzen dena
        }
        else if (event.getSource() == btnServer) {
            //TODO Server botoia klikatzean gertatzen dena
        }
        else if (event.getSource() == btnWhatWeb) {
            //TODO WhatWeb botoia klikatzean gertatzen dena
        }
    }

    @FXML
    void onClickItxi(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Momentuz ez da beharrezkoa ezer sartzea metodo honetan
    }
}
