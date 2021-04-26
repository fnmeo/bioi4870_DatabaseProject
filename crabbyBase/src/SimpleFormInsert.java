
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")

public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String scientificName = request.getParameter("crabInput");
      System.out.print(scientificName);
      PrintWriter out = response.getWriter();
      Connection connection = null;
      String insertSql = "SELECT * FROM sequences WHERE scientificName = ?";

      DBConnection.getDBConnection(getServletContext());
      connection = DBConnection.connection;
      try
      {
      PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
      preparedStmt.setString(1, scientificName);
      preparedStmt.execute();
      ResultSet rs = preparedStmt.executeQuery();

      
      while (rs.next()) {
          String geneName = rs.getString("geneName").trim();
          String sciName = rs.getString("scientificName").trim();
          String sequenceLen = rs.getString("sequenceLength").trim();
          String source = rs.getString("source").trim();
          String geneSymbol = rs.getString("geneSymbol").trim();
          String nucleotide = rs.getString("nucleotideSeq").trim();
          
          String str = nucleotide;
          String parsedStr = str.replaceAll("(.{50})", "$1\n");

          if (scientificName.isEmpty() || scientificName.contains(scientificName)) {
             out.println("Gene Name: " + geneName);
             out.println("Scientific Name: " + sciName);
             out.println("Sequence Length: " + sequenceLen);
             out.println("Sequence Location: " + source);
             out.println("Acc Number: " + geneSymbol);
             out.println("SEQUENCE: " + parsedStr);
          }
       }
      }
      catch(SQLException se)
      {
    	  se.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
