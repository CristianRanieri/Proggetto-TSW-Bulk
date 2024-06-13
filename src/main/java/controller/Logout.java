package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Carrello;
import model.DAO.CarrelloDAO;
import model.Been.Utente;

import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //potrebbe accadere che si sta effettuando un operazione come la creazione di un ordine, ed invalidare la sessione.
        synchronized (req.getSession()) {

            if (req.getSession().getAttribute("utente") == null)
                resp.sendRedirect("pageLogin");
            else {
                CarrelloDAO carrelloDAO = new CarrelloDAO();
                Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
                carrelloDAO.caricaCarrello(carrello.getIdUtente(),carrello);

                req.getSession().invalidate();
                resp.sendRedirect("index.jsp");
            }

        }

    }
}
