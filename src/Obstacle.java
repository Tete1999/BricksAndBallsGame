import java.awt.*;
import java.util.ArrayList;

public class Obstacle {

	private Point center;
	private Rectangle region;
	private int size;
	private Color color;
	protected int hitCount = 5;
	private ArrayList<VanishListener> vanishListeners;



	public Obstacle(Point center) {
		this.center = center;
		size = 50;
		region = new Rectangle(center.x-size/2,center.y-size/2, size, size);
		color = color.RED;
		vanishListeners = new ArrayList<>();

	}

	public void paint(Graphics g){
		g.setColor(color);
		g.fillRect(region.x,region.y, region.width,region.height);
		g.setColor(Color.GREEN);
		g.drawString(Integer.toString(hitCount), center.x, center.y);
	}

	protected Rectangle getRegion() {
		return region;
	}

	public void hitBy(MovingDot d) {


		if ((d.top() > top()) && (d.bottom() < bottom())) {
			d.reflectX(); }
		else {
			if ((d.left() > left()) && (d.right() < right())) {
				d.reflectY();
			}
			else{
				d.reflectX();
				d.reflectY();
			}
		}
		hitCount = hitCount - 1;
		notifyListeners();
	}

	private int top(){
		return region.y;
	}
	private int bottom(){
		return region.y+region.height;
	}
	private int left(){
		return region.x;
	}
	private int right(){
		return region.x +region.width;
	}

	protected void addVanishListener(VanishListener vl) {
		vanishListeners.add(vl);
	}

	private void notifyListeners() {
		VanishEvent e = new VanishEvent(this, hitCount == 0);
		for (VanishListener vl: vanishListeners ){
			vl.update(e);
		}
	}

}



