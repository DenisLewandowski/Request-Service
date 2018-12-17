import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestServiceDB {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:requestsService.db";

    private Connection connection;
    private Statement statement;

    public RequestServiceDB() {
        try {
            Class.forName(RequestServiceDB.DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Error, DB driver not found!");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error, can't make connection with DB!");
            e.printStackTrace();
        }

        createTables();
    }

    private boolean createTables() {
        String createRequests = "CREATE TABLE IF NOT EXISTS requests " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "clientID CHARACTER(6) NOT NULL, " +
                "requestID INTEGER NOT NULL, " +
                "name varchar(255) NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "price REAL NOT NULL)";

        try {
            statement.execute(createRequests);
        } catch (SQLException e) {
            System.out.println("Error, can't create requests table!");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertRequest(Request request) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement(
                    "INSERT INTO requests VALUES (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, request.getClientId());
            prepStmt.setLong(2, request.getRequestId());
            prepStmt.setString(3, request.getName());
            prepStmt.setInt(4, request.getQuantity());
            prepStmt.setDouble(5, request.getPrice());
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error, can't insert request!");
            e.printStackTrace();
            return false;
        } finally {
            try {
                prepStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public List<Request> selectAllRequests() {
        String selectQuery = "SELECT * FROM requests";
        List<Request> requests = new ArrayList<>();

        try {
            ResultSet result = statement.executeQuery(selectQuery);

            while (result.next()) {
                Request request = new Request(result.getString(2),
                        result.getInt(3), result.getString(4),
                        result.getInt(5), result.getDouble(6));
                requests.add(request);
            }

        } catch (SQLException e) {
            System.out.println("Error, can't select any request!");
            e.printStackTrace();
        }

        return requests;
    }

    public List<Request> selectClientRequests(String clientId) {
        List<Request> requests = new ArrayList<>();

        String selectQuery = "SELECT * FROM requests WHERE clientId = " + clientId;

        try {
            ResultSet result = statement.executeQuery(selectQuery);

            while (result.next()) {
                Request request = new Request(result.getString(2),
                        result.getInt(3), result.getString(4),
                        result.getInt(5), result.getDouble(6));
                requests.add(request);
            }

        } catch (SQLException e) {
            System.out.println("Error, can't select any request!");
            e.printStackTrace();
        }

        return requests;
    }

    public int requestNumber() {
        String query = "SELECT Count(*) AS total FROM requests";
        ResultSet resultSet;
        int requestNumber;
        try {
            resultSet = statement.executeQuery(query);
            requestNumber = resultSet.getInt("total");
        } catch (SQLException e) {
            System.out.println("Error, can't get number of requests!");
            e.printStackTrace();
            return 0;
        }

        return requestNumber;
    }

    public int clientRequestNumber(String clientId) {
        String query = "SELECT Count(*) AS total FROM requests WHERE clientId = " + clientId;
        ResultSet resultSet;
        int requestNumber;
        try {
            resultSet = statement.executeQuery(query);
            requestNumber = resultSet.getInt("total");
        } catch (SQLException e) {
            System.out.println("Error, can't get number of client requests!");
            e.printStackTrace();
            return 0;
        }

        return requestNumber;
    }

    public double getTotalRequestPrice() {
        String query = "SELECT price FROM requests";
        ResultSet result;
        Double sum = 0.0;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
                sum += result.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error, can't get total requests price!");
        }
        return sum;
    }

    public double getClientRequestPrice(String clientId) {
        String query = "SELECT price FROM requests WHERE clientId = " + clientId;
        ResultSet result;
        Double sum = 0.0;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
                sum += result.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error, can't get client requests price!");
        }
        return sum;
    }

    public double getRequestPriceAverage() {
        String query = "SELECT AVG(price) AS average FROM requests";
        ResultSet resultSet;
        double avg = 0.0;

        try {
            resultSet = statement.executeQuery(query);
            avg = resultSet.getDouble("average");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error, can't get average of requests price!");
        }
        return avg;
    }

    public double getClientRequestPriceAverage(String clientId) {
        String query = "SELECT AVG(price) AS average FROM requests WHERE clientId = " + clientId;
        ResultSet resultSet;
        double avg = 0.0;

        try {
            resultSet = statement.executeQuery(query);
            avg = resultSet.getDouble("average");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error, can't get average of requests price!");
        }
        return avg;
    }

    public void deleteTable() {
        try {
            statement.execute("DROP TABLE requests");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error, can't close the connection with DB!");
            e.printStackTrace();
        }
    }

    public void makeConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error, can't make connection with DB!");
            e.printStackTrace();
        }
    }
}
