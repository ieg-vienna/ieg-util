package ieg.util.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Xml serializer with <a href="https://jaxb.dev.java.net">jaxb</a>
 * 
 * @author Alex Rind, Thomas Turic 
 */
public class JaxbMarshaller {

	/**
	 * loads an object from an XML file. The XML file has to be in UTF-8.
	 * 
	 * @param xmlFile
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    public static Object load(String xmlFile, Class clazz) {
		Object model = null;
		try {
			model = loadUser(xmlFile, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * loads an object from an XML file without catching exceptions. 
	 * The XML file has to be in UTF-8.
	 * 
	 * @param xmlFile
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	@SuppressWarnings("rawtypes")
	public static Object loadUser(String xmlFile, Class clazz)
			throws IOException, JAXBException {
		Reader reader;
		Object model = null;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				xmlFile), "UTF-8"));

		JAXBContext context;

		context = JAXBContext.newInstance(clazz);

		Unmarshaller m;

		m = context.createUnmarshaller();
		model = m.unmarshal(reader);

		reader.close();

		return model;
	}

	/**
	 * serializes an object and writes it to an XML file. The XML file is always
	 * in UTF-8.
	 * 
	 * @param xmlFile
	 * @param obj
	 */
	public static void save(String xmlFile, Object o) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(xmlFile), "UTF-8"));
			JAXBContext context = JAXBContext.newInstance(o.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(o, writer);
			writer.close();

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
