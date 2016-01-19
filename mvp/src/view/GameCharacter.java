package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

public class GameCharacter {
	int x,y,z;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
	
	public GameCharacter(int x,int y,int z) 
	{ 
		this.x=x;this.y=y;this.z = z;
	}
	public void paint(PaintEvent e,int w,int h)
	{
		e.gc.setBackground(new Color(null,255,0,0));
		e.gc.setForeground(new Color(null,255,0,0));
		e.gc.fillOval(x,y, w, h);
	}
}
