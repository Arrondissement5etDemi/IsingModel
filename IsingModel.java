import java.util.*;
import java.text.DecimalFormat;

public class IsingModel {
	public static void main(String[] args) {
		double k = 0.2;
		int dim = 1;
		int sideLength = 16;
		Cell pandora = new Cell(dim,sideLength);
		System.out.println("initial energy = " + pandora.averageEnergy(k));
		System.out.println("initial magnetization = " + pandora.averageMag());
		
		int totalTime = 1000;
		int relaxTime = 3;//obtained from a plot by Relaxation.java
		System.out.println(energyAndMag(pandora,k,relaxTime,totalTime));
	}
	
	/**performs a sweep, that is, N attempts to flip, where N is the size of the unit cell
 * 	@param pandora Cell, the Ising cell that we do simulation on
 * 	@param k double, the coupling constant */
	private static void sweep(Cell pandora, double k) {
		for (int j = 0; j < pandora.getSize(); j++) {
			Spin sp = pandora.randomFlip();
			double energyChange = pandora.energyChangeByFlip(sp,k);
			if (!Boltzmann.accept(energyChange)) {// if not accepted, we reverse the flip
				sp.flip();
			}
		}
	}

	/**computes average energy and average magnetization, and their uncertainties
 * 	@param pandora Cell, the Ising cell that we do simulation on
 * 	@param k double, the coupling constant
 * 	@param relaxTime the relaxation time
 * 	@param totalTime the total running time
 * 	@return Report, the average energy and average magnetization*/
	private static Report energyAndMag(Cell pandora, double k, int relaxTime, int totalTime) {
		if (totalTime < relaxTime) {
			throw new IllegalArgumentException("totalTime must be >= relaxTime");
		}
		double e = 0.0;
		double eSqr = 0.0;//square of energy
		double m = 0.0;
		double mSqr = 0.0;//square of magnetization
		int n = 0;//counting the sample points

		for (int t = 1; t <= totalTime; t++) {
			sweep(pandora, k);
			if (t%relaxTime == 0) {//sample for instantaneous E and M only after every relaxation
				n = n + 1;
				double instantE = pandora.averageEnergy(k);
				e = e + instantE;
				eSqr = eSqr + instantE*instantE;
				double instantM = Math.abs(pandora.averageMag());
				m = m + instantM;
				mSqr = mSqr + instantM*instantM;
			}
		}
		e = e/(double)n;
		eSqr = eSqr/(double)n;
		m = m/(double)n;
		mSqr = mSqr/(double)n;
		
		//compute the uncertainties
		double varianceE = eSqr - e*e;
		double varianceM = mSqr - m*m;
		double epsilonE = varianceE/Math.sqrt((double)n);
		double epsilonM = varianceM/Math.sqrt((double)n);

		Report result = new Report(n,e,epsilonE,m,epsilonM);
		return result;
	}
}

