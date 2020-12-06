/*package ehu.isad.controllers.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CMSDBKud {

    private static final CMSDBKud instantzia = new CMSDBKud();

    public static CMSDBKud getInstantzia(){
        return instantzia;
    }
}


    //CMS bat pasatuz parametro gisa, eskaneatutako url-en artean zenbat (int) url duten cms hori.
    public int lortuCMSKopurua (String cms) {
        String query = "select count (*) from Whatweb where Whatweb.cms = '"+cms+"'";;
        DBKud dbkudeatzaile = DBKud.getInstantzia();
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
    public String  lortuCMSURL (String cms) {
        String query = "select * from Whatweb where Whatweb.cms = '"+cms+"'";
        DBKud dbkudeatzaile = DBKud.getInstantzia();
        ResultSet rs = dbkudeatzaile.execSQL(query);
        try {
            while (rs.next()) {

            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }


    // Metodo honek, URL bat pasatuz zer cms erabiltzen duen bueltatuko du.
    public String  URLlortuCMS (String URL) {
        String query = "select cms from Whatweb where Whatweb.url = '"+URL+"'";
        DBKud dbkudeatzaile = DBKud.getInstantzia();
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
        DBKud dbkudeatzaile = DBKud.getInstantzia();
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
        DBKud dbkudeatzaile = DBKud.getInstantzia();
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
}
*/
*/