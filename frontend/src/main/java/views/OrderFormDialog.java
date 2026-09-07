package views;

/**
 * Matthew Ferreira
 * 230048870
 *
 */

import domain.Order;

import javax.swing.*;
import java.awt.*;

public class OrderFormDialog extends JDialog {

    private JTextField txtOrderNum, txtOrderDate, txtDeliveryDate, txtTotalAmount, txtStatus, txtItem;
    private JButton btnSave, btnCancel;
    private ClientApp client;
    private Runnable onSuccess;

    public OrderFormDialog(JFrame parent, ClientApp client, Runnable onSuccess) {
        super(parent, "Add Order", true);
        this.client = client;
        this.onSuccess = onSuccess;

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));

        txtOrderNum = new JTextField();
        txtOrderDate = new JTextField("dd/M/yy");
        txtDeliveryDate = new JTextField("dd/M/yy");
        txtTotalAmount = new JTextField();
        txtStatus = new JTextField();
        txtItem = new JTextField();

        form.add(new JLabel("Order Num:")); form.add(txtOrderNum);
        form.add(new JLabel("Order Date:")); form.add(txtOrderDate);
        form.add(new JLabel("Delivery Date:")); form.add(txtDeliveryDate);
        form.add(new JLabel("Total Amount:")); form.add(txtTotalAmount);
        form.add(new JLabel("Status:")); form.add(txtStatus);
        form.add(new JLabel("Item:")); form.add(txtItem);

        JPanel buttons = new JPanel();
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        buttons.add(btnSave);
        buttons.add(btnCancel);

        this.setLayout(new BorderLayout());
        this.add(form, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        this.setSize(400, 300);
        this.setLocationRelativeTo(parent);

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void save() {
        try {
            Order order = new Order();
            order.setOrderNum(txtOrderNum.getText().trim());
            order.setOrderDate(txtOrderDate.getText().trim());
            order.setDeliveryDate(txtDeliveryDate.getText().trim());
            order.setTotalAmount(Double.parseDouble(txtTotalAmount.getText().trim()));
            order.setStatus(txtStatus.getText().trim());
            order.setItem(txtItem.getText().trim());

            new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    client.createOrder(order);
                    return null;
                }
                protected void done() {
                    onSuccess.run();
                    dispose();
                }
            }.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for total amount.");
        }
    }
}