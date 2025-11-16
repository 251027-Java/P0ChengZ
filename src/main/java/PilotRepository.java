import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotRepository {

    private final Connection conn;

    public PilotRepository(Connection conn) {
        this.conn = conn;
    }

    // Add a pilot
    public void addPilot(String name) throws SQLException {

    }

    // Get all pilots
    public List<String> getAllPilots() throws SQLException {

    }

    // Assign pilot to aircraft
    public void assignAircraft(int pilotId, int aircraftId) throws SQLException {

    }

    // Utility: get pilot ID by name (useful in tests)
    public int getPilotIdByName(String name) throws SQLException {

    }
}
