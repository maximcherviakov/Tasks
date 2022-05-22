package com.nc.edu.ta.maximcherviakov.pr8;

import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;

public class TaskXMLSerializer {
    public static void save(AbstractTaskList object, String file) throws RuntimeException {
        if (object == null) {
            throw new NullPointerException();
        }

        if (!file.contains(".xml")) {
            if (file.contains(".")) {
                file = file.substring(0, file.indexOf(".")) + ".xml";
            } else {
                file = file + ".xml";
            }
        }

        try {
            File xsdFile = new File("schema.xsd");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
            Schema schema = xsdFactory.newSchema(xsdFile);

            factory.setSchema(schema);

            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();
            Element tasks = document.createElement("tasks");
            Element task;

            document.appendChild(tasks);
            for (int i = 0; i < object.size(); i++) {
                task = document.createElement("task");
                tasks.appendChild(task);

                task.setAttribute("active", String.valueOf(object.getTask(i).isActive()));
                task.setAttribute("time", String.valueOf(object.getTask(i).getTime()));
                task.setAttribute("start", String.valueOf(object.getTask(i).getStartTime()));
                task.setAttribute("end", String.valueOf(object.getTask(i).getEndTime()));
                task.setAttribute("repeat", String.valueOf(object.getTask(i).getRepeatInterval()));
                task.setAttribute("repeated", String.valueOf(object.getTask(i).isRepeated()));
                task.setTextContent(object.getTask(i).getTitle());
            }

            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

            Transformer t = null;
            t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void load(AbstractTaskList object, String file) throws RuntimeException {
        if (!file.contains(".xml")) {
            if (file.contains(".")) {
                file = file.substring(0, file.indexOf(".")) + ".xml";
            } else {
                file = file + ".xml";
            }
        }

        if (object == null) {
            throw new NullPointerException();
        }

        if (!(new File(file).exists())) {
            throw new RuntimeException("Current file is not exist");
        }

        Task task;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(file));

            NodeList tasks = document.getDocumentElement().getElementsByTagName("task");
            for (int i = 0; i < tasks.getLength(); i++) {
                Node node = tasks.item(i);
                NamedNodeMap attributes = node.getAttributes();
                if (attributes == null) {
                    System.out.println("null");
                }
                if (Boolean.parseBoolean(attributes.getNamedItem("repeated").getNodeValue())) {
                    task = new Task(tasks.item(i).getTextContent(), Integer.parseInt(attributes.getNamedItem("start").getNodeValue()), Integer.parseInt(attributes.getNamedItem("end").getNodeValue()), Integer.parseInt(attributes.getNamedItem("repeat").getNodeValue()));
                } else {
                    task = new Task(tasks.item(i).getTextContent(), Integer.parseInt(attributes.getNamedItem("time").getNodeValue()));
                }
                task.setActive(Boolean.parseBoolean(attributes.getNamedItem("active").getNodeValue()));

                object.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
