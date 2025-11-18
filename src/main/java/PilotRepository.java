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
        String sql = "insert into pilot (name) values (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    // Get all pilots
    public List<String> getAllPilots() throws SQLException {
        List<String> pilots = new ArrayList<>();
        String sql = "select * from pilot";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pilots.add(rs.getInt("id")+": "+rs.getString("name"));
            }
        }
        return pilots;
    }

    // Assign pilot to aircraft
    public void assignAircraft(int pilotId, int aircraftId) throws SQLException {
        String check = "SELECT COUNT(*) FROM pilot_aircraft WHERE pilot_id = ? AND aircraft_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setInt(1, pilotId);
            ps.setInt(2, aircraftId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                String insert = "INSERT INTO pilot_aircraft (pilot_id, aircraft_id) VALUES (?, ?)";
                try (PreparedStatement ps2 = conn.prepareStatement(insert)) {
                    ps2.setInt(1, pilotId);
                    ps2.setInt(2, aircraftId);
                    ps2.executeUpdate();
                }
            }
        }
    }

    // Utility: get pilot ID by name (useful in tests)
    public int getPilotIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM pilot WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }return -1;
    }

    // Get Pilot with Aircraft assignments
    public List<String> getPilotAircraftAssignments() throws SQLException {
        List<String> results = new ArrayList<>();

        String sql = """
        SELECT p.id AS pilot_id, p.name AS pilot_name,
               a.id AS aircraft_id, a.model AS aircraft_model
        FROM pilot_aircraft pa
        JOIN pilot p ON pa.pilot_id = p.id
        JOIN aircraft a ON pa.aircraft_id = a.id
        ORDER BY p.id, a.id
    """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int pid = rs.getInt("pilot_id");
                String pname = rs.getString("pilot_name");
                int aid = rs.getInt("aircraft_id");
                String model = rs.getString("aircraft_model");

                results.add(
                        "Pilot " + pid + " (" + pname + ") -> Aircraft " + aid + " (" + model + ")"
                );
            }
        }
        return results;
    }
}
