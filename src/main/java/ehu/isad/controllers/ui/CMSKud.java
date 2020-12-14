package ehu.isad.controllers.ui;

import ehu.isad.WhatWeb;
import ehu.isad.controllers.db.CMSDBKud;
import ehu.isad.models.CMSModel;
import ehu.isad.utils.Propietateak;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class CMSKud implements Initializable {

    private WhatWeb mainApp;

    @FXML
    private AnchorPane panela;

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

    @FXML
    private Button btnEzabatu;

    @FXML
    private Button btnEguneratu;

    private String scanUrl;
    private String whatwebpath;
    private ArrayList<CMSModel> cmsZerr;
    private ObservableList<CMSModel> cmsZerrObs;

    public CMSKud(WhatWeb main) {
        mainApp = main;
    }

    @FXML
    void onClick(ActionEvent event) {
        if (!txtUrl.getText().equals("")) {
            scanUrl = txtUrl.getText();
            urlEskaneatu();
        }
    }

    @FXML
    void onClickTaula(MouseEvent event) {
        tbCMS.requestFocus();
    }

    @FXML
    void onClickEguneratu(ActionEvent event) {
        if (tbCMS.getSelectionModel().getSelectedItem() != null) {
            scanUrl = tbCMS.getSelectionModel().getSelectedItem().getURL();
            eguneratuKontsulta();
        }
    }

    @FXML
    void onCllickEzabatu(ActionEvent event) {
        if (tbCMS.getSelectionModel().getSelectedItem() != null) {
            desaktibatuFuntzionalitateak();
            CMSDBKud.getDBKud().ezabatuKontsulta(tbCMS.getSelectionModel().getSelectedItem().getURL());
            cmsTaulaSortu();
            eguneratuCmsKop();
            aktibatuFuntzionalitateak();
            garbituFiltroak();
        }
    }

    @FXML
    void onIntro(KeyEvent event) {
        if (!txtUrl.getText().equals("") && !btnAddURL.isDisabled()) {
            if (event.getCode().equals(KeyCode.ENTER) && !urlDagoZerrendan()) {
                btnAddURL.setDisable(true);
                scanUrl = txtUrl.getText();
                urlEskaneatu();
            }
        }
    }

    @FXML
    void onKeyTyped(KeyEvent event) {
        if (!txtUrl.getText().equals("")) {
            btnAddURL.setDisable(urlDagoZerrendan());
        }
        else btnAddURL.setDisable(true);
    }

    private boolean urlDagoZerrendan() {
        boolean dago = false;
        int i = 0;
        while (!dago && i < cmsZerr.size()) {
            dago = cmsZerr.get(i).getURL().contains(txtUrl.getText());
            i++;
        }
        return dago;
    }

    public void initialize(URL location, ResourceBundle resources) {
        Properties properties = Propietateak.lortuEzarpenak();
        whatwebpath = properties.getProperty("whatwebpath");
        tbCMS.setEditable(false);
        url.setCellValueFactory(new PropertyValueFactory<>("URL"));
        cms.setCellValueFactory(new PropertyValueFactory<>("CMS"));
        version.setCellValueFactory(new PropertyValueFactory<>("Version"));
        lastUpdated.setCellValueFactory(new PropertyValueFactory<>("LastUpdated"));

        tbCMS.setRowFactory(tbCMS2 -> {
            final TableRow<CMSModel> errenkada = new TableRow<>();
            errenkada.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = errenkada.getIndex();
                if (index < tbCMS.getItems().size() && tbCMS.getSelectionModel().isSelected(index)  ) {
                    tbCMS.getSelectionModel().clearSelection();
                    btnEzabatu.setDisable(true);
                    btnEguneratu.setDisable(true);
                    event.consume();
                } else {
                    btnEzabatu.setDisable(true);
                    btnEguneratu.setDisable(true);
                }
            });
            return errenkada;
        });

        cmsTaulaSortu();
        eguneratuCmsKop();

        cmbCMS.getItems().add("");
        cmbCMS.getItems().add("Drupal");
        cmbCMS.getItems().add("Joomla");
        cmbCMS.getItems().add("phpMyAdmin");
        cmbCMS.getItems().add("WordPress");
        cmbCMS.getItems().add("ezezaguna");
        cmbCMS.getSelectionModel().selectFirst();

        btnAddURL.setDisable(true);
        btnEzabatu.setDisable(true);
        btnEguneratu.setDisable(true);
        tbCMS.setEditable(false);

        sortuFiltroa();
    }

    public void gaituTaulaEventFilter() {
        WhatWeb.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (!WhatWeb.inHierarchy(evt.getPickResult().getIntersectedNode(), tbCMS)) {
                tbCMS.getSelectionModel().clearSelection();
                btnEzabatu.setDisable(true);
                btnEguneratu.setDisable(true);
            }
        });
    }

    public void gaituTxtURLEventFilter() {
        WhatWeb.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (!WhatWeb.inHierarchy(evt.getPickResult().getIntersectedNode(), txtUrl)) {
                if (evt.getPickResult().getIntersectedNode().getId() != null && !evt.getPickResult().getIntersectedNode().getId().equals("cmbCMS")) {
                    panela.requestFocus();
                }
            }
        });
    }

    private void cmsTaulaSortu() {
        cmsZerr = CMSDBKud.getDBKud().getCMSModel();
        cmsZerrObs = FXCollections.observableArrayList(cmsZerr);
        tbCMS.setItems(cmsZerrObs);
        tbCMS.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!mainApp.prozesuAktibo) {
                btnEzabatu.setDisable(false);
                btnEguneratu.setDisable(false);
            }
        });
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
                if (cmsModel.getURL().toLowerCase().contains(lowerCaseFilter) && cmsModel.getCMS().toLowerCase().contains(cmbCMS.getValue().toLowerCase())) {
                    return true;
                }
                else return cmsModel.getLastUpdated().toLowerCase().contains(lowerCaseFilter) && cmsModel.getCMS().toLowerCase().contains(cmbCMS.getValue().toLowerCase());
            });
            eguneratuCmsKop();
        });

        cmbCMS.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cmsModel -> {
                if (newValue == null || (newValue.isEmpty() && txtUrl.getText().equals(""))) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return cmsModel.getCMS().toLowerCase().contains(lowerCaseFilter) && (cmsModel.getURL().toLowerCase().contains(txtUrl.getText().toLowerCase()) || cmsModel.getLastUpdated().toLowerCase().contains(txtUrl.getText().toLowerCase()));
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
                        boolean emaitza200 = false;
                        while((lerroa = br.readLine())!=null) {
                            if (!emaitza200) {
                                if (lerroa.contains("INSERT IGNORE INTO targets (status,target) VALUES ('200'")) {
                                    emaitza200 = true;
                                } else if (lerroa.contains("INSERT IGNORE INTO request_configs")) {
                                    lerroa = lerroa.replace("IGNORE", "OR IGNORE");
                                    CMSDBKud.getDBKud().sartuLerroa(lerroa);
                                }
                            }
                            emaitza200 = isEmaitza200(lerroa, emaitza200);
                        }
                        br.close();
                        f.delete();
                    }
                    CMSDBKud.getDBKud().eguneratuLastUpdated(scanUrl);
                    cmsTaulaSortu();
                    sortuFiltroa();
                    aktibatuFuntzionalitateak();
                    garbituFiltroak();
                    mainApp.prozesuAktibo = false;
                    txtUrl.requestFocus();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            });
        });
        haria.start();
    }

    private void eguneratuKontsulta() {
        Thread haria = new Thread( () -> {
            allProcesses();
            Platform.runLater( () -> {
                try {
                    String lerroa;
                    File f = new File("scanInfo.sql");
                    if (f.exists() && f.isFile()) {
                        FileReader fr = new FileReader("scanInfo.sql");
                        BufferedReader br = new BufferedReader(fr);
                        boolean emaitza200 = false;
                        while((lerroa = br.readLine())!=null) {
                            if (!emaitza200) {
                                if (lerroa.contains("INSERT IGNORE INTO targets (status,target) VALUES ('200'")) {
                                    emaitza200 = true;
                                    String urlBerria = lerroa.split("INSERT IGNORE INTO targets \\(status,target\\) VALUES \\('200','")[1];
                                    urlBerria = urlBerria.split("'\\);")[0];
                                    CMSDBKud.getDBKud().eguneratuUrl(scanUrl, urlBerria);
                                    CMSDBKud.getDBKud().ezabatuScanZaharra(urlBerria);
                                } else if (lerroa.contains("INSERT IGNORE INTO request_configs")) {
                                    lerroa = lerroa.replace("IGNORE", "OR IGNORE");
                                    CMSDBKud.getDBKud().sartuLerroa(lerroa);
                                }
                            }
                            emaitza200 = isEmaitza200(lerroa, emaitza200);
                        }
                        br.close();
                        f.delete();
                    }
                    cmsTaulaSortu();
                    sortuFiltroa();
                    aktibatuFuntzionalitateak();
                    garbituFiltroak();
                    mainApp.prozesuAktibo = false;
                } catch (Exception err) {
                    err.printStackTrace();
                }
            });
        });
        haria.start();
    }

    private boolean isEmaitza200(String lerroa, boolean emaitza200) {
        if (emaitza200) {
            if (lerroa.contains("INSERT IGNORE INTO targets (status,target) VALUES ('") && !lerroa.contains("INSERT IGNORE INTO targets (status,target) VALUES ('200'")) {
                emaitza200 = false;
                } else {
                lerroa = lerroa.replace("IGNORE", "OR IGNORE");
                CMSDBKud.getDBKud().sartuLerroa(lerroa);
            }
        }
        return emaitza200;
    }

    private void garbituFiltroak() {
        txtUrl.clear();
        cmbCMS.getSelectionModel().selectFirst();
    }

    private void allProcesses() {
        try {
            mainApp.prozesuAktibo = true;
            Process p;
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                p = Runtime.getRuntime().exec("wsl " + whatwebpath + "whatweb --log-sql=scanInfo.sql " + scanUrl);
            } else {
                p = Runtime.getRuntime().exec(whatwebpath + "whatweb --log-sql=scanInfo.sql " + scanUrl);
            }
            desaktibatuFuntzionalitateak();
            while (p.isAlive());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void aktibatuFuntzionalitateak() {
        aktibatuCMS();
        mainApp.aktibatuServer();
        mainApp.aktibatuWhatWeb();
    }

    public void aktibatuCMS() {
        txtUrl.setDisable(false);
        txtUrl.setEditable(true);
        cmbCMS.setDisable(false);
        btnAddURL.setDisable(false);
    }

    private void desaktibatuFuntzionalitateak() {
        desaktibatuCMS();
        mainApp.desaktibatuServer();
        mainApp.desaktibatuWhatWeb();
    }

    public void desaktibatuCMS() {
        txtUrl.setDisable(true);
        cmbCMS.setDisable(true);
        btnAddURL.setDisable(true);
        btnEzabatu.setDisable(true);
        btnEguneratu.setDisable(true);
    }
}