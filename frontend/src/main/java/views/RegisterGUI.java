package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Jayden Avontuur
 */
public class RegisterGUI extends JFrame implements ActionListener {

    private JPanel pnlNorth, pnlCenter, pnlSouth;
    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegister, btnBackToLogin;
    private JLabel lblTitle, lblUsername, lblEmail, lblPassword, lblConfirmPassword;
    private ClientApp client;

    public RegisterGUI(ClientApp client) {
        super("Store Inventory Manager - Register");
        this.client = client;

        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();
        txtUsername = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtConfirmPassword = new JPasswordField(20);
        btnRegister = new JButton("Register");
        btnBackToLogin = new JButton("Back to Login");
        lblTitle = new JLabel("Create an Account");
        lblUsername = new JLabel("Username:");
        lblEmail = new JLabel("Email:");
        lblPassword = new JLabel("Password:");
        lblConfirmPassword = new JLabel("Confirm Password:");
    }

    public void setGUI() {
        pnlNorth.setLayout(new FlowLayout());
        pnlCenter.setLayout(new GridLayout(4, 2, 5, 5));
        pnlSouth.setLayout(new FlowLayout());

        pnlNorth.add(lblTitle);
        pnlCenter.add(lblUsername);
        pnlCenter.add(txtUsername);
        pnlCenter.add(lblEmail);
        pnlCenter.add(txtEmail);
        pnlCenter.add(lblPassword);
        pnlCenter.add(txtPassword);
        pnlCenter.add(lblConfirmPassword);
        pnlCenter.add(txtConfirmPassword);
        pnlSouth.add(btnRegister);
        pnlSouth.add(btnBackToLogin);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        btnRegister.addActionListener(this);
        btnBackToLogin.addActionListener(this);

        this.setSize(600, 275);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String confirmPassword = new String(txtConfirmPassword.getPassword()).trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = client.registerUser(username, email, password);

            if (success) {
                JOptionPane.showMessageDialog(null, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new LoginGUI(client).setGUI();
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnBackToLogin) {
            this.dispose();
            new LoginGUI(client).setGUI();
        }
    }
}
