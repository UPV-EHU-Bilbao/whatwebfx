package ehu.isad.controllers.ui;

public class CMSKud {

    private static final CMSKud nCMSKud = new CMSKud();

    public static CMSKud getCMSKud() {
        return nCMSKud;
    }

    private CMSKud() {}
}
