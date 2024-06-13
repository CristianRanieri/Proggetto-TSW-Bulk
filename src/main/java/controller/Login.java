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
import model.DAO.ProdottoDAO;
import model.DAO.UtenteDAO;

import java.io.IOException;

import static model.Utils.createCarrello;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dove="index.jsp";

        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        //se si effettuano piu login contemporaneamente si possono avere id di un untente con il carrello di un altro.
        synchronized (req.getSession()) {

            if (req.getSession().getAttribute("utente") != null) {
                resp.sendRedirect("AreaUtenti");
            } else {
                if (req.getParameter("email") != null && PatternInput.email(req.getParameter("email"))
                        && req.getParameter("pass") != null && PatternInput.password(req.getParameter("pass"))) {
                    UtenteDAO utenteDAO = new UtenteDAO();
                    Utente utente = new Utente();
                    utente.setEmail(req.getParameter("email"));
                    utente.setPassword(req.getParameter("pass"));

                    //controllo se l'utente si trova nel database
                    utente = utenteDAO.getUtenteByEmailPass(utente);
                    if (utente != null) {
                        //visto che non è loggato, inserisco l'utente nella sessione
                        req.getSession().setAttribute("utente", utente);
                        CarrelloDAO carrelloDAO = new CarrelloDAO();
                        Carrello carr;
                        //se il carrello contiene elementi viene sovrascritto altrimenti si lascia il contenuto inserito prima del login
                        if (!(carr = (Carrello) carrelloDAO.getContnutoCarrelloByUtente(utente.getId())).getContenutoCarrello().isEmpty()) {
                            req.getSession().setAttribute("carrello", carr);
                        }
                        //cambio l'id da -1 generico per utenti non registrati con quello dell'utente
                        ((Carrello) req.getSession().getAttribute("carrello")).setIdUtente(utente.getId());

                    } else {
                        req.setAttribute("error", true);
                        dove = "/WEB-INF/login.jsp";
                    }
                } else {
                    req.setAttribute("error", true);
                    dove = "/WEB-INF/login.jsp";
                }
                RequestDispatcher dispatcher = req.getRequestDispatcher(dove);
                dispatcher.forward(req, resp);
            }

        }

    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
