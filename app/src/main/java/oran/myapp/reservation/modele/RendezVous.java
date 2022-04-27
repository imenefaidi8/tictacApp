package oran.myapp.reservation.modele;

public class RendezVous {
    private String id,Doctor , date , reason;
    private int  stage , room,state;


    public RendezVous() {
    }

    public RendezVous(String id , String doctor, String date, String reason, int stage, int room,int state) {
        this.id=id;
        Doctor = doctor;
        this.date = date;
        this.reason = reason;
        this.stage = stage;
        this.room = room;
        this.state=state;
    }


    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
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
