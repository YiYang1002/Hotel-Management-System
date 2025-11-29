import java.sql.*;

public class Room {
    private int RoomID;
    private String R_Type;
    private double R_Price;

    public void setRoomID(int RoomID) {
        this.RoomID = RoomID;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setR_Type(String R_Type) {
        this.R_Type = R_Type;
    }

    public String getR_Type() {
        return R_Type;
    }

    public void setR_Price(double R_Price) {
        this.R_Price = R_Price;
    }

    public double getR_Price() {
        return R_Price;
    }

    public Room() {
        this.RoomID = 0;
        this.R_Type = null;
        this.R_Price = 0.0;
    }

    public Room(int RoomID, String R_Type, double R_Price) {
        this.RoomID = RoomID;
        this.R_Type = R_Type;
        this.R_Price = R_Price;
    }
    
    public void RoomMenu() {
        Main m = new Main();
        Guest g = new Guest();
        Room Single = new SingleRoom();
        Room Double = new DoubleRoom();
        Room Suite = new Suite();

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.println();
            System.out.print(m.GREEN);
            System.out.printf("%63s", "-Select the room types-\n");
            System.out.print(m.RESET);
            System.out.print(" ");
            for (int i = 0; i < 107; i++) {
                System.out.print("_");
            }
            System.out.print(" ");
            System.out.println();
            System.out.printf("%1s %108s", "|", "|\n");
            System.out.printf("%1s %19s %88s", "|", "1. Single Room", "|\n");
            System.out.printf("%1s %108s %8s", "|", m.BLUE + "A small room with one bed for a single guest, including basic amenities like a desk, TV, and a" + m.RESET, "|\n");
            System.out.printf("%1s %31s %85s", "|", m.BLUE + "private bathroom." + m.RESET, "|\n");
            System.out.printf("%1s %108s", "|", "|\n");
            System.out.printf("%1s %19s %88s", "|","2. Double Room", "|\n");
            System.out.printf("%1s %105s %11s", "|", m.BLUE + "A room with one large bed or two twin beds for two guests, offering more space and standard" + m.RESET, "|\n");
            System.out.printf("%1s %24s %92s", "|", m.BLUE + "amenities." + m.RESET, "|\n");
            System.out.printf("%1s %108s", "|", "|\n");
            System.out.printf("%1s %13s %94s", "|", "3. Suite", "|\n");
            System.out.printf("%1s %106s %10s", "|", m.BLUE + "A spacious room with a separate living area and bedroom, offering extra comfort, luxury, and" + m.RESET, "|\n");
            System.out.printf("%1s %61s %55s", "|", m.BLUE + "additional features like a sofa or kitchenette." + m.RESET, "|\n");
            System.out.printf("%1s %108s", "|", "|\n");
            System.out.printf("%1s %31s %76s", "|", "4. Return to Previous Page", "|\n");
            System.out.print("|");
            for (int i = 0; i < 107; i++) {
                System.out.print("_");
            }
            System.out.print("|");
            System.out.print("\n\nEnter your choice (1, 2, 3 or 4): ");
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
            if (m.userInput.matches("[1234]")) {
                int chooseRoom = Integer.parseInt(m.userInput);
                if (chooseRoom == 1) {
                    Single.RoomMenu();
                }
                else if (chooseRoom == 2) {
                    Double.RoomMenu();
                }
                else if (chooseRoom == 3){
                    Suite.RoomMenu();
                }
                else {
                    g.GuestMenu();                
                }
                break;
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print(m.RED);
                System.out.println("Invalid option. Please enter 1, 2, 3 or 4.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
            }
        }
        m.input.close();
    }

    public void RoomConfirming(Room room) {
        Main m = new Main();
        Guest g = new Guest();
        Database d = new Database();

        while (true) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM room WHERE Room_ID = ?")) {

                    preparedStatement.setInt(1, getRoomID());
                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        if (rs.next()) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            m.Logo();
                            for (int i = 0; i < 109; i++) {
                                System.out.print("=");
                            }
                            System.out.println();
                            System.out.printf("%89s", m.GREEN + "-Please confirm the room information you selected-" + m.RESET);
                            System.out.printf("%7s %11s", "\nRoom ID", ": ");
                            int R_ID = rs.getInt("Room_ID");
                            System.out.println(m.YELLOW + R_ID + m.RESET);
                            System.out.printf("%9s %9s", "Room Type", ": ");
                            R_Type = rs.getString("Room_Type");
                            System.out.println(R_Type);
                            System.out.printf("%15s %6s", "Price Per Night", ": RM ");
                            R_Price = rs.getDouble("Price_Per_Night");
                            System.out.println(R_Price);
                            System.out.printf("%16s %2s", "Available Status", ": ");
                            String R_AStatus = rs.getString("Available_Status");
                            System.out.println(R_AStatus);
                            System.out.printf("%14s %4s", "Cleaned Status", ": ");
                            String R_CStatus = rs.getString("Cleaned_Status");
                            System.out.println(R_CStatus + "\n");
                            System.out.printf("%25s %8s %29s","1. Confirmed and continue", "|", "2. Return to room menu");
                            System.out.print("\n\nEnter your choice (1, 2): ");
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
                            if (m.userInput.matches("[12]")) {
                                if (m.userInput.equals("1")) {
                                    g.RoomBooking(room);
                                }
                                else {
                                    RoomMenu();
                                }
                                break;
                            }
                            else {
                                System.out.print("\033[H\033[2J");
                                System.out.flush();
                                System.out.print(m.RED);
                                System.out.println("Invalid option. Please enter 1 or 2.");
                                System.out.print(m.RESET);
                                System.out.print("Please press ENTER to continue...");
                                m.key = m.input.nextLine();
                            }
                        }
                        connection.close();
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Data error");
                e.printStackTrace();
                break;
            }
        }
    }
}
