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

@WebServlet("/aggiuntaCarrello")
public class AgguintaCarrello extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        //controllo se i valori sono null
        if(req.getParameter("idProduct")!=null && req.getParameter("numberSpecific")!=null && req.getParameter("numeroPezzi")!=null){
            //controllo se i valori seguno un determinato formato
            if(PatternInput.numero(req.getParameter("numberSpecific")) && PatternInput.numero(req.getParameter("numeroPezzi")) && PatternInput.numero(req.getParameter("idProduct"))) {
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                Prodotto prodotto = prodottoDAO.getProdottoByIdAndSpecifica(Integer.parseInt(req.getParameter("idProduct")), Integer.parseInt(req.getParameter("numberSpecific")));
                //controllo se esiste il prodotto
                if (prodotto != null) {

                    //utilizzo sincornaized visto che potrebbe essere aggiunto piu volte lo stesso prodotto dallo stesso utente contemporaneamente
                    synchronized (req.getSession()) {

                        Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
                        //controllo se il prodotto è gia presente
                        boolean b = false;
                        for (ContenutoCarrello c : carrello.getContenutoCarrello()) {
                            if (c.getIdProdotto() == prodotto.getiDProdotto() && c.getNumero() == prodotto.getNumero()) {
                                b = true;
                                //se il prodotto è gia presente incremento il numero di pezzi in base a quanti ne ha inseriti se essi non
                                // supera il numero di pezzi disponibili
                                if ((c.getNumeroPezzi() + Integer.parseInt(req.getParameter("numeroPezzi"))) <= prodotto.getNumeroPezzi()) {
                                    c.setNumeroPezzi(c.getNumeroPezzi() + Integer.parseInt(req.getParameter("numeroPezzi")));
                                }
                            }
                        }

                        //!b=true il prodotto è assente, quindi lo inserisco se non supera il numero massimo
                        if (!b && prodotto.getNumeroPezzi() >= Integer.parseInt(req.getParameter("numeroPezzi"))) {
                            ContenutoCarrello contenutoCarrello = new ContenutoCarrello();
                            contenutoCarrello.setNumero(prodotto.getNumero());
                            contenutoCarrello.setNumeroPezzi(Integer.parseInt(req.getParameter("numeroPezzi")));
                            contenutoCarrello.setIdProdotto(prodotto.getiDProdotto());
                            carrello.getContenutoCarrello().add(contenutoCarrello);
                        }

                    }

                    resp.sendRedirect("pageCarrello");
                }else
                    resp.sendRedirect("gestioneErrori?error=3");
            }else
                resp.sendRedirect("gestioneErrori?error=2");
        }else
            resp.sendRedirect("gestioneErrori?error=1");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
