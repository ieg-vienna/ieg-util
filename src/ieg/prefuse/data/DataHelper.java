package ieg.prefuse.data;

import java.io.PrintStream;
import java.util.Iterator;

import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;

public class DataHelper {
    
    /**
     * build a debug string on meta data of a table column
     * @param t the table to debug
     * @param col the column number of the data field to retrieve
     * @return a debug string
     */
    public static String debugColumnMeta(Table t, int col) {
        return t.getColumnName(col)
                + " type="
                + t.getColumnType(col).getName()
                + " min="
                + t.getString(
                        t.getMetadata(t.getColumnName(col)).getMinimumRow(), col)
                + " max="
                + t.getString(
                        t.getMetadata(t.getColumnName(col)).getMaximumRow(), col);
    }
    
    /**
     * build a debug string on values of a column
     * @param t the table to debug
     * @param col the column number of the data field to retrieve
     * @return a debug string
     */
    public static String debugColumnValues(Table t, int col) {
        StringBuilder sb = new StringBuilder(t.getString(0, col));
        for (int i=1; i < t.getRowCount(); i++) {
            sb.append(", ");
            sb.append(t.getString(i, col));
        }
        return sb.toString();
    }
    
    /**
     * Helper method to dump specific columns of a table or other tuple set to some output stream
     * @param out the output to use
     * @param table the Table to print
     * @param cols the name of the columns
     */
    @SuppressWarnings("unchecked")
    public static void printTable(PrintStream out, TupleSet table, String... cols) {
        
        for (String c : cols) 
            out.printf(" %16s", c + " ");
            
        out.println();

        Iterator<Tuple> i = table.tuples();
        while (i.hasNext()) {
            Tuple tuple = i.next();
            
            for (String c : cols) 
                if (tuple.canGetString(c))
                    out.printf(" %16s", tuple.getString(c) + " ");

            out.println();
        }
    }

    /**
     * Helper method to dump a table to some output stream
     * @param out the output to use
     * @param table the Table to print
     */
    public static void printTable(PrintStream out, Table table) {
        String[] cols = new String[table.getColumnCount()];
        for (int i=0; i< cols.length; i++) 
            cols[i] = table.getColumnName(i);
        
        printTable(out, table, cols);
    }
}
