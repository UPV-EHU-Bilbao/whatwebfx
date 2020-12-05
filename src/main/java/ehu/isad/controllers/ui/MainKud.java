package ehu.isad.controllers.ui;

public class MainKud {

    private static final MainKud nMainKud = new MainKud();

    public static MainKud getMainKud() {
        return nMainKud;
    }

    private MainKud() {}
}
