package datasource;

public interface DatabaseConnection {

    void initializePreparedStatement();

    void closePreparedStatement();

}
