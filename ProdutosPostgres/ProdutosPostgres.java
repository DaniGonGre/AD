package produtosjava;

import java.sql.*;

public class ProdutosPostgres {

    public static Connection conectar() throws SQLException{
    
        String driver = "jdbc:postgresql:";
        String host = "//localhost:"; // tamen poderia ser una ip como "192.168.1.14"
        String porto = "5432";
        String sid = "postgres";
        String usuario = "dam2a";
        String password = "castelao";
        String url = driver + host+ porto + "/" + sid;
        
    Connection conn = DriverManager.getConnection(url,usuario,password);               
        
    return conn;
    }
    
    public static void metodo(Connection conn) throws SQLException {
        
        Statement st = null;
        st = conn.createStatement();
        
        String cidade = "barna";
        String codigo = "p2";
        int codpost = 36452;
        String descricion = "piso";
        int prezo = 16;
        
        String cadeai = "insert into produtos(codigo,descricion,prezo,ci)"
        + " values ('" 
        + codigo + "','" 
        + descricion + "'," 
        + prezo + ",('" 
        + cidade + "'," 
        + codpost + ") )";
        st.executeUpdate(cadeai);

        
        String cadeau = "update produtos set descricion='"+ descricion
        +"', prezo="+prezo
        +" , ci.cidade='barna' where codigo='"+codigo+"'";
        st.executeUpdate(cadeau);
        
        String cadeau2 = "update produtos set ci.cp=(ci).cp+1 where (ci).cidade='"+ cidade+"'";
        st.executeUpdate(cadeau2);
        
        String consultalistado = "select produtos.*, (ci).cidade, (ci).cp from produtos ";
        ResultSet r = st.executeQuery(consultalistado);
        while(r.next()){
            codigo = r.getString("codigo");
            descricion = r.getString("descricion");
            prezo = r.getInt("prezo");
            cidade = r.getString("cidade");
            codpost = r.getInt("cp");
                    

        }

        conn.close();
    }
    
    public static void main(String[] args) throws SQLException {

        metodo(conectar());
    }
    
}
