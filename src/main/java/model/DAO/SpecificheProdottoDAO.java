package model.DAO;

import model.ConPool;
import model.Been.SpecificheProdotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SpecificheProdottoDAO {
    private static final Connection con;

    static {
        try {
            con = ConPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SpecificheProdotto createSpecificheprodotto(ResultSet rs) throws SQLException {
        SpecificheProdotto specificheProdotto= new SpecificheProdotto();
        specificheProdotto.setPrezzo(rs.getDouble("prezzo"));
        specificheProdotto.setGusto(rs.getString("gusto"));
        specificheProdotto.setQuantita(rs.getInt("quantita"));
        specificheProdotto.setNumeroPezzi(rs.getInt("numeroPezzi"));
        specificheProdotto.setNumero(rs.getInt("numero"));
        return specificheProdotto;
    }

    //prende tutte le specifiche di un prodotto, anche quelle non in catalogo(per Utenti)
    public ArrayList<SpecificheProdotto> getSpecificheProdotto(int idProdotto){
        ArrayList<SpecificheProdotto> specifics= new ArrayList<>();
        try{
            PreparedStatement ps= con.prepareStatement("SELECT * FROM Specifiche WHERE Incatalogo=true AND idProdotto=?");
            ps.setInt(1,idProdotto);

            ResultSet rs=ps.executeQuery();
            while(rs.next())
                specifics.add(createSpecificheprodotto(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specifics;
    }

    //prende tutte le specifiche di un prodotto, sono quelle incatalogo(Per Admin)
    public ArrayList<SpecificheProdotto> getSpecificheProdottoAdmin(int idProdotto){
        ArrayList<SpecificheProdotto> specifics= new ArrayList<>();
        try{
            PreparedStatement ps= con.prepareStatement("SELECT * FROM Specifiche WHERE idProdotto=?");
            ps.setInt(1,idProdotto);

            ResultSet rs=ps.executeQuery();
            while(rs.next())
                specifics.add(createSpecificheprodotto(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specifics;
    }


    //ricerca una specifica per un prodotto con un detrminato gusto e quantita, anche non in catalogo(per Admin)
    public SpecificheProdotto getSpecificaProdottoWhitQuantitaAndGusto(int idProdotto,String gusto,int quantia){
        SpecificheProdotto specificheProdotto=null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Specifiche WHERE idProdotto=? AND gusto=? AND quantita=?");
            ps.setInt(1, idProdotto);
            ps.setString(2,gusto);
            ps.setInt(3,quantia);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                specificheProdotto=createSpecificheprodotto(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specificheProdotto;
    }

    //restituisce il prossimo unmero che si puo utilizzare per definire una specifica di un dato prodotto
    public int getNumeroSpecifica(int idProdotto){
        int count=0;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as numeroSpecifhice FROM Specifiche WHERE idProdotto=?");
            ps.setInt(1, idProdotto);

            ResultSet rs = ps.executeQuery();
            rs.next();
            count=rs.getInt("numeroSpecifhice");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    //crea la specifica di un determinato prodotto
    public void creaSpecificaProdotto(SpecificheProdotto specifica, int idProdotto){
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Specifiche VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, idProdotto);
            ps.setInt(2, specifica.getNumero());
            ps.setDouble(3, specifica.getPrezzo());
            ps.setInt(4, specifica.getQuantita());
            ps.setString(5, specifica.getGusto());
            ps.setInt(6, specifica.getNumeroPezzi());
            ps.setBoolean(7, true);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //modificare una specifica
    public void modificaSpecificaProdotto(SpecificheProdotto specifica, int idProdotto){
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Specifiche SET Prezzo=?,NumeroPezzi=?,InCatalogo=? WHERE IdProdotto=? AND Numero=?");
            ps.setDouble(1, specifica.getPrezzo());
            ps.setInt(2, specifica.getNumeroPezzi());
            ps.setBoolean(3, specifica.isInCatalogo());
            ps.setInt(4, idProdotto);
            ps.setInt(5, specifica.getNumero());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modificaNumeroPezzi(SpecificheProdotto specifica, int idProdotto){
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Specifiche SET NumeroPezzi=? WHERE IdProdotto=? AND Numero=?");
            ps.setInt(1, specifica.getNumeroPezzi());
            ps.setInt(2, idProdotto);
            ps.setInt(3, specifica.getNumero());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
