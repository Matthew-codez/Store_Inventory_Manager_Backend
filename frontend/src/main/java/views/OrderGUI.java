package views;

/**
 * Matthew Ferreira
 * 230048870
 *
 */

import domain.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderGUI extends JFrame implements ActionListener {

    private JPanel pnlNorth, pnlCenter, pnlSouth;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnDelete, btnRefresh;
    private JLabel lblTitle;
    private ClientApp client;

    public OrderGUI(ClientApp client) {
        super("Store Inventory Manager - Orders");
        this.client = client;

        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();
        tableModel = new DefaultTableModel(new Object[]{
                "Order Num", "Order Date", "Delivery Date", "Total Amount", "Status", "Item"
        }, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");
        lblTitle = new JLabel("Order Management");
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

        this.setSize(800, 450);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        loadOrders();
    }

    private void loadOrders() {
        new SwingWorker<List<Order>, Void>() {
            protected List<Order> doInBackground() throws Exception {
                return client.getAllOrders();
            }
            protected void done() {
                try {
                    tableModel.setRowCount(0);
                    for (Order o : get()) {
                        tableModel.addRow(new Object[]{
                                o.getOrderNum(),
                                o.getOrderDate(),
                                o.getDeliveryDate(),
                                o.getTotalAmount(),
                                o.getStatus(),
                                o.getItem()
                        });
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(OrderGUI.this, "Failed to load orders: " + ex.getMessage());
                }
            }
        }.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRefresh) {
            loadOrders();

        } else if (e.getSource() == btnAdd) {
            new OrderFormDialog(this, client, this::loadOrders).setVisible(true);

        } else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }
            String orderNum = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this order?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    client.deleteOrder(orderNum);
                    return null;
                }
                protected void done() { loadOrders(); }
            }.execute();
        }
    }
}