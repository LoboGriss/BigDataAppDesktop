import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class AuthClient {

    private static final String BASE_URL = "http://localhost:8080/api/auth";

    public static boolean register(String nombre, String apellido, String email, String telefono,
                                   String puesto, String departamento, String fechaNacimiento, String password) {
        try {
            URL url = new URL(BASE_URL + "/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = String.format("""
                {
                    "nombre":"%s",
                    "apellido":"%s",
                    "email":"%s",
                    "telefono":"%s",
                    "puesto":"%s",
                    "departamento":"%s",
                    "fechaNacimiento":"%s",
                    "password":"%s"
                }
                """, nombre, apellido, email, telefono, puesto, departamento, fechaNacimiento, password);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();
            System.out.println("Server response code: " + code);
            return code == 200 || code == 201;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String email, String password) {
        try {
            URL url = new URL(BASE_URL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();
            System.out.println("Login response code: " + code);

            if (code == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String token = in.readLine();
                System.out.println("Token recibido: " + token);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
