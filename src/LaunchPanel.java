import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class LaunchPanel extends JPanel implements VanishListener {

	private int numLives = 3;
	private ArrayList<MovingDot> dots;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<MovingDot> deadDots;
	private ArrayList<Obstacle> deadObstacles;
	private Bat b;
	private Dot launchPoint;
	private Point s;
	private JLabel lives;
	private final int numObstacles = 5;


	public LaunchPanel() {
		setPreferredSize(new Dimension(500, 500));
		dots = new ArrayList<MovingDot>();
		obstacles = new ArrayList<Obstacle>();
		s = new Point(250, 250);
		b = new Bat(new Point(250, 490));
		launchPoint = new Dot(s);
		launchPoint.setColor(Color.GREEN);
		addMouseListener(new MousePlay());
		addKeyListener(new MoveBat());
		setFocusable(true);
		generateObstacles();
		deadDots = new ArrayList<MovingDot>();
		deadObstacles = new ArrayList<Obstacle>();
		lives = new JLabel("Lives Left: " + Integer.toString(numLives));
		add(lives);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		launchPoint.paint(g);
		b.paint(g);
		try {
			for (MovingDot d : dots) {
				d.paint(g);
			}
			for (Obstacle o : obstacles) {
				o.paint(g); }
		}
		catch (ConcurrentModificationException c){}
	}

	private void generateDot(Point p) {
		MovingDot d = new MovingDot(s, p, 5);
		dots.add(d);
		Thread t = new Thread(new Action(d));
		t.start();
	}
	private void generateObstacles() {
		for (int i = 0, j = 50; (i != numObstacles); i++, j += 100) {
			Obstacle o = new Obstacle(new Point(j, 50));
			o.addVanishListener(this);
			obstacles.add(o);
		}
	}
	private class MousePlay extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			generateDot(e.getPoint());
		}
	}
	private class MoveBat extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				b.setCenterX(-15);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				b.setCenterX(15);
			}
			repaint();
		}
	}

	private class Action implements Runnable {
		private MovingDot d;

		Action(MovingDot d) {
			this.d = d;
		}
		private void action() {
			while (true) {
				try {
					d.move();
					if (d.top() < 0) {
						d.reflectY();
					}
					if ((d.left() < 0) || d.right() > getWidth()) {
						d.reflectX();
					}
					if (d.getRegion().intersects(b.getRegion())) {
						b.hitBy(d);
					}

					if (getHeight() < d.bottom()) {
						numLives --;
						deadDots.add(d);
						dots.remove(d);
						if (numLives == 0 && obstacles.size() != 0){
							lives.setText("Lives Left: " + (numLives));
							JOptionPane.showMessageDialog(null, "You Lost");
							System.exit(0);
						}
						lives.setText("Lives Left: " + (numLives));
						break;
					}
					if (obstacles.size() == 0){
						JOptionPane.showMessageDialog(null,"You Won");
						System.exit(0);
					}
					for (Obstacle o : obstacles) {
							if (d.getRegion().intersects(o.getRegion())) {
								o.hitBy(d); }
							if (o.hitCount == 0){break;}
						}
						for (Obstacle o: deadObstacles){ obstacles.remove(o); }
				}

				catch (ConcurrentModificationException c){ }

				try {
					Thread.sleep(15); }
				catch (InterruptedException e) {
					e.printStackTrace(); }
				repaint();
			}
		}
		@Override
		public void run() {
			action();
		}
	}
	@Override
	public void update(VanishEvent e) {
		if (e.control) {
			deadObstacles.add((Obstacle)(e.getSource()));
		}
	}
}



