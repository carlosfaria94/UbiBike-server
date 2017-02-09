package pt.ulisboa.tecnico.meic.cmu;


public class Transaction {
    public String receiverId;
    public int points;

    public Transaction(String receiverId, int points) {
        this.receiverId = receiverId;
        this.points = points;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
