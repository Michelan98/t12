package ca.mcgill.ecse321.driverapp.model;

import java.io.Serializable;
import java.sql.Time;


public class TripNode implements Serializable {

    private Long id;
    private Position position;
    private PointType pointType;
    private Time time;
    private Trip trip;

    public TripNode() {

    }

    public TripNode(Position position, PointType pointType, Time time, Trip trip) {
        super();
        this.position = position;
        this.pointType = pointType;
        this.time = time;
        this.trip = trip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public void setPointType(PointType value) {
        this.pointType = value;
    }

    public PointType getPointType() {
        return this.pointType;
    }

    public void setTime(Time value) {
        this.time = value;
    }

    public Time getTime() {
        return this.time;
    }

    public Trip getTrip() {
        return this.trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}