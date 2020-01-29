package com.javarush.task.task33.task3309;
import org.w3c.dom.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

/*
Комментарий внутри xml
*/

public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {
        StringWriter stringWriter = new StringWriter();
        String result="";
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            marshaller.marshal(obj,document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");  // this allows you make \n
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

            Node root = document.getDocumentElement();
            NodeList alltags = root.getChildNodes();
            NodeList tags = document.getElementsByTagName(tagName);
            if (tags.getLength()>0){
                checkCDATA(alltags,document);
                for (int i = 0; i < tags.getLength(); i++) {
                    Node tag = tags.item(i);
                    tag.getParentNode().insertBefore(document.createComment(comment),tags.item(i));
                }
            }
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new ByteArrayOutputStream());
            transformer.transform(domSource, streamResult);
            result = streamResult.getOutputStream().toString();
        }catch (JAXBException e){
            e.printStackTrace();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }catch (TransformerException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void checkCDATA(NodeList listtag, Document document){
        for (int i = 0; i < listtag.getLength(); i++) {
            Node tag = listtag.item(i);
            if (tag.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                Node currentNode = tag.getFirstChild();
                if (currentNode.getTextContent().matches(".*[<>&\'\"].*")) {
                    String content = currentNode.getTextContent();
                    CDATASection cdataSection = document.createCDATASection(content);
                    tag.replaceChild(cdataSection, currentNode);
                }
            }else{
                if (tag.hasChildNodes()){
                    NodeList subtags = tag.getChildNodes();
                    checkCDATA(subtags,document);
                }
            }
        }

    }

    public static void main(String[] args) {
        First obj = new First();
        String newxml = toXmlWithComment(obj,"second","it's a comment");
        System.out.println(newxml);
    }

}






/* начало решения задачи

package com.javarush.task.task33.task3309;
import org.w3c.dom.Document;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringWriter;

// Комментарий внутри xml

public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) throws JAXBException {

        StringWriter writer = new StringWriter();
        String result = "";

        try {
        JAXBContext context = JAXBContext.newInstance(Object.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        marshaller.marshal(obj, writer);





        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        result = writer.toString();
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {


    }
}
*/

/*
Может быть поможет, один из вариантов решения:
1. javax.xml.bind.JAXBContext: Создаем новый инстанс JAXBContext.
2. javax.xml.bind.Marshaller: Из инстанса контекста получаем marshaller.
У marshaller устанавливаем свойство Marshaller.JAXB_FORMATTED_OUTPUT в истину, чтобы вывод был разбит по строчкам.
3. javax.xml.parsers.DocumentBuilderFactory: Создаем фабрику DocumentBuilderFactory.
Для преобразования CDATA узлов в текст устанавливаем factory.setCoalescing(true).
4. javax.xml.parsers.DocumentBuilder: Создаем новый DocumentBuilder.
5. org.w3c.dom.Document: С помощью билдера создаем новый документ.
7. Маршализуем объект в документ.
-- Документ (дерево) получили, теперь его необходимо обработать --
8. org.w3c.dom.NodeList: из документа получаем список узлов.
9. Обрабатываем список узлов в цикле:
9.1. Если имя узла соответствует заданному, то вставляем перед ним комментарий node.getParentNode( ).insertBefore( document.createComment( comment ), node)
9.2. Если тип первого дочернего узла node.getFirstChild() ревен Node.TEXT_NODE и контекст подходит под firstChild.getTextContent().matches(".*[<>&\'\"].*"), то заменяем node.replaceChild(cdataSection, firstChild). объект org.w3c.dom.CDATASection необходимо предварительно создать из контекста  firstChild.
-- Документ готов! Получаем из документа строку. --
10. javax.xml.transform.TransformerFactory: Получаем инстанс TransformerFactory.
11. javax.xml.transform.Transformer: С помощью инсттанса TransformerFactory получаем transformer -а.
Устанавливаем свойства вывода: transformer.setOutputProperty( OutputKeys.INDENT, "yes" ); ...ENCODING, "UTF-8", STANDALONE, "no"
12. Получаем StringWriter.
13. Трансформируем данные transformer.transform(new DOMSource(document), new StreamResult(writer)).
14. Все, из stringWriter получаем строку.

Комментарий внутри xml
Реализовать метод toXmlWithComment, который должен возвращать строку - xml представление объекта obj.
В строке перед каждым тегом tagName должен быть вставлен комментарий comment.
Сериализация obj в xml может содержать CDATA с искомым тегом. Перед ним вставлять комментарий не нужно.

Пример вызова:
toXmlWithComment(firstSecondObject, "second", "it's a comment")

Пример результата:
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<first>
<!--it's a comment-->
<second>some string</second>
<!--it's a comment-->
<second>some string</second>
<!--it's a comment-->
<second><![CDATA[need CDATA because of < and >]]></second>
<!--it's a comment-->
<second/>
</first>


Требования:
1. Метод toXmlWithComment должен быть статическим.
2. Метод toXmlWithComment должен быть публичным.
3. Если во входящем xml отсутствует искомый тег, то добавлять комментарии не нужно.
4. Количество комментариев вставленных в xml должно быть равно количеству тегов tagName.
5. Метод toXmlWithComment должен возвращать xml в виде строки преобразованной в соответствии с условием задачи.
*/



/*
package com.javarush.task.task33.task3309;

import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;


//Комментарий внутри xml

public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {
        StringWriter stringWriter = new StringWriter();
        String result="";
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            marshaller.marshal(obj,document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");  // this allows you make \n
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

            Node root = document.getDocumentElement();
            NodeList alltags = root.getChildNodes();
            NodeList tags = document.getElementsByTagName(tagName);
            if (tags.getLength()>0){
                checkCDATA(alltags,document);
                for (int i = 0; i < tags.getLength(); i++) {
                    Node tag = tags.item(i);
                    tag.getParentNode().insertBefore(document.createComment(comment),tags.item(i));
                }
            }
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new ByteArrayOutputStream());
            transformer.transform(domSource, streamResult);
            result = streamResult.getOutputStream().toString();
        }catch (JAXBException e){
            e.printStackTrace();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }catch (TransformerException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void checkCDATA(NodeList listtag, Document document){
        for (int i = 0; i < listtag.getLength(); i++) {
            Node tag = listtag.item(i);
            if (tag.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                Node currentNode = tag.getFirstChild();
                if (currentNode.getTextContent().matches(".*[<>&\'\"].*")) {
                    String content = currentNode.getTextContent();
                    CDATASection cdataSection = document.createCDATASection(content);
                    tag.replaceChild(cdataSection, currentNode);
                }
            }else{
                if (tag.hasChildNodes()){
                    NodeList subtags = tag.getChildNodes();
                    checkCDATA(subtags,document);
                }
            }
        }

    }

    public static void main(String[] args) {
        First obj = new First();
        String newxml = toXmlWithComment(obj,"second","it's a comment");
        System.out.println(newxml);
    }

}


 */