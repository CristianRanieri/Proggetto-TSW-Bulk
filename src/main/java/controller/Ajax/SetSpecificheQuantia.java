package controller.Ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Prodotto;
import model.Been.Utente;
import model.DAO.ProdottoDAO;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/setSpecificheQuantia")
public class SetSpecificheQuantia extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProdottoDAO prodottoDAO= new ProdottoDAO();
        //bisonga fare la differenza tra Utente e Admin
        Prodotto prodotto;

        if(req.getSession().getAttribute("utente")!=null && ((Utente)req.getSession().getAttribute("utente")).isAdmin())
            prodotto=prodottoDAO.getProdottoBySpecificaAdmin(Integer.parseInt(req.getParameter("idProdotto")),req.getParameter("gusto"),req.getParameter("quantita"));
        else
            prodotto=prodottoDAO.getProdottoBySpecifica(Integer.parseInt(req.getParameter("idProdotto")),req.getParameter("gusto"),req.getParameter("quantita"));

        String json="";
        if(prodotto!=null){
            json+="{\"prezzo\":"+prodotto.getPrezzo()+","+
                    "\"numeroPezzi\":"+prodotto.getNumeroPezzi()+","+
                    "\"numeroSpecifica\":"+prodotto.getNumero()+"}";
        }

        System.out.println(json);
        resp.getWriter().println(json);
    }
}
