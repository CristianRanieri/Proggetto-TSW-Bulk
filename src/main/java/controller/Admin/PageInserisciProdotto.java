package controller.Admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Categoria;
import model.Been.Utente;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/pageInserisciProdotto")
public class PageInserisciProdotto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente utente= (Utente) req.getSession().getAttribute("utente");
        String dove="";

        if(utente!=null){
            if(utente.isAdmin()) {
                dove = "/WEB-INF/inserisciProdotto.jsp";
                //creo una lista di tutte le specifiche utilizzate per tutte le categorie
                ArrayList<String> specifiche = new ArrayList<>();
                for (Categoria cat : (ArrayList<Categoria>) req.getServletContext().getAttribute("categorie")) {
                    specifiche.addAll(cat.getSpecifiche());
                }

                req.setAttribute("specifiche", specifiche.stream().distinct().toList());
            }else {
                //l'utete non ha i permessi
                dove="gestioneErrori?error=4";
            }
        }else {
            dove="pageLogin";
        }

        RequestDispatcher dispatcher= req.getRequestDispatcher(dove);
        dispatcher.forward(req,resp);
    }
}
