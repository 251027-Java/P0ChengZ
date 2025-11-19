import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AeroService {
    private PilotRepository pilotRepo;
    private AircraftRepository aircraftRepo;

    public AeroService(PilotRepository pilotRepo, AircraftRepository aircraftRepo) {
        this.pilotRepo = pilotRepo;
        this.aircraftRepo = aircraftRepo;
    }

    public void addPilot(Scanner sc) {
        try {
            System.out.println("Enter pilot name: ");
            String name = sc.nextLine();
            pilotRepo.addPilot(name);
            System.out.println("Pilot added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding pilot: " + e.getMessage());
        }
    }

    public void addAircraft(Scanner sc) {
        try {
            System.out.println("Enter aircraft model: ");
            String model = sc.nextLine();
            aircraftRepo.addAircraft(model);
            System.out.println("Aircraft added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding aircraft: " + e.getMessage());
        }
    }

    public void assignPilotToAircraft(Scanner sc) {
        try {
            System.out.println("Enter Pilot ID: ");
            int pid = Integer.parseInt(sc.nextLine());
            System.out.println("Enter Aircraft ID: ");
            int aid = Integer.parseInt(sc.nextLine());
            pilotRepo.assignAircraft(pid, aid);
            System.out.println("Pilot assigned successfully!");
        } catch (Exception e) {
            System.out.println("Error assigning pilot: " + e.getMessage());
        }
    }

    public void viewPilots() {
        try {
            List<String> pilots = pilotRepo.getAllPilots();
            System.out.println("\n---Pilots---");
            pilots.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error viewing pilots: " + e.getMessage());
        }
    }

    public void viewAircraft() {
        try {
            List<String> pilots = aircraftRepo.getAllAircraft();
            System.out.println("\n---Aircraft---");
            pilots.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error viewing aircrafts: " + e.getMessage());
        }
    }

    public void viewPilotAircraftAssignments() {
        System.out.println("\n---PILOT -> AIRCRAFT ASSIGNMENTS ---");
        try {
            List<String> assignments = pilotRepo.getPilotAircraftAssignments();
            if (assignments.isEmpty()) {
                System.out.println("No assignment found.");
            } else {
                assignments.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing assignments: " + e.getMessage());
        }
    }


}
