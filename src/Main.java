import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main extends JComponent implements Runnable {
	
	public static final int EMPTY = 0;
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;
	public static final int CROSSING = 3;
	
	static int fieldSize = 100;
	static int sizeMap = 3;
	static int[][] map = new int[sizeMap][sizeMap];
	
	static ArrayList<Agent> agents = new ArrayList<Agent>();
	
	static int width = (sizeMap+2)*fieldSize;
	static int height = (sizeMap+2)*fieldSize;
	
	static int agentWidth = 30;
	static int agentHeight = 50;
	
	static Scenario1 scenario1;
	static Scenario2 scenario2;
	static Scenario3 scenario3;
	
	final static float dash1[] = {10.0f};
	final static BasicStroke dashed =
	        new BasicStroke(2.0f,
	                        BasicStroke.CAP_BUTT,
	                        BasicStroke.JOIN_MITER,
	                        10.0f, dash1, 0.0f);
	
	public Main() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			repaint();
			Thread.sleep(4000);
			int nbAgents = agents.size();
			for (int i = 0; i < nbAgents; i++) {
				Agent a = agents.get(0);
				System.out.println("agent goes");
				int orientation = a.getOrientation();
				a.moveFromIntersection();
				float diffX = a.getDestX()*1.0f - a.getX();
				float diffY = a.getDestY()*1.0f - a.getY();
				if (orientation == HORIZONTAL) {
					while (a.getX() != a.getDestX()*1.0) {
						a.setX(a.getX()+diffX/5);
						repaint();
						Thread.sleep(500);
					}
					a.setOrientation(map[a.getDestX()][a.getDestY()]);
					a.setDirection(a.getDestDirection());
				}
				while (a.getY() != a.getDestY()*1.0) {
					a.setY(a.getY()+diffY/5);
					repaint();
					Thread.sleep(500);
				}
				if (orientation == VERTICAL) {
					a.setOrientation(map[a.getDestX()][a.getDestY()]);
					a.setDirection(a.getDestDirection());
					while (a.getX() != a.getDestX()*1.0) {
						a.setX(a.getX()+diffX/5);
						repaint();
						Thread.sleep(500);
					}
				}
				agents.remove(a);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics g){
		Graphics2D g2D = (Graphics2D) g;
		g2D.setPaint(Color.BLACK);
		g2D.setStroke(new BasicStroke(2.0f));
		
		for (int i = 0; i < sizeMap; i++)
			for (int j = 0; j < sizeMap; j++)
				if (map[i][j] == VERTICAL)
					drawVertical(i+1, j+1, g2D);
				else if (map[i][j] == HORIZONTAL)
					drawHorizontal(i+1, j+1, g2D);
		
		
		g2D.setStroke(new BasicStroke(2.0f));
		for (Agent a : agents) {
			drawAgent(a, g2D);
		}
	}
	
	public void drawAgent(Agent a, Graphics2D g2D) {
		Rectangle2D.Double rect;
		QuadCurve2D.Double curve;
		double x = a.getX()*fieldSize+fieldSize+10;
		double y = a.getY()*fieldSize+fieldSize+10;
		int destX = a.getDestX()*fieldSize+fieldSize+10;
		int destY = a.getDestY()*fieldSize+fieldSize+10;
		if (a.getOrientation() == VERTICAL) {
			destX += agentHeight/2;
			destY += agentWidth/2;
			if (a.getDestDirection() == Agent.NORTH)
				destX += fieldSize/2;
			if (a.getDestDirection() == Agent.EAST)
				destY += fieldSize/2;
			
			if (a.getDirection() == Agent.SOUTH) {
				rect = new Rectangle2D.Double(x, y+fieldSize/3, agentWidth, agentHeight);
				curve = new QuadCurve2D.Double(x+agentWidth/2, y+agentHeight/2+fieldSize/3, 
						x+agentHeight/2, x+agentHeight/2, 
						destX, destY);
			}
			else {
				rect = new Rectangle2D.Double(x+fieldSize/2, y, agentWidth, agentHeight);
				x += fieldSize/2;
				curve = new QuadCurve2D.Double(x+agentWidth/2, y+agentHeight/2, 
						x+agentHeight/2, x+agentHeight/2-agentHeight/2, 
						destX, destY);
			}
		}
		else {
			destX += agentWidth/2;
			destY += agentHeight/2;
			if (a.getDestDirection() == Agent.NORTH)
				destX += fieldSize/2;
			if (a.getDestDirection() == Agent.EAST)
				destY += fieldSize/2;
			
			if (a.getDirection() == Agent.WEST) {
				rect = new Rectangle2D.Double(x, y, agentHeight, agentWidth);
				curve = new QuadCurve2D.Double(x+agentHeight/2, y+agentWidth/2, 
						x+agentHeight/2-100-agentWidth/2, x+agentHeight/2-100-agentWidth/2, 
						destX, destY);
			}
			else {
				x += fieldSize/3;
				y += fieldSize/2;
				rect = new Rectangle2D.Double(x, y, agentHeight, agentWidth);
				curve = new QuadCurve2D.Double(x+agentHeight/2, y+agentWidth/2, 
						x+agentHeight/2+100+agentWidth/2, x+agentHeight/2+100+agentWidth/2, 
						destX, destY);
			}
		}
		g2D.setPaint(a.getColor());
		g2D.setStroke(new BasicStroke(2.0f));
		g2D.fill(rect);
		g2D.draw(rect);
		if (a.onIntersection()) {
			g2D.setStroke(dashed);
			g2D.draw(curve);
		}
	}
	
	public void drawVertical(int posX, int posY, Graphics2D g2D) {
		g2D.setPaint(Color.BLACK);
		g2D.setStroke(new BasicStroke(2.0f));
		
		Line2D.Double l = new Line2D.Double(posX*fieldSize, posY*fieldSize, 
				posX*fieldSize, posY*fieldSize+fieldSize);
		g2D.draw(l);
		l = new Line2D.Double(posX*fieldSize+fieldSize, posY*fieldSize, 
				posX*fieldSize+fieldSize, posY*fieldSize+fieldSize);
		g2D.draw(l);
		
		g2D.setStroke(dashed);
		l = new Line2D.Double(posX*fieldSize+fieldSize/2, posY*fieldSize, 
				posX*fieldSize+fieldSize/2, posY*fieldSize+fieldSize);
		g2D.draw(l);
	}
	
	public void drawHorizontal(int posX, int posY, Graphics2D g2D) {
		g2D.setPaint(Color.BLACK);
		g2D.setStroke(new BasicStroke(2.0f));
		
		Line2D.Double l = new Line2D.Double(posX*fieldSize, posY*fieldSize, 
				posX*fieldSize+fieldSize, posY*fieldSize);
		g2D.draw(l);
		l = new Line2D.Double(posX*fieldSize, posY*fieldSize+fieldSize, 
				posX*fieldSize+fieldSize, posY*fieldSize+fieldSize);
		g2D.draw(l);	
		
		g2D.setStroke(dashed);
		l = new Line2D.Double(posX*fieldSize, posY*fieldSize+fieldSize/2, 
				posX*fieldSize+fieldSize, posY*fieldSize+fieldSize/2);
		g2D.draw(l);
	}
	
	private static void initFields(int n){
		for (int i = 0; i < sizeMap; i++)
			for (int j = 0; j < sizeMap; j++) {
				map[i][j] = EMPTY;
			}
		map[0][1] = HORIZONTAL;
		map[1][0] = VERTICAL;
		map[1][2] = VERTICAL;
		map[2][1] = HORIZONTAL;
		map[1][1] = CROSSING;
	}
	
	
	
	private static void runVisualization(){
		JFrame f = new JFrame("TrafficSimulation");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Main());
		System.out.println("running");
		f.setSize(width, height);
		f.setVisible(true);
	}
	
	private static String getConsoleInput(String text) throws IOException{
		System.out.println(text);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		initFields(5);
		int choice = Integer.parseInt(getConsoleInput("Choose scenario: 1/2/3"));
		switch (choice) {
		case 1:
			scenario1 = new Scenario1();
			agents = scenario1.initAgents(map);
			agents = scenario1.moveCars(agents);
			break;
		case 2:
			scenario2 = new Scenario2();
			agents = scenario2.initAgents(map);
			agents = scenario2.moveCars(agents);
			break;
		case 3:
			scenario3 = new Scenario3();
			agents = scenario3.initAgents(map);
			agents = scenario3.moveCars(agents);
			break;
		}
		runVisualization();
	}
}
