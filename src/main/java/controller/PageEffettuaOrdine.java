package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Carrello;
import model.Been.ContenutoCarrello;

import java.io.IOException;
import java.util.ArrayList;

import static model.Utils.createCarrello;

//servlet maschera visto che solo gli utenti che hanno effettuato l'accesso possono accedere alla pagina per inserire i dati ed effettuare
//la registrazione
@WebServlet("/pageEffettuaOrdine")
public class PageEffettuaOrdine extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());
        String dove="/WEB-INF/login.jsp";

        if(req.getSession().getAttribute("utente")!=null){
            if(((Carrello)req.getSession().getAttribute("carrello")).getContenutoCarrello().isEmpty())
                dove= "/WEB-INF/visualizzaCarrello.jsp";
            else
                dove="/WEB-INF/ordine.jsp";
        }

        RequestDispatcher dispatcher= req.getRequestDispatcher(dove);
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
