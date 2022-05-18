package oran.myapp.reservation.modele;

public class messages {
    private String msgID, SenderID, RecieverID, Message, Time;
    private Boolean read ;


    public messages() {
    }

    public messages(String msgID , String senderID , String recieverID , String message , String time) {
        this.msgID = msgID;
        SenderID = senderID;
        RecieverID = recieverID;
        Message = message;
        Time = time;
        read = false;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public String getRecieverID() {
        return RecieverID;
    }

    public void setRecieverID(String recieverID) {
        RecieverID = recieverID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

}
