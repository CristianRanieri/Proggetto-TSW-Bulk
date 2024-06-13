package model.DAO;

import model.Been.Carrello;
import model.ConPool;
import model.Been.ContenutoCarrello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarrelloDAO {
    private static final Connection con;

    static {
        try {
            con = ConPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Carrello getContnutoCarrelloByUtente(int idUtente){
        Carrello carrello= new Carrello();
        ArrayList<ContenutoCarrello> contenutoCarrellos= new ArrayList<>();
        try{
            PreparedStatement ps= con.prepareStatement("SELECT * FROM Contenuto WHERE IdCarrello=?");
            ps.setInt(1,idUtente);

            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                ContenutoCarrello contenutoCarrello= new ContenutoCarrello();
                contenutoCarrello.setIdProdotto(rs.getInt("IdProdotto"));
                contenutoCarrello.setNumeroPezzi(rs.getInt("NumeroPezzi"));
                contenutoCarrello.setNumero(rs.getInt("Numero"));
                contenutoCarrellos.add(contenutoCarrello);
            }

            carrello.setContenutoCarrello(contenutoCarrellos);
            carrello.setIdUtente(idUtente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carrello;
    }

    /*
    public void mergeCarrellos(int idUtente,Carrello c2) {
        Carrello c1 = this.getContnutoCarrelloByUtente(idUtente);
        ArrayList<ContenutoCarrello> contenutoCarrellosRemove= new ArrayList<>();
        boolean b;
        for (ContenutoCarrello contenutoCarrell1 : c1.getContenutoCarrellos()) {
            b=true;
            for (ContenutoCarrello contenutoCarrello2 : c2.getContenutoCarrellos()) {
                if (contenutoCarrell1.equals(contenutoCarrello2)) {
                    b=false;
                    if(Integer.compare(contenutoCarrell1.getNumeroPezzi(), contenutoCarrello2.getNumeroPezzi()) != 0){
                        try {
                            PreparedStatement ps = con.prepareStatement("UPDATE Contenuto SET numeroPezzi=? WHERE idProdotto=? AND numero=?");
                            ps.setInt(1, contenutoCarrello2.getNumeroPezzi());
                            ps.setInt(2, contenutoCarrello2.getIdProdotto());
                            ps.setInt(3, contenutoCarrello2.getNumero());

                            ps.executeUpdate();
                            contenutoCarrellosRemove.add(contenutoCarrello2);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if(b){
                try {
                    PreparedStatement ps=con.prepareStatement("DELETE FROM Contenuto WHERE idProdotto=? AND numero=?");
                    ps.setInt(1,contenutoCarrell1.getIdProdotto());
                    ps.setInt(2,contenutoCarrell1.getNumero());

                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //per gli ultimi elementi nel secondo carrello che non sono nel primo
        for (ContenutoCarrello contenutoCarrello2 : contenutoCarrellosRemove) {
            try {
                //se l'oggetto della lista del contenuto del carrello non e contenuto in quelli rimossi allora lo agguinge al databae
                if(!c2.getContenutoCarrellos().contains(contenutoCarrello2)) {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Contenuto VALUES (?,?,?,?)");
                    ps.setInt(1, contenutoCarrello2.getIdProdotto());
                    ps.setInt(2, idUtente);
                    ps.setInt(3, contenutoCarrello2.getNumeroPezzi());
                    ps.setInt(4, contenutoCarrello2.getNumero());

                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
     */

    public void caricaCarrello(int idUtente,Carrello c2){
        try {
            //elimino tutto cio che si trova in contenuto di quell'utente
            PreparedStatement ps= con.prepareStatement("DELETE FROM Contenuto WHERE idCarrello=?");
            ps.setInt(1,idUtente);

            System.out.println(idUtente);

            ps.executeUpdate();

            //scrivo tutto cio che si trova nel carrello in contenuto di quell'utente
            for(ContenutoCarrello c: c2.getContenutoCarrello()) {
                ps = con.prepareStatement("INSERT INTO Contenuto VALUES (?,?,?,?)");
                ps.setInt(1, c.getIdProdotto());
                ps.setInt(2, idUtente);
                ps.setInt(3, c.getNumeroPezzi());
                ps.setInt(4, c.getNumero());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCarrello(int idUtente){
        PreparedStatement ps= null;
        try {
            ps = con.prepareStatement("INSERT INTO Carrello VALUES (?)");
            ps.setInt(1,idUtente);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
