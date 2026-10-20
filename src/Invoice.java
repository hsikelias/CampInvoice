import java.util.ArrayList;

public class Invoice {
    private String title;
    private String customerAddress;
    private ArrayList<LineItem> items;

    public Invoice(String title, String customerAddress) {
        this.title = title;
        this.customerAddress = customerAddress;
        this.items = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void addItem(LineItem item) {
        items.add(item);
    }

    public double getTotalAmount() {
        double total = 0.0;

        for (LineItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public String getFormattedInvoice() {
        StringBuilder output = new StringBuilder();

        output.append(title).append("\n\n");
        output.append(customerAddress).append("\n\n");

        output.append("----------------------------------------------\n");
        output.append(String.format(
                "%-17s %5s %9s %10s%n",
                "Item", "Qty", "Price", "Total"
        ));
        output.append("----------------------------------------------\n");

        for (LineItem item : items) {
            output.append(String.format(
                    "%-17.17s %5d $%8.2f $%9.2f%n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getUnitPrice(),
                    item.getTotal()
            ));
        }

        output.append("----------------------------------------------\n");
        output.append(String.format(
                "AMOUNT DUE: $%.2f%n",
                getTotalAmount()
        ));

        return output.toString();
    }
}
