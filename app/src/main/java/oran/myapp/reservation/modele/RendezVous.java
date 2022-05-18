package oran.myapp.reservation.modele;

public class RendezVous {
    private String id,did,date,time , pid ;
    private int  state;


    public RendezVous() {
    }

    public RendezVous(String id , String did,String pid , String date,String time, int state) {
        this.id=id;
        this.did = did;
        this.date = date;

        this.state=state;
        this.pid=pid;
        this.time=time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
