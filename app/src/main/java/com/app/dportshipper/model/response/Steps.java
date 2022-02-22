package com.app.dportshipper.model.response;

public class Steps {
    private Description distance;
    private Description duration;
    private Coordinate end_location;
    private String html_instructions;
    private Point polyline;
    private Coordinate start_location;
    private String travel_mode;

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

    public Coordinate getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Coordinate end_location) {
        this.end_location = end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public Point getPolyline() {
        return polyline;
    }

    public void setPolyline(Point polyline) {
        this.polyline = polyline;
    }

    public Coordinate getStart_location() {
        return start_location;
    }

    public void setStart_location(Coordinate start_location) {
        this.start_location = start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }
}
