package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="station")
public class Station {

    @Id
    private String id;

    @Indexed
    public String name;

    public String location;
    public int bikesAvailable;
    public float latitude;
    public float longitude;

    public Station() {
    }

    public Station(String name, String location, int bikesAvailable, float latitude, float longitude) {
        this.name = name;
        this.location = location;
        this.bikesAvailable = bikesAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void incrementBikesAvailable() {
        bikesAvailable++;
    }

    public void decrementBikesAvailable() {
        bikesAvailable--;
    }

    public int getBikesAvailable() {
        return bikesAvailable;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}