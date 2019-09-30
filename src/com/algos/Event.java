package com.algos;

import java.util.ArrayList;
import java.util.Collections;

public class Event {
    private Point point;
    private ArrayList<LineSegment> lineSegments;
    private double value;
    private int type;

    Event(Point p, LineSegment s, int type) {
        this.point = p;
        this.lineSegments = new ArrayList<>(Collections.singletonList(s));
        this.value = p.getX();
        this.type = type;
    }

    Event(Point p, ArrayList<LineSegment> s, int type) {
        this.point = p;
        this.lineSegments = s;
        this.value = p.getX();
        this.type = type;
    }

    public void addPoint(Point p) {
        this.point = p;
    }

    public Point getPoint() {
        return this.point;
    }

    public void addSegment(LineSegment s) {
        this.lineSegments.add(s);
    }

    public ArrayList<LineSegment> getLineSegments() {
        return this.lineSegments;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
