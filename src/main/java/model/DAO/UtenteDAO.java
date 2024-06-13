package model.DAO;

import model.ConPool;
import model.Been.Utente;

import java.sql.*;

public class UtenteDAO {
    private static final Connection con;

    static {
        try {
            con = ConPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente getUtenteByEmailPass(Utente u){
        Utente utente=null;
        try {
            PreparedStatement ps= con.prepareStatement("SELECT * FROM Utente WHERE email=? AND pass=?");
            ps.setString(1,u.getEmail());
            ps.setString(2,u.getPassword());

            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                utente=new Utente();
                utente.setId(rs.getInt("IdUtente"));
                utente.setEmail(rs.getString("email"));
                utente.setNome(rs.getString("nome"));
                utente.setPassword(rs.getString("pass"));
                utente.setAdmin(rs.getBoolean("addmin"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    public Utente getUtenteByEmail(String emaile){
        Utente utente=null;
        try {
            PreparedStatement ps= con.prepareStatement("SELECT * FROM Utente WHERE email=?");
            ps.setString(1,emaile);

            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                utente=new Utente();
                utente.setId(rs.getInt("IdUtente"));
                utente.setEmail(rs.getString("email"));
                utente.setNome(rs.getString("nome"));
                utente.setPassword(rs.getString("pass"));
                utente.setAdmin(rs.getBoolean("addmin"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    public void saveUtente(Utente u){
        try {
            PreparedStatement ps= con.prepareStatement("INSERT INTO Utente(nome,email,pass,addmin) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,u.getNome());
            ps.setString(2,u.getEmail());
            ps.setString(3,u.getPassword());
            ps.setBoolean(4,u.isAdmin());
            ps.executeUpdate();

            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            u.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cambiaNomeEPassword(Utente u){
        try {
            PreparedStatement ps= con.prepareStatement("UPDATE Utente SET nome=? , pass=? WHERE idUtente=?");
            ps.setString(1,u.getNome());
            ps.setString(2,u.getPassword());
            ps.setInt(3,u.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
