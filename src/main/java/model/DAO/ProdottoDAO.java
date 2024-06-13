package model.DAO;

import model.ConPool;
import model.Been.Prodotto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {
    private static final Connection con;

    static {
        try {
            con = ConPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Prodotto createProduct(ResultSet rs) throws SQLException {
        Prodotto p= new Prodotto();
        p.setGusto(rs.getString("gusto"));
        p.setNumero(rs.getInt("numero"));
        p.setQuantita(rs.getInt("quantita"));
        p.setNumeroPezzi(rs.getInt("numeropezzi"));
        p.setNome(rs.getString("nome"));
        p.setDescrizione(rs.getString("descrizione"));
        p.setPrezzo(rs.getDouble("prezzo"));
        p.setCategoria(rs.getString("categoria"));
        p.setSpecifica(rs.getString("specifica"));
        p.setiDProdotto(rs.getInt("idProdotto"));
        p.setInCatalogo(rs.getBoolean("inCatalogo"));
        return p;
    }

    //prendo tutti i prodotti di una determinata categoria con le specifiche, i quali sono in catalogo(per utenti)
    public List<Prodotto> getProdottiPerCategoriaSpecifica(String category, String specific,ArrayList<String> caratterisitche){
        List<Prodotto> prodottos= new ArrayList<>();
        String query= "SELECT * FROM Prodotto p,Specifiche s WHERE s.Incatalogo=true AND p.idProdotto=s.idProdotto AND Categoria=? AND Specifica=?";

        if(!caratterisitche.get(0).equals("tutti"))
            query+= " AND s.gusto=\""+ caratterisitche.get(0)+"\"";
        if(!caratterisitche.get(1).equals("tutti"))
            query+= " AND s.prezzo<="+ caratterisitche.get(1);
        if(!caratterisitche.get(2).equals("tutti"))
            query+= " AND s.quantita=\""+ caratterisitche.get(2)+"\"";

        try{
            PreparedStatement ps= con.prepareStatement(query);
            ps.setString(1,category);
            ps.setString(2,specific);
            ResultSet rs=ps.executeQuery();

            while (rs.next()){
                prodottos.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodottos;
    }


    //prendo tutti i prodotti di una determinata categoria con le specifiche, i quali sono in catalogo(per Admin)
    public List<Prodotto> getProdottiPerCategoriaSpecificaAdmin(String category, String specific,ArrayList<String> caratterisitche){
        List<Prodotto> prodottos= new ArrayList<>();
        String query= "SELECT * FROM Prodotto p,Specifiche s WHERE p.idProdotto=s.idProdotto AND Categoria=? AND Specifica=?";

        if(!caratterisitche.get(0).equals("tutti"))
            query+= " AND s.gusto=\""+ caratterisitche.get(0)+"\"";
        if(!caratterisitche.get(1).equals("tutti"))
            query+= " AND s.prezzo<="+ caratterisitche.get(1);
        if(!caratterisitche.get(2).equals("tutti"))
            query+= " AND s.quantita=\""+ caratterisitche.get(2)+"\"";

        try{
            PreparedStatement ps= con.prepareStatement(query);
            ps.setString(1,category);
            ps.setString(2,specific);
            ResultSet rs=ps.executeQuery();

            while (rs.next()){
                prodottos.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodottos;
    }

    //prendo tutti i prodotti con le specifiche, anche quelli non in catalogo (utile per gli admin)
    public List<Prodotto> getProdottiWhitSpecifiche(){
        List<Prodotto> prodottos= new ArrayList<>();
        try{
            PreparedStatement ps= con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE p.idProdotto=s.idProdotto");
            ResultSet rs=ps.executeQuery();

            while (rs.next()){
                prodottos.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodottos;
    }

    //prendo tutti i prodotti con le specifiche, solo quelli in catalogo (Utente)
    public List<Prodotto> getProdottiWhitSpecificheUtente(){
        List<Prodotto> prodottos= new ArrayList<>();
        try{
            PreparedStatement ps= con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE s.Incatalogo=true AND p.idProdotto=s.idProdotto");
            ResultSet rs=ps.executeQuery();

            while (rs.next()){
                prodottos.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodottos;
    }

    //prende tutti i prodotti con un determinato id,quelliin catalogo(Utente)
    public ArrayList<Prodotto> getProdottiByID(int id){
        ArrayList<Prodotto> prodotti=new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE s.Incatalogo=true AND p.idProdotto=s.idProdotto AND p.idProdotto=?");
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                prodotti.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    //prende tutti i prodotti con un determinato id, anche quelli non in catalogo(Admin)
    public ArrayList<Prodotto> getProdottiByIDAdmin(int id){
        ArrayList<Prodotto> prodotti=new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE p.idProdotto=s.idProdotto AND p.idProdotto=?");
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                prodotti.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    //predno un singolo prodotto con un determinato id e con il suo numero di specifica, in catallogo
    public Prodotto getProdottoByIdAndSpecifica(int id,int numeroSpecific){
        Prodotto p=null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE s.Incatalogo=true AND p.idProdotto=s.idProdotto AND p.idProdotto=? AND s.numero=?");
            ps.setInt(1,id);
            ps.setInt(2,numeroSpecific);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                p= this.createProduct(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    //predno un singolo prodotto con un determinato id e con il suo numero di specifica, in catallogo
    public Prodotto getProdottoByIdAndSpecificaAdmin(int id,int numeroSpecific){
        Prodotto p=null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE p.idProdotto=s.idProdotto AND p.idProdotto=? AND s.numero=?");
            ps.setInt(1,id);
            ps.setInt(2,numeroSpecific);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                p= this.createProduct(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    //prendo il prodotto con un determinato id gusto e quantita, in catalogo.(Utente)
    public Prodotto getProdottoBySpecifica(int id,String gusto, String quantita){
        Prodotto prodotto=null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE s.Incatalogo=true AND p.idProdotto=s.idProdotto AND p.idProdotto=? AND s.gusto=? AND s.quantita=?");
            ps.setInt(1,id);
            ps.setString(2,gusto);
            ps.setString(3,quantita);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                prodotto= this.createProduct(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotto;
    }


    //prendo il prodotto con un determinato id gusto e quantita, non in catalogo (Admin)
    public Prodotto getProdottoBySpecificaAdmin(int id,String gusto, String quantita){
        Prodotto prodotto=null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto p,Specifiche s WHERE p.idProdotto=s.idProdotto AND p.idProdotto=? AND s.gusto=? AND s.quantita=?");
            ps.setInt(1,id);
            ps.setString(2,gusto);
            ps.setString(3,quantita);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                prodotto= this.createProduct(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prodotto;
    }


    //creo un prodotto e lo carico nel database
    public void creaProdotto(Prodotto prodotto){
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Prodotto (Nome,Descrizione,Categoria,Specifica) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setString(3, prodotto.getCategoria());
            ps.setString(4, prodotto.getSpecifica());

            ps.executeUpdate();

            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            prodotto.setiDProdotto(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //prodotti piu venduti (solo per la home)
    public ArrayList<Prodotto> getProdottiPiuVenduti(){
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT s.inCatalogo,p.specifica,p.categoria,s.prezzo,p.descrizione,s.idProdotto,s.numero,s.gusto,s.quantita,s.numeropezzi,p.nome,count(*) as numeroAcquisti FROM Prodotto p,Specifiche s,Acquisto a WHERE p.idProdotto=s.idProdotto AND a.idProdotto=s.idProdotto AND a.idSpecifica=s.numero AND s.inCatalogo=true GROUP BY s.idProdotto,s.numero ORDER BY numeroAcquisti DESC");

            ResultSet rs= ps.executeQuery();
            Prodotto prodotto;
            for(int i=0;i<3 && rs.next();i++){
                prodotto=createProduct(rs);
                prodotti.add(prodotto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prodotti;
    }

    public ArrayList<Prodotto> getProdottiPerNomeESpecifiche(List<String> nomi,ArrayList<String> caratterisitche){
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        String query="SELECT * FROM Prodotto p,Specifiche s WHERE p.idProdotto=s.idProdotto AND s.Incatalogo=true";

        if(!caratterisitche.get(0).equals("tutti"))
            query+= " AND s.gusto=\""+ caratterisitche.get(0)+"\"";
        if(!caratterisitche.get(1).equals("tutti"))
            query+= " AND s.prezzo<="+ caratterisitche.get(1);
        if(!caratterisitche.get(2).equals("tutti"))
            query+= " AND s.quantita=\""+ caratterisitche.get(2)+"\"";

        for (String parola : nomi){
            query+= " AND INSTR(p.nome,\""+ parola +"\")";
        }

        try{
            PreparedStatement ps= con.prepareStatement(query);

            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                prodotti.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    public ArrayList<Prodotto> getProdottiPerNomeESpecificheAdmin(List<String> nomi,ArrayList<String> caratterisitche){
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        String query="SELECT * FROM Prodotto p,Specifiche s WHERE p.idProdotto=s.idProdotto";

        if(!caratterisitche.get(0).equals("tutti"))
            query+= " AND s.gusto=\""+ caratterisitche.get(0)+"\"";
        if(!caratterisitche.get(1).equals("tutti"))
            query+= " AND s.prezzo<="+ caratterisitche.get(1);
        if(!caratterisitche.get(2).equals("tutti"))
            query+= " AND s.quantita=\""+ caratterisitche.get(2)+"\"";

        for (String parola : nomi){
            query+= " AND INSTR(p.nome,\""+ parola +"\")";
        }

        try{
            PreparedStatement ps= con.prepareStatement(query);

            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                prodotti.add(createProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

}
