import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class AeroServiceTest {
    private PilotRepository mockPilotRepo;
    private AircraftRepository mockAircraftRepo;
    private AeroService service;

    @BeforeEach
    void setup() {
        mockPilotRepo = mock(PilotRepository.class);
        mockAircraftRepo = mock(AircraftRepository.class);
        service = new AeroService(mockPilotRepo, mockAircraftRepo);
    }

    // 1. Test addPilot
    @Test
    void testAddPilot_Happy() throws SQLException {
        Scanner sc = new Scanner("Joe\n");
        service.addPilot(sc);
        verify(mockPilotRepo).addPilot("Joe");
    }

    // 2. Negative: addAircraft SQLException
    @Test
    void testAddAircraft_Exception() throws SQLException {
        doThrow(new SQLException("DB error")).when(mockAircraftRepo).addAircraft(anyString());
        Scanner sc = new Scanner("Airbus\n");
        service.addAircraft(sc);
        verify(mockAircraftRepo).addAircraft("Airbus");
    }


    // 3. Happy: assignPilotToAircraft
    @Test
    void testAssignPilotToAircraft_Happy() throws SQLException {
        Scanner sc = new Scanner("1\n2\n"); // pilotId =1, aircraftId=2
        service.assignPilotToAircraft(sc);
        verify(mockPilotRepo).assignAircraft(1, 2);
    }

    // 5. Negative: assignPilotToAircraft/ TDD
    @Test
    void testAssignPilotToAircraft_InvalidInput() throws SQLException {
        Scanner sc = new Scanner("a\n2\n"); // pilotId =1, aircraftId=2
        service.assignPilotToAircraft(sc);
        verify(mockPilotRepo, never()).assignAircraft(anyInt(), anyInt());
    }

    // 6. Negative: assignPilotToAircraft SQLException
    @Test
    void testAssignPilotToAircraft_Exception() throws SQLException {
        doThrow(new SQLException("DB error")).when(mockPilotRepo).assignAircraft(anyInt(),anyInt());
        Scanner sc = new Scanner("1\n2\n"); // pilotId =1, aircraftId=2
        service.assignPilotToAircraft(sc);
        verify(mockPilotRepo).assignAircraft(anyInt(), anyInt());
    }

    // 7. Edge case: viewPilot empty list
    @Test
    void testViewPilots_EmptyList() throws SQLException {
        when(mockPilotRepo.getAllPilots()).thenReturn(Collections.emptyList());
        service.viewPilots();
        verify(mockPilotRepo).getAllPilots();
    }

    // 8. Edge case: viewAircraft empty list
    @Test
    void testViewAircraft_EmptyList() throws SQLException {
        when(mockAircraftRepo.getAllAircraft()).thenReturn(Collections.emptyList());
        service.viewAircraft();
        verify(mockAircraftRepo).getAllAircraft();
    }

    // 9. Happy: viewPilots
    @Test
    void testViewPilots_Happy() throws SQLException {
        when(mockPilotRepo.getAllPilots()).thenReturn(Arrays.asList("1: Joe", "2: Mary"));
        service.viewPilots();
        verify(mockPilotRepo).getAllPilots();
    }

    // 10. Negative: viewAircraft SQLException
    @Test
    void testViewAircraft_Exception() throws SQLException {
        when(mockAircraftRepo.getAllAircraft()).thenThrow(new SQLException("DB error"));
        service.viewAircraft();
        verify(mockAircraftRepo).getAllAircraft();
    }

}