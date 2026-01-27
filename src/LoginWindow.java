import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public LoginWindow() {
        setTitle("Login - RHAuthApp");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton loginBtn = new JButton("Iniciar Sesión");
        JButton registerBtn = new JButton("Registrarse");

        add(new JLabel("Correo electrónico:"));
        add(emailField);
        add(new JLabel("Contraseña:"));
        add(passwordField);
        add(loginBtn);
        add(registerBtn);
        add(statusLabel);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> new RegisterWindow());

        setVisible(true);
    }

    private void login() {
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());
        boolean ok = AuthClient.login(email, pass);

        if (ok) {
            statusLabel.setText("✅ Bienvenido " + email);
            dispose();
            new ProfileWindow(email);
        } else {
            statusLabel.setText("❌ Credenciales incorrectas");
        }
    }
}
