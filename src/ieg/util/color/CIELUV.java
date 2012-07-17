package ieg.util.color;

/**
 * 
 * 
 * <p>
 * Added:          / TL<br>
 * Modifications: 
 * </p>
 * 
 * @author Tim Lammarsch
 *
 */
public class CIELUV {
	public static double getMinLuminanceForChroma(double chroma,double gamma) {
		
		return 0.75*chroma;
	}
	
	public static double getMaxLuminanceForChroma(double chroma,double gamma) {
		
		double oldTry = Double.MAX_VALUE; 
		for(double min = 0; min<256; min++) {
			double q = Math.exp(min*gamma/255.0);
			double triedChroma = 170.0 * q - 2.0/3.0*min*q;
			double newTry = Math.abs(triedChroma-chroma);
			
			if(oldTry < newTry) {
				return (q*255.0+(q-1.0)*min)/2.0;
			}
			
			oldTry = newTry;
		}
		
		return Math.exp(gamma)*255.0-127.5;
	}

}
