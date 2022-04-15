package oran.myapp.reservation.modele;

public class Service {

    private String Name ;
    private int photo_res;

    public Service() {
    }

    public Service(String name, int photo_res) {
        Name = name;
        this.photo_res = photo_res;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPhoto_res() {
        return photo_res;
    }

    public void setPhoto_res(int photo_res) {
        this.photo_res = photo_res;
    }
}
