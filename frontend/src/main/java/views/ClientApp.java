package views;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Customer;
import domain.Inventory;
import domain.Order;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 *
 * @author Jayden Avontuur
 */
public class ClientApp {

    private static final String AUTH_URL = "http://localhost:8080/api/auth";
    private static final String INVENTORY_URL = "http://localhost:8080/api/inventory";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public ClientApp() {
        httpClient = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public boolean authenticateUser(String username, String password) {
        try {
            String json = mapper.writeValueAsString(new LoginRequest(username, password));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AUTH_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(String username, String email, String password) {
        try {
            String json = mapper.writeValueAsString(new RegisterRequest(username, email, password));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AUTH_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 || response.statusCode() == 201;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Inventory> getAllInventory() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(INVENTORY_URL))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(),
                mapper.getTypeFactory().constructCollectionType(List.class, Inventory.class));
    }

    public Inventory createInventory(Inventory inventory) throws Exception {
        String json = mapper.writeValueAsString(inventory);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(INVENTORY_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 500 && response.body().contains("Unique index or primary key violation")) {
            throw new Exception("That Product ID already exists. Please use a different Product ID.");
        }
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Failed to add inventory item (server returned " + response.statusCode() + ").");
        }

        return mapper.readValue(response.body(), Inventory.class);
    }

    public void deleteInventory(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(INVENTORY_URL + "/" + id))
                .DELETE()
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }

    private static class LoginRequest {
        public String username;
        public String password;
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    private static class RegisterRequest {
        public String username;
        public String email;
        public String password;
        public RegisterRequest(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }

    private static final String CUSTOMER_AUTH_URL = "http://localhost:8080/api/customer-auth";
    private static final String ORDER_URL = "http://localhost:8080/api/orders";

    public boolean authenticateCustomer(String username, String password) {
        try {
            String json = mapper.writeValueAsString(new LoginRequest(username, password));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CUSTOMER_AUTH_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean registerCustomer(String firstName, String surname, String email, String username, String password) {
        try {
            String json = String.format(
                    "{\"firstName\":\"%s\",\"surname\":\"%s\",\"email\":\"%s\",\"username\":\"%s\",\"password\":\"%s\"}",
                    firstName, surname, email, username, password);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CUSTOMER_AUTH_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            int code = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).statusCode();
            return code == 200 || code == 201;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    private static final String PURCHASE_URL = "http://localhost:8080/api/purchase";

    public boolean purchase(String customerUsername, Long inventoryId, int quantity) throws Exception {
        String json = String.format(
                "{\"customerId\":\"%s\",\"inventoryId\":%d,\"quantity\":%d}",
                customerUsername, inventoryId, quantity);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PURCHASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

    public Customer createCustomer(Customer customer) throws Exception {
        String json = mapper.writeValueAsString(customer);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/customers"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), Customer.class);
    }

    public List<Customer> getAllCustomers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/customers"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(),
                mapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
    }

    public void deleteCustomer(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/customers/" + id))
                .DELETE()
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }

    public List<Order> getAllOrders() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ORDER_URL))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(),
                mapper.getTypeFactory().constructCollectionType(List.class, Order.class));
    }

    public Order createOrder(Order order) throws Exception {
        String json = mapper.writeValueAsString(order);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ORDER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), Order.class);
    }

    public void deleteOrder(String orderNum) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ORDER_URL + "/" + orderNum))
                .DELETE()
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }

    public static void main(String[] args) {
        ClientApp client = new ClientApp();
        new LoginGUI(client).setGUI();
    }
}