import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * Intersection with four cars
 *
 */
public class Scenario3 implements Scenario{
	
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int D = 3;
	public static final int STOP = 0;
	public static final int GO = 1;
	
	public static final int SSSS = 0;
	public static final int GSSS = 1;
	public static final int SGSS = 2;
	public static final int SSGS = 3;
	public static final int SSSG = 4;
	public static final int GGSS = 5;
	public static final int SGGS = 6;
	public static final int SSGG = 7;
	public static final int GSGS = 8;
	public static final int SGSG = 9;
	public static final int GSSG = 10;
	public static final int SGGG = 11;
	public static final int GSGG = 12;
	public static final int GGSG = 13;
	public static final int GGGS = 14;
	public static final int GGGG = 15;

	ArrayList<Integer>[] payoffMatrix;
	
	public Scenario3() {
		initMatrix();
	}
	
	private void initMatrix(){
		payoffMatrix = new ArrayList[4];
		payoffMatrix[A] = new ArrayList<Integer>();
		payoffMatrix[B] = new ArrayList<Integer>();
		payoffMatrix[C] = new ArrayList<Integer>();
		payoffMatrix[D] = new ArrayList<Integer>();
		
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(10);
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(2);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(0);
		payoffMatrix[A].add(0);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(0);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(0);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(-10);
		payoffMatrix[A].add(-10);
		
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(10);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-5);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-10);
		payoffMatrix[B].add(-10);
		
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(10);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(0);
		payoffMatrix[C].add(-10);
		payoffMatrix[C].add(-10);
		
		payoffMatrix[D].add(-2);
		payoffMatrix[D].add(-2);
		payoffMatrix[D].add(-2);
		payoffMatrix[D].add(-2);
		payoffMatrix[D].add(10);
		payoffMatrix[D].add(-2);
		payoffMatrix[D].add(-2);
		payoffMatrix[D].add(-10);
		payoffMatrix[D].add(-2);
		payoffMatrix[D].add(-10);
		payoffMatrix[D].add(-10);
		payoffMatrix[D].add(-10);
		payoffMatrix[D].add(-10);
		payoffMatrix[D].add(-10);
		payoffMatrix[D].add(0);
		payoffMatrix[D].add(-10);
	}
	
	public ArrayList<Agent> moveCars(ArrayList<Agent> agents) {
		System.out.println("Moving cars");
		int nbAgents = agents.size();
		ArrayList<Agent> agentsInOrder = new ArrayList<Agent>();
		Negotiation negotiation = new Negotiation(agents, payoffMatrix, 3);
		while(agentsInOrder.size() < nbAgents) {
			int results = negotiation.negotiate(agents);
			if (results == GSSS) {
				agentsInOrder.add(agents.get(0));
				agents.remove(0);
			}
			else if (results == SGSS){
				agentsInOrder.add(agents.get(1));
				agents.remove(1);
			}
			else if (results == SSGS){
				if (agents.size() > 1) {
					agentsInOrder.add(agents.get(agents.size()-2));
					agents.remove(agents.size()-2);
				}
				else {
					agentsInOrder.add(agents.get(agents.size()-1));
					agents.remove(agents.size()-1);
				}
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
		a = new Agent(1, 2, Agent.NORTH, Color.GREEN, map[1][2], 2, 1, true, payoffMatrix[3], 1);
		agents.add(a);
		
		return agents;
	}
}