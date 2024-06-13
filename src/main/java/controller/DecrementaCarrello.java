package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Carrello;
import model.Been.ContenutoCarrello;
import model.PatternInput;
import model.Been.Prodotto;
import model.DAO.ProdottoDAO;

import java.io.IOException;

import static model.Utils.createCarrello;

@WebServlet("/decrementaCarrello")
public class DecrementaCarrello extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        //controllo l'esistenza del prodotto con il corrispettivo numero di specifica
        if(req.getParameter("idProduct")!=null && req.getParameter("numberSpecific")!=null){
            if(PatternInput.numero(req.getParameter("numberSpecific"))
                && PatternInput.numero(req.getParameter("idProduct"))){
                ProdottoDAO prodottoDAO= new ProdottoDAO();
                Prodotto prodotto=prodottoDAO.getProdottoByIdAndSpecifica(Integer.parseInt(req.getParameter("idProduct")),Integer.parseInt(req.getParameter("numberSpecific")));

                ContenutoCarrello remouve=null;
                if(prodotto!=null){

                    //utilizzo sincornaized visto che potrebbe essere decrementato piu volte il numero di pezzi dello stesso prodotto dall' utente contemporaneamente
                    synchronized (req.getSession()) {

                        Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
                        //controllo se il prodotto è presente
                        for (ContenutoCarrello c : carrello.getContenutoCarrello()) {
                            if (c.getIdProdotto() == prodotto.getiDProdotto() && c.getNumero() == prodotto.getNumero()) {
                                //se il prodotto è presente decremento il numero di 1 se arriva a 0 lo elimino dal carrello
                                if (c.getNumeroPezzi() - 1 > 0) {
                                    c.setNumeroPezzi(c.getNumeroPezzi() - 1);
                                } else {
                                    remouve = c;
                                }
                            }
                        }
                        //lo rimuovo dal carrello
                        if (remouve != null)
                            carrello.getContenutoCarrello().remove(remouve);

                        resp.sendRedirect("pageCarrello");
                    }

                }else
                    resp.sendRedirect("gestioneErrori?error=3");
            }else
                resp.sendRedirect("gestioneErrori?error=2");
        }else
            resp.sendRedirect("gestioneErrori?error=1");
    }
}
