package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Categoria;
import model.Been.Prodotto;
import model.Been.Utente;
import model.DAO.ProdottoDAO;
import model.PatternInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.Utils.createCarrello;

@WebServlet("/pageProdotti")
public class PageProdotti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dove="/WEB-INF/visualizzaProdotti.jsp";

        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        //controllo se i parametri sono null
        if(req.getParameter("category")==null || req.getParameter("specific")==null){
            dove="gestioneErrori?error=1";
        } else {
            boolean b=false;
            //controllo se la categoria e la specifica sono esistenti
            for(Categoria cat: (ArrayList<Categoria>)req.getServletContext().getAttribute("categorie")){
                if(cat.getNome().equals(req.getParameter("category")) && cat.getSpecifiche().contains(req.getParameter("specific")))
                    b=true;
            }

            //se la categoria e la specifica esistono si esegue il corpo del if
            if(b) {
                ArrayList<String> caratteristiche = new ArrayList<>();
                //non controllo se le caratteristiche sono esistenti o meno (al massimo la pagina dei prodotti sara vuota)
                //se non si specificano le caratteristiche vengono prese tutte
                if(req.getParameter("taste")!=null && PatternInput.gusto(req.getParameter("taste"))) {
                    caratteristiche.add(req.getParameter("taste"));
                    req.setAttribute("gusto",req.getParameter("taste"));
                }
                else
                    caratteristiche.add("tutti");

                if(req.getParameter("price")!=null && PatternInput.numeri1_4Cifre(req.getParameter("price"))) {
                    caratteristiche.add(req.getParameter("price"));
                    req.setAttribute("prezzo",req.getParameter("price"));
                }
                else
                    caratteristiche.add("tutti");

                if(req.getParameter("amount")!=null && PatternInput.numeri2_4Cifre(req.getParameter("amount"))) {
                    caratteristiche.add(req.getParameter("amount"));
                    req.setAttribute("quantitaPro", req.getParameter("amount"));
                }
                else
                    caratteristiche.add("tutti");

                ProdottoDAO prodottoDAO = new ProdottoDAO();
                //va fatta la differenza tra utente e admin, se è admin vede anche i prodotti non in catalogo
                List<Prodotto> prodotti;
                Utente utente= (Utente) req.getSession().getAttribute("utente");

                if(utente!=null && utente.isAdmin()) {
                    prodotti = prodottoDAO.getProdottiPerCategoriaSpecificaAdmin(req.getParameter("category"), req.getParameter("specific"), caratteristiche);
                }else {
                    prodotti = prodottoDAO.getProdottiPerCategoriaSpecifica(req.getParameter("category"), req.getParameter("specific"), caratteristiche);
                }

                req.setAttribute("prodotti", prodotti);
            }else
                dove="gestioneErrori?error=2";
        }

        RequestDispatcher dispatcher= req.getRequestDispatcher(dove);
        dispatcher.forward(req,resp);
    }
}
