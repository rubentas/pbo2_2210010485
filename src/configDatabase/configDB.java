    package configDatabase;

    import java.awt.HeadlessException;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.sql.ResultSet;
    import javax.swing.JOptionPane;
    import javax.swing.JTable;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.TableColumn;

    import java.io.File;
//  berfungsi mengambil file laporan yang dibuar Ireport
    import net.sf.jasperreports.engine.JREmptyDataSource;
    import net.sf.jasperreports.engine.JRException;
    import net.sf.jasperreports.engine.JasperCompileManager;
    import net.sf.jasperreports.engine.JasperFillManager;
    import net.sf.jasperreports.engine.JasperPrint;
    import net.sf.jasperreports.engine.JasperReport;
    import net.sf.jasperreports.engine.design.JRDesignQuery;
    import net.sf.jasperreports.engine.design.JasperDesign;
    import net.sf.jasperreports.engine.xml.JRXmlLoader;
    import net.sf.jasperreports.view.JasperViewer;


public class configDB {
    
    private final String jdbcURL = "jdbc:mysql://localhost:3306/2210010485_pbo2";
    private final String username = "root";
    private final String password = "";
    
    private DefaultTableModel Modelnya;
    private TableColumn Kolomnya;
    
    public configDB(){}

    public Connection getKoneksi() throws SQLException {
        try {
//            Driver mysqldriver = new com.mysql.jdbc.Driver();
//            DriverManager.registerDriver(mysqldriver);
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
        try (Statement perintah = getKoneksi().createStatement()) {
            ResultSet hasilData = perintah.executeQuery(SQL);
            
            // Cek jika hasilData memiliki data
            hasil = hasilData.next();
        }
    } catch (SQLException e) {
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
            String SQLSave = "INSERT INTO " + NamaTabel + " " + getFieldTabel(Fieldnya) + " VALUES " 
                    + getIsiTabel(Isinya);

            try (Statement perintah = getKoneksi().createStatement()) {
                perintah.executeUpdate(SQLSave);
            }
            System.out.println("Data Berhasil Disimpan");
        } catch (SQLException e) {
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
    
    public void UbahDinamis(String NamaTabel, String PrimaryKey, String IsiPrimary
            ,String[] Field, String[] Value){
        
        try {
           String SQLUbah = "UPDATE "+NamaTabel+" SET "+getFieldValueEdit(Field, Value)
                   +" WHERE "+PrimaryKey+"='"+IsiPrimary+"'";
            try (Statement perintah = getKoneksi().createStatement()) {
                perintah.executeUpdate(SQLUbah);
            }
           getKoneksi().close();
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
        
    }
    
    public void HapusDinamis(String NamaTabel, String PK, String isi){
        try {
            String SQL="DELETE FROM "+NamaTabel+" WHERE "+PK+"='"+isi+"'";
            try (Statement perintah = getKoneksi().createStatement()) {
                perintah.executeUpdate(SQL);
            }
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        } catch (HeadlessException | SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public void settingJudulTabel(JTable Tabelnya, String[] JudulKolom){
        try {
            Modelnya = new DefaultTableModel();
            Tabelnya.setModel(Modelnya);
            for (String JudulKolom1 : JudulKolom) {
                Modelnya.addColumn(JudulKolom1);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void settingLebarKolom(JTable Tabelnya,int[] Kolom){
      try {
          Tabelnya.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            for (int i = 0; i < Kolom.length; i++) {
            Kolomnya =Tabelnya.getColumnModel().getColumn(i);
            Kolomnya.setPreferredWidth(Kolom[i]);   
        }
          
        
        } catch (Exception e) {
          System.out.println(e.toString());
        }
  }
    
    public Object[][] isiTabel(String SQL, int jumlah){
        Object[][] data =null;
        try {
            Statement perintah = getKoneksi().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
         );
        ResultSet dataset = perintah.executeQuery(SQL);
        dataset.last();
        int baris = dataset.getRow();
        dataset.beforeFirst();
        int j =0;
         
        data = new Object[baris][jumlah];
         
        while (dataset.next()){
            for (int i = 0; i < jumlah; i++) {
                data[j][i]=dataset.getString(i+1);
            }
            j++;
        }
         
        }catch (SQLException e) {
            System.err.print(e.toString());
        }
      
        return data;
    }
    
    public void tampilTabel(JTable Tabelnya, String SQL, String[] Judul){
        try {
            Tabelnya.setModel(new DefaultTableModel(isiTabel(SQL,Judul.length), Judul));
        }catch (Exception e) {
        System.out.println(e.toString());
        }
    }
    
    public void tampilLaporan(String laporanFile, String SQL) throws SQLException{
        try {
          File file = new File(laporanFile);
          JasperDesign jasDes = JRXmlLoader.load(file);

           JRDesignQuery sqlQuery = new JRDesignQuery();
           sqlQuery.setText(SQL);
           jasDes.setQuery(sqlQuery);

           JasperReport JR = JasperCompileManager.compileReport(jasDes);
           JasperPrint JP = JasperFillManager.fillReport(JR,null,getKoneksi()); 
           JasperViewer.viewReport(JP,false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null,e.toString());       

        }
    }
}
