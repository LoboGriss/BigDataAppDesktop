import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class AuthClient {

    private static final String BASE_URL = "http://localhost:8080/api/auth";

    public static boolean register(String nombreCompleto, String email, String fechaNacimiento,
                                   String curp, String rfc, String nss,
                                   String puesto, String password,
                                   String securityQuestion, String securityAnswer) {
        try {
            String json = String.format("""
                {
                    "nombreCompleto": "%s",
                    "email": "%s",
                    "fechaNacimiento": "%s",
                    "curp": "%s",
                    "rfc": "%s",
                    "nss": "%s",
                    "puestoDeseado": "%s",
                    "password": "%s",
                    "securityQuestion": "%s",
                    "securityAnswer": "%s"
                }
                """, nombreCompleto, email, fechaNacimiento, curp, rfc, nss, puesto, password, securityQuestion, securityAnswer);

            return sendPostRequest("/register", json) == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String email, String password) {
        try {
            String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);
            return sendPostRequest("/login", json) == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getSecurityQuestion(String email) {
        try {
            String json = String.format("{\"email\":\"%s\"}", email);
            String response = sendPostRequestWithResponse("/get-question", json);

            if (response != null && response.contains("question")) {

                String[] parts = response.split(":");
                String questionCode = parts[1].replace("}", "").replace("\"", "").trim();
                return questionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean resetPassword(String email, String answer, String newPassword) {
        try {
            String json = String.format("""
                {
                    "email": "%s",
                    "answer": "%s",
                    "newPassword": "%s"
                }
                """, email, answer, newPassword);

            return sendPostRequest("/reset-with-security", json) == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int sendPostRequest(String endpoint, String jsonInput) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }
        return conn.getResponseCode();
    }

    private static String sendPostRequestWithResponse(String endpoint, String jsonInput) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        if (conn.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return in.readLine();
        }
        return null;
    }
}