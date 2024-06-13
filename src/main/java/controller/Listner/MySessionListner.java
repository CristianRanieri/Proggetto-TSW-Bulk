package controller.Listner;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import model.Been.Carrello;
import model.DAO.CarrelloDAO;
import model.Been.Utente;

@WebListener
public class MySessionListner implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        CarrelloDAO carrelloDAO= new CarrelloDAO();
        Carrello carrello= (Carrello)se.getSession().getAttribute("carrello");

        if(carrello.getIdUtente()!=-1)
            carrelloDAO.caricaCarrello(carrello.getIdUtente(),carrello);
    }
}
