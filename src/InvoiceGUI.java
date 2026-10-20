import javax.swing.*;
import java.awt.*;

public class InvoiceGUI extends JFrame {
    private Invoice invoice;

    private JTextField titleField;
    private JTextArea addressArea;

    private JTextField productField;
    private JTextField quantityField;
    private JTextField priceField;

    private JTextArea outputArea;

    public InvoiceGUI() {

        invoice = new Invoice("INVOICE", "");
        setTitle("Lab 07A - Invoice");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(
                new GridLayout(0, 2, 10, 10)
        );

        titleField = new JTextField("INVOICE");
        addressArea = new JTextArea(3, 20);
        addressArea.setText(
                "Sam's Small Appliances\n"
                        + "100 Main Street\n"
                        + "Anytown, CA 98765"
        );

        productField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();

        inputPanel.add(new JLabel("Invoice Title:"));
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Customer Address:"));
        inputPanel.add(new JScrollPane(addressArea));

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productField);

        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Unit Price:"));
        inputPanel.add(priceField);

        JButton addButton = new JButton("Add Item");
        JButton displayButton = new JButton("Display Invoice");

        addButton.addActionListener(e -> addLineItem());
        displayButton.addActionListener(e -> displayInvoice());

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(
                new Font(Font.MONOSPACED, Font.PLAIN, 14)
        );

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void addLineItem() {
        try {
            String productName = productField.getText().trim();

            if (productName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a product name."
                );
                return;
            }

            int quantity = Integer.parseInt(
                    quantityField.getText().trim()
            );

            double price = Double.parseDouble(
                    priceField.getText().trim()
            );

            if (quantity <= 0 || !Double.isFinite(price)
                    || price < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter a positive quantity and a valid "
                                + "non-negative price."
                );
                return;
            }

            Product product = new Product(productName, price);
            LineItem item = new LineItem(product, quantity);

            invoice.addItem(item);
            productField.setText("");
            quantityField.setText("");
            priceField.setText("");

            displayInvoice();

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers for quantity and price."
            );
        }
    }

    private void displayInvoice() {
        invoice.setTitle(titleField.getText());
        invoice.setCustomerAddress(addressArea.getText());
        outputArea.setText(invoice.getFormattedInvoice());
        outputArea.setCaretPosition(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceGUI());
    }
}
