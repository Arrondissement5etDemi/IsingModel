import java.util.*;
import java.text.DecimalFormat;

public class IsingModel {
	public static void main(String[] args) {
		double k = 0.7;
		int dim = 1;
		int sideLength = 16;
		Cell pandora = new Cell(dim,sideLength);
		int size = pandora.getSize();
		System.out.println("average energy = " + pandora.averageEnergy(k));
		System.out.println("average magnetization = " + pandora.averageMag());
		DecimalFormat ft = new DecimalFormat("0.00000"); 
		
		for (int sweep = 1; sweep <= 50; sweep++) {
			for (int j = 0; j < size; j++) {
				Spin sp = pandora.randomFlip();
				double energyChange = pandora.energyChangeByFlip(sp,k);
				if (!Boltzmann.accept(energyChange)) {// if not accepted, we reverse the flip
					sp.flip();
				}
			}
			double e = pandora.averageEnergy(k);
			double m = pandora.averageMag();
                	System.out.println(ft.format(e) + " " + ft.format(m));
		}
	}
}
