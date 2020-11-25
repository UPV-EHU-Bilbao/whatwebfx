package ehu.isad.controller.db;

public class CMSKud {

    private static final CMSKud nCMSKud = new CMSKud();

    public static CMSKud getCMSKud() {
        return nCMSKud;
    }

    private CMSKud() {}

}
