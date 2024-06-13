package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Carrello;
import model.PatternInput;
import model.Been.Utente;
import model.DAO.CarrelloDAO;
import model.DAO.UtenteDAO;

import java.io.IOException;

@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dove="/WEB-INF/registrazione.jsp";
        UtenteDAO utenteDAO= new UtenteDAO();
        Utente utente= utenteDAO.getUtenteByEmail(req.getParameter("email"));

        //se si effettua per esempio un login e una registrazione conteporaneamente, puo accadere di avere l'id di un untente in sessione con il carrello'dell'altro.
        synchronized (req.getSession()) {

            if (req.getSession().getAttribute("utente") != null)
                resp.sendRedirect("AreaUtenti");
            else {
                if (utente == null) {
                    //validazione dell'input
                    if (
                            req.getParameter("nome") != null && PatternInput.nome(req.getParameter("nome")) &&
                                    req.getParameter("email") != null && PatternInput.email(req.getParameter("email")) &&
                                    req.getParameter("pass") != null && PatternInput.password(req.getParameter("pass"))
                    ) {
                        utente = new Utente();
                        utente.setNome(req.getParameter("nome"));
                        utente.setEmail(req.getParameter("email"));
                        utente.setPassword(req.getParameter("pass"));

                        /*
                        if (req.getParameter("admin") != null && req.getParameter("admin").equals("on"))
                            utente.setAdmin(true);
                        else
                            utente.setAdmin(false);
                        */
                        utente.setAdmin(false);

                        utenteDAO.saveUtente(utente);
                        CarrelloDAO carrelloDAO = new CarrelloDAO();
                        carrelloDAO.createCarrello(utente.getId());

                        //cambio l'id da -1 generico per utenti non registrati con quello dell'utente
                        ((Carrello) req.getSession().getAttribute("carrello")).setIdUtente(utente.getId());

                        req.getSession().setAttribute("utente", utente);
                        resp.sendRedirect("AreaUtenti");
                    } else {
                        req.setAttribute("error1", true);
                        RequestDispatcher dispatcher = req.getRequestDispatcher(dove);
                        dispatcher.forward(req, resp);
                    }
                } else {
                    req.setAttribute("error2", true);
                    RequestDispatcher dispatcher = req.getRequestDispatcher(dove);
                    dispatcher.forward(req, resp);
                }
            }

        }

    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
