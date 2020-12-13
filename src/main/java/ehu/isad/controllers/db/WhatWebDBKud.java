package ehu.isad.controllers.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WhatWebDBKud {

    private static final WhatWebDBKud nWhatWebDBKud = new WhatWebDBKud();

    public static WhatWebDBKud getWhatWebDBKud() {
        return nWhatWebDBKud;
    }

    private WhatWebDBKud() {}

    public ArrayList<String> lortuUrl() {
        String query = "select url, last_updated from server_historiala";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        ArrayList<String> urlList = new ArrayList<>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    String emaitza = rs.getString("url") + " (" + rs.getString("last_updated") + ")";
                    urlList.add(emaitza);
                }
            } catch(SQLException throwables){
                throwables.printStackTrace();
            }
        }
        return urlList;
    }

    public boolean sartutaDagoURL(String url) {
        String query = "select url from server_historiala where url='" + url + "'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            return rs.next();
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return false;
    }

    public void sartuURL(String url) {
        String query = "insert into server_historiala values('" + url + "', (select datetime('now', 'localtime')))";
        DBKud.getDBKud().execSQL(query);
    }

    public void eguneratuURL(String url) {
        String query = "update server_historiala set last_updated=(select datetime('now', 'localtime')) where url ='" + url + "'";
        DBKud.getDBKud().execSQL(query);
    }
}
