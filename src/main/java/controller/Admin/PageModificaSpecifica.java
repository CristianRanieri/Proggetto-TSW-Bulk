package controller.Admin;

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

@WebServlet("/pageModificaSpecifica")
public class PageModificaSpecifica extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente utente= (Utente) req.getSession().getAttribute("utente");

        if(utente!=null){
            if(utente.isAdmin()) {
                //controllo se i valori sono null
                if (req.getParameter("idProdotto") != null && req.getParameter("numeroSpecfica") != null) {
                    //controllo se il formato è corretto
                    if (PatternInput.numeroCivico(req.getParameter("idProdotto")) && PatternInput.numeroCivico(req.getParameter("numeroSpecfica"))) {
                        ProdottoDAO prodottoDAO = new ProdottoDAO();
                        Prodotto prodotto = prodottoDAO.getProdottoByIdAndSpecificaAdmin(Integer.parseInt(req.getParameter("idProdotto")), Integer.parseInt(req.getParameter("numeroSpecfica")));
                        if (prodotto != null) {
                            req.setAttribute("prodotto", prodotto);
                            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp");
                            dispatcher.forward(req,resp);
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
