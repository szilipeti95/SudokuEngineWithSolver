package util;

import java.awt.*;

public class Highlight {
	private int x;
	private int y;
	private Color color;
	
	public Highlight(int x, int y, Color c){
		this.x = x;
		this.y = y;
		this.color = c;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
}
