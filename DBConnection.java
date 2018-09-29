package md.library.isd.dbmanage;
import java.sql.*;
/**
 * @author Dan Tutunaru
 */
public class DBConnection {
    final private static String DBURI = "jdbc:mysql://localhost:3306/library?useSSL=false";
    final private static String DBUSER = "root";
    final private static String DBPASSWORD = "root";
    
    private Connection myConnection;
    
    
    /** Creates a new instance of MyDBConnection */
    public DBConnection() {
        this.init();
    }
    
    /**
     * Initialize the database connection 
     */
    public void init(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            myConnection=DriverManager.getConnection(DBURI,DBUSER,DBPASSWORD);
            }catch(Exception e){
            System.out.println("Failed to get connection");
            e.printStackTrace();
        }
    }
    
    /**
     * Get database connection
     * @return connection
     */
    public Connection getConnection(){
        return myConnection;
    }
    
    /**
     * Destroy database connection
     */
    public void destroy(){
        if(myConnection !=null){
            try {
                myConnection.close();
            } catch (SQLException ex) {
                
            }
        }
    }
}