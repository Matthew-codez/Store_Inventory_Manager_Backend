package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerRegisterGUI extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;
    private ClientApp client;

    public CustomerRegisterGUI(ClientApp client) {
        super("Customer Sign Up");
        this.client = client;
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnRegister = new JButton("Sign Up");
        btnBack = new JButton("Back to Login");
    }

    public void setGUI() {
        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
        form.add(new JLabel("Username:")); form.add(txtUsername);
        form.add(new JLabel("Password:")); form.add(txtPassword);

        JPanel buttons = new JPanel();
        buttons.add(btnRegister); buttons.add(btnBack);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        btnRegister.addActionListener(this);
        btnBack.addActionListener(this);

        setSize(400, 175);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
                return;
            }

            boolean ok = client.registerCustomer(username, username, username, username, password);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registered! Please log in.");
                dispose();
                new LoginGUI(client).setGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Try a different username.");
            }
        } else {
            dispose();
            new LoginGUI(client).setGUI();
        }
    }
}