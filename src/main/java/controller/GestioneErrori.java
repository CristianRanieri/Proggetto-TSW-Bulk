package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PatternInput;

import java.io.IOException;

import static model.Utils.createCarrello;

@WebServlet("/gestioneErrori")
public class GestioneErrori extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        String numberError =  req.getParameter("error");
        if(numberError != null && PatternInput.numeroCivico(numberError)) {
            req.setAttribute("numberError", Integer.parseInt(numberError));
        }else {
            req.setAttribute("numberError","0");
        }

        RequestDispatcher dispatcher= req.getRequestDispatcher("/WEB-INF/error.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
