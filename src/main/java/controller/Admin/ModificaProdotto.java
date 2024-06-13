package controller.Admin;

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

@WebServlet("/modificaProdotto")
public class ModificaProdotto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente utente= (Utente) req.getSession().getAttribute("utente");

        if(utente!=null){
            if(utente.isAdmin()) {
                //controllo se i valori sono null
                if (req.getParameter("idProdotto") != null && req.getParameter("numeroSpecifica") != null && req.getParameter("prezzoProdotto")!=null
                        && req.getParameter("numeroPezzi")!=null && !req.getParameter("idProdotto").equals("") && !req.getParameter("numeroSpecifica").equals("")
                        && !req.getParameter("prezzoProdotto").equals("") && !req.getParameter("numeroPezzi").equals("")) {
                    //controllo se il formato è corretto
                    if (PatternInput.numeroCivico(req.getParameter("idProdotto")) && PatternInput.numeroCivico(req.getParameter("numeroSpecifica"))
                            && PatternInput.prezzo(req.getParameter("prezzoProdotto")) && PatternInput.numeri1_4Cifre(req.getParameter("numeroPezzi")) && Double.parseDouble(req.getParameter("prezzoProdotto"))>0.00) {
                        //controllo se il prodotto esiste
                        ProdottoDAO prodottoDAO = new ProdottoDAO();
                        Prodotto prodotto = prodottoDAO.getProdottoByIdAndSpecificaAdmin(Integer.parseInt(req.getParameter("idProdotto")), Integer.parseInt(req.getParameter("numeroSpecifica")));
                        if (prodotto != null) {
                            SpecificheProdottoDAO specificheProdottoDAO = new SpecificheProdottoDAO();
                            SpecificheProdotto specificheProdotto = new SpecificheProdotto();
                            boolean inCatalogo=false;
                            if(req.getParameter("inCatalogo")!=null)
                                inCatalogo=true;
                            specificheProdotto.setInCatalogo(inCatalogo);
                            specificheProdotto.setNumeroPezzi(Integer.parseInt(req.getParameter("numeroPezzi")));
                            specificheProdotto.setPrezzo(Double.parseDouble(req.getParameter("prezzoProdotto")));
                            specificheProdotto.setNumero(Integer.parseInt(req.getParameter("numeroSpecifica")));
                            specificheProdottoDAO.modificaSpecificaProdotto(specificheProdotto, Integer.parseInt(req.getParameter("idProdotto")));

                            resp.sendRedirect("pageProdotto?idProduct="+prodotto.getiDProdotto()+"&numberSpecific="+prodotto.getNumero());
                        } else {
                            resp.sendRedirect("gestioneErrori?error=3");
                        }
                    } else
                        resp.sendRedirect("gestioneErrori?error=2");
                } else
                    resp.sendRedirect("gestioneErrori?error=1");
            }else
                resp.sendRedirect("gestioneErrori?error=4");
        }else
            resp.sendRedirect("pageLogin");
    }

}

