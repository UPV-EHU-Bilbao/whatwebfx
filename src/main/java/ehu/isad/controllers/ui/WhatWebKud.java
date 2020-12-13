package ehu.isad.controllers.ui;

import ehu.isad.WhatWeb;
import ehu.isad.controllers.db.WhatWebDBKud;
import ehu.isad.utils.Propietateak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class WhatWebKud implements Initializable {

    private WhatWeb mainApp;

    @FXML
    private AnchorPane panela;

    @FXML
    private TextField txtURL;

    @FXML
    private TextArea txtLog;

    @FXML
    private Button btnScan;

    private String url;
    private String whatwebpath;

    public WhatWebKud(WhatWeb main) {
        mainApp = main;
    }

    public void urlEguneratu(String selectedItem) {
        txtURL.setText(selectedItem);
        urlEskaneatu();
    }

    @FXML
    void onClick(ActionEvent event) {
        urlEskaneatu();
    }

    @FXML
    void onClickTxtLog(MouseEvent event) {
        txtLog.requestFocus();
    }

    @FXML
    void onIntro(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) && !btnScan.isDisabled()) {
            urlEskaneatu();
        }
    }

    @FXML
    void onKeyTyped(KeyEvent event) {
        btnScan.setDisable(txtURL.getText().equals(""));
    }

    private void urlEskaneatu() {
        url = txtURL.getText();
        txtLog.setText("Kargatzen. Itxaron, mesedez...");
        txtURL.setEditable(false);
        Thread haria = new Thread( () -> {
            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            allProcesses().forEach(line -> emaitza.append(line).append(newLine));
            Platform.runLater( () -> {
                txtLog.setText(emaitza.toString());
                if (!txtLog.getText().isBlank()) {
                    if (!WhatWebDBKud.getWhatWebDBKud().sartutaDagoURL(url)) {
                        WhatWebDBKud.getWhatWebDBKud().sartuURL(url);
                    } else {
                        WhatWebDBKud.getWhatWebDBKud().eguneratuURL(url);
                    }
                    mainApp.serverPantailaEguneratu();
                }
                aktibatuFuntzionalitateak();
                txtURL.clear();
                txtURL.setEditable(true);
            });
        });
        haria.start();
    }

    public List<String> allProcesses() {
        List<String> processes = new LinkedList<>();
        try {
            String line;
            Process p = null;
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                p = Runtime.getRuntime().exec("wsl " + whatwebpath + "whatweb --colour=never " + url);
            } else {
                p = Runtime.getRuntime().exec(whatwebpath + "whatweb --colour=never " + url);
            }
            desaktibatuFuntzionalitateak();
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                processes.add(line);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return processes;
    }

    public void initialize(URL location, ResourceBundle resources) {
        Properties properties = Propietateak.lortuEzarpenak();
        whatwebpath = properties.getProperty("whatwebpath");
        btnScan.setDisable(true);
    }

    public void gaituTxtURLEventFilter() {
        WhatWeb.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (!WhatWeb.inHierarchy(evt.getPickResult().getIntersectedNode(), txtURL)) {
                panela.requestFocus();
            }
        });
    }

    private void aktibatuFuntzionalitateak() {
        aktibatuWhatWeb();
        mainApp.aktibatuCMS();
        mainApp.aktibatuServer();
    }

    public void aktibatuWhatWeb() {
        txtURL.setDisable(false);
        txtURL.setEditable(true);
        btnScan.setDisable(txtURL.getText().equals(""));
    }

    private void desaktibatuFuntzionalitateak() {
        desaktibatuWhatWeb();
        mainApp.desaktibatuCMS();
        mainApp.desaktibatuServer();
    }

    public void desaktibatuWhatWeb() {
        txtURL.setDisable(true);
        btnScan.setDisable(true);
    }
}
