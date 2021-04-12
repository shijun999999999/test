package com.neayon.ht.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.linke.JYSJ;
import com.linke.JYSJLocator;
import com.linke.JYSJSoap;

public class SafeWebserviceUtil {

	public static Document queryws(String jkid, Map<String, String> param) {
		JYSJ jysj = new JYSJLocator();
		Document document = null;
		try {
			JYSJSoap jp = jysj.getJYSJSoap();
			Document xml = BeanXMLUtil.map2xml(param, "querycondition");
			String response = jp.queryObjectOut(jkid, xml.asXML());
			response = URLDecoder.decode(response, "utf-8");
			document = DocumentHelper.parseText(response);
		} catch (DocumentException | UnsupportedEncodingException | RemoteException | ServiceException e) {
			e.printStackTrace();
		}
		return document;
	}
}
