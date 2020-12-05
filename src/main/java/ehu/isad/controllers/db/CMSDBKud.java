package ehu.isad.controllers.db;

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
        String query = ;
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
        String query = ;
        DBKud dbkudeatzaile = DBKud.getInstantzia();
        ResultSet rs = dbkudeatzaile.execSQL(query);

        try {
            while (rs.next()) {

            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return zenbakia;
    }

    // Metodo honek, URL bat pasatuz zer cms erabiltzen duen bueltatuko du.
    public String  URLlortuCMS (String URL) {
        String query = ;
        DBKud dbkudeatzaile = DBKud.getInstantzia();
        ResultSet rs = dbkudeatzaile.execSQL(query);

        try {
            while (rs.next()) {

            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return zenbakia;
    }
    //Metodo honek, URL bat emanda zer cms bertsio duen bueltatuko du.
    public String  lortuCMSBertsioa (String URL) {
        String query = ;
        DBKud dbkudeatzaile = DBKud.getInstantzia();
        ResultSet rs = dbkudeatzaile.execSQL(query);

        try {
            while (rs.next()) {

            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return zenbakia;
    }
    //Metodo honek, URL bat emanda, azkenengo aldiz eguneratu (Last updated) den data bueltatuko du.
    public String  URLlortuCMS (String URL) {
        String query = ;
        DBKud dbkudeatzaile = DBKud.getInstantzia();
        ResultSet rs = dbkudeatzaile.execSQL(query);

        try {
            while (rs.next()) {

            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return zenbakia;
    }
}
