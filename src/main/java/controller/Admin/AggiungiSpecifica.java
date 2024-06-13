package controller.Admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Been.Categoria;
import model.Been.Prodotto;
import model.Been.SpecificheProdotto;
import model.Been.Utente;
import model.DAO.CategoriaDAO;
import model.DAO.ProdottoDAO;
import model.DAO.SpecificheProdottoDAO;
import model.PatternInput;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@MultipartConfig
@WebServlet("/aggiungiSpecificaProdotto")
public class AggiungiSpecifica extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente utente= (Utente) req.getSession().getAttribute("utente");

        if(utente!=null){
            if(utente.isAdmin()) {
                //contorollo se ne esiste uno non specificato, nel caso ci sia si effettia in redirect veroso una pagina di errore
                if (req.getParameter("idProdotto") != null && req.getParameter("prezzo") != null && req.getParameter("quantita") != null &&
                        req.getParameter("gusto") != null && req.getParameter("numeroPezzi") != null && req.getPart("imagine1").getSize() !=0
                        && !req.getParameter("idProdotto").equals("") && !req.getParameter("prezzo").equals("") && !req.getParameter("quantita").equals("")
                        && !req.getParameter("gusto").equals("") && !req.getParameter("numeroPezzi").equals("")) {
                    //controllo che tutti i valori rispettino il giusto formato
                    if (PatternInput.numeroCivico(req.getParameter("idProdotto")) && PatternInput.prezzo(req.getParameter("prezzo")) && Double.parseDouble(req.getParameter("prezzo"))>0.00 &&
                            PatternInput.numeri2_4Cifre(req.getParameter("quantita")) && PatternInput.gusto(req.getParameter("gusto")) && PatternInput.numeri2_4Cifre(req.getParameter("numeroPezzi"))&& req.getPart("imagine1").getContentType().contains("image")) {
                        //controllo se il prodotto esiste
                        ProdottoDAO prodottoDAO = new ProdottoDAO();
                        ArrayList<Prodotto> prodotti= prodottoDAO.getProdottiByIDAdmin(Integer.parseInt(req.getParameter("idProdotto")));
                        if(!prodotti.isEmpty()) {
                            Prodotto prodotto = prodotti.get(0);

                            //controllo se la specifica gia esiste
                            SpecificheProdottoDAO specificheDAO = new SpecificheProdottoDAO();
                            SpecificheProdotto specificaProdotto=specificheDAO.getSpecificaProdottoWhitQuantitaAndGusto(Integer.parseInt(req.getParameter("idProdotto")), req.getParameter("gusto"), Integer.parseInt(req.getParameter("quantita")));
                            if(specificaProdotto==null) {

                                //creo la specifica del prodotto
                                specificaProdotto = new SpecificheProdotto();
                                int numero = specificheDAO.getNumeroSpecifica(prodotto.getiDProdotto()) + 1;
                                specificaProdotto.setNumero(numero);
                                specificaProdotto.setPrezzo(Double.parseDouble(req.getParameter("prezzo")));
                                specificaProdotto.setQuantita(Integer.parseInt(req.getParameter("quantita")));
                                specificaProdotto.setGusto(req.getParameter("gusto"));
                                specificaProdotto.setNumeroPezzi(Integer.parseInt(req.getParameter("numeroPezzi")));

                                specificheDAO.creaSpecificaProdotto(specificaProdotto, prodotto.getiDProdotto());
                                //se il gusto e il formato utilizzati non esistono si aggiornano gli array nel servletContexst
                                ArrayList<String> gusti = (ArrayList<String>) req.getServletContext().getAttribute("gusti");
                                ArrayList<Integer> quantita = (ArrayList<Integer>) req.getServletContext().getAttribute("quantita");
                                if (!gusti.contains(req.getParameter("gusto")))
                                    gusti.add(req.getParameter("gusto"));

                                boolean fleg_quantita=false;
                                for(Integer qua : quantita){
                                    if (qua.compareTo(Integer.parseInt(req.getParameter("quantita")))==0)
                                        fleg_quantita=true;
                                }
                                if(!fleg_quantita)
                                    quantita.add(Integer.parseInt(req.getParameter("quantita")));

                                //gestione delle immagini,salavataggio nella cartella
                                for (int i = 1; i <= 3; i++) {
                                    if (req.getPart("imagine" + i).getSize() != 0 && req.getPart("imagine"+i).getContentType().contains("image")) {
                                        Part image = req.getPart("imagine" + i);

                                        String destinazione = "upload" + File.separator + "ID_" + prodotto.getiDProdotto() + "_NU_" + numero + "_"+i+".png";
                                        Path pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));

                                        InputStream imageInputStream = image.getInputStream();

                                        // crea la cartella upload, se non esiste
                                        Files.createDirectories(pathDestinazione.getParent());
                                        // salava l'immagine nella cartella upload
                                        Files.copy(imageInputStream, pathDestinazione);
                                    }
                                }
                                //l'inserimento è avvenuto correttamente, allora lo manda alla pagina del prodotto
                                resp.sendRedirect("pageProdotto?idProduct=" + prodotto.getiDProdotto() + "&numberSpecific=" + numero);
                            }else
                                //l'inserimento non è avvenuto visto che il prodotto è gia presente
                                resp.sendRedirect("pageProdotto?idProduct=" + prodotto.getiDProdotto() + "&numberSpecific=" + specificaProdotto.getNumero());
                        }else
                            //errore prodotto non esite
                            resp.sendRedirect("gestioneErrori?error=3");
                    } else {
                        //errore non rispettano il formato
                        resp.sendRedirect("gestioneErrori?error=2");
                    }
                } else {
                    //errore esiste un valore null
                    resp.sendRedirect("gestioneErrori?error=1");
                }
            }else {
                //errore l'utente non ha i permessi per accedere
                resp.sendRedirect("gestioneErrori?error=4");
            }
        }else{
            //l'utente o non ha effettuato il login
            resp.sendRedirect("pageLogin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
