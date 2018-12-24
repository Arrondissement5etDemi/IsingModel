import java.util.*;

/** a class for spins in the Ising model */
public class Spin {
	private int x,y;//the coordinates of the spin
	private int value;//spin up or spin down, only takes values -1 or 1
	
	public Spin(int xx, int yy, int v) {
		if (v != 1 && v != -1) {
			throw new IllegalArgumentException("v must be either 1 or -1");
		}
		x = xx;
		y = yy;
		value = v;
	}

	/**gets the x coordinate
 * 	@return int, the x coordinate */
	public int getX() {
		return x;
	}

	/**gets the y coordinate
 * 	@return int, the y coordinate */
	public int getY() {
		return y;
	}

	/**gets the value of the spin
 * 	@return int, the value of the spin */
	public int getValue() {
		return value;
	}

	/**flips the spin */
	public void flip() {
		value = -value;
	}

	/**sets spin value 
 * 	@param v int, the value to be set, must be 1 or -1 */
	public void setValue(int v) {
		if (v != 1 && v != -1) {
                        throw new IllegalArgumentException("v must be either 1 or -1");
                }
		value = v;
	}
	
	/**gets the square distance to another spin
 * 	@param another Spin, another spin
 * 	@return int, the square distance to the other spin */
	public int sqrDistanceTo(Spin another) {
		int dx = x-another.getX();
		int dy = y-another.getY();
		return dx*dx + dy*dy;
	}

	/**checks if two spins are at the same position and have the same value
 * 	@param another Spin, another spin
 * 	@return boolean, if the other spin is at the same position and have the same value */
	public boolean equals(Spin another) {
		return (x == another.getX() && y == another.getY() && value == another.getValue());
	}

	/**to string
 * 	@return String, a string that has the info of the spin*/
	public String toString() {
		return x + " " + y + " " + value;
	}
}
