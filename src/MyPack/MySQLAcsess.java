package MyPack;

import java.io.*;
import java.sql.*;

/**
 * Created by taras on 27.02.2016.
 */
public class MySQLAcsess implements Serializable{
    private Connection myConnnection = null;
    private Statement myStatement = null;
    private PreparedStatement myPreparedStatement = null;
    private ResultSet myResultSet = null;

    public void runQueries() throws Exception {
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.jdbc.Driver");
            // Connection
            myConnnection = DriverManager
                    .getConnection("jdbc:mysql://localhost/classicmodels?" + "user=user1&password=pass1");

            // Statement for queries
            myStatement = myConnnection.createStatement();

            // Result set - the result of the SQL query
            // executeQuery - for DQL
            myResultSet = myStatement.executeQuery("SELECT customerNumber, paymentDate, amount FROM payments Limit 5");
            printResults(myResultSet);
            System.out.println("-------------------------------");

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    private void printMetaData(ResultSet resultSet) throws SQLException {
        // Metadata from the database
        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " "
                    + resultSet.getMetaData().getColumnName(i));
        }
    }

    private String[] getColumnNames(ResultSet resultSet) throws SQLException {
        // Metadata from the database
        String[] columns = new String[resultSet.getMetaData().getColumnCount()];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = resultSet.getMetaData().getColumnName(i + 1);
        }
        return columns;
    }

    private String[] getColumnTypes(ResultSet resultSet) throws SQLException {
        // Metadata from the database
        String[] types = new String[resultSet.getMetaData().getColumnCount()];

        for (int i = 0; i < types.length; i++) {
            types[i] = resultSet.getMetaData().getColumnTypeName(i + 1);
        }
        return types;
    }

    private String getData(ResultSet resultSet, String columnName,
                           String columnType) {
        String res;
        try {
            switch (columnType) {
                case "INT":
                case "BIGINT":
                    res = "" + resultSet.getInt(columnName);
                    break;
                case "DATE":
                    res = "" + resultSet.getDate(columnName);
                    break;
                case "CHAR":
                case "VARCHAR":
                    res = "" + resultSet.getString(columnName);
                    break;
                case "FLOAT":
                    res = "" + resultSet.getFloat(columnName);
                    break;
                case "DOUBLE":
                    res = "" + resultSet.getDouble(columnName);
                    break;
                case "BOOLEAN":
                    res = "" + resultSet.getBoolean(columnName);
                    break;
                default:
                    res = "";
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void printResults(ResultSet resultSet) throws SQLException {
        int a = 5;
        while (resultSet.next()) {
            int CustoMnumber = resultSet.getInt("customerNumber");
            Date PaymenTdate = resultSet.getDate("paymentDate");
            float Amount = resultSet.getFloat("amount");

            Payments Payments = new Payments(PaymenTdate,CustoMnumber, Amount);
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(a++ + ".dat"))) {
                    out.writeObject(Payments);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



        }
    }



    // closing
    private void close() {
        try {
            if (myResultSet != null) {
                myResultSet.close();
            }
            if (myStatement != null) {
                myStatement.close();
            }
            if (myPreparedStatement != null) {
                myPreparedStatement.close();
            }
            if (myConnnection != null) {
                myConnnection.close();
            }
        } catch (Exception e) {
        }
    }
}


