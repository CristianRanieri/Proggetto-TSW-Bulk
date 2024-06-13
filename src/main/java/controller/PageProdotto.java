package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Prodotto;
import model.Been.SpecificheProdotto;
import model.Been.Utente;
import model.DAO.ProdottoDAO;
import model.DAO.SpecificheProdottoDAO;
import model.PatternInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.Utils.createCarrello;

@WebServlet("/pageProdotto")
public class PageProdotto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dove="gestioneErrori?error=1";

        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        //Controllo l'esistenza del prodotto dato id nella request e nel caso prendo le specifiche dal database per inviare alla jsp prodotto.jsp
        //che mi permette di visualizzare il prodotto.
        if(req.getParameter("idProduct")!=null && req.getParameter("numberSpecific")!=null){
            if(PatternInput.numeroCivico(req.getParameter("idProduct")) && PatternInput.numeroCivico(req.getParameter("numberSpecific"))) {
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                //bisonga fare la differenza tra Utente e Admin
                Prodotto prodotto;
                if(req.getSession().getAttribute("utente")!=null && ((Utente)req.getSession().getAttribute("utente")).isAdmin())
                    prodotto = prodottoDAO.getProdottoByIdAndSpecificaAdmin(Integer.parseInt(req.getParameter("idProduct")), Integer.parseInt(req.getParameter("numberSpecific")));
                else
                    prodotto = prodottoDAO.getProdottoByIdAndSpecifica(Integer.parseInt(req.getParameter("idProduct")), Integer.parseInt(req.getParameter("numberSpecific")));

                if (prodotto != null) {
                    SpecificheProdottoDAO specificheProdottoDAO = new SpecificheProdottoDAO();
                    ArrayList<SpecificheProdotto> specificheProdottos;
                    //bisonga fare la differenza tra utente e Admin
                    if(req.getSession().getAttribute("utente")!=null && ((Utente)req.getSession().getAttribute("utente")).isAdmin())
                        specificheProdottos=specificheProdottoDAO.getSpecificheProdottoAdmin(Integer.parseInt(req.getParameter("idProduct")));
                    else {
                        specificheProdottos = specificheProdottoDAO.getSpecificheProdotto(Integer.parseInt(req.getParameter("idProduct")));
                        System.out.println("qui2");
                    }
                    req.setAttribute("prodotto", prodotto);
                    //lista di gusti delle specifiche di quel prodotto
                    List<String> gusti = (List<String>) specificheProdottos.stream().map(x -> x.getGusto()).distinct().toList();
                    //lista delle quantita delle specifiche del prodotto con quello specifico gusto
                    List<Integer> quantita = (List<Integer>) specificheProdottos.stream().filter(x -> x.getGusto().equals(prodotto.getGusto())).map(x -> x.getQuantita()).distinct().toList();
                    req.setAttribute("gusti", gusti);
                    req.setAttribute("quantita", quantita);
                    dove = "/WEB-INF/prodotto.jsp";
                }else {
                    dove="gestioneErrori?error=3";
                }
            }else {
                dove="gestioneErrori?error=2";
            }
        }
        RequestDispatcher dispatcher= req.getRequestDispatcher(dove);
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
