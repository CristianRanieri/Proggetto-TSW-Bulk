package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static model.Utils.createCarrello;

//servlet maschera per la jsp
@WebServlet("/pageRegistrazione")
public class PageRegistrazione extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        if(req.getSession().getAttribute("utente")==null){
            RequestDispatcher dispatcher= req.getRequestDispatcher("/WEB-INF/registrazione.jsp");
            dispatcher.forward(req,resp);
        }else {
            resp.sendRedirect("AreaUtenti");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
