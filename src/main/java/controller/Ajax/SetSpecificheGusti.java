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
import java.util.List;

@WebServlet("/setSpecificheGusti")
public class SetSpecificheGusti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProdottoDAO prodottoDAO= new ProdottoDAO();
        //va fatta la differenza tra utente e admin
        ArrayList<Prodotto> specificheProdotti;
        if(req.getSession().getAttribute("utente")!=null && ((Utente)req.getSession().getAttribute("utente")).isAdmin())
            specificheProdotti=(ArrayList<Prodotto>) prodottoDAO.getProdottiByIDAdmin(Integer.parseInt(req.getParameter("idProdotto")));
        else
            specificheProdotti=(ArrayList<Prodotto>) prodottoDAO.getProdottiByID(Integer.parseInt(req.getParameter("idProdotto")));

        String json2="";
        List<Prodotto> gusti= (List<Prodotto>) specificheProdotti.stream().filter(x -> x.getGusto().equals(req.getParameter("gusto"))).toList();
        Prodotto prodotto= gusti.get(0);


        if(prodotto!=null){
            json2+="{\"prezzo\":"+prodotto.getPrezzo()+","+
                    "\"numeroPezzi\":"+prodotto.getNumeroPezzi()+","+
                    "\"numeroSpecifica\":"+prodotto.getNumero()+",";

            List<Integer> quantita= (List<Integer>) gusti.stream().map(x-> x.getQuantita()).distinct().toList();

            json2+="\"quantita\":[";
            for(int i=0; i<quantita.size()-1;i++){
                json2+= quantita.get(i)+",";
            }
            json2+= quantita.get(quantita.size()-1)+"]}";
        }

        System.out.println(json2);
        resp.getWriter().println(json2);
    }
}
