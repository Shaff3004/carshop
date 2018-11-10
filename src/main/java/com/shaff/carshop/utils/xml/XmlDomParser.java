package com.shaff.carshop.utils.xml;

import com.shaff.carshop.constants.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlDomParser {

    public Map<String, List<String>> parseDocument(String path) throws ParserConfigurationException, IOException, SAXException {
        Map<String, List<String>> resultMap = new HashMap<>();
        List<String> userPatterns = new ArrayList<>();
        List<String> adminPatterns = new ArrayList<>();

        File inputFile = new File(path);
        Document document = getDocumentBuilder().parse(inputFile);

        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName(XML.CONSTRAINT_ELEMENT);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;

            if (checkIfRoleIsUser(element)) {
                parseUserPattern(element, userPatterns);
            } else if (checkIfRoleIsAdmin(element)) {
                parseAdminPattern(element, adminPatterns);
            }
        }

        resultMap.put(XML.USER_ROLE_ID, userPatterns);
        resultMap.put(XML.ADMIN_ROLE_ID, adminPatterns);
        return resultMap;
    }

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder();
    }

    private void parseUserPattern(Element element, List<String> userUrls) {
        userUrls.add(element.getElementsByTagName(XML.PATTERN_ELEMENT).item(0).getTextContent());
    }

    private void parseAdminPattern(Element element, List<String> adminUrls) {
        adminUrls.add(element.getElementsByTagName(XML.PATTERN_ELEMENT).item(0).getTextContent());
    }

    private boolean checkIfRoleIsUser(Element element) {
        return element.getElementsByTagName(XML.ROLE_ELEMENT).item(0).getTextContent().equals(XML.USER_VALUE);
    }

    private boolean checkIfRoleIsAdmin(Element element) {
        return element.getElementsByTagName(XML.ROLE_ELEMENT).item(0).getTextContent().equals(XML.ADMIN_VALUE);
    }
}