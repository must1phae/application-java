package models;
public class Representant extends Employe {
    private double chiffreAffaire;
    public Representant(int id, String nom, int age, String date, double ca) {
        super(id, nom, age, date);
        this.chiffreAffaire = ca;
    }
    public Representant(String nom, int age, String date, double ca) {
        super(nom, age, date);
        this.chiffreAffaire = ca;
    }
    @Override public double calculerSalaire() {
        return (0.2 * chiffreAffaire) + 8000;
    }
}