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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainKud implements Initializable {

    @FXML
    private CMSKud cmsController;

    @FXML
    private ServerKud serverController;

    @FXML
    private WhatWebKud whatwebController;

    @FXML
    private Button btnCMS;

    @FXML
    private Button btnServer;

    @FXML
    private Button btnWhatWeb;

    @FXML
    private Pane pnlInfo;

    @FXML
    private Label lblInfo;

    @FXML
    private StackPane stackPanePanelak;

    @FXML
    private AnchorPane paneCMS;

    @FXML
    private AnchorPane paneServer;

    @FXML
    private AnchorPane paneWhatWeb;

    @FXML
    void onClick(ActionEvent event) {
        if (event.getSource() == btnCMS) {
            lblInfo.setText("CMS");
            pnlInfo.setBackground(new Background(new BackgroundFill(Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY)));

            paneServer.setVisible(false);
            paneWhatWeb.setVisible(false);
            paneCMS.setVisible(true);

            paneCMS.toFront();
            paneCMS.requestFocus();
        }
        else if (event.getSource() == btnServer) {
            lblInfo.setText("Server");
            pnlInfo.setBackground(new Background(new BackgroundFill(Color.rgb(43, 63, 99), CornerRadii.EMPTY, Insets.EMPTY)));

            paneCMS.setVisible(false);
            paneWhatWeb.setVisible(false);
            paneServer.setVisible(true);

            paneServer.toFront();
            paneServer.requestFocus();
        }
        else if (event.getSource() == btnWhatWeb) {
            erakutsiWhatWeb();
        }
    }

    @FXML
    void onClickItxi(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void onClickMinimizatu(MouseEvent event) {
        WhatWeb.getStage().setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hasieran bakarrik cmsPane ikusteko
        paneServer.setVisible(false);
        paneWhatWeb.setVisible(false);
    }

    public void erakutsiWhatWeb() {
        lblInfo.setText("WhatWeb");
        pnlInfo.setBackground(new Background(new BackgroundFill(Color.rgb(43, 99, 63), CornerRadii.EMPTY, Insets.EMPTY)));

        paneCMS.setVisible(false);
        paneServer.setVisible(false);
        paneWhatWeb.setVisible(true);

        paneWhatWeb.toFront();
        paneWhatWeb.requestFocus();
    }
}
