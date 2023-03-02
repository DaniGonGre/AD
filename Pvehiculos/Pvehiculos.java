package pvehiculos;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.*;
import org.bson.Document;

public class Pvehiculos {
    
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
    
    public static void cFinalveh(Connection conn) throws SQLException {
        
        int id, ncompras, desconto = 0, anomatricula, prezoorixe, pf = 0;
        String dni, codveh, nomc = null, nomveh = null;
                                      
        // Creating a Mongo client 
        MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
        MongoCredential credential = MongoCredential.createCredential("sampleUser", "test", 
         "password".toCharArray()); 
        System.out.println("Connected to the database successfully");  
        MongoDatabase database = mongo.getDatabase("test"); 
        MongoCollection<Document> collection = database.getCollection("vendas");
        MongoCursor<Document> cursor = collection.find().iterator();
        
        //Conexión a objectDB
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(
                "/home/dam2a/Descargas/vehicli.odb");
        
            EntityManager em = emf.createEntityManager();

        // Itera a través de cada documento
        while (cursor.hasNext()) {
            Document doc = cursor.next();
    
            // Obtiene los valores de los campos "id", "dni" y "codveh" del documento actual
            id = doc.getInteger("_id");
            dni = doc.getString("dni");
            codveh = doc.getString("codveh");
            
            System.out.println(id + ".0 ," + dni + " ," + codveh);
            
            TypedQuery<Clientes> query2 =
                em.createQuery("SELECT c FROM Clientes c where c.dni= '" +dni + "'", Clientes.class);
            List<Clientes> results2 = query2.getResultList();
            for (Clientes c : results2) {

                nomc = c.getNomec();
                ncompras = c.getNcompras();
            
                System.out.println("nomec -> " + nomc + ". ncompras -> " + ncompras);
            
            if (ncompras != 0) {
                desconto = 500;
            } else
                desconto = 0;
            }
        
            TypedQuery<Vehiculos> query =
                em.createQuery("SELECT v FROM Vehiculos v where v.codveh= '" + codveh + "'", Vehiculos.class);
            List<Vehiculos> results = query.getResultList();
            for (Vehiculos v : results) {

                nomveh = v.getNomveh();
                anomatricula = v.getAnomatricula();
                prezoorixe = v.getPrezoorixe();
            
                pf = prezoorixe-((2019-anomatricula)*500) - desconto;
                    
                System.out.println("nomvhe -> " + nomveh + ", prezoorixe -> " + prezoorixe + ", anomatricula -> " + anomatricula);
                System.out.println(pf);

            }
            
            Statement st = null;
            st = conn.createStatement();
            
            String cadeai = "insert into finalveh(id,dni,nomec,vehf)"
            + " values ('" 
            + id + "','" 
            + dni + "','" 
            + nomc + "',('" 
            + nomveh + "'," 
            + pf + ") )";
            st.executeUpdate(cadeai);
            
            System.out.println("___________________________________________________");
            
            pf = 0;
            // Para borrar los datos de la tabla: delete from finalveh;

        }    
        em.close();
        emf.close();
    }
     /*
    public static void objectDB() {
        
        EntityManagerFactory emf =
        Persistence.createEntityManagerFactory(
                "/home/dam2a/Descargas/vehicli.odb");
        
        EntityManager em = emf.createEntityManager();
                  
        TypedQuery<Vehiculos> query =
            em.createQuery("SELECT v FROM Vehiculos v", Vehiculos.class);
        List<Vehiculos> results = query.getResultList();
        for (Vehiculos v : results) {
            System.out.println(v);
            
        }
             
        System.out.println("");
        
        EntityManager em2 = emf.createEntityManager();
                  
        TypedQuery<Clientes> query2 =
            em.createQuery("SELECT c FROM Clientes c", Clientes.class);
        List<Clientes> results2 = query2.getResultList();
        for (Clientes c : results2) {
            System.out.println(c);
        }
        
        em.close();
        em2.close();
        emf.close();
        
    }
    
    public static void mongoDB() {
        
        // Creating a Mongo client 
        MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
   
        // Creating Credentials 
        MongoCredential credential; 
        credential = MongoCredential.createCredential("sampleUser", "test", 
         "password".toCharArray()); 
        System.out.println("Connected to the database successfully");  
      
        // Accessing the database 
        MongoDatabase database = mongo.getDatabase("test"); 
 
        MongoCollection<Document> collection = database.getCollection("vendas");
      
        // Realiza una consulta para obtener todos los documentos
        MongoCursor<Document> cursor = collection.find().iterator();

        // Crea variables para almacenar los valores de los campos "id", "dni" y "codveh"
        int id;
        String dni;
        String codveh;

        // Itera a través de cada documento
        while (cursor.hasNext()) {
            Document doc = cursor.next();
    
            // Obtiene los valores de los campos "id", "dni" y "codveh" del documento actual
            id = doc.getInteger("_id");
            dni = doc.getString("dni");
            codveh = doc.getString("codveh");
    
            System.out.println("Id: " + id + ", dni= " + dni + ", codveh= " + codveh);
 
        }
    }
    
    public static void mNombreCompras() {
        
        EntityManagerFactory emf =
        Persistence.createEntityManagerFactory(
                "/home/dam2a/Descargas/vehicli.odb");
        
        EntityManager em = emf.createEntityManager();

        String nomc;
        int ncompras, desconto = 0;
        
        TypedQuery<Clientes> query2 =
            em.createQuery("SELECT c FROM Clientes c", Clientes.class);
        List<Clientes> results2 = query2.getResultList();
        for (Clientes c : results2) {

            nomc = c.getNomec();
            ncompras = c.getNcompras();
            
            System.out.println("Nome= " + nomc + ", numero de compras= " + ncompras);
            
            if (ncompras != 0) {
                desconto = 500;
            } else
                desconto = 0;
        }
        
        System.out.println("");
                         
        String nomveh;
        int anomatricula, prezoorixe, pf;
        
        TypedQuery<Vehiculos> query =
            em.createQuery("SELECT v FROM Vehiculos v", Vehiculos.class);
        List<Vehiculos> results = query.getResultList();
        for (Vehiculos v : results) {

            nomveh = v.getNomveh();
            anomatricula = v.getAnomatricula();
            prezoorixe = v.getPrezoorixe();
            
            pf = prezoorixe-((2019-anomatricula)*500) - desconto;
                    
            System.out.println("Nome do vehiculo: " + nomveh + ", ano matrícula: " + anomatricula + ", prezo orixe: " + prezoorixe);

            System.out.println("Precio final= " + pf);
            pf = 0;
        }
        
        em.close();
        emf.close();
        
    }
*/
    public static void main(String[] args) throws SQLException {
        
        cFinalveh(conectar());
    }
    
}
