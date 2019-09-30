package com.algos;

import java.util.*;

class Intersection {
    ArrayList<LineSegment> lineSegments;
    private Queue<Event> pQ;
    private NavigableSet<LineSegment> sL;
    private ArrayList<Point> points;

    Intersection(ArrayList<LineSegment> lineSegments) {
        this.pQ = new PriorityQueue<>(new event_comparator());
        this.sL = new TreeSet<>(new segment_comparator());
        this.points = new ArrayList<>();
        this.lineSegments = lineSegments;

        for(LineSegment s : lineSegments) {
            this.pQ.add(new Event(s.first(), s, 0));
            this.pQ.add(new Event(s.second(), s, 1));
        }
    }

    void bruteForce(){
        pQ.clear();

        for(int i=0; i<lineSegments.size(); i++){
            for(int j = i + 1; j<lineSegments.size(); j++){
                this.reportIntersection(lineSegments.get(i), lineSegments.get(j), lineSegments.get(i).first().getX());
            }
        }

        for(Event event: pQ){
            points.add(event.getPoint());
        }
    }

    void findIntersections() {
        while(!this.pQ.isEmpty()) {
            Event e = this.pQ.poll();
            double L = e.getValue();
            switch(e.getType()) {
                case 0:
                    for(LineSegment s : e.getLineSegments()) {
                        this.recalculate(L);
                        this.sL.add(s);
                        if(this.sL.lower(s) != null) {
                            LineSegment r = this.sL.lower(s);
                            this.reportIntersection(r, s, L);
                        }
                        if(this.sL.higher(s) != null) {
                            LineSegment t = this.sL.higher(s);
                            this.reportIntersection(t, s, L);
                        }
                        if(this.sL.lower(s) != null && this.sL.higher(s) != null) {
                            LineSegment r = this.sL.lower(s);
                            LineSegment t = this.sL.higher(s);
                            this.removeFuture(r, t);
                        }
                    }
                    break;
                case 1:
                    for(LineSegment s : e.getLineSegments()) {
                        if(this.sL.lower(s) != null && this.sL.higher(s) != null) {
                            LineSegment r = this.sL.lower(s);
                            LineSegment t = this.sL.higher(s);
                            this.reportIntersection(r, t, L);
                        }
                        this.sL.remove(s);
                    }
                    break;
                case 2:
                    LineSegment s1 = e.getLineSegments().get(0);
                    LineSegment s2 = e.getLineSegments().get(1);
                    this.swap(s1, s2);
                    if(s1.get_value() < s2.get_value()) {
                        handleIntersection(L, s1, s2);
                    } else {
                        handleIntersection(L, s2, s1);
                    }
                    this.points.add(e.getPoint());
                    break;
            }
        }
    }

    private void handleIntersection(double l, LineSegment s1, LineSegment s2) {
        if(this.sL.higher(s1) != null) {
            LineSegment t = this.sL.higher(s1);
            this.reportIntersection(t, s1, l);
            this.removeFuture(t, s2);
        }
        if(this.sL.lower(s2) != null) {
            LineSegment r = this.sL.lower(s2);
            this.reportIntersection(r, s2, l);
            this.removeFuture(r, s1);
        }
    }

    @SuppressWarnings("Duplicates")
    private void reportIntersection(LineSegment s1, LineSegment s2, double L) {
        double x1 = s1.first().getX();
        double y1 = s1.first().getY();
        double x2 = s1.second().getX();
        double y2 = s1.second().getY();
        double x3 = s2.first().getX();
        double y3 = s2.first().getY();
        double x4 = s2.second().getX();
        double y4 = s2.second().getY();
        double r = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3);
        if(r != 0) {
            double t = ((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / r;
            double u = ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / r;
            if(t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                double x_c = x1 + t * (x2 - x1);
                double y_c = y1 + t * (y2 - y1);
                if(x_c > L) {
                    this.pQ.add(new Event(new Point(x_c, y_c), new ArrayList<>(Arrays.asList(s1, s2)), 2));
                }
            }
        }
    }

    private void removeFuture(LineSegment s_1, LineSegment s_2) {
        for(Event e : this.pQ) {
            if(e.getType() == 2) {
                if((e.getLineSegments().get(0) == s_1 && e.getLineSegments().get(1) == s_2) || (e.getLineSegments().get(0) == s_2 && e.getLineSegments().get(1) == s_1)) {
                    this.pQ.remove(e);
                    return;
                }
            }
        }
    }

    private void swap(LineSegment s_1, LineSegment s_2) {
        this.sL.remove(s_1);
        this.sL.remove(s_2);
        double value = s_1.get_value();
        s_1.set_value(s_2.get_value());
        s_2.set_value(value);
        this.sL.add(s_1);
        this.sL.add(s_2);
    }

    private void recalculate(double L) {
        for (LineSegment lineSegments : this.sL) {
            lineSegments.calculate_value(L);
        }
    }

    void printIntersections() {
        for(Point p : this.points) {
            System.out.println("(" + p.getX() + ", " + p.getY() + ")");
        }
    }

    ArrayList<Point> get_intersections() {
        return this.points;
    }

    private class event_comparator implements Comparator<Event> {
        @Override
        public int compare(Event e_1, Event e_2) {
            return Double.compare(e_1.getValue(), e_2.getValue());
        }
    }

    private class segment_comparator implements Comparator<LineSegment> {
        @Override
        public int compare(LineSegment s_1, LineSegment s_2) {
            return Double.compare(s_2.get_value(), s_1.get_value());
        }
    }
}
