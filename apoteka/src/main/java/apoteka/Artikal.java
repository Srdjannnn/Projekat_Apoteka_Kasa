package apoteka;

public class Artikal {
    private String barkod;
    private String naziv;
    private double cena;
    private int kolicina;

    public Artikal(String barkod, String naziv, double cena, int kolicina) {
        this.barkod = barkod;
        this.naziv = naziv;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    // Get metode za pristup atributima
    public String getBarkod() {
        return barkod;
    }

    public String getNaziv() {
        return naziv;
    }

    public double getCena() {
        return cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    // Set metode za postavljanje vrednosti atributa
    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public String toString() {
        return "Artikal [barkod=" + barkod + ", naziv=" + naziv + ", cena=" + cena + ", kolicina=" + kolicina + "]";
    }
}
