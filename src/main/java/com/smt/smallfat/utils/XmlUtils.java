package com.smt.smallfat.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtils {
    private Document doc = null;
    private Element root = null;

    public XmlUtils() {
        if (doc == null)
            doc = DocumentHelper.createDocument();
        if (root == null)
            root = doc.addElement("xml");
    }

    public XmlUtils(String xml) {
        try {
            doc = DocumentHelper.parseText(xml);
            root = doc.getRootElement();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getNodeValue(String key) {
        Element element = root.element(key);
        return element.getTextTrim();
    }

    public void addRootCDATANode(String key, String value) {
        Element keyElement = root.addElement(key);
        keyElement.addCDATA(value);
    }

    public void addRootNode(String key, String value) {
        Element keyElement = root.addElement(key);
        keyElement.setText(value);
    }

    public Element getRoot() {
        return root;
    }

    public Document getDoc() {
        return doc;
    }
}
