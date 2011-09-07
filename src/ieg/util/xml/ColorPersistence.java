package ieg.util.xml;

import java.awt.Color;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import prefuse.util.ColorLib;

/**
 * Four adapters to serialize colors with JAXB.
 * 
 * <li> {@link FieldsAdapter} and {@link IntegerFieldsAdapter} serialize color as
 * three XML attributes (red, green, blue) 
 * <li> {@link StringAdapter} and {@link IntegerStringAdapter} serialize color as 
 * a hex encoded string (#rrggbb) 
 * <li> {@link FieldsAdapter} and {@link StringAdapter} work with
 * {@link java.awt.Color} objects 
 * <li> {@link IntegerFieldsAdapter} and {@link IntegerStringAdapter} work with 
 * colors saved as int (e.g., in prefuse)
 * 
 * @author Alex Rind
 * */
public class ColorPersistence {

    /**
     * color model that can be serialized with JAXB.
     * 
     * @author Alex Rind
     */
    static class ColorModel {
        @XmlAttribute(required = true)
        int red, green, blue;
    }

    /**
     * converts a {@link java.awt.Color} object to a color model that can be
     * serialized with JAXB.
     * 
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
     * converts a prefuse color to a color model that can be serialized with
     * JAXB.
     * 
     * @author Alex Rind
     */
    public static class IntegerFieldsAdapter extends
            XmlAdapter<ColorModel, Integer> {

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
     * 
     * @author Alex Rind
     */
    public static class StringAdapter extends XmlAdapter<String, Color> {

        private String hex(int v) {
            if (v > 0xF)
                return Integer.toString(v, 16);
            else
                return "0" + Integer.toString(v, 16);
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

    /**
     * serializes a prefuse color to a string with its RGB hex value (e.g.,
     * #ff0000).
     * 
     * @author Alex Rind
     */
    public static class IntegerStringAdapter extends XmlAdapter<String, Integer> {

        @Override
        public String marshal(Integer v) throws Exception {
            return "#"
                    + Integer.toString((v & 0xFFFFFF) | 0x1000000, 16)
                            .substring(1);
        }

        @Override
        public Integer unmarshal(String v) throws Exception {
            return Integer.valueOf(v.substring(1), 16) | 0xFF000000;
        }
    }
}
