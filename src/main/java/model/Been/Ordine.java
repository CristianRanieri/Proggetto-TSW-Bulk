package model.Been;

import java.sql.Date;
import java.util.ArrayList;

public class Ordine {

    public Ordine() {
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public java.util.Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public int getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = Integer.parseInt(civico);
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public ArrayList<Acquisto> getAcquisti() {
        return acquisti;
    }

    public void setAcquistoOrdine(Acquisto a,int index){
        acquisti.add(index,a);
    }
    public Acquisto getIndexAcquistoOrdine(int index){
        return acquisti.get(index);
    }

    public void setAcquisti(ArrayList<Acquisto> acquisti) {
        this.acquisti = acquisti;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine = (Ordine) o;
        return idOrdine == ordine.idOrdine;
    }
    private int idOrdine;
    private Date data;
    private String via;
    private int civico;
    private String cap;

    private ArrayList<Acquisto> acquisti= new ArrayList<>();
}
