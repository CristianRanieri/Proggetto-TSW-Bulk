package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Acquisto;
import model.Been.Ordine;
import model.Been.Prodotto;
import model.DAO.OrdineDAO;
import model.Been.Utente;
import model.DAO.ProdottoDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import static model.Utils.createCarrello;

@WebServlet("/pageOrdini")
public class PageOrdini extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        String dove="/WEB-INF/login.jsp";

        Utente utente= (Utente) req.getSession().getAttribute("utente");
        if(utente!=null){
            dove="/WEB-INF/visualizzaOrdini.jsp";

            OrdineDAO ordineDAO = new OrdineDAO();
            ArrayList<Ordine> ordini = null;
            ArrayList<Double> prezzoOrdini = new ArrayList<>();
            if (utente.isAdmin()) {
                ordini = ordineDAO.getOrdini();
            }else {
                ordini = ordineDAO.getOrdiniUtente(utente.getId());
            }

            for(Ordine ordine : ordini){
                Double prezzptotale = 0.0d;
                for(Acquisto acquisto : ordine.getAcquisti()){
                    prezzptotale+= acquisto.getPrezzoAcquisto()*acquisto.getNumeroPezzi();
                }
                prezzoOrdini.add(BigDecimal.valueOf(prezzptotale).setScale(2, RoundingMode.HALF_UP).doubleValue());
            }

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            ArrayList<Prodotto> prodottiDiInteresse= new ArrayList<>();
            ArrayList<Prodotto> prodotti= (ArrayList<Prodotto>) prodottoDAO.getProdottiWhitSpecificheUtente();

            for (int i=0;i<3;i++){
                Random random = new Random();
                int j= random.nextInt(100000);
                if(j<0)
                    j=j*(-1);
                prodottiDiInteresse.add(prodotti.get(j%prodotti.size()));
            }

            req.setAttribute("prodottiInteresse",prodottiDiInteresse);
            req.setAttribute("prezzoOrdini",prezzoOrdini);
            req.setAttribute("ordini",ordini);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(dove);
        dispatcher.forward(req,resp);
    }
}
