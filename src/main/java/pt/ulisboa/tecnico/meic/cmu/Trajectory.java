package pt.ulisboa.tecnico.meic.cmu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="trajectory")
public class Trajectory {

    @Id
    private String id;

    @JsonIgnore
    private String userId;

    public int distTravelled;
    public int pointsEarned;
    public long travelTime;

    public Trajectory() {
    }

    public Trajectory(String userId, int distTravelled, int pointsEarned, long travelTime) {
        this.userId = userId;
        this.distTravelled = distTravelled;
        this.pointsEarned = pointsEarned;
        this.travelTime = travelTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDistTravelled() {
        return distTravelled;
    }

    public void setDistTravelled(int distTravelled) {
        this.distTravelled = distTravelled;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }
}
