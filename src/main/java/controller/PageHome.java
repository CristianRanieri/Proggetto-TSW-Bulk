package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Prodotto;
import model.DAO.ProdottoDAO;

import java.io.IOException;
import java.util.ArrayList;

import static model.Utils.createCarrello;

@WebServlet("/index.jsp")
public class PageHome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        ProdottoDAO prodottoDAO= new ProdottoDAO();
        ArrayList<Prodotto> prodotti= prodottoDAO.getProdottiPiuVenduti();
        req.setAttribute("prodottiPiuVenduti",prodotti);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/home.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
