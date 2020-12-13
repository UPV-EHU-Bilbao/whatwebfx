package ehu.isad.models;

import javafx.scene.control.Button;

public class CMSModel {

    private final String url;
    private final String cms;
    private final String version;
    private String lastUpdated; //TODO Model bat egin daiteke atributu honetarako
    private Button update;

    public CMSModel(String pUrl, String pCms, String pVersion, String pLastUpdated) {
        this.url = pUrl;
        this.cms = pCms;
        this.version = pVersion;
        this.lastUpdated = pLastUpdated;
    }

    public String getURL() {
        return url;
    }

    public String getCMS() {
        return cms;
    }

    public String getVersion() {
        return version;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
