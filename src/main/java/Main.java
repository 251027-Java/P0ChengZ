import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:h2:mem:aerofleet;DB_CLOSE_DELAY=-1";
        String user = "";
        String password = "";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            new DatabaseManager(conn);
            PilotRepository pilotRepo = new PilotRepository(conn);
            AircraftRepository aircraftRepo =  new AircraftRepository(conn);
            AeroService service = new AeroService(pilotRepo, aircraftRepo);

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("\n--- AERO FLEET MANAGEMENT ---");
                System.out.println("1. Add Pilot");
                System.out.println("2. Add Aircraft");
                System.out.println("3. Assign Pilot to Aircraft");
                System.out.println("4. View Pilots");
                System.out.println("5. View Aircraft");
                System.out.println("6. View Pilot-Aircraft Assignments");
                System.out.println("0. Exit");
                System.out.println("Choose option: ");
                String input = sc.nextLine();

                switch (input) {
                    case "1" -> service.addPilot(sc);
                    case "2" -> service.addAircraft(sc);
                    case "3" -> service.assignPilotToAircraft(sc);
                    case "4" -> service.viewPilots();
                    case "5" -> service.viewAircraft();
                    case "6" -> service.viewPilotAircraftAssignments();
                    case "0" -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error Starting app: " + e.getMessage());
        }
    }
}
