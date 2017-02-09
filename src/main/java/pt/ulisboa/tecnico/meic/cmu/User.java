package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection="user")
public class User {

    @Id
    private String id;

    @Indexed
    public String firstName, lastName, email;

    public String password, secretKey;

    public int points;

    public List<Integer> timestamp = new ArrayList<Integer>();

    public ArrayList<Transaction> pointsTransactions = new ArrayList<Transaction>();

    // Register user
    public User(String firstName, String lastName, String email, String password, String secretKey) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.secretKey = secretKey;
    }

    // Register user
    public User(String firstName, String lastName, String email, String password, String secretKey, ArrayList<Transaction> pointsTransactions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.secretKey = secretKey;
        this.pointsTransactions = pointsTransactions;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Integer> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Integer> timestamp) {
        this.timestamp = timestamp;
    }

    public void addTimestamp(int timestamp) {
        this.timestamp.add(timestamp);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Transaction> getPointsTransactions() {
        return pointsTransactions;
    }

    public void setPointsTransactions(ArrayList<Transaction> pointsTransactions) {
        this.pointsTransactions = pointsTransactions;
    }
}