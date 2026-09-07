package views;

import domain.*;

import javax.swing.*;
import java.awt.*;

public class InventoryFormDialog extends JDialog {

    private JTextField txtProductId, txtProductName, txtSupplierId, txtCategoryName,
            txtQuantity, txtUnitPrice, txtLocation;
    private JButton btnSave, btnCancel;
    private ClientApp client;
    private Runnable onSuccess;

    public InventoryFormDialog(JFrame parent, ClientApp client, Runnable onSuccess) {
        super(parent, "Add Inventory Item", true);
        this.client = client;
        this.onSuccess = onSuccess;

        JPanel form = new JPanel(new GridLayout(7, 2, 5, 5));

        txtProductId = new JTextField();
        txtProductName = new JTextField();
        txtSupplierId = new JTextField();
        txtCategoryName = new JTextField();
        txtQuantity = new JTextField();
        txtUnitPrice = new JTextField();
        txtLocation = new JTextField();

        form.add(new JLabel("Product ID:")); form.add(txtProductId);
        form.add(new JLabel("Product Name:")); form.add(txtProductName);
        form.add(new JLabel("Supplier ID:")); form.add(txtSupplierId);
        form.add(new JLabel("Category:")); form.add(txtCategoryName);
        form.add(new JLabel("Quantity In Stock:")); form.add(txtQuantity);
        form.add(new JLabel("Unit Price:")); form.add(txtUnitPrice);
        form.add(new JLabel("Location:")); form.add(txtLocation);

        JPanel buttons = new JPanel();
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        buttons.add(btnSave);
        buttons.add(btnCancel);

        this.setLayout(new BorderLayout());
        this.add(form, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        this.setSize(400, 320);
        this.setLocationRelativeTo(parent);

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void save() {
        try {
            Product product = new Product();
            product.setProductId(txtProductId.getText().trim());
            product.setProductName(txtProductName.getText().trim());

            Supplier supplier = new Supplier();
            supplier.setSupplierId(txtSupplierId.getText().trim());

            Category category = new Category(txtCategoryName.getText().trim(), "");

            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setSupplier(supplier);
            inventory.setCategory(category);
            inventory.setQuantityInStock(Integer.parseInt(txtQuantity.getText().trim()));
            inventory.setUnitPrice(Double.parseDouble(txtUnitPrice.getText().trim()));
            inventory.setLocation(txtLocation.getText().trim());

            new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    client.createInventory(inventory);
                    return null;
                }
                protected void done() {
                    try {
                        get();
                        onSuccess.run();
                        dispose();
                    } catch (Exception ex) {
                        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
                        JOptionPane.showMessageDialog(InventoryFormDialog.this, message, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity/price fields.");
        }
    }
}