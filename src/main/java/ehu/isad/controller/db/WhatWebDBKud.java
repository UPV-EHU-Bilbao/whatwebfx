package ehu.isad.controller.db;

public class WhatWebDBKud {

    private static final WhatWebDBKud nWhatWebDBKud = new WhatWebDBKud();

    public static WhatWebDBKud getWhatWebDBKud() {
        return nWhatWebDBKud;
    }

    private WhatWebDBKud() {}
}
