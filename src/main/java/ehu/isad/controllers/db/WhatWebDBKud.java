package ehu.isad.controllers.db;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WhatWebDBKud {

    private static final WhatWebDBKud nWhatWebDBKud = new WhatWebDBKud();

    public static WhatWebDBKud getWhatWebDBKud() {
        return nWhatWebDBKud;
    }

    private WhatWebDBKud() {}

    public void sartuURL(String url) {
        String query = "insert into server_historiala values('" + url + "')";
        DBKud.getDBKud().execSQL(query);
    }

    public ArrayList<String> lortuUrl() {
        String query = "select url from server_historiala";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        ArrayList<String> urlList = new ArrayList<>();
        try {
            while (rs.next()) {
                urlList.add(rs.getString("url"));
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
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
}
