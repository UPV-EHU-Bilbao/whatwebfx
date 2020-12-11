package ehu.isad.controllers.db;

import ehu.isad.models.CMSModel;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CMSDBKud {

    private static final CMSDBKud instantzia = new CMSDBKud();

    public static CMSDBKud getDBKud(){
        return instantzia;
    }

    //CMS bat pasatuz parametro gisa, eskaneatutako url-en artean zenbat (int) url duten cms hori.
    public int lortuCMSKopurua (String cms) {
        String query = "select count (*) from Whatweb where Whatweb.cms = '"+cms+"'";;
        DBKud dbkudeatzaile = DBKud.getDBKud();
        ResultSet rs = dbkudeatzaile.execSQL(query);
        int zenbakia=0;
        try {
            while (rs.next()) {
                zenbakia=zenbakia+1;
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return zenbakia;
    }


    //Metodo honek, cms hori duten url-ak agertuko dira pantailan.
    /*public String  lortuCMSURL (String cms) {
        String query = "select * from Whatweb where Whatweb.cms = '"+cms+"'";
        DBKud dbkudeatzaile = DBKud.getInstantzia();
        ResultSet rs = dbkudeatzaile.execSQL(query);
        try {
            while (rs.next()) {

            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }*/


    // Metodo honek, URL bat pasatuz zer cms erabiltzen duen bueltatuko du.
    public String  URLlortuCMS (String URL) {
        String query = "select cms from Whatweb where Whatweb.url = '"+URL+"'";
        DBKud dbkudeatzaile = DBKud.getDBKud();
        ResultSet rs = dbkudeatzaile.execSQL(query);
        String cms = "";
        try {
            while (rs.next()) {
                cms=rs.getString("cms");
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return cms;
    }


    //Metodo honek, URL bat emanda zer cms bertsio duen bueltatuko du.
    public String  lortuCMSBertsioa (String URL) {
        String query = "select bertsioa from Whatweb where Whatweb.url = '"+URL+"'";;
        DBKud dbkudeatzaile = DBKud.getDBKud();
        ResultSet rs = dbkudeatzaile.execSQL(query);
        String bertsioa = "";
        try {
            while (rs.next()) {
            bertsioa=rs.getString("bertsioa");
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return bertsioa;
    }


    //Metodo honek, URL bat emanda, azkenengo aldiz eguneratu (Last updated) den data bueltatuko du.
    public String  lortuURLData (String URL) {
        String query = "select lastupdated from Whatweb where Whatweb.url = '"+URL+"'";;
        DBKud dbkudeatzaile = DBKud.getDBKud();
        ResultSet rs = dbkudeatzaile.execSQL(query);
        String lastupdated = "";
        try {
            while (rs.next()) {
                lastupdated=rs.getString("lastupdated");
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return lastupdated;
    }

    public ArrayList<CMSModel> getCMSModel() {
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
                    String cms = "";
                    String version = "";
                    ArrayList<Integer> idPluginZerr = new ArrayList<>();
                    ArrayList<String> versionZerr = new ArrayList<>();
                    while (rs.next()) {
                        int idPluginEmaitza = rs.getInt(1);
                        idPluginZerr.add(idPluginEmaitza);
                        String versionEmaitza = rs.getString(2);
                        versionZerr.add(versionEmaitza);
                    }
                    if (idPluginZerr.contains(414)) {
                        cms = "Drupal";
                        version = versionZerr.get(idPluginZerr.indexOf(414));
                    } else if (idPluginZerr.contains(732)) {
                        cms = "Joomla";
                        version = versionZerr.get(idPluginZerr.indexOf(732));
                    } else if (idPluginZerr.contains(1149)) {
                        cms = "phpMyAdmin";
                        version = versionZerr.get(idPluginZerr.indexOf(1149));
                    } else if (idPluginZerr.contains(1716)) {
                        cms = "WordPress";
                        version = versionZerr.get(idPluginZerr.indexOf(1716));
                    } else {
                        cms = "ezezaguna";
                        version = "0";
                    }
                    cmsModels.add(new CMSModel(url,cms,version, lastUpdated));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return cmsModels;
    }

    public void sartuLerroa(String lerroa) {
        DBKud.getDBKud().execSQL(lerroa);
    }

    public void eguneratuLastUpdated(String scanUrl) {
        String query = "update targets set last_updated=strftime('%Y/%m/%d', date('now')) where target like '%" + scanUrl + "%'";
        DBKud.getDBKud().execSQL(query);
    }
}
