package views;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Jayden Avontuur
 */
public class LoginGUI extends JFrame implements ActionListener {

    private JPanel pnlNorth, pnlCenter, pnlSouth;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JRadioButton radioEmployee, radioCustomer;
    private ButtonGroup roleGroup;
    private JButton btnLogin, btnGoToRegister;
    private JLabel lblTitle, lblUsername, lblPassword, lblRole;
    private ClientApp client;

    public LoginGUI(ClientApp client) {
        super("Store Inventory Manager - Login");
        this.client = client;

        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);

        radioEmployee = new JRadioButton("Employee", true);
        radioCustomer = new JRadioButton("Customer");
        roleGroup = new ButtonGroup();
        roleGroup.add(radioEmployee);
        roleGroup.add(radioCustomer);

        btnLogin = new JButton("Login");
        btnGoToRegister = new JButton("Sign Up as Customer");
        lblTitle = new JLabel("Store Inventory Manager");
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
        lblRole = new JLabel("Login as:");
    }

    public void setGUI() {
        pnlNorth.setLayout(new FlowLayout());
        pnlCenter.setLayout(new GridLayout(3, 2, 5, 5));
        pnlSouth.setLayout(new FlowLayout());

        pnlNorth.add(lblTitle);

        pnlCenter.add(lblRole);
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.add(radioEmployee);
        rolePanel.add(radioCustomer);
        pnlCenter.add(rolePanel);

        pnlCenter.add(lblUsername);
        pnlCenter.add(txtUsername);
        pnlCenter.add(lblPassword);
        pnlCenter.add(txtPassword);

        pnlSouth.add(btnLogin);
        pnlSouth.add(btnGoToRegister);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        btnLogin.addActionListener(this);
        btnGoToRegister.addActionListener(this);

        this.setSize(600, 220);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (radioEmployee.isSelected()) {
                boolean success = client.authenticateUser(username, password);
                if (!success) {
                    JOptionPane.showMessageDialog(null, "Invalid login!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    txtUsername.setText("");
                    txtPassword.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new InventoryGUI(client).setGUI();
                }
            } else {
                boolean success = client.authenticateCustomer(username, password);
                if (!success) {
                    JOptionPane.showMessageDialog(null, "Invalid login!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    txtUsername.setText("");
                    txtPassword.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new ShopGUI(client, username).setGUI();
                }
            }

        } else if (e.getSource() == btnGoToRegister) {
            this.dispose();
            new CustomerRegisterGUI(client).setGUI();
        }
    }
}