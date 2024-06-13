package model.Been;

public class Prodotto {
    public Prodotto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return specifiche.getPrezzo();
    }

    public void setPrezzo(double prezzo) {
        this.specifiche.setPrezzo(prezzo);
    }

    public int getQuantita() {
        return specifiche.getQuantita();
    }

    public void setQuantita(int quantita) {
        this.specifiche.setQuantita(quantita);
    }

    public String getGusto() {
        return specifiche.getGusto();
    }

    public void setGusto(String gusto) {
        this.specifiche.setGusto(gusto);
    }

    public int getNumeroPezzi() {
        return specifiche.getNumeroPezzi();
    }

    public void setNumeroPezzi(int numeroPezzi) {
        this.specifiche.setNumeroPezzi(numeroPezzi);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSpecifica() {
        return specifica;
    }

    public void setSpecifica(String specifica) {
        this.specifica = specifica;
    }

    public int getiDProdotto() {
        return iDProdotto;
    }

    public void setiDProdotto(int iDProdotto) {
        this.iDProdotto = iDProdotto;
    }

    public boolean isInCatalogo() {
        return this.specifiche.isInCatalogo();
    }

    public void setInCatalogo(boolean inCatalogo) {
        this.specifiche.setInCatalogo(inCatalogo);
    }

    public SpecificheProdotto getSpecificaProdotto() {
        return specifiche;
    }

    public void setSpecificaProdotto(SpecificheProdotto specifiche) {
        this.specifiche = specifiche;
    }

    public void setNumero(int numero){
        this.specifiche.setNumero(numero);
    }

    public int getNumero(){
        return this.specifiche.getNumero();
    }


    private SpecificheProdotto specifiche= new SpecificheProdotto();
    private String categoria;
    private String specifica;
    private int iDProdotto;
    private String nome;
    private String descrizione;
}
