package controller.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.*;
import model.Been.Categoria;
import model.Been.Prodotto;
import model.Been.SpecificheProdotto;
import model.Been.Utente;
import model.DAO.CategoriaDAO;
import model.DAO.ProdottoDAO;
import model.DAO.SpecificheProdottoDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
@MultipartConfig
@WebServlet("/inserimentoProdotto")
public class InserimentoProdotto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente utente= (Utente) req.getSession().getAttribute("utente");

        if(utente!=null){
            if(utente.isAdmin()) {
                //contorollo se ne esiste uno non specificato, nel caso ci sia si effettia in redirect veroso una pagina di errore
                if (req.getParameter("nome") != null && req.getParameter("descrizione") != null && req.getParameter("categoria") != null &&
                        req.getParameter("specifica") != null && req.getParameter("prezzo") != null && req.getParameter("quantita") != null &&
                        req.getParameter("gusto") != null && req.getParameter("numeroPezzi") != null && req.getPart("imagine1").getSize() != 0 &&
                        !req.getParameter("nome").equals("") && !req.getParameter("descrizione").equals("") && !req.getParameter("categoria").equals("") &&
                        !req.getParameter("specifica").equals("") && !req.getParameter("prezzo").equals("") && !req.getParameter("quantita").equals("") &&
                        !req.getParameter("gusto").equals("") && !req.getParameter("numeroPezzi").equals("")) {
                    //controllo che tutti i valori rispettino il giusto formato
                    if (PatternInput.stringaConNumeri(req.getParameter("nome")) && PatternInput.nome(req.getParameter("categoria")) && PatternInput.nome(req.getParameter("specifica")) &&
                            PatternInput.prezzo(req.getParameter("prezzo")) && Double.parseDouble(req.getParameter("prezzo"))>0.00 && PatternInput.numeri2_4Cifre(req.getParameter("quantita")) && PatternInput.gusto(req.getParameter("gusto")) &&
                            PatternInput.numeri2_4Cifre(req.getParameter("numeroPezzi")) && PatternInput.descrizione(req.getParameter("descrizione")) && req.getPart("imagine1").getContentType().contains("image")) {

                        //devo inserire la sincronizzazione visto che possono essere inseriti piu prodotti insieme e quindi deve verificare l'esistenza
                        // della categoria in modo corretto altrimenti ci sarebbe un accezzione per l'inserimento di una categoria gia esistente
                        synchronized (req.getServletContext()) {

                            //controllo se la categoria è esistente
                            boolean categoria = false;
                            boolean specifica = false;
                            ArrayList<Categoria> categorie = (ArrayList<Categoria>) req.getServletContext().getAttribute("categorie");
                            for (Categoria cat : categorie)
                                if (cat.getNome().equals(req.getParameter("categoria"))) {
                                    categoria = true;
                                    for (String spe : cat.getSpecifiche())
                                        if (spe.equals(req.getParameter("specifica")))
                                            specifica = true;
                                }

                            //se la categoria non esiste la creo
                            if (!categoria || !specifica) {
                                CategoriaDAO categoriaDAO = new CategoriaDAO();
                                categoriaDAO.creaCategoria(req.getParameter("categoria"), req.getParameter("specifica"));
                                //se non esiste si aggiorna L'array di Categorie nel servletContexst
                                if (!categoria) {// se categoria è falso significa che non è esiste neanche la categoria quindi si deve creare un nuovo array
                                    Categoria categoriaNew = new Categoria();
                                    ArrayList<String> specificheProdotti = new ArrayList<>();
                                    specificheProdotti.add(req.getParameter("specifica"));

                                    categoriaNew.setNome(req.getParameter("categoria"));
                                    categoriaNew.setSpecifiche(specificheProdotti);
                                    categorie.add(categoriaNew);

                                } else {// nel caso in cui esiste la categoria ma non la specifica di essa allora si agguinge solo la specifica all'array
                                    for (Categoria cat : categorie)
                                        if (cat.getNome().equals(req.getParameter("categoria"))) {
                                            cat.getSpecifiche().add(req.getParameter("specifica"));
                                        }
                                }
                            }

                        }

                        //creo il prodotto
                        ProdottoDAO prodottoDAO = new ProdottoDAO();
                        Prodotto prodotto = new Prodotto();
                        prodotto.setNome(req.getParameter("nome"));
                        prodotto.setDescrizione(req.getParameter("descrizione"));
                        prodotto.setCategoria(req.getParameter("categoria"));
                        prodotto.setSpecifica(req.getParameter("specifica"));
                        prodottoDAO.creaProdotto(prodotto);

                        //creo la specifica del prodotto
                        SpecificheProdottoDAO specificheDAO = new SpecificheProdottoDAO();
                        SpecificheProdotto specificaProdotto = new SpecificheProdotto();
                        int numero = 1;
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

                                String destinazione = "upload" + File.separator + "ID_" + prodotto.getiDProdotto() + "_NU_" + numero + "_" + i + ".png";
                                Path pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));

                                InputStream imageInputStream = image.getInputStream();

                                // crea la cartella upload, se non esiste
                                Files.createDirectories(pathDestinazione.getParent());
                                // salava l'immagine nella caretlla upload
                                Files.copy(imageInputStream, pathDestinazione);
                            }
                        }
                        //l'inserimento è avvenuto correttamente, allora lo manda alla pagina del prodotto
                        resp.sendRedirect("pageProdotto?idProduct=" + prodotto.getiDProdotto() + "&numberSpecific=" + numero);
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
