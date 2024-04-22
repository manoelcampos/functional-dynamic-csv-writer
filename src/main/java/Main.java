import java.util.List;

/**
 * @author Manoel Campos
 */
public class Main {
    public static void main(String[] args) {
        final var products = List.of(
            new Product(1, "Product 1", 10.0),
            new Product(2, "Product 2", 20.0),
            new Product(3, "Product 3", 30.0)
        );

        final var csvWriter = new CsvWriter<Product>()
                .addColumn("Name", Product::getName)
                .addColumn("Price", Product::getPrice);

        /* Defines a column that is computed based on the product price.
         * In this case, it creates a lambda expression (anonymous function)
         * to compute that.*/
        csvWriter.addColumn("Price with discount", product -> product.getPrice() * 0.9);

        /*Creates a column to indicate the number of orders of a product.
         * The Function that gets the value for such a column will be generated
         * randomly, to simulate a computationally complex operation.
         * It is also created as a new method to simulte a more complex
         * operation with multiple lines. */
        csvWriter.addColumn("Orders", product -> getOrders(product));

        System.out.println(csvWriter.write(products));
    }

    /**
     * Simulates a computationally complex operation to get the number of orders of a product.
     * @param product
     * @return
     */
    private static long getOrders(Product product) {
        return Math.round(product.getId() * Math.random() * 100);
    }
}
