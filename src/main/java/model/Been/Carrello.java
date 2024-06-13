package model.Been;

import java.util.ArrayList;

public class Carrello {
    public Carrello(){
    }
    public ArrayList<ContenutoCarrello> getContenutoCarrello() {
        return contenutiCarrello;
    }

    public void setContenutoCarrello(ArrayList<ContenutoCarrello> contenutoCarrello) {
        this.contenutiCarrello = contenutoCarrello;
    }
    public int getIdUtente() {
        return idUtente;
    }
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }
    public void setIndexContenutoCrrello(ContenutoCarrello c,int index){
        contenutiCarrello.add(index,c);
    }
    public ContenutoCarrello getIndexContenutoCarrello(int index){
        return contenutiCarrello.get(index);
    }

    private ArrayList<ContenutoCarrello> contenutiCarrello;
    private int idUtente;
}
