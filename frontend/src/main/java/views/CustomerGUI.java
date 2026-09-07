package views;

/**
 * Matthew Ferreira
 * 230048870
 *
 */

import domain.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerGUI extends JFrame implements ActionListener {

    private JPanel pnlNorth, pnlCenter, pnlSouth;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnDelete, btnRefresh;
    private JLabel lblTitle;
    private ClientApp client;

    public CustomerGUI(ClientApp client) {
        super("Store Inventory Manager - Customers");
        this.client = client;

        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "First Name", "Surname", "Email", "Phone", "Address", "City", "Postal Code", "Country"
        }, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");
        lblTitle = new JLabel("Customer Management");
    }

    public void setGUI() {
        pnlNorth.setLayout(new FlowLayout());
        pnlCenter.setLayout(new BorderLayout());
        pnlSouth.setLayout(new FlowLayout());

        pnlNorth.add(lblTitle);
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

        this.setSize(900, 450);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        loadCustomers();
    }

    private void loadCustomers() {
        new SwingWorker<List<Customer>, Void>() {
            protected List<Customer> doInBackground() throws Exception {
                return client.getAllCustomers();
            }
            protected void done() {
                try {
                    tableModel.setRowCount(0);
                    for (Customer c : get()) {
                        tableModel.addRow(new Object[]{
                                c.getCustomerId(),
                                c.getFirstName(),
                                c.getSurname(),
                                c.getEmail(),
                                c.getPhoneNumber(),
                                c.getAddress(),
                                c.getCity(),
                                c.getPostalCode(),
                                c.getCountry()
                        });
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CustomerGUI.this, "Failed to load customers: " + ex.getMessage());
                }
            }
        }.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRefresh) {
            loadCustomers();

        } else if (e.getSource() == btnAdd) {
            new CustomerFormDialog(this, client, this::loadCustomers).setVisible(true);

        } else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }
            Long id = (Long) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    client.deleteCustomer(String.valueOf(id));
                    return null;
                }
                protected void done() { loadCustomers(); }
            }.execute();
        }
    }
}