import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RecoverWindow extends JFrame {
    private JTextField emailField;
    private JTextField answerField;
    private JPasswordField newPasswordField;
    private JLabel questionLabel;
    private JButton searchBtn, resetBtn, backBtn1, backBtn2;
    private JPanel mainPanel, step1Panel, step2Panel;

    public RecoverWindow() {
        setTitle("Recuperar Contraseña");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra esta ventana

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        step1Panel = new JPanel();
        step1Panel.setLayout(new GridLayout(4, 1, 5, 5));
        step1Panel.setBorder(BorderFactory.createTitledBorder("Paso 1: Ingresa tu correo"));
        step1Panel.setMaximumSize(new Dimension(400, 150));

        emailField = new JTextField();
        searchBtn = new JButton("Buscar mi Pregunta");
        searchBtn.setBackground(new Color(0, 123, 255));
        searchBtn.setForeground(Color.WHITE);

        backBtn1 = new JButton("Cancelar (Volver al Login)");
        backBtn1.setForeground(Color.RED);

        step1Panel.add(new JLabel("Correo Electrónico:"));
        step1Panel.add(emailField);
        step1Panel.add(searchBtn);
        step1Panel.add(backBtn1);

        step2Panel = new JPanel();
        step2Panel.setLayout(new GridLayout(7, 1, 5, 5));
        step2Panel.setBorder(BorderFactory.createTitledBorder("Paso 2: Responde y Cambia"));

        questionLabel = new JLabel("Pregunta: ...");
        questionLabel.setForeground(new Color(0, 102, 204));
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        answerField = new JTextField();
        newPasswordField = new JPasswordField();

        resetBtn = new JButton("Confirmar Nueva Contraseña");
        resetBtn.setBackground(new Color(40, 167, 69)); // Verde
        resetBtn.setForeground(Color.WHITE);

        backBtn2 = new JButton("Cancelar");
        backBtn2.setForeground(Color.RED);

        step2Panel.add(questionLabel);
        step2Panel.add(new JLabel("Tu Respuesta Secreta:"));
        step2Panel.add(answerField);
        step2Panel.add(new JLabel("Nueva Contraseña:"));
        step2Panel.add(newPasswordField);
        step2Panel.add(resetBtn);
        step2Panel.add(backBtn2);

        step2Panel.setVisible(false);

        mainPanel.add(step1Panel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(step2Panel);

        searchBtn.addActionListener(this::buscarPregunta);
        resetBtn.addActionListener(this::cambiarPassword);

        backBtn1.addActionListener(e -> dispose());
        backBtn2.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buscarPregunta(ActionEvent e) {
        String email = emailField.getText().trim();
        if(email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un correo válido.");
            return;
        }


        String questionCode = AuthClient.getSecurityQuestion(email);

        if (questionCode != null && !questionCode.isEmpty()) {

            String textoBonito = obtenerTextoPregunta(questionCode);

            questionLabel.setText("<html>Pregunta:<br><b>" + textoBonito + "</b></html>");
            step1Panel.setVisible(false);
            step2Panel.setVisible(true);
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
        }
    }

    private void cambiarPassword(ActionEvent e) {
        String email = emailField.getText();
        String answer = answerField.getText();
        String pass = new String(newPasswordField.getPassword());

        if(pass.length() < 6){
            JOptionPane.showMessageDialog(this, "La contraseña es muy corta.");
            return;
        }

        boolean success = AuthClient.resetPassword(email, answer, pass);

        if(success) {
            JOptionPane.showMessageDialog(this, "¡Listo! Contraseña actualizada.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Respuesta incorrecta.");
        }
    }

    private String obtenerTextoPregunta(String codigo) {
        switch (codigo) {
            case "nombre_mascota": return "¿Cómo se llamaba tu primera mascota?";
            case "ciudad_nacimiento": return "¿En qué ciudad naciste?";
            case "escuela_primaria": return "¿Cuál fue el nombre de tu escuela primaria?";
            case "mejor_amigo": return "¿Cuál es el nombre de tu mejor amigo de la infancia?";
            case "nombre_madre": return "¿Cuál es el segundo nombre de tu madre?";
            default: return "Pregunta de seguridad (" + codigo + ")";
        }
    }
}