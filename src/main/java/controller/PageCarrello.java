package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Carrello;
import model.Been.ContenutoCarrello;
import model.Been.Prodotto;
import model.DAO.ProdottoDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import static model.Utils.createCarrello;

//non è una servlet mashera, serve per prendere tutti i prodotti dal carrello visto che nel carrello non ci sono le specifiche
//dei prodotti ma solo i loro id e numero di specifica.
@WebServlet("/pageCarrello")
public class PageCarrello extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        //creo un arrey di tutti i prodotti del carrello
        Double totalePrezzo= 0.0d;
        ArrayList<Prodotto> prodottos= new ArrayList<>();
        ProdottoDAO prodottoDAO= new ProdottoDAO();
        ArrayList<ContenutoCarrello> daRimuovere = new ArrayList<>();
        Carrello carrello = (Carrello)req.getSession().getAttribute("carrello");
        for(ContenutoCarrello contenutoCarrello : carrello.getContenutoCarrello()){
            Prodotto prodotto = prodottoDAO.getProdottoByIdAndSpecifica(contenutoCarrello.getIdProdotto(),contenutoCarrello.getNumero());
            if(prodotto!=null){
                if(contenutoCarrello.getNumeroPezzi()<= prodotto.getNumeroPezzi()) {
                    totalePrezzo += prodotto.getPrezzo() * contenutoCarrello.getNumeroPezzi();
                    prodottos.add(prodotto);
                }else {
                    if(prodotto.getNumeroPezzi()!=0){
                        System.out.println("giusto");
                        contenutoCarrello.setNumeroPezzi(prodotto.getNumeroPezzi());
                        totalePrezzo += prodotto.getPrezzo() * contenutoCarrello.getNumeroPezzi();
                        prodottos.add(prodotto);
                    }else {
                        System.out.println("problemaDacapire");
                        daRimuovere.add(contenutoCarrello);
                    }

                    req.setAttribute("error","1");
                }
            }else {
                req.setAttribute("error","1");
                daRimuovere.add(contenutoCarrello);
            }
        }

        for(ContenutoCarrello contenutoCarrello : daRimuovere){
            carrello.getContenutoCarrello().remove(contenutoCarrello);
        }

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
        req.setAttribute("prezzoTotale", BigDecimal.valueOf(totalePrezzo).setScale(2, RoundingMode.HALF_UP).doubleValue());
        req.setAttribute("prodotti",prodottos);

        RequestDispatcher dispatcher=req.getRequestDispatcher("/WEB-INF/visualizzaCarrello.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
