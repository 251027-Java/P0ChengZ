import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AircraftRepository {
    private Connection conn;

    public AircraftRepository(Connection conn) {
        this.conn = conn;
    }

    public void addAircraft(String model) throws SQLException {
        String sql = "INSERT INTO Aircraft (model) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, model);
            ps.executeUpdate();
        }
    }

    public List<String> getAllAircraft() throws SQLException {
        List<String> aircraft = new ArrayList<>();
        String sql = "SELECT * FROM Aircraft";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                aircraft.add(rs.getInt("id")+": "+rs.getString("model"));
            }
        }
        return aircraft;
    }
}

