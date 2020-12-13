package ehu.isad.controllers.db;

import ehu.isad.models.CMSModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CMSDBKud {

    private static final CMSDBKud instantzia = new CMSDBKud();

    public static CMSDBKud getDBKud(){
        return instantzia;
    }

    public ArrayList<CMSModel> getCMSModel() {
        int drupalId = getDrupalId();
        int joomlaId = getJoomlaId();
        int phpayadminId = getPhpayadminId();
        int wordpressId = getWordpressId();
        ArrayList<CMSModel> cmsModels = new ArrayList<>();
        String query = "select target_id,target,last_updated from targets";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        ArrayList<String> hirukote;
        ArrayList<ArrayList<String>> emaitzaZerr = new ArrayList<>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    hirukote = new ArrayList<>();
                    String urlEmaitza = rs.getString("target");
                    hirukote.add(urlEmaitza);
                    String idTargetEmaitza = rs.getString("target_id");
                    hirukote.add(idTargetEmaitza);
                    String lastUpdatedEmaitza = rs.getString("last_updated");
                    hirukote.add(lastUpdatedEmaitza);
                    emaitzaZerr.add(hirukote);
                }
                rs.close();
                for (ArrayList<String> x : emaitzaZerr) {
                    String url = x.get(0);
                    String idTarget = x.get(1);
                    String lastUpdated = x.get(2);
                    query = "select s.plugin_id,s.version from scans s,plugins p where target_id=" + idTarget;
                    rs = DBKud.getDBKud().execSQL(query);
                    String cms;
                    String version;
                    ArrayList<Integer> idPluginZerr = new ArrayList<>();
                    ArrayList<String> versionZerr = new ArrayList<>();
                    while (rs.next()) {
                        int idPluginEmaitza = rs.getInt(1);
                        idPluginZerr.add(idPluginEmaitza);
                        String versionEmaitza = rs.getString(2);
                        versionZerr.add(versionEmaitza);
                    }
                    if (idPluginZerr.contains(drupalId)) {
                        cms = "Drupal";
                        version = versionZerr.get(idPluginZerr.indexOf(drupalId));
                    } else if (idPluginZerr.contains(joomlaId)) {
                        cms = "Joomla";
                        version = versionZerr.get(idPluginZerr.indexOf(joomlaId));
                    } else if (idPluginZerr.contains(phpayadminId)) {
                        cms = "phpMyAdmin";
                        version = versionZerr.get(idPluginZerr.indexOf(phpayadminId));
                    } else if (idPluginZerr.contains(wordpressId)) {
                        cms = "WordPress";
                        version = versionZerr.get(idPluginZerr.indexOf(wordpressId));
                    } else {
                        cms = "ezezaguna";
                        version = "0";
                    }
                    cmsModels.add(new CMSModel(url, cms, version, lastUpdated));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return cmsModels;
    }

    private int getDrupalId() {
        String query = "select plugin_id from plugins where name='Drupal'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            return rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private int getJoomlaId() {
        String query = "select plugin_id from plugins where name='Joomla'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            return rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private int getPhpayadminId() {
        String query = "select plugin_id from plugins where name='phpMyAdmin'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            return rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private int getWordpressId() {
        String query = "select plugin_id from plugins where name='WordPress'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            return rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void sartuLerroa(String lerroa) {
        DBKud.getDBKud().execSQL(lerroa);
    }

    public void eguneratuLastUpdated(String scanUrl) {
        String query = "update targets set last_updated=strftime('%Y/%m/%d', date('now')) where target like '%" + scanUrl + "%'";
        DBKud.getDBKud().execSQL(query);
    }

    public void ezabatuKontsulta(String url) {
        String query = "select target_id from targets where target='" + url + "'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) {
                int idTarget = rs.getInt(1);
                rs.close();
                query = "delete from scans where target_id=" + idTarget;
                DBKud.getDBKud().execSQL(query);
                query = "delete from targets where target_id=" + idTarget;
                DBKud.getDBKud().execSQL(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void eguneratuUrl(String urlZaharra, String urlBerria) {
        String query = "UPDATE targets SET target='" + urlBerria + "' WHERE target='" + urlZaharra + "'";
        DBKud.getDBKud().execSQL(query);
        eguneratuLastUpdated(urlBerria);
    }

    public void ezabatuScanZaharra(String urlBerria) {
        String query = "select target_id from targets where target='" + urlBerria + "'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) {
                int idTarget = rs.getInt(1);
                rs.close();
                query = "delete from scans where target_id=" + idTarget;
                DBKud.getDBKud().execSQL(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
