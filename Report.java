import java.util.*;

/** a class to report the average energy and magnetization of an Ising model */
public class Report {
	private double e, m, uncertE, uncertM;
	private int n;
	
	public Report(int nn, double ee, double ue, double mm, double um) {
		n = nn;
		e = ee;
		m = mm;
		uncertE = ue;
		uncertM = um;
	}

	/** to String
 * 	@return String, the string report energy and magnetization */
	public String toString() {
		return "The number of samples: " + n + "\n" +
			"Energy: " + e + " " + uncertE + "\n" +
			"Magnetization: " + m + " " + uncertM;
	}
}
