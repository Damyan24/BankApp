package bank.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bank.entities.Transaction;
import bank.entities.User;

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("receiver").trim();
        String amount1 = request.getParameter("amount").trim();
        
        
      
       

        EntityManager em = Persistence.createEntityManagerFactory("Bank").createEntityManager();
        
        HttpSession session = request.getSession();
        User sender = (User) session.getAttribute("User");
        
        
        String error = null;
        
        if (email.isEmpty() || amount1.isEmpty()) {
        	error = "Please fill in the form";
       	 request.setAttribute("TransferError", error);
       	request.getRequestDispatcher("/views/main.jsp").forward(request, response);
        }

      
        
        
        
        
        
        BigDecimal amount = new BigDecimal(amount1);
        
        if(!EmailValidation(email,em)) {
        	error = "No account associated with this email";
        	 request.setAttribute("TransferError", error);
        }
        
        if(sender.getBalance().compareTo(amount) == -1) {
        	error = "Not enough balance";
        	 request.setAttribute("TransferError", error);
        }
        
        
        if(error == null) {
        	
        	
        	 User receiver = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                     .setParameter("email", email)
                     .getSingleResult();
        	 
        	Transaction positive = new Transaction();
        	positive.setTransactionAmount(amount);
        	positive.setTransactionDate(new Date());
        	positive.setTransactionType("positive");
        	positive.setUser(receiver);
        	positive.setReceiver(sender.getEmail());
        	
        	Transaction negative = new Transaction();
        	negative.setTransactionAmount(amount.multiply(BigDecimal.valueOf(-1)));
        	negative.setTransactionDate(new Date());
        	negative.setTransactionType("negative");
        	negative.setUser(sender);
        	negative.setReceiver(receiver.getEmail());
        	
        	EntityTransaction transaction = em.getTransaction();
        	
        	receiver.setBalance(receiver.getBalance().add(amount));
        	receiver.addTransaction(positive);
        	sender.setBalance(sender.getBalance().subtract(amount));
        	sender.addTransaction(negative);
        	
 

        	try {
        	    transaction.begin();
        	    em.persist(positive);
        	    em.persist(negative);
        	    em.merge(sender);
        	    em.merge(receiver);
        	    transaction.commit();
        	} catch (Exception e) {
        	    if (transaction.isActive()) {
        	        transaction.rollback();
        	    }
        	    e.printStackTrace();
        	} finally {
        	    em.close();
        	    
        	}
        	
        }
        
       
        request.getRequestDispatcher("/views/main.jsp").forward(request, response);
        
}
    
    private boolean EmailValidation(String email,EntityManager em){
    	
    	Query query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);

        Long count = (Long) query.getSingleResult();
        
        return count > 0;
    }
}