package ieg.util.xml;

import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * generic mapping of a HashMap for JAXB (experimental) 
 * @author Alex Rind
 *
 * @param <K>
 * @param <V>
 */
public class LinkedHashMapXmlAdapter<K, V> extends
		XmlAdapter<LinkedHashMapXmlAdapter.MarshalledForm<K, V>, LinkedHashMap<K, V>> {

	@Override
	public MarshalledForm<K, V> marshal(LinkedHashMap<K, V> v) throws Exception {
		MarshalledForm<K, V> result = new MarshalledForm<K, V>();

		for (Map.Entry<K, V> var : v.entrySet()) {
			result.entries.add(new KeyValuePair<K, V>(var.getKey(), 
					var.getValue()));
		}

		return result;
	}

	@Override
	public LinkedHashMap<K, V> unmarshal(MarshalledForm<K, V> v)
			throws Exception {
		LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();

		for (KeyValuePair<K, V> var : v.entries) {
			result.put(var.m_key, var.m_value);
		}

		return result;
	}

	static class MarshalledForm<K, V> {
		@XmlElement(name = "option")
		LinkedList<KeyValuePair<K, V>> entries = new LinkedList<KeyValuePair<K, V>>();
	}

	static class KeyValuePair<K, V> {
		@XmlElement
		// @XmlAttribute(required = true)
		K m_key;

		@XmlElement
		// @XmlValue
		V m_value;

		public KeyValuePair() {
		}

		KeyValuePair(K key, V value) {
			this.m_key = key;
			this.m_value = value;
		}
	}
}
