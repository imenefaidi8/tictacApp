package oran.myapp.reservation.modele;

public class Ordenance {

    private String id , NomUser , date ;




    public Ordenance (){

    }

    public Ordenance(String id , String NomUser , String date  ){
        this.id=id;
        this.NomUser=NomUser;
        this.date=date;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomUser() {
        return NomUser;
    }

    public void setNomUser(String nomUser) {
        NomUser = nomUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
