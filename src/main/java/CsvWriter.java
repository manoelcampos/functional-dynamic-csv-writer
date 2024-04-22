import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Creates a CSV file from a list of {@link Product}.
 * It uses functional programming to define the columns to be shown in the CSV file.
 * This way, we can decide which columns to be shown
 * (based on the attributes of a Product, accessed through a getter {@link Function})
 * and even compute new columns based on the existing ones.
 *
 * @author Manoel Campos
 */
public class CsvWriter {
    private static final String SEPARATOR = ",";

    /**
     * A map where each key is a column name and its value
     * is a {@link Function} that extract the value from a {@link Product} object
     * to show as the column data.
     * This Function is usually a getter from the Product class.
     */
    private Map<String, Function<Product, Object>> columnMapper = new LinkedHashMap<>();

    /**
     * {@return the header of the CSV file}
     * It contains the name of the columns separated by {@link #SEPARATOR}.
     */
    private String header(){
        return String.join(SEPARATOR, columnMapper.keySet()) + System.lineSeparator();
    }

    public String write(final List<Product> products){
        final var csv = new StringBuilder(header());
        for (var product : products) {
            for (var entry : columnMapper.entrySet()) {
                final Function<Product, Object> columnFunction = entry.getValue();

                // Calls the Product function to get the value to be shown in the column
                final Object columnValue = columnFunction.apply(product);
                csv.append(columnValue).append(SEPARATOR);
            }
            csv.append(System.lineSeparator());
        }

        return csv.toString();
    }

    public CsvWriter addColumn(String columnName, Function<Product, Object> columnFunction){
        columnMapper.put(columnName, columnFunction);
        return this;
    }
}
