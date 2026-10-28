import java.util.Scanner;
import java.io.*;

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static int[] rooms = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110};
    static String[] guestNames = new String[10];
    static String[] phoneNumbers = new String[10];
    static int[] days = new int[10];
    static double[] roomPrices = {1500, 1500, 2000, 2000, 2500, 2500, 3000, 3000, 3500, 3500};
    static boolean[] booked = new boolean[10];

    static String fileName = "bookings.txt";

    public static void main(String[] args) {

        loadBookings();

        int choice;

        do {
            System.out.println("\n========== HOTEL RESERVATION SYSTEM ==========");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. Check-In");
            System.out.println("6. Check-Out");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelBooking();
                    break;

                case 4:
                    viewBooking();
                    break;

                case 5:
                    checkIn();
                    break;

                case 6:
                    checkOut();
                    break;

                case 7:
                    saveBookings();
                    System.out.println("Thank you for using our system!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);

        sc.close();
    }

    static void viewRooms() {

        System.out.println("\nRoom Number\tRoom Type\tPrice\t\tStatus");

        for (int i = 0; i < rooms.length; i++) {

            String roomType;

            if (roomPrices[i] == 1500) {
                roomType = "Single";
            } else if (roomPrices[i] == 2000) {
                roomType = "Double";
            } else if (roomPrices[i] == 2500) {
                roomType = "Deluxe";
            } else if (roomPrices[i] == 3000) {
                roomType = "Premium";
            } else {
                roomType = "Luxury";
            }

            String status;

            if (booked[i]) {
                status = "Booked";
            } else {
                status = "Available";
            }

            System.out.println(rooms[i] + "\t\t" + roomType + "\t\tRs. " 
                    + roomPrices[i] + "\t" + status);
        }
    }

    static void bookRoom() {

        viewRooms();

        System.out.print("\nEnter room number: ");
        int roomNumber = sc.nextInt();

        int index = findRoom(roomNumber);

        if (index == -1) {
            System.out.println("Room not found.");
            return;
        }

        if (booked[index]) {
            System.out.println("This room is already booked.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter guest name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        System.out.print("Enter number of days: ");
        int numberOfDays = sc.nextInt();

        if (numberOfDays <= 0) {
            System.out.println("Number of days must be greater than zero.");
            return;
        }

        guestNames[index] = name;
        phoneNumbers[index] = phone;
        days[index] = numberOfDays;
        booked[index] = true;

        double totalAmount = roomPrices[index] * numberOfDays;

        System.out.println("\nRoom booked successfully!");
        System.out.println("Room Number: " + rooms[index]);
        System.out.println("Guest Name: " + guestNames[index]);
        System.out.println("Total Amount: Rs. " + totalAmount);

        saveBookings();
    }

    static void cancelBooking() {

        System.out.print("Enter room number to cancel: ");
        int roomNumber = sc.nextInt();

        int index = findRoom(roomNumber);

        if (index == -1) {
            System.out.println("Room not found.");
            return;
        }

        if (!booked[index]) {
            System.out.println("This room is not booked.");
            return;
        }

        booked[index] = false;
        guestNames[index] = "";
        phoneNumbers[index] = "";
        days[index] = 0;

        System.out.println("Booking cancelled successfully.");

        saveBookings();
    }

    static void viewBooking() {

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();

        int index = findRoom(roomNumber);

        if (index == -1) {
            System.out.println("Room not found.");
            return;
        }

        if (!booked[index]) {
            System.out.println("This room is not booked.");
            return;
        }

        System.out.println("\n========== BOOKING DETAILS ==========");
        System.out.println("Room Number: " + rooms[index]);
        System.out.println("Guest Name: " + guestNames[index]);
        System.out.println("Phone Number: " + phoneNumbers[index]);
        System.out.println("Number of Days: " + days[index]);
        System.out.println("Room Price: Rs. " + roomPrices[index]);
        System.out.println("Total Amount: Rs. " + (roomPrices[index] * days[index]));
    }

    static void checkIn() {

        System.out.print("Enter room number for check-in: ");
        int roomNumber = sc.nextInt();

        int index = findRoom(roomNumber);

        if (index == -1) {
            System.out.println("Room not found.");
            return;
        }

        if (!booked[index]) {
            System.out.println("No booking found for this room.");
            return;
        }

        System.out.println("Check-in successful for " + guestNames[index]);
        System.out.println("Room Number: " + rooms[index]);
    }

    static void checkOut() {

        System.out.print("Enter room number for check-out: ");
        int roomNumber = sc.nextInt();

        int index = findRoom(roomNumber);

        if (index == -1) {
            System.out.println("Room not found.");
            return;
        }

        if (!booked[index]) {
            System.out.println("This room is not booked.");
            return;
        }

        double totalAmount = roomPrices[index] * days[index];

        System.out.println("\n========== CHECK-OUT ==========");
        System.out.println("Guest Name: " + guestNames[index]);
        System.out.println("Room Number: " + rooms[index]);
        System.out.println("Total Bill: Rs. " + totalAmount);
        System.out.println("Check-out successful!");

        booked[index] = false;
        guestNames[index] = "";
        phoneNumbers[index] = "";
        days[index] = 0;

        saveBookings();
    }

    static int findRoom(int roomNumber) {

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] == roomNumber) {
                return i;
            }
        }

        return -1;
    }

    static void saveBookings() {

        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < rooms.length; i++) {

                if (booked[i]) {
                    bw.write(rooms[i] + "," + guestNames[i] + "," 
                            + phoneNumbers[i] + "," + days[i]);
                    bw.newLine();
                }
            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    static void loadBookings() {

        try {
            File file = new File(fileName);

            if (!file.exists()) {
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int roomNumber = Integer.parseInt(data[0]);
                int index = findRoom(roomNumber);

                if (index != -1) {
                    booked[index] = true;
                    guestNames[index] = data[1];
                    phoneNumbers[index] = data[2];
                    days[index] = Integer.parseInt(data[3]);
                }
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error loading bookings.");
        }
    }
}