import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame {
    private JTextField nombreField, apellidoField, emailField, telefonoField, puestoField, departamentoField, fechaField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public RegisterWindow() {
        setTitle("Registro de Usuario - RHAuthApp");
        setSize(400, 600);
        setLayout(new GridLayout(10, 1));

        nombreField = new JTextField();
        apellidoField = new JTextField();
        emailField = new JTextField();
        telefonoField = new JTextField();
        puestoField = new JTextField();
        departamentoField = new JTextField();
        fechaField = new JTextField("2000-01-01");
        passwordField = new JPasswordField();
        statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton registerBtn = new JButton("Registrar");

        add(new JLabel("Nombre:"));
        add(nombreField);
        add(new JLabel("Apellido:"));
        add(apellidoField);
        add(new JLabel("Correo electrónico:"));
        add(emailField);
        add(new JLabel("Teléfono:"));
        add(telefonoField);
        add(new JLabel("Puesto:"));
        add(puestoField);
        add(new JLabel("Departamento:"));
        add(departamentoField);
        add(new JLabel("Fecha de nacimiento (YYYY-MM-DD):"));
        add(fechaField);
        add(new JLabel("Contraseña:"));
        add(passwordField);
        add(registerBtn);
        add(statusLabel);

        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

    private void register() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String email = emailField.getText();
        String telefono = telefonoField.getText();
        String puesto = puestoField.getText();
        String departamento = departamentoField.getText();
        String fecha = fechaField.getText();
        String password = new String(passwordField.getPassword());

        boolean ok = AuthClient.register(
                nombre, apellido, email, telefono, puesto, departamento, fecha, password
        );

        if (ok)
            statusLabel.setText("✅ Registro exitoso");
        else
            statusLabel.setText("❌ Error al registrar");
    }
}
