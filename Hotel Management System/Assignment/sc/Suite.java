import java.sql.*;

class Suite extends Room {
    public void RoomMenu() {
        Main m = new Main();
        Room room = new Room();
        Database d = new Database();

        while (true) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
    
                String query = "SELECT * FROM room WHERE Room_Type = 'Suite'";

                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();

                int a = 0;
                System.out.printf("%6s", "_");
                for (int i = 0; i < 98; i++) {
                    System.out.print("_");
                }
                System.out.println();
                System.out.printf("%7s", "|  ");
                for (int i = 0; i < 95; i++) {
                    System.out.print("_");
                }
                System.out.print("  |\n");
                System.out.printf("%7s %98s", "| |", "| |\n");
                System.out.printf("%7s %6s %11s %13s %23s %14s %18s", "| |", "No.", "Room ID", "Room Type", "Price per Night(RM)",
                 "Room Status", "Cleaning Status");
                System.out.printf("%8s", "| |\n");
                System.out.printf("%7s %98s", "| |", "| |\n");
                while (rs.next()) {
                    a++;
                    System.out.printf("%7s %6s %9s %13s %17.2f %21s %15s %11s", "| |", "[" + a + "]", rs.getInt("Room_ID"), 
                    rs.getString("Room_Type"), rs.getDouble("Price_Per_Night"), 
                    rs.getString("Available_Status"), rs.getString("Cleaned_Status"), "| |" + "\n");
                }
                System.out.printf("%7s", "| |");
                for (int i = 0; i < 95; i++) {
                    System.out.print("_");
                }
                System.out.print("| |\n");
                System.out.printf("%5s", "|");
                for (int i = 0; i < 99; i++) {
                    System.out.print("_");
                }
                System.out.print("|");
                connection.close();
                System.out.println("\n\n" + m.YELLOW + "Press (R/r) to return to the previous page." + m.RESET);
                System.out.print("Enter your choice (No.): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print(m.RED);
                    System.out.println("Invalid input! Option cannot be empty.");
                    System.out.print(m.RESET);
                    System.out.print("Please press ENTER to continue...");
                    m.key = m.input.nextLine();
                    continue;
                }
                if (m.userInput.matches("[1234Rr]")) {
                    if (m.userInput.equals("1")) {
                        room.setRoomID(301);
                        if (d.RoomStatusChecking(room) == false) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(m.RED + "The Room " + m.RESET + + room.getRoomID() + m.RED + 
                            " is currently unavailable. Please select another room.");
                            System.out.print("Please press ENTER to continue..." + m.RESET);
                            m.key = m.input.nextLine();
                            continue;
                        }
                        room.RoomConfirming(room);
                    }
                    else if (m.userInput.equals("2")) {
                        room.setRoomID(302);
                        if (d.RoomStatusChecking(room) == false) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(m.RED + "The Room " + m.RESET + + room.getRoomID() + m.RED + 
                            " is currently unavailable. Please select another room.");
                            System.out.print("Please press ENTER to continue..." + m.RESET);
                            m.key = m.input.nextLine();
                            continue;
                        }
                        room.RoomConfirming(room);
                    }
                    else if (m.userInput.equals("3")) {
                        room.setRoomID(303);
                        if (d.RoomStatusChecking(room) == false) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(m.RED + "The Room " + m.RESET + + room.getRoomID() + m.RED + 
                            " is currently unavailable. Please select another room.");
                            System.out.print("Please press ENTER to continue..." + m.RESET);
                            m.key = m.input.nextLine();
                            continue;
                        }
                        room.RoomConfirming(room);
                    }
                    else if (m.userInput.equals("4")) {
                        room.setRoomID(304);
                        if (d.RoomStatusChecking(room) == false) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(m.RED + "The Room " + m.RESET + + room.getRoomID() + m.RED + 
                            " is currently unavailable. Please select another room.");
                            System.out.print("Please press ENTER to continue..." + m.RESET);
                            m.key = m.input.nextLine();
                            continue;
                        }
                        room.RoomConfirming(room);
                    }
                    else {
                        super.RoomMenu();
                    }
                    break;
                }
                else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print(m.RED);
                    System.out.println("Invalid option. Please enter 1, 2, 3, 4 or R/r.");
                    System.out.print(m.RESET);
                    System.out.print("Please press ENTER to continue...");
                    m.key = m.input.nextLine();
                }
            }
            catch (Exception e) {
                System.out.println("Data error");
                e.printStackTrace();
            }
        }
    }
}