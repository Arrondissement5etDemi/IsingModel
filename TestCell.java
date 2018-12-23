import java.util.*;

public class TestCell {
	public static void main(String[] args) {
		double k = 0.7;
		System.out.println("1D cell:");
		Cell pandora = new Cell(1,16);
		System.out.println("average energy = " + pandora.averageEnergy(0.7));
		System.out.println("average magnetization = " + pandora.averageMag());
		
		System.out.println("2D cell:");
                Cell pandora2D = new Cell(2,2);
		System.out.println(pandora2D);
                System.out.println("average energy = " + pandora2D.averageEnergy(0.7));
                System.out.println("average magnetization = " + pandora2D.averageMag());
		
		for (int i = 1; i <=3; i++) {
			Spin sp = pandora2D.randomFlip();
			System.out.println(pandora2D);
                	System.out.println("average energy = " + pandora2D.averageEnergy(0.7));
                	System.out.println("average magnetization = " + pandora2D.averageMag());
			System.out.println("energy change by flip = " + pandora2D.energyChangeByFlip(sp,k));
		}
	}
}
