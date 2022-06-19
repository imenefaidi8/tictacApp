package oran.myapp.reservation.modele;

public class Medicament {

        private String id , nom,date ;

    public Medicament() {
    }

    public Medicament(String id , String nom,String date) {
        this.id = id;
        this.nom = nom;
        this.date=date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
