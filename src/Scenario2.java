import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * Intersection with three cars
 *
 */
public class Scenario2 implements Scenario{
	
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int STOP = 0;
	public static final int GO = 1;
	
	public static final int SSS = 0;
	public static final int GSS = 1;
	public static final int SGS = 2;
	public static final int SSG = 3;
	public static final int GGS = 4;
	public static final int GSG = 5;
	public static final int SGG = 6;
	public static final int GGG = 7;

	ArrayList<Integer>[] payoffMatrix;
	
	public Scenario2() {
		initMatrix();
	}
	
	private void initMatrix(){
		payoffMatrix = new ArrayList[3];
		payoffMatrix[A] = new ArrayList<Integer>();
		payoffMatrix[B] = new ArrayList<Integer>();
		payoffMatrix[C] = new ArrayList<Integer>();
		
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(10);
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(0);
		payoffMatrix[A].add(-10);
		
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(10);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-10);
		
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(10);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(-10);
	}
	
	public ArrayList<Agent> moveCars(ArrayList<Agent> agents) {
		System.out.println("Moving cars");
		int nbAgents = agents.size();
		ArrayList<Agent> agentsInOrder = new ArrayList<Agent>();
		Negotiation negotiation = new Negotiation(agents, payoffMatrix, 2);
		while(agentsInOrder.size() < nbAgents) {
			int results = negotiation.negotiate(agents);
			if (results == GSS) {
				agentsInOrder.add(agents.get(0));
				agents.remove(0);
			}
			else if (results == SGS){
				agentsInOrder.add(agents.get(1));
				agents.remove(1);
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
		Agent a = new Agent(0, 1, Agent.EAST, Color.BLUE, map[0][1], 1, 0, true, payoffMatrix[0], 1);
		agents.add(a);
		a = new Agent(2, 1, Agent.WEST, Color.RED, map[2][1], 1, 2, true, payoffMatrix[1], 1);
		agents.add(a);
		a = new Agent(1, 0, Agent.SOUTH, Color.MAGENTA, map[1][0], 1, 2, true, payoffMatrix[2], 1);
		agents.add(a);
		
		return agents;
	}
}