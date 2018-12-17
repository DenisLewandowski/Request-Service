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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestFileReader {

    public static List<Request> readFiles(String[] fileNames) {
        List<Request> allRequests = new LinkedList<>();

        for(String fileName : fileNames) {
            allRequests.addAll(readFile(fileName));
        }

        return allRequests;
    }

    private static List<Request> readFile(String fileName) {
        if(fileName.contains(".csv"))
            return readCSV(fileName);
        if (fileName.contains(".xml"))
            return readXML(fileName);

        System.out.println("Error, wrong document type!");
        return null;
    }

    private static List<Request> readCSV(String fileName) {
        List<Request> requests = new ArrayList<>();
        List<String> reading = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            reading = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error, can't open CSV file!");
        }

        // Remove header with variable names
        reading.remove(0);

        for (String line : reading) {
            requests.add(parseCsvLine(line));
        }

        return requests;
    }

    private static Request parseCsvLine(String line) {
        String[] requestData = line.split(",");

        String clientId = requestData[0];
        long requestId = Long.parseLong(requestData[1]);
        String name = requestData[2];
        int quantity = Integer.parseInt(requestData[3]);
        Double price = Double.parseDouble(requestData[4]);

        return new Request(clientId, requestId, name, quantity, price);
    }

    private static List<Request> readXML(String fileName) {
        List<Request> requests = new ArrayList<>();

        File xmlFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("request");

            for (int i = 0; i < nodeList.getLength(); i++) {
                requests.add(getRequest(nodeList.item(i)));
            }

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            System.out.println("Error, can't open XML file!");
        }


        return requests;
    }

    private static Request getRequest(Node node) {
        Request request = new Request();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            request.setClientId(getTagValue("clientId", element));
            request.setRequestId(Long.parseLong(getTagValue("requestId", element)));
            request.setName(getTagValue("name", element));
            request.setQuantity(Integer.parseInt(getTagValue("quantity", element)));
            request.setPrice(Double.parseDouble(getTagValue("price", element)));
        }

        return request;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
