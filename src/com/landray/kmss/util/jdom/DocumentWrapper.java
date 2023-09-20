package com.landray.kmss.util.jdom;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;


public class DocumentWrapper
{

  public static <T extends Node> List<T> selectNodes(Document document, String path) {
    List<Node> nodeList = document.selectNodes(path);
    List<T> elementList = new ArrayList<>();
    for (Node node : nodeList) {
      if (node instanceof Element) {
        Element element = (Element)node;
        elementList.add((T) element);
      }
      
      if (node instanceof Attribute) {
        Attribute attribute = (Attribute)node;
        elementList.add((T) attribute);
      }
    } 

    
    return elementList;
  }
  
  public static List<Element> selectNodes(Element parentElement, String path) {
    List<Node> nodeList = parentElement.selectNodes(path);
    List<Element> elementList = new ArrayList<>();
    for (Node node : nodeList) {
      Element element = (Element)node;
      elementList.add(element);
    } 
    return elementList;
  }
}
