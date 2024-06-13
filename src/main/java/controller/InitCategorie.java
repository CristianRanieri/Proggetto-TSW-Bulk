package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import model.Been.Categoria;
import model.DAO.CategoriaDAO;
import model.Been.Prodotto;
import model.DAO.ProdottoDAO;

import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns ="/myinit",loadOnStartup = 0)
public class InitCategorie extends HttpServlet {
    @Override
    public void init() throws ServletException {
        //Gestione delle categorie, inserimeto nell application
        CategoriaDAO categoriaDAO= new CategoriaDAO();
        List<Categoria> categorie=categoriaDAO.getCategory();

        getServletContext().setAttribute("categorie",categorie);

        ProdottoDAO prodottoDAO= new ProdottoDAO();
        List<Prodotto> prodotti= prodottoDAO.getProdottiWhitSpecifiche();

        //Gestione delle caratteristiche (Gusto Quantita), inserimeto nell application
        ArrayList<String> gusti= new ArrayList<>();
        ArrayList<Integer> quantita= new ArrayList<>();
        for (Prodotto prodotto : prodotti){
            if(!gusti.contains(prodotto.getGusto()))
                gusti.add(prodotto.getGusto());

            if(!quantita.contains(prodotto.getQuantita()))
                quantita.add(prodotto.getQuantita());
        }

        getServletContext().setAttribute("quantita",quantita);
        getServletContext().setAttribute("gusti",gusti);

        super.init();
    }
}
