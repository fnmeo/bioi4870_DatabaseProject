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

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }


   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      String scientificName = request.getParameter("crabInput");
	      System.out.print(scientificName);
	      PrintWriter out = response.getWriter();
	      Connection connection = null;
	      String insertSql = "SELECT * FROM organism WHERE scientificName = ?";

	      DBConnection.getDBConnection(getServletContext());
	      connection = DBConnection.connection;
	      try
	      {
	      PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
	      preparedStmt.setString(1, scientificName);
	      preparedStmt.execute();
	      ResultSet rs = preparedStmt.executeQuery();

	      
	      while (rs.next()) {
	          String organismName = rs.getString("organismName").trim();
	          String sciName = rs.getString("scientificName").trim();
	          String desc = rs.getString("description").trim();
	          
	          if (scientificName.isEmpty() || scientificName.contains(scientificName)) {
	             out.println("Gene Name: " + organismName);
	             out.println("Scientific Name: " + sciName);
	             out.println("Description: " + desc);
	             out.println("<img src=\"images/" + sciName +".jpg\" alt=\"" + sciName + "\">" );
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
