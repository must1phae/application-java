package models;
public class Vendeur extends Employe {
    private double chiffreAffaire;
    public Vendeur(int id, String nom, int age, String date, double ca) { super(id, nom, age, date); this.chiffreAffaire = ca; }
    public Vendeur(String nom, int age, String date, double ca) { super(nom, age, date); this.chiffreAffaire = ca; }
    @Override public double calculerSalaire() { return (0.2 * chiffreAffaire) + 4000; } // [cite: 358]
}