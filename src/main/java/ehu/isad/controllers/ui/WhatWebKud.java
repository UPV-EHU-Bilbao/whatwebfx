package ehu.isad.controllers.ui;

import ehu.isad.utils.Propietateak;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WhatWebKud implements Initializable {

    @FXML
    private TextField txtURL;

    @FXML
    private TextArea txtLog;

    @FXML
    private Button btnScan;

    private String url;
    private String whatwebpath;

    @FXML
    void onClick(ActionEvent event) {
        url = txtURL.getText();
        urlEskaneatu(); //TODO Ez dakit zertarako balio duen pasatzen zaion String parametroa
    }

    @FXML
    void onIntro(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            url = txtURL.getText();
            urlEskaneatu(); //TODO Ez dakit zertarako balio duen pasatzen zaion String parametroa
        }
    }

    private void urlEskaneatu() {
        String emaitza = "";
        String newLine = System.getProperty("line.separator");
        emaitza = allProcesses().stream().collect(Collectors.joining(newLine));
        txtLog.setText(emaitza);

        //TODO Sartu emaitza DBan

        //TODO Eguneratu Server pantaila
    }

    public List<String> allProcesses() {
        List<String> processes = new LinkedList<String>();
        try {
            String line;
            Process p = null;
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                p = Runtime.getRuntime().exec("wsl " + whatwebpath + "whatweb " + url);
            } else {
                p = Runtime.getRuntime().exec(whatwebpath + "whatweb " + url); //TODO Funtzionatzen du, baina falta da komandoari aukerak (options) sartzea
            }
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
    }
}
