package models;
public class Manutentionnaire extends Employe {
    protected int heures;
    public Manutentionnaire(int id, String nom, int age, String date, int heures) { super(id, nom, age, date); this.heures = heures; }
    public Manutentionnaire(String nom, int age, String date, int heures) { super(nom, age, date); this.heures = heures; }
    @Override public double calculerSalaire() { return heures * 650; } // [cite: 361]
}