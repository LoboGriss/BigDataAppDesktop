import javax.swing.*;
import java.awt.*;

public class ProfileWindow extends JFrame {
    public ProfileWindow(String email) {
        setTitle("Perfil de Usuario");
        setSize(400, 250);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("<html><center><h1>¡Bienvenido!</h1><br>Usuario conectado:<br>" + email + "</center></html>", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("Cerrar Sesión (Volver al Inicio)");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(220, 53, 69)); // Rojo
        logoutBtn.setForeground(Color.WHITE);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow();
        });

        add(logoutBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}