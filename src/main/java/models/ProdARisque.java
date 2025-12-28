package models;
public class ProdARisque extends Producteur {
    public ProdARisque(int id, String nom, int age, String date, int unites) { super(id, nom, age, date, unites); }
    public ProdARisque(String nom, int age, String date, int unites) { super(nom, age, date, unites); }
    @Override public double calculerSalaire() { return super.calculerSalaire() + 2000; }
}