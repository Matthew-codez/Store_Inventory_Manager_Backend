package views;

/**
 * Matthew Ferreira
 * 230048870
 *
 */

import domain.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerFormDialog extends JDialog {

    private JTextField txtFirstName, txtSurname, txtEmail, txtPhoneNumber,
            txtAddress, txtCity, txtPostalCode, txtCountry;
    private JButton btnSave, btnCancel;
    private ClientApp client;
    private Runnable onSuccess;

    public CustomerFormDialog(JFrame parent, ClientApp client, Runnable onSuccess) {
        super(parent, "Add Customer", true);
        this.client = client;
        this.onSuccess = onSuccess;

        JPanel form = new JPanel(new GridLayout(8, 2, 5, 5));

        txtFirstName = new JTextField();
        txtSurname = new JTextField();
        txtEmail = new JTextField();
        txtPhoneNumber = new JTextField();
        txtAddress = new JTextField();
        txtCity = new JTextField();
        txtPostalCode = new JTextField();
        txtCountry = new JTextField();

        form.add(new JLabel("First Name:")); form.add(txtFirstName);
        form.add(new JLabel("Surname:")); form.add(txtSurname);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Phone Number:")); form.add(txtPhoneNumber);
        form.add(new JLabel("Address:")); form.add(txtAddress);
        form.add(new JLabel("City:")); form.add(txtCity);
        form.add(new JLabel("Postal Code:")); form.add(txtPostalCode);
        form.add(new JLabel("Country:")); form.add(txtCountry);

        JPanel buttons = new JPanel();
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        buttons.add(btnSave);
        buttons.add(btnCancel);

        this.setLayout(new BorderLayout());
        this.add(form, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        this.setSize(400, 400);
        this.setLocationRelativeTo(parent);

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void save() {
        Customer customer = new Customer();
        customer.setFirstName(txtFirstName.getText().trim());
        customer.setSurname(txtSurname.getText().trim());
        customer.setEmail(txtEmail.getText().trim());
        customer.setPhoneNumber(txtPhoneNumber.getText().trim());
        customer.setAddress(txtAddress.getText().trim());
        customer.setCity(txtCity.getText().trim());
        customer.setPostalCode(txtPostalCode.getText().trim());
        customer.setCountry(txtCountry.getText().trim());

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() throws Exception {
                client.createCustomer(customer);
                return null;
            }
            protected void done() {
                onSuccess.run();
                dispose();
            }
        }.execute();
    }
}
