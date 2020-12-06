package ehu.isad.controllers.ui;

//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.WhatWeb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

    @FXML
    private Pane pnlInfo;

    @FXML
    private Label lblInfo;

    private WhatWeb mainApp;

    public void setMainApp(WhatWeb main) {
        mainApp = main;
    }

    @FXML
    void onClick(ActionEvent event) {
        if (event.getSource() == btnCMS) {
            lblInfo.setText("CMS");
            pnlInfo.setBackground(new Background(new BackgroundFill(Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY)));
            //TODO CMS botoia klikatzean gertatzen dena
        }
        else if (event.getSource() == btnServer) {
            lblInfo.setText("Server");
            pnlInfo.setBackground(new Background(new BackgroundFill(Color.rgb(43, 63, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            //TODO Server botoia klikatzean gertatzen dena
        }
        else if (event.getSource() == btnWhatWeb) {
            lblInfo.setText("WhatWeb");
            pnlInfo.setBackground(new Background(new BackgroundFill(Color.rgb(43, 99, 63), CornerRadii.EMPTY, Insets.EMPTY)));
            //TODO WhatWeb botoia klikatzean gertatzen dena
        }
    }

    @FXML
    void onClickItxi(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void onSartuItxi(MouseEvent event) {
        //btnItxi.setFill(Color.rgb(255, 0, 0));
    }

    @FXML
    void onAteraItxi(MouseEvent event) {
        //btnItxi.setFill(Color.rgb(134, 131, 131));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Momentuz ez da beharrezkoa ezer sartzea metodo honetan
    }
}
