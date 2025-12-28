package models;
public class ManutARisque extends Manutentionnaire {
    public ManutARisque(int id, String nom, int age, String date, int heures) { super(id, nom, age, date, heures); }
    public ManutARisque(String nom, int age, String date, int heures) { super(nom, age, date, heures); }
    @Override public double calculerSalaire() { return super.calculerSalaire() + 2000; }
}