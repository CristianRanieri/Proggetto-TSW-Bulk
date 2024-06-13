package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Prodotto;
import model.Been.Utente;
import model.DAO.ProdottoDAO;
import model.PatternInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.Utils.createCarrello;

@WebServlet("/pageRicercaProdottiNome")
public class PageRicercaProdottiNome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        String nomeRicerca= req.getParameter("nomeProdotto");
        if(nomeRicerca!=null){
            List<String> nomi= Arrays.stream(nomeRicerca.split(" ")).toList();
            //aggiungo le carateristiche se specificate
            //non controllo se le caratteristiche sono esistenti o meno (al massimo la pagina dei prodotti sara vuota)
            //se non si specificano le caratteristiche vengono prese tutte
            ArrayList<String> caratteristiche = new ArrayList<>();

            if(req.getParameter("gusto")!=null && PatternInput.gusto(req.getParameter("gusto"))) {
                caratteristiche.add(req.getParameter("gusto"));
                req.setAttribute("gusto",req.getParameter("gusto"));
            }
            else
                caratteristiche.add("tutti");

            if(req.getParameter("prezzo")!=null && PatternInput.numeri1_4Cifre(req.getParameter("prezzo"))) {
                caratteristiche.add(req.getParameter("prezzo"));
                req.setAttribute("prezzo",req.getParameter("prezzo"));
            }
            else
                caratteristiche.add("tutti");

            if(req.getParameter("quantitaPro")!=null && PatternInput.numeri2_4Cifre(req.getParameter("quantitaPro"))) {
                caratteristiche.add(req.getParameter("quantitaPro"));
                req.setAttribute("quantitaPro", req.getParameter("quantitaPro"));
            }
            else
                caratteristiche.add("tutti");

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            ArrayList<Prodotto> prodotti;
            Utente utente= (Utente) req.getSession().getAttribute("utente");
            if(utente!=null && utente.isAdmin())
                prodotti= prodottoDAO.getProdottiPerNomeESpecificheAdmin(nomi,caratteristiche);
            else
                prodotti= prodottoDAO.getProdottiPerNomeESpecifiche(nomi,caratteristiche);

            req.setAttribute("prodotti",prodotti);
            RequestDispatcher dispatcher= req.getRequestDispatcher("/WEB-INF/visualizzaProdottiNome.jsp");
            dispatcher.forward(req,resp);
        }else
            resp.sendRedirect("index.jsp");
    }
}
