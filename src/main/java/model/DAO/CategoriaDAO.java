package model.DAO;

import model.Been.Categoria;
import model.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private static final Connection con;

    static {
        try {
            con = ConPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //prendo tuttle le categorie nel databse, dove le categorie sono un oggeto nome della categoria e lista delle sue specifiche
    public List<Categoria> getCategory(){
        List<Categoria> categorias= new ArrayList<>();
        try{
            PreparedStatement ps= con.prepareStatement("SELECT DISTINCT Categoria FROM Categoria");

            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                ps= con.prepareStatement("SELECT Specifica FROM Categoria WHERE Categoria=?");
                ps.setString(1,rs.getString(1));

                ResultSet rs2= ps.executeQuery();

                Categoria categoria= new Categoria();
                ArrayList<String> specifiche= new ArrayList<>();
                categoria.setNome(rs.getString(1));
                categoria.setSpecifiche(specifiche);

                while (rs2.next()){
                    specifiche.add(rs2.getString("Specifica"));
                }

                categorias.add(categoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categorias;
    }

    public void creaCategoria(String cat, String spe){
        try{
            PreparedStatement ps= con.prepareStatement("INSERT INTO Categoria VALUES (?,?)");
            ps.setString(1,cat);
            ps.setString(2,spe);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
