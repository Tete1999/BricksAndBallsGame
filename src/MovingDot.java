import org.w3c.dom.css.Rect;

import java.awt.*;

public class MovingDot extends Dot {

	private Rectangle location;
	private double dx;
	private double dy;
	private double x;
	private double y;
	private double speed;

	public MovingDot(Point center, Point direction, double speed){
		super(center);
		x = center.x -radius;
		y = center.y - radius;
		location = new Rectangle(center.x -radius,center.y - radius, 2*radius, 2 *radius);
		double ang = Math.acos((direction.x-center.x)/direction.distance(center));

		double dx = (speed*Math.cos(ang));
		double dy = (speed*Math.sin(ang));
		if (direction.y < this.y){
			dy = -dy;
		}
		setMotion(dx, dy);
	}

	public MovingDot(Point center) {
		this(center,center, 0);
	}

	public MovingDot(Point center, double speed) {
		this(center, center, speed);
	}

	@Override
	void paint(Graphics g) {
		g.setColor(color);
		g.fillOval((int)x, (int)y, radius*2, radius*2);
	}

	private void setMotion(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}

	protected void move(){
		x += dx;
		y += dy;
		location.x = (int)x;
		location.y = (int)y;
	}

	protected Rectangle getRegion(){
		return location;
	}

	protected void reflectX(){
		dx = -dx;
	}
	protected void reflectY(){
		dy = -dy;
	}

	protected int top(){
		return location.y;
	}
	protected int bottom(){
		return location.y+location.height;
	}
	protected int left(){
		return location.x;
	}
	protected int right(){
		return location.x +location.width;
	}
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
