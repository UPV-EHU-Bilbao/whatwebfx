package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CMSDBKud;
import ehu.isad.controllers.db.WhatWebDBKud;
import ehu.isad.models.CMSModel;
import ehu.isad.utils.Propietateak;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.function.Predicate;

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
    private ComboBox<String> cmbCMS;

    @FXML
    private Button btnAddURL;

    @FXML
    private Label lblCMSKop;

    private String scanUrl;
    private String whatwebpath;
    ObservableList<CMSModel> cmsZerrObs;

    @FXML
    void onClick(ActionEvent event) {
        if (!txtUrl.getText().equals("")) {
            btnAddURL.setDisable(true);
            scanUrl = txtUrl.getText();
            urlEskaneatu();
        }
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (!txtUrl.getText().equals("")) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                btnAddURL.setDisable(true);
                scanUrl = txtUrl.getText();
                urlEskaneatu();
            }
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
        lastUpdated.setCellValueFactory(new PropertyValueFactory<>("LastUpdated"));

        cmsTaulaSortu();
        eguneratuCmsKop();

        cmbCMS.getItems().add("");
        cmbCMS.getItems().add("Drupal");
        cmbCMS.getItems().add("Joomla");
        cmbCMS.getItems().add("phpMyAdmin");
        cmbCMS.getItems().add("WordPress");
        cmbCMS.getSelectionModel().selectFirst();

        sortuFiltroa();
    }

    private void cmsTaulaSortu() {
        cmsZerr = CMSDBKud.getDBKud().getCMSModel();
        cmsZerrObs = FXCollections.observableArrayList(cmsZerr);
        tbCMS.setItems(cmsZerrObs);
    }

    private void eguneratuCmsKop() {
        lblCMSKop.setText("Record count: " + tbCMS.getItems().size());
    }

    private void sortuFiltroa() {
        FilteredList<CMSModel> filteredData = new FilteredList<>(cmsZerrObs, b -> true);

        txtUrl.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cmsModel -> {
                if (newValue == null || (newValue.isEmpty() && cmbCMS.getValue().equals(""))) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (cmsModel.getURL().toLowerCase().indexOf(lowerCaseFilter) != -1 && cmsModel.getCMS().toLowerCase().indexOf(cmbCMS.getValue().toLowerCase()) != -1) {
                    return true;
                }
                else if (cmsModel.getLastUpdated().toLowerCase().indexOf(lowerCaseFilter)!= -1 && cmsModel.getCMS().toLowerCase().indexOf(cmbCMS.getValue().toLowerCase()) != -1)
                    return true;
                else
                    return false;
            });
            eguneratuCmsKop();
        });

        cmbCMS.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cmsModel -> {
                if (newValue == null || (newValue.isEmpty() && txtUrl.getText().equals(""))) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (cmsModel.getCMS().toLowerCase().indexOf(lowerCaseFilter) != -1 && (cmsModel.getURL().toLowerCase().indexOf(txtUrl.getText().toLowerCase()) != -1 || cmsModel.getLastUpdated().toLowerCase().indexOf(txtUrl.getText().toLowerCase()) != -1)) {
                    return true;
                } else
                    return false;
            });
            eguneratuCmsKop();
        }));
        SortedList<CMSModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbCMS.comparatorProperty());
        tbCMS.setItems(sortedData);
    }

    private void urlEskaneatu() {
        txtUrl.setEditable(false);
        Thread haria = new Thread( () -> {
            allProcesses();
            Platform.runLater( () -> {
                try {
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
                        br.close();
                        f.delete();
                    }
                    CMSDBKud.getDBKud().eguneratuLastUpdated(scanUrl);
                    cmsTaulaSortu();
                    sortuFiltroa();
                    txtUrl.clear();
                    txtUrl.setEditable(true);
                    cmbCMS.getSelectionModel().selectFirst();
                    btnAddURL.setDisable(false);
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