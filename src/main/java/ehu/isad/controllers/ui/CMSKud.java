package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CMSDBKud;
import ehu.isad.controllers.db.WhatWebDBKud;
import ehu.isad.models.CMSModel;
import ehu.isad.utils.Propietateak;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class CMSKud implements Initializable {

    @FXML
    private TableView<CMSModel> tbCMS;

    @FXML
    private TableColumn<CMSModel, String> url;

    @FXML
    private TableColumn<CMSModel, String> cms;

    @FXML
    private TableColumn<CMSModel, String> version;

    @FXML
    private TableColumn<CMSModel, String> lastUpdated;

    @FXML
    private TextField txtUrl;

    @FXML
    private Button addURL;

    @FXML
    private Label lblCMSKop;

    private String scanUrl;
    private String whatwebpath;

    @FXML
    void onClick(ActionEvent event) {
        scanUrl = txtUrl.getText();
        urlEskaneatu();
    }

    @FXML
    void onIntro(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            scanUrl = txtUrl.getText();
            urlEskaneatu();
        }
    }

    private ArrayList<CMSModel> cmsZerr;

    public void initialize(URL location, ResourceBundle resources) {
        Properties properties = Propietateak.lortuEzarpenak();
        whatwebpath = properties.getProperty("whatwebpath");
        tbCMS.setEditable(false);
        url.setCellValueFactory(new PropertyValueFactory<>("URL"));
        cms.setCellValueFactory(new PropertyValueFactory<>("CMS"));
        version.setCellValueFactory(new PropertyValueFactory<>("Version"));
        lastUpdated.setCellValueFactory(new PropertyValueFactory<>("Last Updated"));

        this.cmsTaulaSortu();
        this.eguneratuCmsKop();
    }

    private void cmsTaulaSortu() {
        cmsZerr = new ArrayList<>();
        cmsZerr = CMSDBKud.getDBKud().getCMSModel();
        ObservableList<CMSModel> cmsZerrObs = FXCollections.observableArrayList(cmsZerr);
        tbCMS.setItems(cmsZerrObs);
    }

    private void eguneratuCmsKop() {
        lblCMSKop.setText("Record count: " + tbCMS.getItems().size());
    }

    private void urlEskaneatu() {
        txtUrl.setEditable(false);
        Thread haria = new Thread( () -> {
            allProcesses();
            Platform.runLater( () -> {
                try {
                    cmsTaulaSortu();
                    txtUrl.clear();
                    txtUrl.setEditable(true);
                    String lerroa;
                    File f = new File("scanInfo.sql");
                    if (f.exists() && f.isFile()) {
                        FileReader fr = new FileReader("scanInfo.sql");
                        BufferedReader br = new BufferedReader(fr);
                        while((lerroa = br.readLine())!=null) {
                            lerroa = lerroa.replace("IGNORE", "OR IGNORE");
                            System.out.println(lerroa);
                            CMSDBKud.getDBKud().sartuLerroa(lerroa);
                        }
                        System.out.println(f.getAbsolutePath());
                        f.delete();
                        System.out.println(f.getAbsolutePath());
                        br.close();
                    } else System.out.println("no entra");
                } catch (Exception err) {
                    err.printStackTrace();
                }
            });
        });
        haria.start();
    }

    private void allProcesses() {
        List<String> processes = new LinkedList<String>();
        try {
            Process p = null;
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                p = Runtime.getRuntime().exec("wsl " + whatwebpath + "whatweb --log-sql=scanInfo.sql " + txtUrl.getText());
            } else {
                p = Runtime.getRuntime().exec(whatwebpath + "whatweb --log-sql=scanInfo.sql " + txtUrl.getText());
            }
            while (p.isAlive());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}