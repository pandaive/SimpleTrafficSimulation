import java.util.ArrayList;

public class ContractNetwork {

	int nbAgents;
	ArrayList<Integer>[] payoffMatrix;
	ArrayList<Integer> movements;
	boolean success = false;
	
	public ContractNetwork(ArrayList<Agent> agents, ArrayList<Integer>[] payoffMatrix) {
		this.nbAgents = agents.size();
		this.payoffMatrix = payoffMatrix;
		movements = new ArrayList<Integer>();
	}
	
	public int[] callForProposals(ArrayList<Agent> agents) {
		nbAgents = agents.size();
		int[] results = new int[nbAgents];
		for (int i = 0; i < nbAgents; i++) {
			System.out.println("proposing movements");
			results[i] = agents.get(i).proposeMovement(movements);
			System.out.println("Agent " + i + " proposes " + results[i]);
		}
		for (int i = 0; i < nbAgents; i++)
			movements.add(results[i]);
		return results;
	}
	
	public int negotiate(ArrayList<Agent> agents){
		int result = 0;
		int count = 0;
		do {
			count++;
			for (int i = 0; i < nbAgents; i++)
				for (int j = 0; j < nbAgents; j++) {
					if (i != j) {
						if (agents.get(j).accept(movements.get(i))) {
							//System.out.println("Agent " + j + " accepts proposal of agent " + i);
							return movements.get(i);
						}
						else if (agents.get(i).accept(movements.get(j))) {
							//System.out.println("Agent " + i + " accepts proposal of agent " + j);
							return movements.get(j);
						}
					}
				}
			movements.clear();
			for (int i = 0; i < nbAgents; i++)
				movements.add(callForProposals(agents)[i]);
		} while (count <= Math.pow(2, agents.size()));
		
		return result;
	}
	
	public boolean success() {
		return success;
	}
}
