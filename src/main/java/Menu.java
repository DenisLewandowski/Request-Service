import java.util.List;
import java.util.Scanner;

public class Menu {
    ReportGenerator reportGenerator;
    RequestServiceDB db;
    Menu() {
        db = new RequestServiceDB();
        reportGenerator = new ReportGenerator(db);
    }

    public void mainInterface() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("++++++++++REQUEST SERVICE++++++++++\n\n");
            System.out.println("1. Load XML or CSV files.");
            System.out.println("2. Reports Generator.");
            System.out.println("0. Exit.\n");

            switch (sc.nextInt()) {
                case 1: {
                    System.out.println("Write file names (requests.csv, requests3.xml, requests5.csv ...) : ");

                    sc.nextLine();
                    String[] fileNames = sc.nextLine().split(", ");
                    List<Request> requests = RequestFileReader.readFiles(fileNames);
                    for (Request r : requests) {
                        reportGenerator.db.insertRequest(r);
                    }
                    System.out.println("Request added properly!");
                    break;
                }
                case 2: {
                    reportInterface();
                    return;
                }
                case 0: {
                    System.exit(0);
                }
                default: {
                    System.out.println("Choose correct value!");
                }
            }

        }
    }

    public void reportInterface() {
        Scanner sc = new Scanner(System.in);

        showReportInfo();
        while (true) {
            switch (sc.nextInt()) {
                case 1: {
                    showReportInfo();
                    reportGenerator.allRequestNumberReport();
                    break;
                }
                case 2: {
                    showReportInfo();
                    System.out.print("Write client ID: ");
                    reportGenerator.clientRequestNumberReport(sc.next());
                    break;
                }
                case 3: {
                    showReportInfo();
                    reportGenerator.wholeRequestPriceReport();
                    break;
                }
                case 4: {
                    showReportInfo();
                    System.out.print("Write client ID: ");
                    reportGenerator.clientRequestPriceReport(sc.next());
                    break;
                }
                case 5: {
                    showReportInfo();
                    reportGenerator.allRequestReport();
                    break;
                }
                case 6: {
                    showReportInfo();
                    System.out.print("Write client ID: ");
                    reportGenerator.clientRequestReport(sc.next());
                    break;
                }
                case 7: {
                    showReportInfo();
                    reportGenerator.requestAverage();
                    break;
                }
                case 8: {
                    showReportInfo();
                    System.out.print("Write client ID: ");
                    reportGenerator.clientRequestAverage(sc.next());
                    break;
                }
                case 9: {
                    mainInterface();
                    return;
                }
                case 0: {
                    System.exit(0);
                }
                default: {
                    System.out.println("Write correct value!");
                }

            }
        }
    }

    private static void showReportInfo()  {
        System.out.println("++++++++++REPORT GENERATOR+++++++++++\n");
        System.out.println("1. All request number.");
        System.out.println("2. Client request number.");
        System.out.println("3. Total price of all requests.");
        System.out.println("4. Client requests total price.");
        System.out.println("5. Show all requests.");
        System.out.println("6. Show client requests.");
        System.out.println("7. Request price average.");
        System.out.println("8. Client price average.");
        System.out.println("9. Back.");
        System.out.println("0. Exit.\n");
    }


}
