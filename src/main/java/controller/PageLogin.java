package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static model.Utils.createCarrello;


//questa classe serve come maschera, permette di accedere alla pagina di login, altrimenti senza di essa avrei duovuto rendere
//la jsp del login visivile al browuser mettendola fuori da WEB-INF, ma non voglio che l'utente possa accedere direttamente alla jsp
//quando per esempio a gia effettuato il login, quindi utilizzo questa servlet solo per effettuare dei controlli e nel caso in cui
//non è loggato allora potra accedere alla pagina dove potra inserire le credenziali.
@WebServlet("/pageLogin")
public class PageLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        if(req.getSession().getAttribute("utente")==null){
            RequestDispatcher dispatcher= req.getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(req,resp);
        }else {
            resp.sendRedirect("AreaUtenti");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
