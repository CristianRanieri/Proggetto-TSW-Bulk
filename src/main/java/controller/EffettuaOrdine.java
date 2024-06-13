package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.*;
import model.DAO.OrdineDAO;
import model.DAO.ProdottoDAO;
import model.DAO.SpecificheProdottoDAO;
import model.PatternInput;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import static model.Utils.createCarrello;

@WebServlet("/effettuaOrdine")
public class EffettuaOrdine extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Codice comune a tutte le servlet per la creazione del carrello
        createCarrello(req.getSession());

        //deve essere tutto sincornozzato visto che mentre si effettua l'oerazione di creazione dell' ordine possono essere effettuate diverse operazioni su di esso.
        synchronized (req.getSession()) {

            //controllo se il carrello non è vuoto
            if (!((Carrello) req.getSession().getAttribute("carrello")).getContenutoCarrello().isEmpty() && ((Utente) req.getSession().getAttribute("utente")) != null) {

                //creo un arrey di tutti gli Acquisti dati i prodotti nel carrello
                ArrayList<Acquisto> acquisti = new ArrayList<>();
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                boolean fleg = true;
                Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
                ArrayList<ContenutoCarrello> daRimuovere = new ArrayList<>();
                for (ContenutoCarrello contenutoCarrello : carrello.getContenutoCarrello()) {
                    Prodotto prodotto = prodottoDAO.getProdottoByIdAndSpecifica(contenutoCarrello.getIdProdotto(), contenutoCarrello.getNumero());
                    if (prodotto != null) {
                        if (contenutoCarrello.getNumeroPezzi() <= prodotto.getNumeroPezzi()) {
                            Acquisto acquisto = new Acquisto();
                            acquisto.setProdotto(prodotto);
                            acquisto.setNumeroPezzi(contenutoCarrello.getNumeroPezzi());
                            acquisto.setPrezzoAcquisto((double) prodotto.getPrezzo());
                            acquisti.add(acquisto);
                        } else {
                            if (prodotto.getNumeroPezzi() != 0) {
                                contenutoCarrello.setNumeroPezzi(prodotto.getNumeroPezzi());
                            } else {
                                daRimuovere.add(contenutoCarrello);
                            }
                            fleg = false;
                        }
                    } else {
                        daRimuovere.add(contenutoCarrello);
                        fleg = false;
                    }
                }

                for (ContenutoCarrello contenutoCarrello : daRimuovere) {
                    carrello.getContenutoCarrello().remove(contenutoCarrello);
                }

                if (fleg) {
                    //controllo degli input se sono null
                    if (req.getParameter("Via") != null && req.getParameter("Civico") != null && req.getParameter("CAP") != null && req.getParameter("numeroCarta") != null &&
                            req.getParameter("dataScadenza") != null && req.getParameter("nome") != null && req.getParameter("cognome") != null && req.getParameter("CCV") != null && !(((String) req.getParameter("dataScadenza")).isEmpty()) &&
                            !req.getParameter("Via").equals("") && !req.getParameter("Civico").equals("") && !req.getParameter("CAP").equals("") && !req.getParameter("numeroCarta").equals("") && !req.getParameter("nome").equals("") && !req.getParameter("cognome").equals("") && !req.getParameter("CCV").equals("")) {
                        //controllo se gli input hanno il formato corretto
                        if (PatternInput.stringaConSPazzi(req.getParameter("Via")) && PatternInput.numeroCivico(req.getParameter("Civico")) && PatternInput.numeroCAP(req.getParameter("CAP"))
                                && PatternInput.numeroCarta(req.getParameter("numeroCarta")) && PatternInput.data(Date.valueOf(req.getParameter("dataScadenza")))
                                && PatternInput.gusto(req.getParameter("nome")) && PatternInput.gusto(req.getParameter("cognome")) && PatternInput.numeroCCV(req.getParameter("CCV"))) {

                            //Creo l'ordine e setto i valori
                            Ordine ordine = new Ordine();
                            ordine.setVia(req.getParameter("Via"));
                            ordine.setCivico(req.getParameter("Civico"));
                            ordine.setCap(req.getParameter("CAP"));
                            Date date = new Date(System.currentTimeMillis());
                            LocalDate localDate = date.toLocalDate();
                            ordine.setData(Date.valueOf(localDate));
                            ordine.setAcquisti(acquisti);


                            //inserisco l'ordine nel database
                            Utente utente = (Utente) req.getSession().getAttribute("utente");
                            OrdineDAO ordineDAO = new OrdineDAO();
                            ordineDAO.creaOrdine(ordine, utente.getId());

                            //decremento il numero di pezzi per ogni prodotto del numero di pezzi acquistati
                            for (ContenutoCarrello contenutoCar : ((Carrello) req.getSession().getAttribute("carrello")).getContenutoCarrello()) {
                                SpecificheProdottoDAO specificheProdottoDAO = new SpecificheProdottoDAO();
                                Prodotto prodotto = prodottoDAO.getProdottoByIdAndSpecifica(contenutoCar.getIdProdotto(), contenutoCar.getNumero());
                                int pezziRimasti = prodotto.getNumeroPezzi() - contenutoCar.getNumeroPezzi();
                                prodotto.setNumeroPezzi(pezziRimasti);
                                specificheProdottoDAO.modificaNumeroPezzi(prodotto.getSpecificaProdotto(), prodotto.getiDProdotto());
                            }

                            //svoto il carrello creandone uno nuovo
                            Carrello newCarrello = new Carrello();
                            newCarrello.setIdUtente(utente.getId());
                            ArrayList<ContenutoCarrello> contenutoCarrello = new ArrayList<>();
                            newCarrello.setContenutoCarrello(contenutoCarrello);
                            req.getSession().setAttribute("carrello", newCarrello);

                            resp.sendRedirect("pageOrdini");
                        } else {
                            //input errati pagina di errore
                            RequestDispatcher dispatcher = req.getRequestDispatcher("gestioneErrori?error=2");
                            dispatcher.forward(req, resp);
                        }

                    } else {
                        //il valori di input sono nulli
                        RequestDispatcher dispatcher = req.getRequestDispatcher("gestioneErrori?error=1");
                        dispatcher.forward(req, resp);
                    }
                } else {
                    req.setAttribute("error", "1");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("pageCarrello");
                    dispatcher.forward(req, resp);
                }
            } else {
                resp.sendRedirect("pageCarrello");
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
