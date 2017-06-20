package com.github.steevedroz.optimalbubble;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.github.steevedroz.util.DoubleUtil;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Bubble extends Element {
    private static final double SIZE = 40;
    private static final double SPEED = 2;
    private static final Random RANDOM = new Random();

    private Set<Bubble> neighbors;
    private Set<Bubble> collapsed;
    private double size;
    private double speed;
    private double nextX;
    private double nextY;
    private boolean merged;

    private Ellipse display;

    public Bubble(double x, double y) {
	super(x, y);
	this.neighbors = new HashSet<Bubble>();
	this.collapsed = new HashSet<Bubble>();
	this.size = SIZE;
	this.speed = SPEED;
	this.merged = false;

	this.display = new Ellipse(x, y, size, size);
	this.display.setFill(new Color(RANDOM.nextDouble(), RANDOM.nextDouble(), RANDOM.nextDouble(), 1));
    }

    public static List<Bubble> fill(double width, double height) {
	Bubble[][] bubbles = new Bubble[(int) (height / (2 * SIZE))][];
	List<Bubble> list = new ArrayList<Bubble>();
	for (int y = 0; y < bubbles.length; y++) {
	    bubbles[y] = new Bubble[(int) (width / (2 * SIZE))];
	    for (int x = 0; x < bubbles[y].length; x++) {
		bubbles[y][x] = new Bubble(2 * SIZE * x + SIZE, 2 * SIZE * y + SIZE);
		list.add(bubbles[y][x]);
	    }
	}

	for (int y = 0; y < bubbles.length; y++) {
	    for (int x = 0; x < bubbles[y].length; x++) {
		if (y > 0)
		    bubbles[y][x].neighbors.add(bubbles[y - 1][x]);
		if (y < bubbles.length - 1)
		    bubbles[y][x].neighbors.add(bubbles[y + 1][x]);
		if (x > 0)
		    bubbles[y][x].neighbors.add(bubbles[y][x - 1]);
		if (x < bubbles[y].length - 1)
		    bubbles[y][x].neighbors.add(bubbles[y][x + 1]);
	    }
	}

	return list;
    }

    public double getSize() {
	return size;
    }

    public Ellipse getDisplay() {
	return display;
    }

    public void setSize(double size) {
	this.size = Math.abs(size);
	display.setRadiusX(size);
	display.setRadiusY(size);
    }

    @Override
    protected void setX(double x) {
	super.setX(x);
	display.setCenterX(x);
    }

    @Override
    protected void setY(double y) {
	super.setX(y);
	display.setCenterY(y);
    }

    @Override
    public void prepare() {
	double[] newLocation = { x, y };
	for (Bubble neighbor : neighbors) {
	    double[] attraction = DoubleUtil.getDirectedLength(new double[] { x, y },
		    new double[] { neighbor.x, neighbor.y }, speed);
	    newLocation[0] += attraction[0];
	    newLocation[1] += attraction[1];

	    if (neighbor != this
		    && DoubleUtil.distance(new double[] { x, y }, new double[] { neighbor.x, neighbor.y }) < 1) {
		collapsed.add(neighbor);
	    }
	}
	System.out.println("Distance: " + DoubleUtil.distance(new double[] { x, y }, newLocation));
	this.nextX = newLocation[0];
	this.nextY = newLocation[1];
    }

    @Override
    public void update() throws MergedException {
	if (merged) {
	    throw new MergedException();
	}

	setX(nextX);
	setY(nextY);

	Set<Bubble> newNeighbors = new HashSet<Bubble>();

	newNeighbors.addAll(neighbors);

	for (Bubble collapsedBubble : collapsed) {
	    newNeighbors.addAll(collapsedBubble.neighbors);
	    collapsedBubble.merged = true;
	}

	neighbors = newNeighbors;

	notifyListeners();
    }

    @Override
    public String toString() {
	StringBuilder str = new StringBuilder();
	str.append("Bubble (").append(x).append(", ").append(y).append(") with ").append(neighbors.size())
		.append(" neighbors");
	return str.toString();
    }
}
