package views;

import domain.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ShopGUI extends JFrame implements ActionListener {

    private ClientApp client;
    private String customerUsername;

    private List<Inventory> allInventory = new ArrayList<>();
    private List<Inventory> filteredInventory = new ArrayList<>();

    private JComboBox<String> comboCategory;
    private JButton btnBuy, btnLogout;
    private DefaultTableModel tableModel;
    private JTable table;
    private JSpinner spinnerQuantity;

    public ShopGUI(ClientApp client, String customerUsername) {
        super("Shop - Store Inventory Manager");
        this.client = client;
        this.customerUsername = customerUsername;

        comboCategory = new JComboBox<>();
        tableModel = new DefaultTableModel(new Object[]{"Product", "Unit Price", "Qty Available"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    }

    public void setGUI() {
        table = new JTable(tableModel);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Category:"));
        top.add(comboCategory);
        top.add(Box.createHorizontalStrut(30));
        top.add(new JLabel("Logged in as: " + customerUsername));

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(new JLabel("Quantity:"));
        bottom.add(spinnerQuantity);
        btnBuy = new JButton("Buy");
        btnLogout = new JButton("Log out");
        bottom.add(btnBuy);
        bottom.add(btnLogout);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        comboCategory.addActionListener(this);
        btnBuy.addActionListener(this);
        btnLogout.addActionListener(this);

        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loadInventory();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboCategory) {
            applyFilter();
        } else if (e.getSource() == btnBuy) {
            buySelected();
        } else if (e.getSource() == btnLogout) {
            client.logoutAndShowLogin();
        }
    }

    private void loadInventory() {
        new SwingWorker<List<Inventory>, Void>() {
            protected List<Inventory> doInBackground() throws Exception {
                return client.getAllInventory();
            }
            protected void done() {
                try {
                    allInventory = get();
                    populateCategoryDropdown();
                    applyFilter();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ShopGUI.this, "Failed to load inventory: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private String categoryOf(Inventory inv) {
        if (inv.getProduct() == null) {
            return null;
        }
        String category = inv.getProduct().getCategory();
        if (category == null || category.isBlank()) {
            return null;
        }
        return category;
    }

    private void populateCategoryDropdown() {
        Set<String> categories = new LinkedHashSet<>();
        categories.add("All");
        for (Inventory inv : allInventory) {
            String category = categoryOf(inv);
            if (category != null) {
                categories.add(category);
            }
        }
        String previouslySelected = (String) comboCategory.getSelectedItem();
        comboCategory.removeAllItems();
        for (String c : categories) comboCategory.addItem(c);
        if (previouslySelected != null && categories.contains(previouslySelected)) {
            comboCategory.setSelectedItem(previouslySelected);
        }
    }

    private void applyFilter() {
        String selected = (String) comboCategory.getSelectedItem();
        filteredInventory = new ArrayList<>();
        for (Inventory inv : allInventory) {
            String catName = categoryOf(inv);
            if (selected == null || selected.equals("All") || selected.equals(catName)) {
                filteredInventory.add(inv);
            }
        }

        tableModel.setRowCount(0);
        for (Inventory inv : filteredInventory) {
            String productName = inv.getProduct() != null ? inv.getProduct().getProductName() : "";
            tableModel.addRow(new Object[]{productName, inv.getUnitPrice(), inv.getQuantityInStock()});
        }
    }

    private void buySelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product first.");
            return;
        }

        Inventory selectedItem = filteredInventory.get(row);
        int quantity = (int) spinnerQuantity.getValue();

        if (quantity > selectedItem.getQuantityInStock()) {
            JOptionPane.showMessageDialog(this, "Not enough stock available.");
            return;
        }

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() throws Exception {
                client.purchase(customerUsername, selectedItem.getInventoryId(), quantity);
                return null;
            }
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(ShopGUI.this, "Purchase successful!");
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    JOptionPane.showMessageDialog(ShopGUI.this, "Purchase failed: " + cause.getMessage());
                }
                loadInventory(); // refresh stock either way
            }
        }.execute();
    }
}