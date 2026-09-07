package views;

import domain.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Jayden Avontuur
 */
public class InventoryGUI extends JFrame implements ActionListener {

    private JPanel pnlNorth, pnlCenter, pnlSouth;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnDelete, btnRefresh, btnRegisterEmployee, btnViewOrders;
    private JLabel lblTitle;
    private ClientApp client;

    public InventoryGUI(ClientApp client) {
        super("Store Inventory Manager - Inventory");
        this.client = client;

        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Product ID", "Product Name", "Category", "Supplier", "Qty", "Unit Price", "Location"
        }, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");
        btnRegisterEmployee = new JButton("Register New Employee");
        btnViewOrders = new JButton("View Orders");
        lblTitle = new JLabel("Inventory Management");
    }

    public void setGUI() {
        pnlNorth.setLayout(new FlowLayout());
        pnlCenter.setLayout(new BorderLayout());
        pnlSouth.setLayout(new FlowLayout());

        pnlNorth.add(lblTitle);
        pnlNorth.add(btnRegisterEmployee);
        pnlNorth.add(btnViewOrders);
        pnlCenter.add(new JScrollPane(table), BorderLayout.CENTER);
        pnlSouth.add(btnAdd);
        pnlSouth.add(btnDelete);
        pnlSouth.add(btnRefresh);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnRegisterEmployee.addActionListener(this);
        btnViewOrders.addActionListener(this);

        this.setSize(900, 450);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        loadInventory();
    }

    private void loadInventory() {
        new SwingWorker<List<Inventory>, Void>() {
            protected List<Inventory> doInBackground() throws Exception {
                return client.getAllInventory();
            }
            protected void done() {
                try {
                    tableModel.setRowCount(0);
                    for (Inventory inv : get()) {
                        tableModel.addRow(new Object[]{
                                inv.getInventoryId(),
                                inv.getProduct() != null ? inv.getProduct().getProductId() : "",
                                inv.getProduct() != null ? inv.getProduct().getProductName() : "",
                                inv.getCategory() != null ? inv.getCategory().getName() : "",
                                inv.getSupplier() != null ? inv.getSupplier().getSupplierId() : "",
                                inv.getQuantityInStock(),
                                inv.getUnitPrice(),
                                inv.getLocation()
                        });
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(InventoryGUI.this, "Failed to load inventory: " + ex.getMessage());
                }
            }
        }.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRefresh) {
            loadInventory();

        } else if (e.getSource() == btnAdd) {
            new InventoryFormDialog(this, client, this::loadInventory).setVisible(true);

        } else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }
            Long id = (Long) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this inventory item?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    client.deleteInventory(id);
                    return null;
                }
                protected void done() { loadInventory(); }
            }.execute();

        } else if (e.getSource() == btnRegisterEmployee) {
            new RegisterGUI(client).setGUI();
        } else if (e.getSource() == btnViewOrders) {
            new OrderGUI(client).setGUI();
        }
    }
}