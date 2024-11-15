package configDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class configDB {
    
    private String jdbcURL = "jdbc:mysql://localhost:3306/2210010485_pbo2";
    private String username = "root";
    private String password = "";
    
    public configDB(){}

    public Connection getKoneksi() throws SQLException {
        try {
            // Tidak perlu registrasi driver manual
            System.out.println("Koneksi DB Berhasil");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return DriverManager.getConnection(jdbcURL, username, password);
    }
    
    public boolean DuplicateKey(String NamaTabel, String PrimaryKey, String isiData) {
    boolean hasil = false;

    try {
        String SQL = "SELECT * FROM " + NamaTabel + " WHERE " + PrimaryKey + " = '" + isiData + "'";
        Statement perintah = getKoneksi().createStatement();
        ResultSet hasilData = perintah.executeQuery(SQL);
        
        // Cek jika hasilData memiliki data
        hasil = hasilData.next();
        perintah.close();
    } catch (Exception e) {
        System.out.println(e.toString());
    }
    return hasil;   
    }
    
    public String getFieldTabel(String[] FieldTabelnya){
        String hasilnya="";
        int deteksiIndexAkhir=FieldTabelnya.length-1;
        try {
            for (int i = 0; i < FieldTabelnya.length; i++) {
                if (i==deteksiIndexAkhir) {
                    hasilnya=hasilnya+FieldTabelnya[i];
                }else{
                    hasilnya=hasilnya+FieldTabelnya[i]+",";
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "("+hasilnya+")";
    }
    
    public String getIsiTabel(String[] IsiTabelnya) {
    String hasilnya = "";
    int DeteksiIndex = IsiTabelnya.length - 1;
    try {
        for (int i = 0; i < IsiTabelnya.length; i++) {
            if (i == DeteksiIndex) {
                hasilnya += "'" + IsiTabelnya[i] + "'";
            } else {
                hasilnya += "'" + IsiTabelnya[i] + "',";
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
    }
    return "(" + hasilnya + ")";
    }

    
    public void simpanDinamis(String NamaTabel, String[] Fieldnya, String[] Isinya)
    {
        try {
            String SQLSave = "INSERT INTO " + NamaTabel + " " + getFieldTabel(Fieldnya) + " VALUES " + getIsiTabel(Isinya);

            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQLSave);
            perintah.close();
            System.out.println("Data Berhasil Disimpan");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public String getFieldValueEdit(String[] Field, String[] value){
        String hasil = "";
        int deteksi = Field.length-1;
        try {
            for (int i = 0; i < Field.length; i++) {
                if (i==deteksi){
                    hasil = hasil +Field[i]+" ='"+value[i]+"'";
                }else{
                   hasil = hasil +Field[i]+" ='"+value[i]+"',";  
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return hasil;
    }
    
    public void UbahDinamis(String NamaTabel, String PrimaryKey, String IsiPrimary,String[] Field, String[] Value){
        
        try {
           String SQLUbah = "UPDATE "+NamaTabel+" SET "+getFieldValueEdit(Field, Value)+" WHERE "+PrimaryKey+"='"+IsiPrimary+"'";
           Statement perintah = getKoneksi().createStatement();
           perintah.executeUpdate(SQLUbah);
           perintah.close();
           getKoneksi().close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
    }
    
    public void HapusDinamis(String NamaTabel, String PK, String isi){
        try {
            String SQL="DELETE FROM "+NamaTabel+" WHERE "+PK+"='"+isi+"'";
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQL);
            perintah.close();
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }




}
