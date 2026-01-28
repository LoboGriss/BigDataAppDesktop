import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public LoginWindow() {
        setTitle("Login");
        setSize(350, 350); // Un poco más alto para que quepa todo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 5, 5)); // Aumentamos filas a 7

        emailField = new JTextField();
        passwordField = new JPasswordField();
        statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton loginBtn = new JButton("Iniciar Sesión");
        loginBtn.setBackground(new Color(0, 123, 255)); // Azul
        loginBtn.setForeground(Color.WHITE);

        JButton registerBtn = new JButton("Registrarse");

        JButton forgotBtn = new JButton("¿Olvidaste tu contraseña?");
        forgotBtn.setForeground(Color.BLUE);
        forgotBtn.setBorderPainted(false); // Que parezca un link
        forgotBtn.setContentAreaFilled(false);
        forgotBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(new JLabel("  Correo electrónico:"));
        add(emailField);
        add(new JLabel("  Contraseña:"));
        add(passwordField);

        add(loginBtn);

        JPanel secondaryPanel = new JPanel(new GridLayout(1, 2));
        secondaryPanel.add(registerBtn);
        secondaryPanel.add(forgotBtn); // Agregamos el botón aquí
        add(secondaryPanel);

        add(statusLabel);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> {

            new RegisterWindow();
        });

        forgotBtn.addActionListener(e -> new RecoverWindow());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());

        statusLabel.setText("Conectando...");

        SwingUtilities.invokeLater(() -> {
            boolean ok = AuthClient.login(email, pass);

            if (ok) {
                statusLabel.setText("✅ Éxito");
                dispose();
                new ProfileWindow(email);
            } else {
                statusLabel.setText("❌ Credenciales incorrectas");
                statusLabel.setForeground(Color.RED);
            }
        });
    }
}