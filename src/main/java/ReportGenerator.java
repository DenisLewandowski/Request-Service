import java.util.List;

public class ReportGenerator {
    public RequestServiceDB db;

    ReportGenerator(RequestServiceDB db) {
        this.db = db;
    }

    public void allRequestNumberReport() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("All requests: " + db.requestNumber());
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void clientRequestNumberReport(String clientId) {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("ClientId: " + clientId + " Requests: " + db.clientRequestNumber(clientId));
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void wholeRequestPriceReport() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("Total price of all requests: " + db.getTotalRequestPrice() + "$");
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void clientRequestPriceReport(String clientId) {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("ClientId: " + clientId + " requests price: " + db.getClientRequestPrice(clientId) + "$");
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void allRequestReport() {
        List<Request> requests = db.selectAllRequests();
        System.out.println("\n ALL REQUESTS: ");
        for(Request r : requests) {
            System.out.println(r);
        }
        System.out.println();
    }

    public void clientRequestReport(String clientId) {
        List<Request> requests = db.selectClientRequests(clientId);
        System.out.println("\n CLIENT ID: " + clientId + " REQUESTS: ");
        for(Request r : requests) {
            System.out.println(r);
        }
        System.out.println();
    }

    public void requestAverage() {
        System.out.println("Request price average: " + db.getRequestPriceAverage());
    }

    public void clientRequestAverage(String clientId) {
        System.out.println("Client ID: " + clientId + " Request average: " + db.getClientRequestPriceAverage(clientId));
    }

}
