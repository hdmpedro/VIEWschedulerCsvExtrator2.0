package io.hdmpedro.scheduler.controller;

import io.hdmpedro.scheduler.agendador.model.Database;
import io.hdmpedro.scheduler.agendador.model.Query;
import io.hdmpedro.scheduler.agendador.model.SchedulerConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLConfigController {

    public void saveConfig(SchedulerConfig config, String filePath) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        doc.setXmlVersion("1.0");

        Element rootElement = doc.createElement("scheduler-config");
        doc.appendChild(rootElement);

        Element databasesElement = doc.createElement("databases");
        config.getDatabases().forEach(db -> {
            Element dbElement = doc.createElement("database");
            dbElement.appendChild(createElement(doc, "name", db.getName()));
            dbElement.appendChild(createElement(doc, "host", db.getHost()));
            dbElement.appendChild(createElement(doc, "port", db.getPort()));
            dbElement.appendChild(createElement(doc, "databaseName", db.getDatabaseName()));
            dbElement.appendChild(createElement(doc, "user", db.getUser()));
            dbElement.appendChild(createElement(doc, "password", db.getPassword()));
            databasesElement.appendChild(dbElement);
        });
        rootElement.appendChild(databasesElement);


        Element scheduleElement = doc.createElement("schedule");

        String cron = config.getCronExpression() != null ?
                config.getCronExpression() :
                "0 0 * * * ?";

        scheduleElement.appendChild(createElement(doc, "cron-expression", cron));
        rootElement.appendChild(scheduleElement);

        Element queriesElement = doc.createElement("queries");
        config.getQueries().forEach(query -> {
            Element queryElement = doc.createElement("query");
            queryElement.appendChild(createElement(doc, "database-ref", query.getDatabaseRef()));
            queryElement.appendChild(createElement(doc, "sql", query.getSql()));

            Element csvElement = doc.createElement("csv");
            csvElement.appendChild(createElement(doc, "output-path", query.getOutputPath()));
            csvElement.appendChild(createElement(doc, "file-name", query.getFileName()));
            csvElement.appendChild(createElement(doc, "delimiter", query.getDelimiter()));

            queryElement.appendChild(csvElement);
            queriesElement.appendChild(queryElement);
        });
        rootElement.appendChild(queriesElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        }
    }

        public SchedulerConfig loadConfig(String filePath) throws Exception {

            File xmlFile = new File(filePath);
            if (!xmlFile.exists()) {
                return new SchedulerConfig();
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            SchedulerConfig config = new SchedulerConfig();

            NodeList databaseNodes = doc.getElementsByTagName("database");
            for (int i = 0; i < databaseNodes.getLength(); i++) {
                Element dbElement = (Element) databaseNodes.item(i);
                Database db = new Database(
                        getElementValue(dbElement, "name"),
                        getElementValue(dbElement, "host"),
                        getElementValue(dbElement, "port"),
                        getElementValue(dbElement, "databaseName"),
                        getElementValue(dbElement, "user"),
                        getElementValue(dbElement, "password")
                );
                config.getDatabases().add(db);
            }

            Element scheduleElement = (Element) doc.getElementsByTagName("schedule").item(0);
            if (scheduleElement != null) {
                config.setCronExpression(getElementValue(scheduleElement, "cron-expression"));
            }

            NodeList queryNodes = doc.getElementsByTagName("query");
            for (int i = 0; i < queryNodes.getLength(); i++) {
                Element queryElement = (Element) queryNodes.item(i);
                Query query = new Query();
                query.setDatabaseRef(getElementValue(queryElement, "database-ref"));
                query.setSql(getElementValue(queryElement, "sql"));

                Element csvElement = (Element) queryElement.getElementsByTagName("csv").item(0);
                if (csvElement != null) {
                    query.setOutputPath(getElementValue(csvElement, "output-path"));
                    query.setFileName(getElementValue(csvElement, "file-name"));
                    query.setDelimiter(getElementValue(csvElement, "delimiter"));
                }
                config.getQueries().add(query);
            }

            return config;
        }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }


    private Element createElement(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value != null ? value : ""));
        return element;
    }
}


