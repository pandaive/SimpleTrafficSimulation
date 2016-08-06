import java.util.ArrayList;

public interface Scenario {
	
	public ArrayList<Agent> moveCars(ArrayList<Agent> agents);
	
	public ArrayList<Agent> initAgents(int[][] map);

}
