import java.util.*;
/** models the unit cell (1D or 2D) of the Ising model */

public class Cell {
	private int dim; // dimension of the cell; it is 1 or 2
	private int sideLength; // the side length of the cell
	private int size; // number of spins in the cell
	private Spin[] spinArr; // the array of spins in the cell
	
	public Cell(int d, int s) {
		if (d != 1 && d != 2) {
			throw new IllegalArgumentException("d must be either 1 or 2");
		}
		dim = d;
		sideLength = s;
		size = (int) Math.pow((double)sideLength,(double)dim);
		spinArr	= new Spin[size];
		if (dim == 1) {//for one-dimensional cell
			for (int i = 0; i < sideLength; i++) {
				Spin p = new Spin(i, 0, 1); // note the ferromagnetic default
				spinArr[i] = p;
			}
		}
		if (dim == 2) {//for 2D cell
			int k = 0;
			for (int i = 0; i < sideLength; i++) {
				for (int j = 0; j < sideLength; j++) {
                                	Spin p = new Spin(i, j, 1);
                                	spinArr[k] = p;
					k++;
				}
                        }
		}
	}
	
	/**dim accessor 
 * 	@return int, the dimension of the cell */
	public int getDim() {
		return dim;
	}

	/**sideLength accessor
 * 	@return int, the side length of the cell */
	public int getSideLength() {
		return sideLength;
	}

	/**spinArr accessor
 * 	@return Spin[], a deep copy of spinArr */
	public Spin[] toArray() {
		Spin[] spinArrCopy = new Spin[size];
		for (int i = 0; i < size; i++) {
			Spin thisSpin = spinArr[i];
			Spin spinCopy = new Spin(thisSpin.getX(),thisSpin.getY(),thisSpin.getValue());
			spinArrCopy[i] = spinCopy;
		}
		return spinArrCopy;
	}
	
	/**computes the average energy of spins in the cell
 *	@param k, double, the coupling constant
 *	@return double, the average energy */
	public double averageEnergy(double k) {
		double totalE = 0;
		for (int i = 0; i < size; i++) {
			totalE = totalE + energyAtSpin(spinArr[i],k);
		}
		return (totalE/(double)size)/2.0; //divide by 2 to account for double counting
	}

	/**computes the energy felt at a particular spin in the cell
 * 	@param sp, Spin, a particular spin in the cell
 * 	@param k, double, the coupling constant
 * 	@return double, the energy at the input spin */
	public double energyAtSpin(Spin sp, double k) {
		if (!checkIn(sp)) {
			throw new IllegalArgumentException("sp must be a Spin in the cell");
		}
		//find the positions of nearest neighbor spins of sp, note PBC applied
		int neighborSize = dim*2;
		int[][] neighbors = new int[neighborSize][2];
		if (dim == 1) {
			int[][] neighbors1 = 
			{{(sp.getX()+sideLength-1)%sideLength,0},{(sp.getX()+1)%sideLength,0}};
			for (int i = 0; i < neighborSize; i++){
				for (int j = 0; j < 2; j++) {
					neighbors[i][j] = neighbors1[i][j];
				}
			}
		}
		if (dim == 2) {
			int[][] neighbors2 = {
			 	{(sp.getX()+sideLength-1)%sideLength,sp.getY()},
			 	{(sp.getX()+1)%sideLength,sp.getY()},
			 	{sp.getX(),(sp.getY()+sideLength-1)%sideLength},
			 	{sp.getX(),(sp.getY()+1)%sideLength}
			};
			for (int i = 0; i < neighborSize; i++){
                                for (int j = 0; j < 2; j++) {
                                        neighbors[i][j] = neighbors2[i][j];
                                }
                        }
                }

		double result = 0.0;
		for (int i = 0; i < size; i++) {
			Spin thatSpin = spinArr[i];
			int thatX = thatSpin.getX();
			int thatY = thatSpin.getY();
			//check if that spin is a neighbor of sp
			for (int j = 0; j < neighborSize; j++) {
				if (thatX == neighbors[j][0] && thatY == neighbors[j][1]) { 
					result = result - k*(double)sp.getValue()*(double)thatSpin.getValue();
				}
			}
		}
		return result;
	}

	/**checks if a spin is in the cell
 * 	@param sp, Spin, a particular spin
 * 	@return boolean, if the spin is in the cell */
	public boolean checkIn(Spin sp) {
		for (int i = 0; i < size; i++) {
			if (sp.equals(spinArr[i])) {
				return true;
			}
		}
		return false;
	} 

	/**computes the average magnetization of spins in the cell 
 * 	@return double, the average magnetization */
	public double averageMag() {
		int totalMag = 0; //the total magnetization
		for (int i = 0; i < size; i++) {
			totalMag = totalMag + spinArr[i].getValue();
		}
		return (double)totalMag/(double)size;
	}

	/**flips a random spin in the cell
 * 	@return Spin, the spin flipped  */
	public Spin randomFlip() {
		int ind = (int) Math.floor(getRandomNumberInRange(0.0,(double)size));
		Spin sp = spinArr[ind];
		sp.flip();
		return sp;
	}

	/**computes the energy change caused by a spin flip
 * 	@param spinFlipped Spin, the spin that has been flipped
 * 	@param k double, the coupling constant
 * 	@return double, the energy change caused by this flip */
	public double energyChangeByFlip(Spin spinFlipped, double k) {
		return energyAtSpin(spinFlipped,k)*2.0/(double)size;
	}

	/**to string
 * 	@return String, a string of the info of the spins in the cell */
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			result = result + spinArr[i].toString() + "\n";
		}
		return result;
	}

	//auxillary functions
	private static double getRandomNumberInRange(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
	        }		
		Random r = new Random();	
       		return r.nextDouble()*(max - min) + min;
	}
}
