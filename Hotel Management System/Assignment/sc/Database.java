import java.util.Random;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Database {
  public String url = "jdbc:mysql://localhost:3306/hotel";
  public String username = "root";
  public String password = "";

  private Employee employee;
  private int GuestID;

  public Employee getEmployee() {
    return this.employee;
  }

  public void setEmployee (Employee employee) {
    this.employee = employee;
  }

  public int getGuestID() {
    return this.GuestID;
  }

  public void setGuestID(int GuestID) {
    this.GuestID = GuestID;
  }
  
  public static boolean EmployeeIDchecking(int EID) {
    Main m = new Main();
    Database d = new Database();

    int EmployeeId;
    String EmployeeName;
    String EmployeeRole;
    Double EmployeeSalary;
    
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
                
      // Use PreparedStatement to prevent SQL injection
      String query = "SELECT * FROM employees WHERE Employee_ID = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, EID);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) { // ID found, now display data
        System.out.print("\033[H\033[2J");
        System.out.flush();
        m.Logo();
        for (int i = 0; i < 109; i++) {
          System.out.print("=");
        }
        System.out.println();
        System.out.print("Employee_ID " + m.YELLOW);
        EmployeeId = resultSet.getInt("Employee_ID");
        System.out.print(EmployeeId);
        System.out.print(m.RESET);
        System.out.println();

        EmployeeName = resultSet.getString("Name");
        System.out.print("Name  : " + EmployeeName);
        int lengthName = Math.max(EmployeeName.length(), 36); // Math.max = Compare the numbers in () and select the larger one
        int paddingName = lengthName - EmployeeName.length();
        String spaceName = " ".repeat(paddingName); // " ".repeat() = Dynamically insert spaces
        System.out.print(spaceName + m.GREEN_B + "_______" + m.RESET + " Working Time\n");

        EmployeeRole = resultSet.getString("Role");
        System.out.print("Role  : " + EmployeeRole);
        int lengthRole = Math.max(EmployeeRole.length(), 36);
        int paddingRole = lengthRole - EmployeeRole.length();
        String spaceRole = " ".repeat(paddingRole);
        System.out.print(spaceRole + m.CYAN_B + "_______" + m.RESET + " Break Time\n");

        EmployeeSalary = resultSet.getDouble("Salary");
        String Em_Salary = Double.toString(EmployeeSalary); // Convert double to string, because double cannot use .length() function
        System.out.printf("Salary: RM%.2f ", EmployeeSalary);
        int lengthSalary = Math.max(Em_Salary.length(), 32); 
        int paddingSalary = lengthSalary - Em_Salary.length(); 
        String spaceSalary = " ".repeat(paddingSalary); 
        System.out.print(spaceSalary + m.WHITE_B + "_______" + m.RESET + " None\n");

        System.out.println("\nWork Schedule");
        connection.close();
        switch (EmployeeId) {
          case 1002:
              YYTimetable();
              break;
          case 3318:
              ZWTimetable();
              break;
          case 8166:
              ZXTimetable();
              break;
          case 1103:
              JoeyTimetable();
              break;
          default:
              TraineeTimetable();
              break;
        }
        return true;
      }
      else {
          System.out.print("\033[H\033[2J");
          System.out.flush();
          System.out.print(m.RED);
          System.out.println("Invalid input. Employee ID was not found.");
          System.out.print(m.RESET);
          System.out.print("Please press ENTER to continue...");
          m.key = m.input.nextLine();
          return false;
      }
    }
    catch (Exception e) {
      System.out.println("Data error");
    }
    return false;
  }

  public void NewEmployeeStore() {
    Main m = new Main();
    Random rand = new Random();

    int NewID;
    boolean EmIDchecking;
    Connection conn = null;
    String role = "Trainee";
    Double salary = 2500.00;

    System.out.print("\033[H\033[2J");
    System.out.flush();
    m.Logo();
    for (int i = 0; i < 109; i++) {
      System.out.print("=");
    }
    System.out.println();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(url, username, password);

      do {
        NewID = 1000 + rand.nextInt(9000); // Ensures a 4 digit number (1000-9999)

        // Check if the ID exists in the database
        PreparedStatement checkStmt = conn.prepareStatement("SELECT 1 FROM employees WHERE Employee_ID = ?");
        checkStmt.setInt(1, NewID);
        ResultSet rs = checkStmt.executeQuery();
        EmIDchecking = rs.next(); // If there's a result, the ID exists

        rs.close();
        checkStmt.close();
      }
      while (EmIDchecking); // Keep generating ID until find one that doesn't exist in the database

      System.out.printf("%86s", "-Your Employee ID is " + m.YELLOW + NewID + m.RESET + ". Please remember it-");
      System.out.println("\n");
      System.out.printf("%20s %10s", "Name", ": ");
      System.out.println(getEmployee().getName());
      System.out.printf("%20s %10s", "Role", ": ");
      System.out.println(role);
      System.out.printf("%22s %10s", "Salary", ": RM");
      System.out.println(salary + "0\n");
      
      // Insert the new employee with the unique ID
      String sql = "INSERT INTO employees (Employee_ID, Name, Role, Salary) VALUES (?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
  
      stmt.setInt(1, NewID);
      stmt.setString(2, getEmployee().getName());
      stmt.setString(3, role);
      stmt.setDouble(4, salary);
  
      stmt.executeUpdate();
      stmt.close();
    }
    catch (Exception e) {
      System.out.print("Database error");
      e.printStackTrace();
    }
    finally {
      // Close the connection in a finally block to ensure it always happens
      if (conn != null) {
        try {
          conn.close();
        }
        catch (SQLException e) {
          System.out.println("Error closing connection: " + e.getMessage());
        }
      }
    }

    while (true) {
      System.out.print("Enter 1 to return employee page: ");
      m.userInput = m.input.nextLine();
      if (m.userInput.contains(" ")) {
        System.out.println(m.RED + "Invalid input! Option cannot be empty." + m.RESET);
        continue;
      }
      switch (m.userInput) {
        case "1":
          employee.EmployeeMenu();
          break;
        
        default:
          System.out.println(m.RED + "Invalid input! Option cannot be empty." + m.RESET);
          break;
      }
    }
  }

  public static void YYTimetable() {
    Employee em = new Employee();
    Main m = new Main();

    System.out.print(" ");
    for (int i = 0; i < 191; i++) {
      System.out.print("_");
    }
    System.out.print("\n| 00:00 | 01:00 | 02:00 | 03:00 | 04:00 | 05:00 | 06:00 | 07:00 | 08:00 | 09:00 | 10:00 | 11:00 | 12:00 | 13:00 | 14:00 | 15:00 | 16:00 | 17:00 | 18:00 | 19:00 | 20:00 | 21:00 | 22:00 | 23:00 |\n");
    System.out.print("|" + m.GREEN_B);
    for (int i = 0; i <15; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.WHITE_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 15; i++) {
      System.out.print("_");
    }
    System.out.print(m.RESET + "|\n\n");
    em.EmpLogInContinue();
  }

  public static void ZWTimetable() {
    Employee em = new Employee();
    Main m = new Main();

    System.out.print(" ");
    for (int i = 0; i < 191; i++) {
      System.out.print("_");
    }
    System.out.print("\n| 00:00 | 01:00 | 02:00 | 03:00 | 04:00 | 05:00 | 06:00 | 07:00 | 08:00 | 09:00 | 10:00 | 11:00 | 12:00 | 13:00 | 14:00 | 15:00 | 16:00 | 17:00 | 18:00 | 19:00 | 20:00 | 21:00 | 22:00 | 23:00 |\n");
    System.out.print("|" + m.GREEN_B);
    for (int i = 0; i < 15; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.WHITE_B);
    for (int i = 0; i < 31; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
     System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 31; i++) {
      System.out.print("_");
    }
    System.out.print(m.RESET + "|\n\n");
    em.EmpLogInContinue();
  }

  public static void ZXTimetable() {
    Employee em = new Employee();
    Main m = new Main();
    
    System.out.print(" ");
    for (int i = 0; i < 191; i++) {
      System.out.print("_");
    }
    System.out.print("\n| 00:00 | 01:00 | 02:00 | 03:00 | 04:00 | 05:00 | 06:00 | 07:00 | 08:00 | 09:00 | 10:00 | 11:00 | 12:00 | 13:00 | 14:00 | 15:00 | 16:00 | 17:00 | 18:00 | 19:00 | 20:00 | 21:00 | 22:00 | 23:00 |\n");
    System.out.print("|" + m.GREEN_B);
    for (int i = 0; i < 23; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.WHITE_B);
    for (int i = 0; i < 31; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 23; i++) {
      System.out.print("_");
    }
    System.out.print(m.RESET + "|\n\n");
    em.EmpLogInContinue();
  }

  public static void JoeyTimetable() {
    Employee em = new Employee();
    Main m = new Main();
    
    System.out.print(" ");
    for (int i = 0; i < 191; i++) {
      System.out.print("_");
    }
    System.out.print("\n| 00:00 | 01:00 | 02:00 | 03:00 | 04:00 | 05:00 | 06:00 | 07:00 | 08:00 | 09:00 | 10:00 | 11:00 | 12:00 | 13:00 | 14:00 | 15:00 | 16:00 | 17:00 | 18:00 | 19:00 | 20:00 | 21:00 | 22:00 | 23:00 |\n");
    System.out.print("|" + m.WHITE_B);
    for (int i = 0; i < 15; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.WHITE_B);
    for (int i = 0; i < 15; i++) {
      System.out.print("_");
    }
    System.out.print(m.RESET + "|\n\n");
    em.EmpLogInContinue();
  }
  
  public static void TraineeTimetable() {
    Employee em = new Employee();
    Main m = new Main();

    System.out.print(" ");
    for (int i = 0; i < 191; i++) {
      System.out.print("_");
    }
    System.out.print("\n| 00:00 | 01:00 | 02:00 | 03:00 | 04:00 | 05:00 | 06:00 | 07:00 | 08:00 | 09:00 | 10:00 | 11:00 | 12:00 | 13:00 | 14:00 | 15:00 | 16:00 | 17:00 | 18:00 | 19:00 | 20:00 | 21:00 | 22:00 | 23:00 |\n");
    System.out.print("|" + m.GREEN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.WHITE_B);
    for (int i = 0; i < 31; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 47; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.CYAN_B);
    for (int i = 0; i < 7; i++) {
      System.out.print("_");
    }
    System.out.print(m.BLACK_B + "|" + m.GREEN_B);
    for (int i = 0; i < 39; i++) {
      System.out.print("_");
    }
    System.out.print(m.RESET + "|\n\n");
    em.EmpLogInContinue();
  }

  public void GuestDetailStore(BookingInfo bookinginfo) {
    int GuestRecordCount = 0;

    String GName = bookinginfo.getGName();
    String GAge = bookinginfo.getGAge();
    String GcontactNum = bookinginfo.getGcontactNum();
    LocalDate checkInDate = bookinginfo.getcheckInDate();
    LocalTime checkInTime = bookinginfo.getcheckInTime();
    LocalDate checkOutDate = bookinginfo.getcheckOutDate();
    LocalTime checkOutTime = bookinginfo.getcheckOutTime();

    String query = "INSERT INTO guest (Guest_ID, Name, Age, Contact_Number, Check_In_Date, Check_Out_Date, Check_In_Time, Check_Out_Time)"
     + "VALUE (?, ?, ?, ?, ?, ?, ?, ?)";
     String sql = "SELECT COUNT(*) FROM guest";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(url, username, password);
      PreparedStatement stmt = conn.prepareStatement(query);

      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
          GuestRecordCount = rs.getInt(1); // Calculate how many record in the guest table
        }
      }
      catch (Exception e) {
        System.out.println("Data Error");
      }
      setGuestID(1000 + GuestRecordCount);

      stmt.setInt(1, getGuestID());
      stmt.setString(2, GName);
      stmt.setString(3, GAge);
      stmt.setString(4, GcontactNum);
      stmt.setDate(5, Date.valueOf(checkInDate));
      stmt.setDate(6, Date.valueOf(checkOutDate));
      stmt.setTime(7, Time.valueOf(checkInTime));
      stmt.setTime(8, Time.valueOf(checkOutTime));

      stmt.executeUpdate();
      stmt.close();
      conn.close();
    }
    catch (Exception e) {
      System.out.println("Data error");
      e.printStackTrace();
    }
  }

  public void RoomStatusUpdate(Room room) {
    String RoomAvailabilityStatus = "Unavailable";
    String RoomCleanedStatus = "Booked";
    String query = "UPDATE room SET Available_Status = ?, Cleaned_Status = ? WHERE Room_ID = ?";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(url, username, password);
      PreparedStatement stmt = conn.prepareStatement(query);

      stmt.setString(1, RoomAvailabilityStatus);
      stmt.setString(2, RoomCleanedStatus);
      stmt.setInt(3, room.getRoomID());

      stmt.executeUpdate();
      stmt.close();
    }
    catch (Exception e) {
      System.out.println("Data error");
    }
  }

  public void PaymentDetailsStore(Payment payment, Room room) {
    String TransactionStatus = "";
    String query = "INSERT INTO payment_transactions (Payment_ID, Guest_ID, Room_ID, Amount_Paid, Payment_Method, Transaction_Status)"
     + "VALUES (?, ?, ?, ?, ?, ?)";
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(url, username, password);
      PreparedStatement stmt = conn.prepareStatement(query);

      if (payment.getSelectedMethod().equals("Cash on Arrival")) {
        TransactionStatus = "Pending Payment";
      }
      else {
        TransactionStatus = "Paid";
      }

      stmt.setInt(1, payment.getPaymentID());
      stmt.setInt(2, getGuestID());
      stmt.setInt(3, room.getRoomID());
      stmt.setDouble(4, payment.getTotalAmount());
      stmt.setString(5, payment.getSelectedMethod());
      stmt.setString(6, TransactionStatus);

      stmt.executeUpdate();
      stmt.close();
    }
    catch (Exception e) {
      System.out.println("Data error");
      e.printStackTrace();
    }
  }

  public boolean RoomStatusChecking(Room room) {
    String query = "SELECT * FROM room WHERE Room_ID = ?";
    String AvailableStatus = null;
    String CleanedStatus = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(url, username, password);
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setInt(1, room.getRoomID());
      ResultSet resultset = stmt.executeQuery();

      if (resultset.next()) {
        room.setRoomID(resultset.getInt("Room_ID")); // Set RoomID from the database
        AvailableStatus = resultset.getString("Available_Status");
        CleanedStatus = resultset.getString("Cleaned_Status");
      }
      else {
        return false;
      }
    }
    catch (Exception e) {
      System.out.println("Data error");
      return false;
    }

    if ("Unavailable".equals(AvailableStatus) || "Booked".equals(CleanedStatus)) {
      return false; // Room is unavailable
    }
    return true; // Room is available
  }

  public static boolean GuestIDChecking(int GuestIDGet, Guest guest) {
    Database d = new Database();

    String query = "SELECT * FROM guest WHERE Guest_ID = ?";
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(d.url, d.username, d.password);
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setInt(1, GuestIDGet);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        guest.setGuestid(GuestIDGet);
        return true; // ID found
      }
    }
    catch (Exception e) {
      System.out.println("Data error");
    }
    return false;
  }

  public void DetailsFromDatabase(Guest guest) {
    Main m = new Main();
    BookingInfo b = new BookingInfo();

    String query = "SELECT * FROM guest WHERE Guest_ID = ?";
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection(url, username, password);
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, guest.getGuestid());
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
          String GuestName = rs.getString("Name");
          String GuestAge = rs.getString("Age");
          String GuestContactNum = rs.getString("Contact_Number");
          LocalDate checkInDate = rs.getDate("Check_In_Date").toLocalDate();
          String FormattedCheckInDate = checkInDate.format(m.dateformat);
          LocalTime checkInTime = rs.getTime("Check_In_Time").toLocalTime();
          LocalDate checkOutDate = rs.getDate("Check_Out_Date").toLocalDate();
          String FormattedCheckOutDate = checkOutDate.format(m.dateformat);
          LocalTime checkOutTime = rs.getTime("Check_Out_Time").toLocalTime();

          while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
              System.out.print("=");
            }
            System.out.printf("\n%72s", "-Guest ID: " + m.YELLOW + guest.getGuestid() + m.RESET + "-\n");
            System.out.printf("\n%9s %6s %12s %-19s", "[1]", "Name", ":", GuestName);
            System.out.printf("\n%9s %5s %13s %-2s", "[2]", "Age", ":", GuestAge);
            System.out.printf("\n%9s %16s %6s %-10s", "[3]", "Contact Number", ": +60", GuestContactNum);
            System.out.printf("\n%9s %15s \n%33s %4s %-10s", "[4]", "Check-In Date", m.PURPLE + "(dd/MM/yyyy)" + m.RESET, 
            ":", FormattedCheckInDate);
            System.out.printf("\n%9s %15s \n%28s %9s %-5s", "[5]", "Check-In Time", m.PURPLE + "(HH:mm)" + m.RESET, ":", 
            checkInTime);
            System.out.printf("\n%9s %16s \n%33s %4s %-10s", "[6]", "Check-Out Date", m.PURPLE + "(dd/MM/yyyy)" + m.RESET, 
            ":", FormattedCheckOutDate);
            System.out.printf("\n%9s %16s \n%28s %9s %-5s", "[7]", "Check-Out Time", m.PURPLE + "(HH:mm)" + m.RESET, ":", 
            checkOutTime);
            System.out.printf("\n\n%32s %10s %29s", "(R/r) Return to Guest Page", "|", "(1-7) Modify Details");
            System.out.print("\n\nEnter your choice (R/r or 1 - 7): ");
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
            if (m.userInput.matches("[1234567Rr]")) {
              if (m.userInput.matches("[1234567]")) {
                if (m.userInput.equals("1")) {
                  b.GuestNameModify(guest);
                }
                else if (m.userInput.equals("2")) {
                  b.GuestAgeModify(guest);
                }
                else if (m.userInput.equals("3")) {
                  b.GuestContactNumModify(guest);
                }
                else if (m.userInput.equals("4")) {
                  b.GuestCheckInDateModify(guest);
                }
                else if (m.userInput.equals("5")) {
                  b.GuestCheckInTimeModify(guest);
                }
                else if (m.userInput.equals("6")) {
                  b.GuestCheckOutDateModify(guest);
                }
                else {
                  b.GuestCheckOutTimeModify(guest);
                }
              }
              else {
                guest.GuestMenu();
              }
              break;
            }
            else {
              System.out.print("\033[H\033[2J");
              System.out.flush();
              System.out.println(m.RED + "Invalid option. Please enter R or 1 - 7." + m.RESET);
              System.out.print("Please press ENTER to continue...");
              m.key = m.input.nextLine();
              continue;
            }
          }
      }
      else {
        System.out.println("Guest ID not found in the database.");
      }
      rs.close();
      statement.close();
      connection.close();
    }
    catch (Exception e) {
      System.out.println("Data error");
    }
  }

  /*public void ChangeRoomType(Guest guest) {
    Main m = new Main();

    int RoomID;
    String RoomType;
    double RoomPrice;

    String sql = "SELECT Room_ID FROM payment_transactions WHERE Guest_ID = ?";
    String query = "SELECT * FROM room WHERE Room_ID = ?";

    while (true) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement1 = connection.prepareStatement(sql);
        statement1.setInt(1, guest.getGuestid());
        ResultSet result1 = statement1.executeQuery();
        if (result1.next()) {
          RoomID = result1.getInt("Room_ID");
          try {
            PreparedStatement statement2 = connection.prepareStatement(query);
            statement2.setInt(1, RoomID);
            ResultSet result2 = statement2.executeQuery();
            if (result2.next()) {
              RoomType = result2.getString("Room_Type");
              RoomPrice = result2.getDouble("Price_Per_Night");

              System.out.print("\033[H\033[2J");
              System.out.flush();
              m.Logo();
              for (int i = 0; i < 109; i++) {
                System.out.print("=");
              }
              System.out.printf("\n%72s", "-Guest ID: " + m.YELLOW + guest.getGuestid() + m.RESET + "-\n");
              System.out.printf("\n%24s %8s %3s", "Room ID", ":", RoomID);
              System.out.printf("\n%26s %6s %5s %4s", "Room Type", ":", RoomType, "Room");
              System.out.printf("\n%36s %6.2f", "Price Per Night: RM", RoomPrice);
              if ("Single".equals(RoomType)) {
                System.out.printf("\n\n%32s %4s %27s %3s %29s", "1. Upgrade to Double Room", "|", 
                "2. Upgrade to Suite Room", "|", "3. Return to Previous Page");
                System.out.printf("\n%99s", m.YELLOW + "*Please note that there will be an extra charge for upgrading your room*" + m.RESET);
                System.out.print("\n\nEnter your choice (1, 2 or 3): ");
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
                if (m.userInput.matches("[123]")) {
                  if (m.userInput.equals("1")) {
                    System.out.printf("\n%95s", m.GREEN + "-Upgrading to a Double Room will cost an additional RM100.00-\n" 
                    + m.RESET);
                    UpgradetoDouble(guest);
                  }
                  else if (m.userInput.equals("2")) {
                    System.out.printf("\n%95s", m.GREEN + "-Upgrading to a Suite Room will cost an additional RM220.00-\n" 
                    + m.RESET);
                    UpgradetoSuite(guest);
                  }
                  else {
                    guest.ModifyReservation();
                  }
                  break;
                }
                else {
                  System.out.print("\033[H\033[2J");
                  System.out.flush();
                  System.out.print(m.RED);
                  System.out.println("Invalid option. Please enter 1, 2 or 3.");
                  System.out.print(m.RESET);
                  System.out.print("Please press ENTER to continue...");
                  m.key = m.input.nextLine();
                }
              }
              else if ("Double".equals(RoomType)) {
                System.out.printf("\n\n%32s %4s %27s %3s %29s", "1. Downgrade to Single Room", "|", 
                "2. Upgrade to Suite Room", "|", "3. Return to Previous Page");
                System.out.printf("\n%99s", m.YELLOW + "*Please note that there will be an extra charge for upgrading your room*" + m.RESET);
                System.out.print("\n\nEnter your choice (1, 2 or 3): ");
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
                if (m.userInput.matches("[123]")) {
                  if (m.userInput.equals("1")) {
                    System.out.printf("\n%95s", m.GREEN + "-We will arrange a downgrade refund within a few days-\n" + m.RESET);
                    DowngradetoSingle(guest);
                  }
                  else if (m.userInput.equals("2")) {
                    System.out.printf("\n%95s", m.GREEN + "-Upgrading to a Suite Room will cost an additional RM120.00-\n" 
                    + m.RESET);
                    UpgradetoSuite(guest);
                  }
                  else {
                    guest.ModifyReservation();
                  }
                  break;
                }
                else {
                  System.out.print("\033[H\033[2J");
                  System.out.flush();
                  System.out.print(m.RED);
                  System.out.println("Invalid option. Please enter 1, 2 or 3.");
                  System.out.print(m.RESET);
                  System.out.print("Please press ENTER to continue...");
                  m.key = m.input.nextLine();
                }
              }
              else if ("Suite".equals(RoomType)) {
                System.out.printf("\n\n%33s %3s %29s %3s %29s", "1. Downgrade to Single Room", "|", 
                "2. Downgrade to Double Room", "|", "3. Return to Previous Page");
                System.out.printf("\n%99s", m.YELLOW + "*Please note that there will be an extra charge for upgrading your room*" + m.RESET);
                System.out.print("\n\nEnter your choice (1, 2 or 3): ");
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
                if (m.userInput.matches("[123]")) {
                  if (m.userInput.equals("1")) {
                    System.out.printf("\n%95s", m.GREEN + "-We will arrange a downgrade refund within a few days-\n" + m.RESET);
                    DowngradetoSingle(guest);
                  }
                  else if (m.userInput.equals("2")) {
                    System.out.printf("\n%95s", m.GREEN + "-We will arrange a downgrade refund within a few days-\n" + m.RESET);
                    DowngradetoDouble(guest);
                  }
                  else {
                    guest.ModifyReservation();
                  }
                  break;
                }
                else {
                  System.out.print("\033[H\033[2J");
                  System.out.flush();
                  System.out.print(m.RED);
                  System.out.println("Invalid option. Please enter 1, 2 or 3.");
                  System.out.print(m.RESET);
                  System.out.print("Please press ENTER to continue...");
                  m.key = m.input.nextLine();
                }
              }
              else {
                System.out.println("Invalid room type.");
              }
            }
          }
          catch (Exception e) {
            System.out.println("Data error");
          }
        }
        else {
          System.out.println("No payment record found.");
        }
      }
      catch (Exception e) {
        System.out.println("Data error");
        e.printStackTrace();
      }
    }
  }

  public void UpgradetoDouble(Guest guest) {
    Main m = new Main();

    int a = 0;
    String query = "SELECT * FROM room WHERE Room_Type = 'Double' AND Available_Status = 'Available'";
    while (true) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    
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
        System.out.printf("%7s %12s %14s %24s %15s %19s", "| |", "Room ID", "Room Type", "Price per Night(RM)",
        "Room Status", "Cleaning Status");
        System.out.printf("%10s", "| |\n");
        System.out.printf("%7s %98s", "| |", "| |\n");
        while (rs.next()) {
          a++;
          System.out.printf("%7s %10s %14s %18.2f %22s %16s %13s", "| |", 
          rs.getInt("Room_ID"), rs.getString("Room_Type"), rs.getDouble("Price_Per_Night"), 
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
        System.out.print("Enter your choice (Room ID): ");
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
      }
      catch (Exception e) {
        System.out.println("Data error");
      }
    }
  }

  public void UpgradetoSuite(Guest guest) {
    Main m = new Main();

    int a = 0;
    String query = "SELECT * FROM room WHERE Room_Type = 'Suite' AND Available_Status = 'Available'";
    while (true) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    
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
        System.out.printf("%7s %12s %14s %24s %15s %19s", "| |", "Room ID", "Room Type", "Price per Night(RM)",
        "Room Status", "Cleaning Status");
        System.out.printf("%10s", "| |\n");
        System.out.printf("%7s %98s", "| |", "| |\n");
        while (rs.next()) {
          a++;
          System.out.printf("%7s %10s %14s %18.2f %22s %16s %13s", "| |", 
          rs.getInt("Room_ID"), rs.getString("Room_Type"), rs.getDouble("Price_Per_Night"), 
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
        System.out.print("Enter your choice (Room ID): ");
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
      }
      catch (Exception e) {
        System.out.println("Data error");
      }
    }
  }

  public void DowngradetoDouble(Guest guest) {
    Main m = new Main();

    int a = 0;
    String query = "SELECT * FROM room WHERE Room_Type = 'Double' AND Available_Status = 'Available'";
    while (true) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    
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
        System.out.printf("%7s %12s %14s %24s %15s %19s", "| |", "Room ID", "Room Type", "Price per Night(RM)",
        "Room Status", "Cleaning Status");
        System.out.printf("%10s", "| |\n");
        System.out.printf("%7s %98s", "| |", "| |\n");
        while (rs.next()) {
          a++;
          System.out.printf("%7s %10s %14s %18.2f %22s %16s %13s", "| |", 
          rs.getInt("Room_ID"), rs.getString("Room_Type"), rs.getDouble("Price_Per_Night"), 
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
        System.out.print("Enter your choice (Room ID): ");
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
      }
      catch (Exception e) {
        System.out.println("Data error");
      }
    }
  }

  public void DowngradetoSingle(Guest guest) {
    Main m = new Main();

    int a = 0;
    int RoomID;
    String RoomType;
    double RoomPrice;
    String query = "SELECT * FROM room WHERE Room_Type = 'Single' AND Available_Status = 'Available'";
    while (true) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    
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
        System.out.printf("%7s %12s %14s %24s %15s %19s", "| |", "Room ID", "Room Type", "Price per Night(RM)",
        "Room Status", "Cleaning Status");
        System.out.printf("%10s", "| |\n");
        System.out.printf("%7s %98s", "| |", "| |\n");
        while (rs.next()) {
          a++;
          System.out.printf("%7s %10s %14s %18.2f %22s %16s %13s", "| |", 
          rs.getInt("Room_ID"), rs.getString("Room_Type"), rs.getDouble("Price_Per_Night"), 
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
        System.out.print("Enter your choice (Room ID): ");
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
        else if (m.userInput.matches("[Rr]")) {
          ChangeRoomType(guest);
          break;
        }
        try {
          int roomId = Integer.parseInt(m.userInput);
          String verifyQuery = "SELECT * FROM room WHERE Room_ID = ? AND Room_Type = 'Single' AND Available_Status = 'Available'";
          PreparedStatement pstmt = connection.prepareStatement(verifyQuery);
          pstmt.setInt(1, roomId);
          ResultSet verifyRs = pstmt.executeQuery();

          if (verifyRs.next()) {
            RoomID = verifyRs.getInt("Room_ID");
            RoomType = verifyRs.getString("Room_Type");
            RoomPrice = verifyRs.getDouble("Price_Per_Night");

            System.out.printf("\n%69s", m.YELLOW + "-Change to-" + m.RESET);
            System.out.printf("\n%24s %8s %3s", "Room ID", ":", RoomID);
            System.out.printf("\n%26s %6s %5s %4s", "Room Type", ":", RoomType, "Room");
            System.out.printf("\n%36s %6.2f", "Price Per Night: RM", RoomPrice);
            System.out.printf("\n\n%40s %7s %43s", "1. Confirm to Downgrade", "|", "2. Cancel and Return to Previous Page");
            System.out.print("\n\nEnter your choice (1 or 2): ");
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
                String newRoomType = "Single";
                try (Connection conn = DriverManager.getConnection(url, username, password)) {
                  String findRoomSQL = "SELECT Room_ID FROM room WHERE Room_Type = ? AND Available_Status = 'Available' LIMIT 1";
                  int newRoomId;
                  try (PreparedStatement statement = conn.prepareStatement(findRoomSQL)) {
                    statement.setString(1, newRoomType);
                    ResultSet result = statement.executeQuery();
                    if (!rs.next()) {
                      System.out.println("No available " + newRoomType + " rooms");
                      return;
                    }
                    newRoomId = rs.getInt("Room_ID");
                  }
                  String currentRoomSQL = "SELECT Room_ID FROM payment_transactions WHERE Guest_ID = ? ORDER BY Payment_ID DESC LIMIT 1";
                  int currentRoomId;
                  try (PreparedStatement stmt = conn.prepareStatement(currentRoomSQL)) {
                    stmt.setInt(1, guestId);
                    ResultSet rs = stmt.executeQuery();
                    if (!rs.next()) {
                        System.out.println("Guest not found");
                        return;
                    }
                    currentRoomId = rs.getInt("Room_ID");
                }
                }
                guest.ModifyReservation();
              }
              else {
                ChangeRoomType(guest);
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
          verifyRs.close();
          pstmt.close();
        }
        catch (Exception e) {
          System.out.print("\033[H\033[2J");
          System.out.flush();
          System.out.print(m.RED);
          System.out.println("Invalid input! Please enter correct Room ID.");
          System.out.print(m.RESET);
          System.out.print("Please press ENTER to continue...");
          m.key = m.input.nextLine();
        }
        connection.close();
      }
      catch (Exception e) {
        System.out.println("Data error");
      }
    }
  }
  */
}