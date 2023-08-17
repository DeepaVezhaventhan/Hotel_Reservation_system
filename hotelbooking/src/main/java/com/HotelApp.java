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
        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        GenericDAO<Room, Long> roomDAO = new RoomDAOImpl(sessionFactory);
        GenericDAO<Customer, Long> customerDAO = new CustomerDAOImpl(sessionFactory);
        ReservationDAOImpl reservationDAO = new ReservationDAOImpl(sessionFactory);

        boolean continueLoop = true;

        while (continueLoop) {
        	System.out.println("-------------------------------------");
            System.out.println("Welcome to Hotel Reservation System!");
            System.out.println("1. Make a reservation");
            System.out.println("2. View reservation details");
            System.out.println("3. Exit");
            System.out.println("-------------------------------------");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
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
                        if (Integer.parseInt(room.getRoomNumber()) == roomNumber) {
                            selectedRoom = room;
                            break;
                        }
                    }
                    if (selectedRoom == null) {
                        System.out.println("Invalid room number. Exiting reservation creation...");
                        continue;
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

                    // confirmation reservation
                    System.out.println("\n!!!Thank you for the reservation in our Hotel!!!");
                    System.out.println("----------------------------------------");
                    break;
                case 2:
                    // View reservation details code
                    System.out.print("Enter customer's last name: ");
                    String customerLastName = scanner.nextLine();

                    List<Reservation> reservations = reservationDAO.getReservationsByCustomerLastName(customerLastName);

                    if (reservations.isEmpty()) {
                        System.out.println("Sorry there is no reservations found for the customer.");
                    } else {
                        System.out.println("Reservation Details for " + customerLastName + ":");
                        for (Reservation reservation1 : reservations) {
                        	System.out.println("-------------------------------");
                            System.out.println("Reservation ID: " + reservation1.getId());
                            System.out.println("Room: " + reservation1.getRoom().getRoomNumber());
                            System.out.println("Check-in Date: " + reservation1.getCheckInDate());
                            System.out.println("Check-out Date: " + reservation1.getCheckOutDate());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting from Hotel Reservation System!...");
                    System.out.println("-------------------------------");
                    continueLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }

        sessionFactory.close();
    }
}






























