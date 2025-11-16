import java.sql.*;

public class DatabaseManager {
    public DatabaseManager(Connection conn) throws SQLException {
        createTables(conn);
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Pilot (
                    id IDENTITY PRIMARY KEY,
                    name VARCHAR(100) NOT NULL
                );
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Aircraft (
                    id IDENTITY PRIMARY KEY,
                    model VARCHAR(100) NOT NULL
                );
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Pilot_Aircraft (
                    pilot_id INT,
                    aircraft_id INT,
                    PRIMARY KEY (pilot_id, aircraft_id),
                    FOREIGN KEY (pilot_id) REFERENCES Pilot(id),
                    FOREIGN KEY (aircraft_id) REFERENCES Aircraft(id)
                );
            """);
        }
    }
}

