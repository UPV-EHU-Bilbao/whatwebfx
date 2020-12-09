package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CMSDBKud;
import ehu.isad.models.CMSModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private Button addURL;

    @FXML
    private Label lblCMSKop;

    @FXML
    void onClick(ActionEvent event) {
        //TODO
    }

    private ArrayList<CMSModel> cmsZerr;

    public void initialize(URL location, ResourceBundle resources) {
        tbCMS.setEditable(false);
        url.setCellValueFactory(new PropertyValueFactory<>("URL"));
        cms.setCellValueFactory(new PropertyValueFactory<>("CMS"));
        version.setCellValueFactory(new PropertyValueFactory<>("Version"));
        lastUpdated.setCellValueFactory(new PropertyValueFactory<>("Last Updated"));

        this.cmsTaulaSortu();
        this.eguneratuCmsKop();
    }

    private void cmsTaulaSortu() {
        cmsZerr = new ArrayList<>(); //TODO Beheko komando erabilgarria denean hau kendu daiteke
        //cmsZerr = CMSDBKud.getDBKud().getCMSModel();
        ObservableList<CMSModel> cmsZerrObs = FXCollections.observableArrayList(cmsZerr);
        tbCMS.setItems(cmsZerrObs);
    }

    private void eguneratuCmsKop() {
        lblCMSKop.setText("Record count: " + tbCMS.getItems().size());
    }
}