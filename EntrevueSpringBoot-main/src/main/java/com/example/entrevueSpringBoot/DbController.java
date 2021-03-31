package com.example.entrevueSpringBoot;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement; 

import java.sql.ResultSet; 

public class DbController 
{

        // JDBC driver name and database URL 
        String JDBC_DRIVER = "org.h2.Driver"; 
        String DB_URL = "jdbc:h2:~/test";  
   
        //  Database credentials 
        String USER = "sa"; 
        String PASS = "";   

        Connection conn = null; 
        Connection conn2 = null; 
        Statement stmt = null;
        Statement stmt2 = null; 

	public void DbController() 
	{        
       
	}

        public void dbcreate() 
	{ 
try{
         // STEP 1: Register JDBC driver 
         Class.forName(JDBC_DRIVER);  
         
         // STEP 2: Open a connection 
         System.out.println("Connecting to a selected database..."); 
         conn = DriverManager.getConnection(DB_URL,USER,PASS); 
         System.out.println("Connected database successfully..."); 
         
         // STEP 3: Execute a query 
         stmt = conn.createStatement(); 
         String sqldrop = "DROP TABLE IF EXISTS FILMS";
         stmt.executeUpdate(sqldrop);
         String sqldrop2 = "DROP TABLE IF EXISTS ACTORS";
         stmt.executeUpdate(sqldrop2);
         String sqlcreate =  "CREATE TABLE ACTORS(id INT PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(255), prenom VARCHAR(255), titre VARCHAR(255))";  
         stmt.executeUpdate(sqlcreate);
         String sqlcreate2 =  "CREATE TABLE FILMS(id INT PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(255), description VARCHAR(255))";  
         stmt.executeUpdate(sqlcreate2);
         String sql = "INSERT INTO FILMS(titre, description) VALUES('Memento', 'Intriguant et confus')"; 
         
         stmt.executeUpdate(sql); 
         sql = "INSERT INTO FILMS(titre, description) VALUES('Coraline', 'Marionette inquietante')";  
         
         stmt.executeUpdate(sql); 
         sql = "INSERT INTO ACTORS(nom, prenom, titre) VALUES('amnesique', 'mais pas fou', 'Memento')"; 
         
         stmt.executeUpdate(sql); 
         sql = "INSERT INTO ACTORS(nom, prenom, titre) VALUES('marionette', 'en stop motion', 'Coraline')"; 
         
         stmt.executeUpdate(sql); 
         System.out.println("Inserted records into the table...");

         // STEP 4: Clean-up environment 
         stmt.close(); 
         conn.close(); 
      } catch(SQLException se) { 
         // Handle errors for JDBC 
         se.printStackTrace(); 
      } catch(Exception e) { 
         // Handle errors for Class.forName 
         e.printStackTrace(); 
      } finally { 
         // finally block used to close resources 
         try {
            if(stmt!=null) stmt.close();  
         } catch(SQLException se2) { 
         } // nothing we can do 
         try { 
            if(conn!=null) conn.close(); 
         } catch(SQLException se) { 
            se.printStackTrace(); 
         } // end finally try 
      } // end try 
      System.out.println("Goodbye!"); 
        }

public String dbReadDatabase() 
	{ 
        String leResult = "";
        try{
         // STEP 1: Register JDBC driver 
         Class.forName(JDBC_DRIVER);  
         
         // STEP 2: Open a connection 
         System.out.println("Connecting to a selected database..."); 
         conn = DriverManager.getConnection(DB_URL,USER,PASS); 
         System.out.println("Connected database successfully..."); 
         
         // STEP 3: Execute a query 
         stmt = conn.createStatement(); 

         String sqlselect = "SELECT id, titre, description FROM FILMS"; 
         ResultSet rs = stmt.executeQuery(sqlselect); 
         
         // STEP 4: Extract data from result set 
         while(rs.next()) 
         { 
            // Retrieve by column name 
            int id  = rs.getInt("id"); 
            String titre = rs.getString("titre"); 
            String description = rs.getString("description");  
            
            // Display values 
            System.out.println("ID: " + id); 
            System.out.println(", Titre: " + titre); 
            System.out.println(", Description: " + description); 

            leResult = leResult + "ID: " + id;
            leResult = leResult + ", Titre: " + titre;
            leResult = leResult + ", Description: " + description;

            String sqlSelectActors = "SELECT nom, prenom FROM ACTORS WHERE titre='" + titre + "'"; 
            stmt2 = conn.createStatement();
            ResultSet rsActors = stmt2.executeQuery(sqlSelectActors);
            
            while(rsActors.next()) 
            { 
               String lenom = rsActors.getString("nom"); 
               String leprenom = rsActors.getString("prenom"); 
               System.out.println("Featuring: " + lenom + " " + leprenom);
               leResult = leResult + "Featuring: " + lenom + " " + leprenom;
            }
         leResult = leResult + ";";
         }
         // STEP 5: Clean-up environment 
         stmt.close(); 
         conn.close(); 
      } catch(SQLException se) { 
         // Handle errors for JDBC 
         se.printStackTrace(); 
      } catch(Exception e) { 
         // Handle errors for Class.forName 
         e.printStackTrace(); 
      } finally { 
         // finally block used to close resources 
         try {
            if(stmt!=null) stmt.close();  
         } catch(SQLException se2) { 
         } // nothing we can do 
         try { 
            if(conn!=null) conn.close(); 
         } catch(SQLException se) { 
            se.printStackTrace(); 
         } // end finally try 
      } // end try 
      System.out.println("Goodbye!");
      return leResult; 
    }

public String dbGetMovie(String movieId) 
	{ 
        String leResult = "";
try{
         // STEP 1: Register JDBC driver 
         Class.forName(JDBC_DRIVER);  
         
         // STEP 2: Open a connection 
         System.out.println("Connecting to a selected database..."); 
         conn = DriverManager.getConnection(DB_URL,USER,PASS); 
         System.out.println("Connected database successfully..."); 
         
         // STEP 3: Execute a query 
         stmt = conn.createStatement(); 

         String sqlselect = "SELECT id, titre, description FROM FILMS WHERE id=" + movieId; 
         ResultSet rs = stmt.executeQuery(sqlselect); 
         
         // STEP 4: Extract data from result set 
         while(rs.next()) 
         { 
            // Retrieve by column name 
            int id  = rs.getInt("id"); 
            String titre = rs.getString("titre"); 
            String description = rs.getString("description");  
            
            // Display values 
            System.out.println("ID: " + id); 
            System.out.println(", Titre: " + titre); 
            System.out.println(", Description: " + description); 

            leResult = leResult + "ID: " + id;
            leResult = leResult + ", Titre: " + titre;
            leResult = leResult + ", Description: " + description + " ";

            String sqlSelectActors = "SELECT nom, prenom FROM ACTORS WHERE titre='" + titre + "'"; 
            stmt2 = conn.createStatement();
            ResultSet rsActors = stmt2.executeQuery(sqlSelectActors);
            
            while(rsActors.next()) 
            { 
               String lenom = rsActors.getString("nom"); 
               String leprenom = rsActors.getString("prenom"); 
               System.out.println("Featuring: " + lenom + " " + leprenom);
               leResult = leResult + "Featuring: " + lenom + " " + leprenom;
            }
         leResult = leResult + ";";
         }
         // STEP 5: Clean-up environment 
         stmt.close(); 
         conn.close(); 
      } catch(SQLException se) { 
         // Handle errors for JDBC 
         se.printStackTrace(); 
      } catch(Exception e) { 
         // Handle errors for Class.forName 
         e.printStackTrace(); 
      } finally { 
         // finally block used to close resources 
         try {
            if(stmt!=null) stmt.close();  
         } catch(SQLException se2) { 
         } // nothing we can do 
         try { 
            if(conn!=null) conn.close(); 
         } catch(SQLException se) { 
            se.printStackTrace(); 
         } // end finally try 
      } // end try 
      System.out.println("Goodbye!");
      return leResult; 
        }


}