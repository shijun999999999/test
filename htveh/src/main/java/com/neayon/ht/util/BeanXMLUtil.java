package com.neayon.ht.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neayon.ht.entity.Veh;

@SuppressWarnings("all")
public class BeanXMLUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanXMLUtil.class);

	public static Document bean2xml(Object bean, String element) throws NoSuchMethodException {
		Field[] fields = bean.getClass().getDeclaredFields();

		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root");
		Element subelement = root.addElement(element);
		logger.debug("fields size" + fields.length);
		for (Field field : fields) {
			String fname = field.getName();
			String getMehod = "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length());
			try {
				Method method = bean.getClass().getMethod(getMehod);
				Object value = method.invoke(bean);
				Element felement = subelement.addElement(fname);
				if (value != null && !"".equals(value)) {
					felement.setText(URLEncoder.encode(value.toString(), "UTF-8"));
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| UnsupportedEncodingException | NoSuchMethodException e) {
				logger.error("bean2xml执行异常", e);
			}
		}
		logger.debug("subelement" + subelement.asXML());
		logger.debug("document" + document.asXML());
		return document;
	}

	public static Document map2xml(Map map, String element) {
		logger.debug("map:+" + map);
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root");
		Element subelement = root.addElement(element);
		Set<String> set = map.keySet();
		for (String key : set) {
			Element felement = subelement.addElement(key);
			try {
				if (map.get(key) != null) {
					String val = map.get(key).toString().trim();
					if ("rgjyjgs".equals(key) || "yqsbjyjgs".equals(key)) {
						if (val != null && !"".equals(val.trim())) {
							Document sd = DocumentHelper.parseText(val);
							List<Element> list = sd.getRootElement().elements();
							for (Element e : list) {
								felement.add(e.detach());
							}
						}
					} else {
						felement.setText(URLEncoder.encode(val, "UTF-8"));
					}
				}
			} catch (Exception e) {
				logger.error("map2xml执行异常", e);
			}
		}
		logger.debug("document:" + document.asXML());
		return document;
	}

	public static Document list2xml(List<Map> data, String element) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root");
		int i = 1;
		for (Map map : data) {
			Element subElement = root.addElement(element);
			subElement.setAttributeValue("xh", i++ + "");
			Set<String> keys = map.keySet();
			for (String key : keys) {
				Element e = subElement.addElement(key);
				Object o = map.get(key);
				String val = null;
				if (o != null) {
					val = o.toString();
				}
				val = val == null ? "" : val;
				try {
					e.setText(URLEncoder.encode(val, "UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					logger.error("map2xml执行异常", e1);
				}
			}
		}

		return document;
	}
	
	public static void main(String[] args) throws NoSuchMethodException {
		System.out.println(BeanXMLUtil.bean2xml(new Veh(), "QU"));
	}
}
