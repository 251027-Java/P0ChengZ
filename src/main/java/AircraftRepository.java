import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AircraftRepository {
    private Connection conn;

    public AircraftRepository(Connection conn) {
        this.conn = conn;
    }

    public void addAircraft(String model) throws SQLException {

    }

    public List<String> getAllAircraft() throws SQLException {

    }
}

