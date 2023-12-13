package bank.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import bank.entities.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String birthday = request.getParameter("birthday");

        List<String> errorMessages = new ArrayList<>();

        if (alreadyExistValidation(email)) {
            errorMessages.add("There is an account already associated with this email");
        }

        if (!emailValidation(email)) {
            errorMessages.add("Invalid email");
        }

        if (!passwordValidation(password)) {
            errorMessages.add("Invalid password");
        }

        if (!firstNameValidation(firstName)) {
            errorMessages.add("Invalid first name");
        }

        if (!lastNameValidation(lastName)) {
            errorMessages.add("Invalid last name");
        }

        if (!ageValidation(birthday)) {
            errorMessages.add("Invalid age");
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errorMessages", errorMessages);
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password.trim(), BCrypt.gensalt(12)));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            BigDecimal value = new BigDecimal(0);
            user.setBalance(value);

            try {
                user.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            EntityManager entityManager = Persistence.createEntityManagerFactory("Bank").createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                entityManager.persist(user);
                transaction.commit();

                response.sendRedirect(request.getContextPath() + "/views/index.jsp");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
        }
    }

    private boolean alreadyExistValidation(String email) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("Bank").createEntityManager();

        Query query = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);

        Long count = (Long) query.getSingleResult();

        entityManager.close();

        return count > 0;
    }

        



    private boolean emailValidation(String email) {
        return Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
                .matcher(email)
                .matches();
    }

    private boolean passwordValidation(String password) {
        return Pattern.compile("^(?=.*[A-Z])[a-zA-Z0-9]{8,}$")
                .matcher(password)
                .matches();
    }

    private boolean firstNameValidation(String firstName) {
        return Pattern.compile("^[a-zA-Z]+$")
                .matcher(firstName)
                .matches();
    }

    private boolean lastNameValidation(String lastName) {
        return Pattern.compile("^[a-zA-Z]+$")
                .matcher(lastName)
                .matches();
    }

    private boolean ageValidation(String birthday) {
        try {
            LocalDate birth = LocalDate.parse(birthday);
            LocalDate current = LocalDate.now();
            LocalDate dt1 = LocalDate.parse("1943-01-01"); 

            if (birth.isAfter(current)|| birth.isBefore(dt1)) {
                return false;
            }
            int age = Period.between(birth, current).getYears();

            return age > 18;
        } catch (Exception e) {
            return false;
        }
    }
}