import java.awt.Color;
import java.util.ArrayList;
public class Agent {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private int direction;
	private float[] position = new float[2];
	private boolean priority;
	private int orientation;
	public Color color;
	private int destX;
	private int destY;
	private int destDirection;
	private boolean intersection;
	private ArrayList<Integer> payoffs;
	private int strategyMode;
	
	public Agent(int posX, int posY, int direction, Color color,
			int orientation, int destX, int destY, boolean intersection, ArrayList<Integer> payoffs, int mode) {
		this.position[0] = posX * 1.0f;
		this.position[1] = posY * 1.0f;
		this.direction = direction;
		this.color = color;
		this.orientation = orientation;
		this.destX = destX;
		this.destY = destY;
		setDestDirection();
		this.intersection = intersection;
		this.payoffs = payoffs;
		this.strategyMode = mode;
	}
	
	private void setDestDirection(){
		if (direction == SOUTH || direction == NORTH) {
			if (destX < position[0])
				destDirection = WEST;
			else if (destX > position[0])
				destDirection = EAST;
			else if (destY < position[1])
				destDirection = NORTH;
			else
				destDirection = SOUTH;
		}
		else {
			if (destY < position[1])
				destDirection = NORTH;
			else if (destY > position[1])
				destDirection = SOUTH;
			else if (destX < position[0])
				destDirection = WEST;
			else
				destDirection = EAST;
		}
	}
	
	public void move(){
		intersection = false;
	}
	
	public int proposeMovement(ArrayList<Integer> lastMovements) {
		int answer = 0;
		int best = 0;
		boolean found = false;
		Strategy strategy = new Strategy(strategyMode, payoffs);
		if (lastMovements.isEmpty())
			return strategy.chooseMove();
		ArrayList<Integer> payoffsTemp = payoffs;
		while (!found) {
			for (int i = 0; i < payoffsTemp.size(); i++)
				if (payoffsTemp.get(i) > best) {
					answer = i;
				}
			if (lastMovements.isEmpty() || !payoffsTemp.get(best).equals(lastMovements.get(lastMovements.size()-1)))
				found = true;
			else
				payoffsTemp.remove(best);
		}
		return answer;
	}
	
	public boolean accept(int proposal) {
		if (payoffs.get(proposal) > 0)
			return true;
		return false;
	}
	
	public float getX() { return position[0]; }
	public float getY() { return position[1]; }
	public boolean getPriority() { return priority; }
	public int getOrientation() { return orientation; }
	public int getDirection() { return direction; }
	public Color getColor() { return color; }
	public int getDestX() { return destX; }
	public int getDestY() { return destY; }
	public int getDestDirection() { return destDirection; }
	public boolean onIntersection() { return intersection; }
	
	public void setX(float x) { position[0] = Math.round(x*10.0f)/10.0f; }
	public void setY(float y) { position[1] = Math.round(y*10.0f)/10.0f; }
	public void setOrientation(int o) { orientation = o; }
	public void setDirection(int d) { direction = d; }
	public void setDestX(int x) { destX = x; }
	public void setDestY(int y) { destY = y; }
	public void moveFromIntersection() { intersection = false; }
	public void moveToIntersetion() { intersection = true; }
}
