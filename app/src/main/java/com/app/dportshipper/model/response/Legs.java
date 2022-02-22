package com.app.dportshipper.model.response;

import java.util.List;

public class Legs {
    private Description distance;
    private Description duration;
    private String end_address;
    private Coordinate end_location;
    private String start_address;
    private Coordinate start_location;
    private List<Steps> steps;
    private List traffic_speed_entry;
    private List via_waypoint;

    public Description getDistance() {
        return distance;
    }

    public void setDistance(Description distance) {
        this.distance = distance;
    }

    public Description getDuration() {
        return duration;
    }

    public void setDuration(Description duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public Coordinate getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Coordinate end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public Coordinate getStart_location() {
        return start_location;
    }

    public void setStart_location(Coordinate start_location) {
        this.start_location = start_location;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public List getTraffic_speed_entry() {
        return traffic_speed_entry;
    }

    public void setTraffic_speed_entry(List traffic_speed_entry) {
        this.traffic_speed_entry = traffic_speed_entry;
    }

    public List getVia_waypoint() {
        return via_waypoint;
    }

    public void setVia_waypoint(List via_waypoint) {
        this.via_waypoint = via_waypoint;
    }
}
