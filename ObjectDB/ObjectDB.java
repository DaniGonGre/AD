package tutorial;

import java.util.List;
import javax.persistence.*;

public class ObjectDB {
    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(
                "$objectdb/db/p2.odb");
        
        EntityManager em = emf.createEntityManager();

        // EJERCICIO 1 Y 2
                
        try {
                   
        em.getTransaction().begin();
        for (int i = 0; i < 11; i++) {
            Point p = new Point(i, i, i);
            em.persist(p);
        }
        em.getTransaction().commit();
        
        } catch(Exception e) {
            System.out.println(e);
        }
        
        System.out.println("\n***EJERCICIO 1 Y 2***\n");    
        
        // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + q1.getSingleResult());

        // Find the average X value:
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Average X: " + q2.getSingleResult());

        // Retrieve all the Point objects from the database:
        TypedQuery<Point> query =
            em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            System.out.println(p);
        }

        // Close the database connection:
        em.close();
              
        // EJERCICIO 3
        EntityManager em2 = emf.createEntityManager();

        System.out.println("\n***EJERCICIO 3***\n");

        Query q3 = em2.createQuery("SELECT p FROM Point p where id=10");
        System.out.println("Atributos do punto con id =10: " + q3.getSingleResult());

        // Close the database connection:
        em2.close();
        
        // EJERCICIO 4
        EntityManager em3 = emf.createEntityManager();

        System.out.println("\n***EJERCICIO 4***\n");

        Query q4 = em3.createQuery("update Point p set y = y+2 where id=10");
        q4.executeUpdate();
        Query q5 = em3.createQuery("SELECT p FROM Point p where id=10");
        System.out.println("Actualizado punto con id =10: " + q5.getSingleResult());

        // Close the database connection:
        em3.close();
        
        emf.close();
    }
}
