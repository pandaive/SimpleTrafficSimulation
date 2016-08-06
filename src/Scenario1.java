import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * Intersection with two cars
 *
 */
public class Scenario1 implements Scenario {
	
	public static final int A = 0;
	public static final int B = 1;
	public static final int STOP = 0;
	public static final int GO = 1;
	
	public static final int SS = 0;
	public static final int GS = 1;
	public static final int SG = 2;
	public static final int GG = 3;

	//int[][][] payoffMatrix;
	ArrayList<Integer>[] payoffMatrix;
	
	public Scenario1() {
		initMatrix();
	}
	
	@SuppressWarnings("unchecked")
	private void initMatrix(){
		payoffMatrix = new ArrayList[2];
		payoffMatrix[A] = new ArrayList<Integer>();
		payoffMatrix[B] = new ArrayList<Integer>();
		
		// stop/stop, go/stop, stop/go, go/go
		payoffMatrix[A].add(0);
		payoffMatrix[A].add(10);
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(-10);
		
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(0);
		payoffMatrix[B].add(10);
		payoffMatrix[B].add(-10);
	}
	
	public ArrayList<Agent> moveCars(ArrayList<Agent> agents) {
		System.out.println("Moving cars");
		int nbAgents = agents.size();
		ArrayList<Agent> agentsInOrder = new ArrayList<Agent>();
		Negotiation negotiation = new Negotiation(agents, payoffMatrix, 1);
		while(agentsInOrder.size() < nbAgents) {
			int results = negotiation.negotiate(agents);
			if (results == GS) {
				agentsInOrder.add(agents.get(0));
				agents.remove(0);
			}
			else {
				agentsInOrder.add(agents.get(agents.size()-1));
				agents.remove(agents.size()-1);
			}
		}
		return agentsInOrder;
	}
	
	public ArrayList<Agent> initAgents(int[][] map){
		ArrayList<Agent> agents = new ArrayList<Agent>();
		Agent a = new Agent(0, 1, Agent.EAST, Color.BLUE, map[0][1], 2, 1, true, payoffMatrix[0], 1);
		agents.add(a);
		a = new Agent(2, 1, Agent.WEST, Color.RED, map[2][1], 1, 2, true, payoffMatrix[1], 1);
		agents.add(a);
		
		return agents;
	}
}