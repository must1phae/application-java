package models;
public class Producteur extends Employe {
    protected int unites;
    public Producteur(int id, String nom, int age, String date, int unites) { super(id, nom, age, date); this.unites = unites; }
    public Producteur(String nom, int age, String date, int unites) { super(nom, age, date); this.unites = unites; }
    @Override public double calculerSalaire() { return unites * 5; } // [cite: 360]
}