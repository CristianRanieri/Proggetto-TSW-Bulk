package model;

import jakarta.servlet.http.HttpSession;
import model.Been.Carrello;
import model.Been.ContenutoCarrello;

import java.util.ArrayList;

public class Utils {
    public static void createCarrello(HttpSession session){

        if(session.getAttribute("carrello")==null){
            Carrello carrello= new Carrello();
            carrello.setIdUtente(-1);
            ArrayList<ContenutoCarrello> contenutoCarrello= new ArrayList<>();
            carrello.setContenutoCarrello(contenutoCarrello);
            session.setAttribute("carrello",carrello);
        }

    }
}
