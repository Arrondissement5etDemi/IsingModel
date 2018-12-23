import java.util.*;

public class Boltzmann {

	private static Random rnd = new Random();

	/**computes the Boltzmann acceptance probability
 	@param energyChange double, the temperature difference
	@return double, the acceptance probability*/
	private static double boProb(double energyChange) {
		if (energyChange <= 0) {
			return 1.0;
		}
		else return Math.exp(- energyChange);
	}

	/**accepts or rejects the new situation according to the Boltzmann probablity
	@param energyChange double, the temperature difference
	@return boolean, accept the new situation or not*/
	public static boolean accept(double energyChange) {
		//calculate the Boltzmann probability
		double prob = boProb(energyChange);
		//get a random number between 0 and 1
		double x = rnd.nextDouble();
		//and accept or reject
		if (x <= prob) return true;
		else return false;
	}
}
