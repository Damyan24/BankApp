package bank.servlets;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import bank.entities.User;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        
        String error ;
        
        
       
        
    
        	if(Validation(email,password)) {
        		 EntityManager em = Persistence.createEntityManagerFactory("Bank").createEntityManager();

        		        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
        		                .setParameter("email", email)
        		                .getSingleResult();
        		        em.close();
        		        
        		        request.setAttribute("User", user);
        		        request.getRequestDispatcher("/views/main.jsp").forward(request, response);
        }	
        	else {
        	error = "Invalid email or password";
        	request.setAttribute("errorMessage",error);
        	
        	request.getRequestDispatcher("/views/index.jsp").forward(request, response);
        }
        	
        }	
      
        
	
	
	
	private boolean Validation(String email, String password) {
	    EntityManager em = Persistence.createEntityManagerFactory("Bank").createEntityManager();

	    try {
	        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
	                .setParameter("email", email)
	                .getSingleResult();

	        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
	            return true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    } finally {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }

	    return false;
	}


}
