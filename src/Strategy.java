import java.util.ArrayList;
import java.util.Random;

public class Strategy {
	
	public static final int PURE = 0;
	public static final int MIXED = 1;
	
	private int strategy;
	private int pureStrategyMove = 0;
	ArrayList<Integer> payoffs;
	Random random = new Random();
	
	public Strategy(int mode, ArrayList<Integer> payoffs) {
		this.payoffs = payoffs;
		this.strategy = mode;
		if (strategy == PURE) {
			int best = 0;
			int str = 0;
			for (int i = 0; i < payoffs.size(); i++)
				if (payoffs.get(i) > best) {
					best = payoffs.get(i);
					str = i;
				}
			pureStrategyMove = str;
		}
		else {
			
		}
	}
	
	public int chooseMove(){
		if (strategy == PURE)
			return pureStrategy();
		else {
			return mixedStrategy();
		}
	}
	
	private int pureStrategy(){
		return pureStrategyMove;
	}
	
	private int mixedStrategy(){
		/* two agents only
		 * double temp = ((payoffs.get(Scenario1.GG)-payoffs.get(Scenario1.GS))*1.0d)
		 * /((payoffs.get(Scenario1.SS) - payoffs.get(Scenario1.SG) - payoffs.get(Scenario1.GS) + payoffs.get(Scenario1.GG))*1.0d);
		 */
		double temp;
		int temp1 = payoffs.get(payoffs.size()-1);
		int temp2 = payoffs.get(0);
		for (int i = payoffs.size()/2; i < payoffs.size(); i++) {
			temp1 -= payoffs.get(i);
		}
		for (int i = 0; i < payoffs.size(); i++) {
			temp2 -= payoffs.get(i);
		}
		temp = (temp1*1.0d)/(temp2*1.0d);
		double d = random.nextDouble();
		if (temp > d) {
			int answer = 0;
			int best = 0;
			for (int i = 0; i < payoffs.size(); i++)
			if (payoffs.get(i) > best) {
				answer = i;
			}
			return payoffs.get(answer);
		}
		else {
			 return random.nextInt(payoffs.size()/2)+1;
		}
	}
}
