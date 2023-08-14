package com;

import HotelDAO.GenericDAO;
import HotelDAO.RoomDAOImpl;
import HotelDAO.CustomerDAOImpl;
import HotelDAO.ReservationDAOImpl;
import Model.Room;
import Model.Customer;
import Model.Reservation;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HotelApp {
    public static void main(String[] args) {
        // Create a Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Create DAO instances
        GenericDAO<Room, Long> roomDAO = new RoomDAOImpl(sessionFactory);
        GenericDAO<Customer, Long> customerDAO = new CustomerDAOImpl(sessionFactory);
        GenericDAO<Reservation, Long> reservationDAO = new ReservationDAOImpl(sessionFactory);

        Scanner scanner = new Scanner(System.in);

        // Display available rooms
        List<Room> availableRooms = roomDAO.getAll();
        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.println(room.getRoomNumber() + ". " + room.getType() + " - $" + room.getPrice());
        }

        // Ask user to choose a room
        System.out.print("Choose a room by entering its room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        Room selectedRoom = null;
        for (Room room : availableRooms) {
            // Convert the String room number to int for comparison
            if (Integer.parseInt(room.getRoomNumber()) == roomNumber) {
                selectedRoom = room;
                break;
            }
        }
        if (selectedRoom == null) {
            System.out.println("Invalid room number. Exiting...");
            sessionFactory.close();
            return;
        }

        // Gather customer details
        System.out.print("Enter customer's first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter customer's last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter customer's email: ");
        String email = scanner.nextLine();

        System.out.print("Enter customer's phone number: ");
        String phone = scanner.nextLine();

        // Create and save the customer
        Customer customer = new Customer(firstName, lastName, email, phone);
        customerDAO.save(customer);

        // Gather reservation details
        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDateStr = scanner.nextLine();
        LocalDate checkInDate = LocalDate.parse(checkInDateStr);

        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDateStr = scanner.nextLine();
        LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);

        // Create and save the reservation
        Reservation reservation = new Reservation(customer, selectedRoom, checkInDate, checkOutDate);
        reservationDAO.save(reservation);

        // Display reservation information
        System.out.println("\nReservation Details:");
        System.out.println("Room: " + selectedRoom.getRoomNumber() + " - " + selectedRoom.getType());
        System.out.println("Check-in Date: " + checkInDate);
        System.out.println("Check-out Date: " + checkOutDate);
        System.out.println("Customer: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("\n Thank you for the reservation in our Hotel!!!");

        // Close the Hibernate SessionFactory
        sessionFactory.close();
    }
}
