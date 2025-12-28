package models;

public abstract class Employe {
    protected int id;
    protected String nom;
    protected int age;
    protected String dateEntree;

    // Constructeur sans ID (pour la création)
    public Employe(String nom, int age, String dateEntree) {
        this.nom = "L'employé " + nom; // Exigence du TP [cite: 354]
        this.age = age;
        this.dateEntree = dateEntree;
    }

    // Constructeur complet (pour la lecture depuis la BDD)
    public Employe(int id, String nom, int age, String dateEntree) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.dateEntree = dateEntree;
    }

    public abstract double calculerSalaire();

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getAge() { return age; }
    public String getDateEntree() { return dateEntree; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
}