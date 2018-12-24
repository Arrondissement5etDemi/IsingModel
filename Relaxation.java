import java.util.*;
import java.text.DecimalFormat;

/** a class to estimate the relaxation time of the Ising model */
public class Relaxation {
	public static void main(String[] args) {
		double k = 0.2;
		int dim = 1;
		int sideLength = 32;
		Cell pandora = new Cell(dim,sideLength);
		System.out.println("average energy = " + pandora.averageEnergy(k));
		System.out.println("average magnetization = " + pandora.averageMag());
		DecimalFormat ft = new DecimalFormat("0.00000"); 
		
		int numTrials = 10000;
		int totalTime = 50;
		double[] plotE = new double[totalTime]; //the plot of instant. energy against time
		double[] plotM = new double[totalTime]; //the plot of instant. magnetization against time
		for (int trial = 0; trial < numTrials; trial++) {
			pandora.initialize(); 
			for (int t = 0; t < totalTime; t++) {
				sweep(pandora,k);
				plotE[t] = plotE[t] + pandora.averageEnergy(k);
				plotM[t] = plotM[t] + Math.abs(pandora.averageMag());
			}
		}
		for (int t = 0; t < totalTime; t++) {
			plotE[t] = plotE[t]/(double)numTrials;
			plotM[t] = plotM[t]/(double)numTrials;
			System.out.println(ft.format(plotE[t]) + " " + ft.format(plotM[t]));
		}
		
	}

	private static void sweep(Cell pandora, double k) {
		for (int j = 0; j < pandora.getSize(); j++) {
			Spin sp = pandora.randomFlip();
			double energyChange = pandora.energyChangeByFlip(sp,k);
			if (!Boltzmann.accept(energyChange)) {// if not accepted, we reverse the flip
				sp.flip();
			}
		}
	}
}


