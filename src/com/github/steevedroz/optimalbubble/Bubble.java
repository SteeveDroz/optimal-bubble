package com.github.steevedroz.optimalbubble;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javafx.geometry.Point2D;

public class Bubble extends Element {
    private List<Bubble> neighbors;
    private static double size = 10;
    private Point2D location;

    public Bubble(double x, double y) {
	this.neighbors = new ArrayList<Bubble>();
	this.location = new Point2D(x, y);
    }

    public static Enumeration<Bubble> fill(double width, double height) {
	Dictionary<Point2D, Bubble> bubbles = new Hashtable<Point2D, Bubble>();
	for (int x = 0; x < width; x += getSize()) {
	    for (int y = 0; y < height; y += getSize()) {
		Bubble bubble = new Bubble(x, y);
		bubbles.put(new Point2D(x, y), bubble);
	    }
	}

	for (int x = 0; x < width; x += getSize()) {
	    for (int y = 0; y < height; y += getSize()) {
		Bubble bubble = bubbles.get(new Point2D(x, y));

		if (x > 0) {
		    Bubble neighbor = bubbles.get(new Point2D(x - 1, y));
		    bubble.neighbors.add(neighbor);
		}
		if (x < width - getSize()) {
		    Bubble neighbor = bubbles.get(new Point2D(x + 1, y));
		    bubble.neighbors.add(neighbor);
		}
		if (y > 0) {
		    Bubble neighbor = bubbles.get(new Point2D(x, y - 1));
		    bubble.neighbors.add(neighbor);
		}
		if (y < height - getSize()) {
		    Bubble neighbor = bubbles.get(new Point2D(x, y + 1));
		    bubble.neighbors.add(neighbor);
		}
	    }
	}
	return bubbles.elements();
    }

    public static double getSize() {
	return size;
    }

    public static void setSize(double size) {
	Bubble.size = size;
    }
}
