import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame {

    private JTextField nombreField, emailField, fechaField, curpField, rfcField, nssField, puestoField;
    private JPasswordField passwordField;
    private JComboBox<String> questionBox;
    private JTextField answerField;
    private JLabel statusLabel;

    public RegisterWindow() {
        setTitle("Registro Completo");
        setSize(500, 700); // Hacemos la ventana más alta
        setLayout(new GridLayout(12, 2, 10, 10)); // Grid con espacio


        nombreField = new JTextField();
        emailField = new JTextField();
        fechaField = new JTextField("2000-01-01");
        curpField = new JTextField();
        rfcField = new JTextField();
        nssField = new JTextField();
        puestoField = new JTextField();
        passwordField = new JPasswordField();


        String[] questions = {
                "¿Cómo se llamaba tu primera mascota?",
                "¿En qué ciudad naciste?",
                "¿Cuál fue el nombre de tu escuela primaria?",
                "¿Cuál es el nombre de tu mejor amigo de la infancia?",
                "¿Cuál es el segundo nombre de tu madre?"
        };
        questionBox = new JComboBox<>(questions);
        answerField = new JTextField();

        statusLabel = new JLabel("", SwingConstants.CENTER);
        JButton registerBtn = new JButton("Registrar Candidato");


        add(new JLabel("  Nombre Completo:")); add(nombreField);
        add(new JLabel("  Email:")); add(emailField);
        add(new JLabel("  Fecha Nacimiento (YYYY-MM-DD):")); add(fechaField);
        add(new JLabel("  CURP:")); add(curpField);
        add(new JLabel("  RFC:")); add(rfcField);
        add(new JLabel("  NSS:")); add(nssField);
        add(new JLabel("  Puesto Deseado:")); add(puestoField);


        add(new JLabel("  Pregunta Seguridad:")); add(questionBox);
        add(new JLabel("  Respuesta Secreta:")); add(answerField);
        add(new JLabel("  Contraseña:")); add(passwordField);

        add(registerBtn);
        add(statusLabel);

        registerBtn.addActionListener(e -> register());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void register() {

        String fullQuestion = (String) questionBox.getSelectedItem();
        String questionCode = fullQuestion.split("-")[0].trim();

        boolean ok = AuthClient.register(
                nombreField.getText(),
                emailField.getText(),
                fechaField.getText(),
                curpField.getText(),
                rfcField.getText(),
                nssField.getText(),
                puestoField.getText(),
                new String(passwordField.getPassword()),
                questionCode,
                answerField.getText()
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "¡Registro Exitoso! Ahora inicia sesión.");
            dispose();
        } else {
            statusLabel.setText("❌ Error: Verifica los datos o el servidor.");
            statusLabel.setForeground(Color.RED);
        }
    }
}