package ejerciciomongo;

import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.Iterator;
import org.bson.Document;

public class EjercicioMongo {

    public static void main(String[] args) {

      // Creating a Mongo client 
      MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
   
      // Creating Credentials 
      MongoCredential credential; 
      credential = MongoCredential.createCredential("sampleUser", "test", 
         "password".toCharArray()); 
      System.out.println("Connected to the database successfully");  
      
      // Accessing the database 
      MongoDatabase database = mongo.getDatabase("test"); 
 
      MongoCollection<Document> collection = database.getCollection("datos");
 /*     
        // Insertar un nuevo documento a la colección datos 
      
        Document document = new Document("_id", 11)
            .append("puntuacion", 9)
            .append("exame", "test")
            .append("estudiante", 20);
      
        collection.insertOne(document);
        System.out.println("Document inserted successfully");

        // Consultar los documentos de la colección
        FindIterable<Document> iterDoc = collection.find();
            int i = 1;
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
		System.out.println(it.next());
		i++;
	}
        */
            
        // Listar los códigos de estudiantes y la puntuación de los exámenes teóricos.
        Document query = new Document("exame", "teoria");
        MongoCursor<Document> cursor = collection.find(query).iterator();

        // Recorremos el cursor e imprimimos los códigos de los estudiantes y sus puntuaciones
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String codigo = doc.getInteger("estudiante").toString();
            String puntuacion = doc.getInteger("puntuacion").toString();
            System.out.println("Código de estudiante: " + codigo + ", puntuación: " + puntuacion);
        }

/*
        
        // Incrementar en 2 la puntuación del examen tipo test del estudiante 40
               collection.updateOne(Filters.and(
                Filters.eq("exame", "test"),
                Filters.eq("estudiante", 40)
            ),
            Updates.inc("puntuacion", 2)
        );
               
               System.out.println("Puntuación incrementada");
 */       
    }
    
}
