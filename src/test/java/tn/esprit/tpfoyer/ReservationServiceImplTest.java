package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository; // Mocking the repository

    @InjectMocks
    private ReservationServiceImpl reservationService; // The service to be tested

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
    }

    @Test
    void testRetrieveAllReservations() {
        // Arrange
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation()); // Adding a mock reservation
        when(reservationRepository.findAll()).thenReturn(reservations); // Mocking the behavior

        // Act
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Assert
        assertEquals(1, result.size()); // Check that one reservation is returned
        verify(reservationRepository, times(1)).findAll(); // Verify the repository method was called
    }

    @Test
    void testRetrieveReservation() {
        // Arrange
        Reservation mockReservation = new Reservation();
        when(reservationRepository.findById("1")).thenReturn(Optional.of(mockReservation));

        // Act
        Reservation result = reservationService.retrieveReservation("1");

        // Assert
        assertNotNull(result); // Check that the result is not null
        assertEquals(mockReservation, result); // Check that the returned reservation is the same as the mock
        verify(reservationRepository, times(1)).findById("1"); // Verify that the repository method was called
    }

    @Test
    void testAddReservation() {
        // Arrange
        Reservation newReservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(newReservation);

        // Act
        Reservation result = reservationService.addReservation(newReservation);

        // Assert
        assertEquals(newReservation, result); // Check that the result is the same as the new reservation
        verify(reservationRepository, times(1)).save(newReservation); // Verify the save method was called
    }

    @Test
    void testModifyReservation() {
        // Arrange
        Reservation modifiedReservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(modifiedReservation);

        // Act
        Reservation result = reservationService.modifyReservation(modifiedReservation);

        // Assert
        assertEquals(modifiedReservation, result); // Check that the result is the same as the modified reservation
        verify(reservationRepository, times(1)).save(modifiedReservation); // Verify the save method was called
    }

    @Test
    void testTrouverResSelonDateEtStatus() {
        // Arrange
        Date date = new Date();
        List<Reservation> reservations = new ArrayList<>();
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, true)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(date, true);

        // Assert
        assertEquals(reservations, result); // Check that the result matches the mocked list
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, true); // Verify the method call
    }

    @Test
    void testRemoveReservation() {
        // Act
        reservationService.removeReservation("1");

        // Assert
        verify(reservationRepository, times(1)).deleteById("1"); // Verify that the delete method was called
    }
}
