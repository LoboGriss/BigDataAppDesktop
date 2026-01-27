import javax.swing.*;
import java.awt.*;

public class ProfileWindow extends JFrame {
    public ProfileWindow(String email) {
        setTitle("Perfil de Usuario - RHAuthApp");
        setSize(400, 200);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Bienvenido al sistema, " + email, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        setVisible(true);
    }
}
