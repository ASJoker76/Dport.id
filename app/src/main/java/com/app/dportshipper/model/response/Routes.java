package com.app.dportshipper.model.response;

import java.util.List;

public class Routes {
    private Bounds bounds;
    private String copyrights;
    private List<Legs> legs;
    private Point overview_polyline;
    private String summary;
    private List warnings;
    private List waypoint_order;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public Point getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Point overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List getWarnings() {
        return warnings;
    }

    public void setWarnings(List warnings) {
        this.warnings = warnings;
    }

    public List getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(List waypoint_order) {
        this.waypoint_order = waypoint_order;
    }
}
