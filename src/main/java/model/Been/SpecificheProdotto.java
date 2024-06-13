package model.Been;

public class SpecificheProdotto {

    public SpecificheProdotto(){

    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getGusto() {
        return gusto;
    }

    public void setGusto(String gusto) {
        this.gusto = gusto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getNumeroPezzi() {
        return numeroPezzi;
    }

    public void setNumeroPezzi(int numeroPezzi) {
        this.numeroPezzi = numeroPezzi;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isInCatalogo() {
        return inCatalogo;
    }

    public void setInCatalogo(boolean inCatalogo) {
        this.inCatalogo = inCatalogo;
    }

    private int quantita;
    private String gusto;
    private double prezzo;
    private int numeroPezzi;
    private boolean inCatalogo;
    private int numero;
}
