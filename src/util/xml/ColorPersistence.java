package visuexplore.persistence;

import java.awt.Color;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import prefuse.util.ColorLib;

/**
 * color model that can be serialized with JAXB. 
 * */
public class ColorPersistence {

	/**
	 * color model that can be serialized with JAXB. 
	 * @author Alex Rind
	 */
	static class ColorModel {
		@XmlAttribute(required=true)
		int red, green, blue;
	}
	
	/**
	 * converts a java.awt.Color object to a color model that can be serialized 
	 * with JAXB. 
	 * @author Alex Rind
	 */
	public static class FieldsAdapter extends XmlAdapter<ColorModel, Color> {
		
		@Override
		public ColorModel marshal(Color v) throws Exception {
			ColorModel m = new ColorModel();
			m.red = v.getRed();
			m.green = v.getGreen();
			m.blue = v.getBlue();  
			return m;
		}

		@Override
		public Color unmarshal(ColorModel v) throws Exception {
			return new Color(v.red, v.green, v.blue);
		}
	}

	/**
	 * converts a prefuse color to a color model that can be serialized 
	 * with JAXB. 
	 * @author Alex Rind
	 */
	public static class IntegerFieldsAdapter extends XmlAdapter<ColorModel, Integer> {
		
		@Override
		public ColorModel marshal(Integer v) throws Exception {
			ColorModel m = new ColorModel();
			m.red = ColorLib.red(v.intValue());
			m.green = ColorLib.green(v.intValue());
			m.blue = ColorLib.blue(v.intValue());  
			return m;
		}

		@Override
		public Integer unmarshal(ColorModel v) throws Exception {
			return new Integer(ColorLib.rgb(v.red, v.green, v.blue));
		}
	}
	
	/**
	 * serializes a java.awt.Color object to a string with its RGB hex value 
	 * (e.g., #ff0000). 
	 * @author Alex Rind
	 */
	static class StringAdapter extends XmlAdapter<String, Color> {
		
		private String hex(int v) {
			if (v > 0xF)
				return  Integer.toString(v, 16);
			else
				return  "0" + Integer.toString(v, 16);
		}

		@Override
		public String marshal(Color v) throws Exception {
			return "#" + hex(v.getRed()) + hex(v.getGreen()) + hex(v.getBlue());
		}

		@Override
		public Color unmarshal(String v) throws Exception {
			return Color.decode(v);
		}
	}
}
