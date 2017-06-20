package com.github.steevedroz.optimalbubble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class OptimalBubble extends Scene {
    private List<Bubble> bubbles;
    private AnchorPane root;
    private Random random = new Random();

    public OptimalBubble() {
	super(new AnchorPane(), 400, 400);
	this.root = (AnchorPane) getRoot();

	bubbles = Bubble.fill(getWidth(), getHeight());

	for (Bubble bubble : bubbles) {
	    this.root.getChildren().add(bubble.getDisplay());
	}

	setOnMouseClicked((event) -> {
	    List<Bubble> toBeRemoved = new ArrayList<Bubble>();

	    for (Bubble bubble : bubbles) {
		bubble.prepare();
		try {
		    bubble.update();
		} catch (MergedException e) {
		    toBeRemoved.add(bubble);
		}
	    }
	    System.out.println("REMOVE " + toBeRemoved.size());
	    for (Bubble bubble : toBeRemoved) {
		bubbles.remove(bubble);
		root.getChildren().remove(bubble.getDisplay());
	    }
	    System.out.println(bubbles.size());
	});
    }
}
