package model.DAO;

import model.*;
import model.Been.Acquisto;
import model.Been.Ordine;
import model.Been.Prodotto;

import java.sql.*;
import java.util.ArrayList;

public class OrdineDAO {
    private static final Connection con;

    static {
        try {
            con = ConPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //restituisce tutti gli ordini (Per gli admin)
    public ArrayList<Ordine> getOrdini(){
        ArrayList<Ordine> ordini = new ArrayList<>();
        try{
            String qeuary="SELECT * FROM Acquisto WHERE IdOrdine=?";

            PreparedStatement ps= con.prepareStatement("SELECT * FROM Ordini");
            ResultSet rs=ps.executeQuery();

            while (rs.next()){
                ps= con.prepareStatement(qeuary);
                ps.setInt(1,rs.getInt("IdOrdine"));

                Ordine ordine= new Ordine();
                ordine.setIdOrdine(rs.getInt("IdOrdine"));
                ordine.setData(rs.getDate("DataOrdine"));
                ordine.setVia(rs.getString("Via"));
                ordine.setCivico(rs.getString("Civico"));
                ordine.setCap(rs.getString("CAP"));
                ordini.add(ordine);

                ResultSet rs2=ps.executeQuery();
                while (rs2.next()){
                    ProdottoDAO prodottoDAO= new ProdottoDAO();
                    Prodotto prodotto= prodottoDAO.getProdottoByIdAndSpecificaAdmin(rs2.getInt("IdProdotto"), rs2.getInt("IdSpecifica"));

                    Acquisto acquisto = new Acquisto();
                    acquisto.setPrezzoAcquisto(rs2.getDouble("PrezzoAcquisto"));
                    acquisto.setNumeroPezzi(rs2.getInt("NumeroPezzi"));
                    acquisto.setProdotto(prodotto);

                    ordine.setAcquistoOrdine(acquisto,ordine.getAcquisti().size());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordini;
    }




    //restituisce tutti gli ordini di un utetne
    public ArrayList<Ordine> getOrdiniUtente(int id){
        ArrayList<Ordine> ordini = new ArrayList<>();
        try{
            String qeuary="SELECT * FROM Acquisto WHERE IdOrdine=?";

            PreparedStatement ps= con.prepareStatement("SELECT DISTINCT o.IdOrdine,o.DataOrdine,o.Via,o.Civico,o.CAP FROM Ordini o,Acquisto a WHERE o.IdOrdine=a.IdOrdine AND a.IdCarrello=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();

            while (rs.next()){
                ps= con.prepareStatement(qeuary);
                ps.setInt(1,rs.getInt("IdOrdine"));

                Ordine ordine= new Ordine();
                ordine.setIdOrdine(rs.getInt("IdOrdine"));
                ordine.setData(rs.getDate("DataOrdine"));
                ordine.setVia(rs.getString("Via"));
                ordine.setCivico(rs.getString("Civico"));
                ordine.setCap(rs.getString("CAP"));
                ordini.add(ordine);

                ResultSet rs2=ps.executeQuery();
                while (rs2.next()){
                    ProdottoDAO prodottoDAO= new ProdottoDAO();
                    Prodotto prodotto= prodottoDAO.getProdottoByIdAndSpecificaAdmin(rs2.getInt("IdProdotto"), rs2.getInt("IdSpecifica"));
                    Acquisto acquisto = new Acquisto();
                    acquisto.setPrezzoAcquisto(rs2.getDouble("PrezzoAcquisto"));
                    acquisto.setNumeroPezzi(rs2.getInt("NumeroPezzi"));
                    acquisto.setProdotto(prodotto);

                    ordine.setAcquistoOrdine(acquisto,ordine.getAcquisti().size());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordini;
    }



    public void creaOrdine(Ordine ordine, int idCarrello){
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Ordini (DataOrdine,Via,Civico,CAP) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, (Date) ordine.getData());
            ps.setString(2, ordine.getVia());
            ps.setInt(3,ordine.getCivico());
            ps.setString(4,ordine.getCap());

            ps.executeUpdate();

            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int idOrdine=rs.getInt(1);

            for(Acquisto acquisto : ordine.getAcquisti()){
                ps = con.prepareStatement("INSERT INTO Acquisto (idProdotto,IdSpecifica,IdCarrello,IdOrdine,NumeroPezzi,PrezzoAcquisto) VALUES (?,?,?,?,?,?)");
                ps.setInt(1,acquisto.getProdotto().getiDProdotto());
                ps.setInt(2,acquisto.getProdotto().getNumero());
                ps.setInt(3, idCarrello);
                ps.setInt(4,idOrdine);
                ps.setInt(5,acquisto.getNumeroPezzi());
                ps.setDouble(6,acquisto.getPrezzoAcquisto());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
