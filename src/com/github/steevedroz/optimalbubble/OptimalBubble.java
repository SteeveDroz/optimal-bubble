package com.github.steevedroz.optimalbubble;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class OptimalBubble extends Scene {
    private List<Bubble> bubbles;

    public OptimalBubble(Parent root) {
	super(root, 400, 400);
	bubbles = Bubble.fill(getWidth(), getHeight());
    }
}
