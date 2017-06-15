package com.github.steevedroz.optimalbubble;

import java.util.Enumeration;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class OptimalBubble extends Scene {
    private Enumeration<Bubble> bubbles;

    public OptimalBubble(Parent root) {
	super(root, 400, 400);

	bubbles = Bubble.fill(getWidth(), getHeight());
    }
}
