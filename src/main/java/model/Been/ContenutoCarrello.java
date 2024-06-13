package model.Been;

public class ContenutoCarrello {
    public ContenutoCarrello(){

    }

    public int getNumeroPezzi() {
        return numeroPezzi;
    }

    public void setNumeroPezzi(int numeroPezzi) {
        this.numeroPezzi = numeroPezzi;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContenutoCarrello that = (ContenutoCarrello) o;
        return idProdotto == that.idProdotto && numero == that.numero;
    }

    private int numeroPezzi;
    private int idProdotto;
    private int numero;
}
