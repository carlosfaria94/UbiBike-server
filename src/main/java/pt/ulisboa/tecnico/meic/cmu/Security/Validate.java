package pt.ulisboa.tecnico.meic.cmu.Security;

public class Validate {
    public String hmac, tuple, sender, receiverFirstName;
    public int timestamp, points;

    public Validate() {

    }

    public Validate(String hmac, String sender, String receiverFirstName, int timestamp, int points) {
        this.hmac = hmac;
        this.sender = sender;
        this.receiverFirstName = receiverFirstName;
        this.timestamp = timestamp;
        this.points = points;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiverFirstName() {
        return receiverFirstName;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public String getTuple() {
        return tuple;
    }

    public void setTuple(String tuple) {
        this.tuple = tuple;
    }

    public void buildTuple() {
        setTuple(getSender() + "," + getReceiverFirstName() + "," + getTimestamp() + "," + getPoints());
    }
}
