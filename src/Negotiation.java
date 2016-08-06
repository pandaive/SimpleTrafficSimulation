import java.util.ArrayList;

public class Negotiation {

	
	
	ContractNetwork contractNetwork;
	int[] negotiationSet;
	ArrayList<Agent> agents;
	ArrayList<Integer>[] payoffMatrix;
	private int scenario;
	
	public Negotiation(ArrayList<Agent> agents, ArrayList<Integer>[] payoffMatrix, int scenario) {
		this.agents = agents;
		this.payoffMatrix = payoffMatrix;
		contractNetwork = new ContractNetwork(agents, payoffMatrix);
		negotiationSet = new int[agents.size()];
		this.scenario = scenario;
	}
	
	public int negotiate(ArrayList<Agent> agents){
		System.out.println("negotiate");
		int result = 0;
		negotiationSet = contractNetwork.callForProposals(agents);
		if (negotiationSet.length == 1)
			return negotiationSet[0];
		boolean agreed = true;
		System.out.println("called");
		for (int i = 1; i < negotiationSet.length; i++)
			if (negotiationSet[i] != negotiationSet [i-1])
				agreed = false;
		
		if (agreed)
			return negotiationSet[0];
		int count = 0;
		do {
			count++;
			System.out.println("getting results");
			result = contractNetwork.negotiate(agents);
		} while (!success(result) && count < Math.pow(2, agents.size()));
		
		return result;
	}
	
	public boolean success(int results) {
		if (scenario == 1) {
			if (results == Scenario1.GS || results == Scenario1.SG)
				return true;
		}
		else if (scenario == 2) {
			if (results == Scenario2.GSS || results == Scenario2.SGS || results == Scenario2.SSG)
				return true;
		} 
		else if (scenario == 3) {
			if (results == Scenario3.GSSS || results == Scenario3.SGSS || results == Scenario3.SSGS || results == Scenario3.SSSG)
				return true;
		}
		return false;
	}
}