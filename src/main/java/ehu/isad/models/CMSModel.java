package ehu.isad.models;

public class CMSModel {

    private final String url;
    private final String cms;
    private final String version;
    private String lastUpdated; //TODO Model bat egin daiteke atributu honetarako



    public CMSModel(String pUrl, String pCms, String pVersion, String pLastUpdated) {
        this.url = pUrl;
        this.cms = pCms;
        this.version = pVersion;
        this.lastUpdated = pLastUpdated;
    }
}
