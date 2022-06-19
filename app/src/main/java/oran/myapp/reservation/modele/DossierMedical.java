package oran.myapp.reservation.modele;

public class DossierMedical {
    private String id , date , pid,did, note;
    private Ordenance ordenance;

    public DossierMedical() {
    }

    public DossierMedical(String id , String date , String pid , String did , Ordenance ordenance) {
        this.id = id;
        this.date = date;
        this.pid = pid;
        this.did = did;
        this.ordenance = ordenance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Ordenance getOrdenance() {
        return ordenance;
    }

    public void setOrdenance(Ordenance ordenance) {
        this.ordenance = ordenance;
    }


}
