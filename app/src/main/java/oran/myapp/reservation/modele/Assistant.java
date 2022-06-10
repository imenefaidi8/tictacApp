package oran.myapp.reservation.modele;

public class Assistant {

    private String uid , nom , prenom , email , password , medecin ;

    public Assistant() {
    }

    public Assistant(String uid , String nom , String prenom , String email , String password , String medecin) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.medecin = medecin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }
}
