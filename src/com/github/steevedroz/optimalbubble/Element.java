package com.github.steevedroz.optimalbubble;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {
    private List<MovementListener> listeners;

    protected double x;
    protected double y;

    public Element(double x, double y) {
	this.x = x;
	this.y = y;

	this.listeners = new ArrayList<MovementListener>();
    }

    public abstract void prepare();

    public abstract void update() throws MergedException;

    public double getX() {
	return x;
    }

    public double getY() {
	return y;
    }

    protected void setX(double x) {
	this.x = x;
    }

    protected void setY(double y) {
	this.y = y;
    }

    public boolean addListener(MovementListener listener) {
	return listeners.add(listener);
    }

    public boolean removeListener(MovementListener listener) {
	return listeners.remove(listener);
    }

    protected void notifyListeners() {
	for (MovementListener listener : listeners) {
	    listener.notify(x, y);
	}
    }
}
