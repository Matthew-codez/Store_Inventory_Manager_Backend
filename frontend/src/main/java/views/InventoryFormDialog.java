package views;

import domain.*;

import javax.swing.*;
import java.awt.*;

public class InventoryFormDialog extends JDialog {

    private JTextField txtProductId, txtSupplierId, txtCategoryName, txtCategoryDescription,
            txtQuantity, txtMinStock, txtMaxStock, txtUnitPrice, txtLocation, txtRestockedDate;
    private JButton btnSave, btnCancel;
    private ClientApp client;
    private Runnable onSuccess;

    public InventoryFormDialog(JFrame parent, ClientApp client, Runnable onSuccess) {
        super(parent, "Add Inventory Item", true);
        this.client = client;
        this.onSuccess = onSuccess;

        JPanel form = new JPanel(new GridLayout(10, 2, 5, 5));

        txtProductId = new JTextField();
        txtSupplierId = new JTextField();
        txtCategoryName = new JTextField();
        txtCategoryDescription = new JTextField();
        txtQuantity = new JTextField();
        txtMinStock = new JTextField();
        txtMaxStock = new JTextField();
        txtUnitPrice = new JTextField();
        txtLocation = new JTextField();
        txtRestockedDate = new JTextField("yyyy-MM-dd");

        form.add(new JLabel("Product ID:")); form.add(txtProductId);
        form.add(new JLabel("Supplier ID:")); form.add(txtSupplierId);
        form.add(new JLabel("Category Name:")); form.add(txtCategoryName);
        form.add(new JLabel("Category Description:")); form.add(txtCategoryDescription);
        form.add(new JLabel("Quantity In Stock:")); form.add(txtQuantity);
        form.add(new JLabel("Minimum Stock:")); form.add(txtMinStock);
        form.add(new JLabel("Maximum Stock:")); form.add(txtMaxStock);
        form.add(new JLabel("Unit Price:")); form.add(txtUnitPrice);
        form.add(new JLabel("Location:")); form.add(txtLocation);
        form.add(new JLabel("Last Restocked Date:")); form.add(txtRestockedDate);

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
        try {
            Product product = new Product();
            product.setProductId(txtProductId.getText().trim());

            Supplier supplier = new Supplier();
            supplier.setSupplierId(txtSupplierId.getText().trim());

            Category category = new Category(
                    txtCategoryName.getText().trim(),
                    txtCategoryDescription.getText().trim()
            );

            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setSupplier(supplier);
            inventory.setCategory(category);
            inventory.setQuantityInStock(Integer.parseInt(txtQuantity.getText().trim()));
            inventory.setMinimumStockLevel(Integer.parseInt(txtMinStock.getText().trim()));
            inventory.setMaximumStockLevel(Integer.parseInt(txtMaxStock.getText().trim()));
            inventory.setUnitPrice(Double.parseDouble(txtUnitPrice.getText().trim()));
            inventory.setLocation(txtLocation.getText().trim());
            inventory.setLastRestockedDate(txtRestockedDate.getText().trim());

            new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    client.createInventory(inventory);
                    return null;
                }
                protected void done() {
                    onSuccess.run();
                    dispose();
                }
            }.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity/price fields.");
        }
    }
}
