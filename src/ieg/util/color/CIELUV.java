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
	public static float getMaxChroma() {
		return getMaxChroma(2.4f);
	}
	public static float getMaxChroma(float gamma) {
		return 512f/3f*(float)Math.exp(gamma/100f);
	}
	
	public static float getMaxChromaForLuminance(float luminance) {
		return getMaxChromaForLuminance(luminance,2.4f);
	}
	public static float getMaxChromaForLuminance(float luminance,float gamma) {
		float chroma = 4f/3f*luminance;
		
		if(3f*chroma/2f>255) {
			
		}
		
		return chroma;
	}
}
