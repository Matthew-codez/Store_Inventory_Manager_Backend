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
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBackToLogin;
    private JLabel lblTitle, lblUsername, lblPassword;
    private ClientApp client;

    public RegisterGUI(ClientApp client) {
        super("Store Inventory Manager - Register Employee");
        this.client = client;

        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnRegister = new JButton("Register");
        btnBackToLogin = new JButton("Back to Login");
        lblTitle = new JLabel("Register New Employee");
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
    }

    public void setGUI() {
        pnlNorth.setLayout(new FlowLayout());
        pnlCenter.setLayout(new GridLayout(2, 2, 5, 5));
        pnlSouth.setLayout(new FlowLayout());

        pnlNorth.add(lblTitle);
        pnlCenter.add(lblUsername);
        pnlCenter.add(txtUsername);
        pnlCenter.add(lblPassword);
        pnlCenter.add(txtPassword);
        pnlSouth.add(btnRegister);
        pnlSouth.add(btnBackToLogin);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        btnRegister.addActionListener(this);
        btnBackToLogin.addActionListener(this);

        this.setSize(600, 175);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = client.registerUser(username, username, password);

            if (success) {
                JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
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