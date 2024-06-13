package model.Been;

import java.util.ArrayList;

public class Categoria {
    public Categoria() {
    }

    public ArrayList<String> getSpecifiche() {
        return specifiche;
    }

    public void setSpecifiche(ArrayList<String> specifiche) {
        this.specifiche = specifiche;
    }

    public void setIndexSpecifiche(int index, String s){
        this.specifiche.add(s);
    }

    public String getIndexSpecifiche(int index){
        return this.specifiche.get(index);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String nome;
    private ArrayList<String> specifiche= new ArrayList<>();
}
