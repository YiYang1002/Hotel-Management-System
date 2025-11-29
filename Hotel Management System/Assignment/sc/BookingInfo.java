import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

// For hold all the guest booking details
public class BookingInfo {
    private String GName;
    private String GAge;
    private String GcontactNum;
    private LocalDate checkInDate;
    private LocalTime checkInTime;
    private LocalDate checkOutDate;
    private LocalTime checkOutTime;

    public BookingInfo (String GName, String GAge, String GcontactNum, 
    LocalDate checkInDate, LocalTime checkInTime, LocalDate checkOutDate, LocalTime checkOutTime) {
        this.GName = GName;
        this.GAge = GAge;
        this.GcontactNum = GcontactNum;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
    }

    public BookingInfo() {
    }

    public String getGName() {
        return GName;
    }

    public String getGAge() {
        return GAge;
    }

    public String getGcontactNum() {
        return GcontactNum;
    }

    public LocalDate getcheckInDate() {
        return checkInDate;
    }

    public LocalTime getcheckInTime() {
        return checkInTime;
    }

    public LocalDate getcheckOutDate() {
        return checkOutDate;
    }

    public LocalTime getcheckOutTime() {
        return checkOutTime;
    }

    // Formatted check-in/out date & time
    public String getFormattedCheckInDate() {
        Main m = new Main();
        return checkInDate.format(m.dateformat);
    }
    
    public String getFormattedCheckInTime() {
        Main m = new Main();
        return checkInTime.format(m.timeformat);
    }
    
    public String getFormattedCheckOutDate() {
        Main m = new Main();
        return checkOutDate.format(m.dateformat);
    }
    
    public String getFormattedCheckOutTime() {
        Main m = new Main();
        return checkOutTime.format(m.timeformat);
    }

    public BookingInfo BookingDetailsModify(Room room, BookingInfo bookinginfo) {
        Main m = new Main();
        Guest g = new Guest();

        String OriginalName = bookinginfo.getGName();
        String NewName = OriginalName;

        String OriginalAge = bookinginfo.getGAge();
        String NewAge = OriginalAge;

        String OriginalcontactNum = bookinginfo.getGcontactNum();
        String NewcontactNum = OriginalcontactNum;

        String OriginalCheckInDate = bookinginfo.getFormattedCheckInDate();
        String NewCheckInDate = OriginalCheckInDate;

        String OriginalCheckInTime = bookinginfo.getFormattedCheckInTime();
        String NewCheckInTime = OriginalCheckInTime;

        String OriginalCheckOutDate = bookinginfo.getFormattedCheckOutDate();
        String NewCheckOutDate = OriginalCheckOutDate;

        String OriginalCheckOutTime = bookinginfo.getFormattedCheckOutTime();
        String NewCheckOutTime = OriginalCheckOutTime;

        LocalDate currentDate = LocalDate.now();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        m.Logo();
        for (int i = 0; i < 109; i++) {
            System.out.print("=");
        }
        if (Guest.getModifyOption().equals("1")) { // Name Modify
            System.out.printf("\n%89s", m.GREEN + "-Name must between 3 to 20 letters (no numbers or symbols)-");
            System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
            System.out.printf("%22s %-20s", "Original Name:", OriginalName);
            while (true) {
                System.out.printf("\n%17s %5s", "Change to", ": ");
                NewName = m.input.nextLine();
                m.returnMatch = m.returnCommand.matcher(NewName);
                if (NewName.contains(" ")) {
                    System.out.printf("%53s", m.RED + "Invalid input! Name cannot be empty." + m.RESET);
                    continue;
                }
                else if (m.returnMatch.matches()) {
                    g.BookingConfirmation(room, bookinginfo);
                    return null;
                }
                if (NewName.matches("[a-zA-Z\\s]{3,20}")) {
                    if (NewName.replaceAll("\\s+", "").length() >= 3) {
                        System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                        break;
                    }
                    else {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    }             
                }
                else {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                }
            }
            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                }
                if (m.userInput.equals("1")) {
                    BookingInfo updatedInfo = new BookingInfo(NewName, bookinginfo.getGAge(), 
                    bookinginfo.getGcontactNum(), bookinginfo.getcheckInDate(), 
                    bookinginfo.getcheckInTime(), bookinginfo.getcheckOutDate(), 
                    bookinginfo.getcheckOutTime());
                    g.BookingConfirmation(room, updatedInfo);
                    return updatedInfo;
                }
                else if (m.userInput.equals("2")) {
                    BookingDetailsModify(room, bookinginfo);
                    return null;
                }
                else {
                    System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                }
            }
        }
        else if (Guest.getModifyOption().equals("2")){ // Age Modify
            System.out.printf("\n%101s", m.GREEN + "-Age must be greater than or equal to 18 and between 99 (no characters or symbols)-" + m.RESET);
            System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
            System.out.printf("%21s %-2s", "Original Age:", OriginalAge);
            while (true) {
                System.out.printf("\n%17s %4s", "Change to", ": ");
                NewAge = m.input.nextLine();
                m.returnMatch = m.returnCommand.matcher(NewAge);
                if (NewAge.contains(" ")) {
                    System.out.printf("%52s", m.RED + "Invalid input! Age cannot be empty." + m.RESET);
                    continue;
                }
                else if (m.returnMatch.matches()) {
                    g.BookingConfirmation(room, bookinginfo);
                    return null;
                }
                if (!NewAge.matches("[0-9]{2}")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                }
                int New_Age = Integer.parseInt(NewAge);
                if (NewAge.matches("[0-9]{2}") && New_Age >= 18 && New_Age <= 99) {
                    System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                    break;
                }
                else {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                }
            }
            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                }
                if (m.userInput.equals("1")) {
                    BookingInfo updatedInfo = new BookingInfo(bookinginfo.getGName(), 
                    NewAge, bookinginfo.getGcontactNum(),
                    bookinginfo.getcheckInDate(), bookinginfo.getcheckInTime(), 
                    bookinginfo.getcheckOutDate(), bookinginfo.getcheckOutTime());
                    g.BookingConfirmation(room, updatedInfo);
                    return updatedInfo;
                }
                else if (m.userInput.equals("2")) {
                    BookingDetailsModify(room, bookinginfo);
                    return null;
                }
                else {
                    System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                }
            }
        }
        else if (Guest.getModifyOption().equals("3")) { // Contact Number Modify
            System.out.printf("\n%80s", m.GREEN + "-Contact number must be within 10 numbers-" + m.RESET);
            System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
            System.out.printf("%36s %-10s", "Original Contact Number: +60", OriginalcontactNum);
            while (true) {
                System.out.printf("\n%17s %5s", "Change to", ": ");
                NewcontactNum = m.input.nextLine();
                m.returnMatch = m.returnCommand.matcher(NewcontactNum);
                if (NewcontactNum.contains(" ")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Contact number cannot be empty." + m.RESET);
                    continue;
                }
                else if (m.returnMatch.matches()) {
                    g.BookingConfirmation(room, bookinginfo);
                    return null;
                }
                if (NewcontactNum.matches("[0-9]{9,10}")) {
                    System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                    break;                
                }
                else {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                }
            }
            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                }
                if (m.userInput.equals("1")) {
                    BookingInfo updatedInfo = new BookingInfo(bookinginfo.getGName(), 
                    bookinginfo.getGAge(), NewcontactNum, 
                    bookinginfo.getcheckInDate(), bookinginfo.getcheckInTime(), 
                    bookinginfo.getcheckOutDate(), bookinginfo.getcheckOutTime());
                    g.BookingConfirmation(room, updatedInfo);
                    return updatedInfo;
                }
                else if (m.userInput.equals("2")) {
                    BookingDetailsModify(room, bookinginfo);
                    return null;
                }
                else {
                    System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                }
            }
        }
        else if (Guest.getModifyOption().equals("4")) { // Check In Date Modify
            System.out.printf("\n%90s", m.GREEN + "-Check-in date cannot be in the past (Use dd/MM/yyyy format)-");
            System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
            System.out.printf("%31s %-10s", "Original Check In Date:", OriginalCheckInDate);
            while (true) {
                System.out.printf("\n%17s %14s", "Change to", ": ");
                NewCheckInDate = m.input.nextLine();
                m.returnMatch = m.returnCommand.matcher(NewCheckInDate);
                if (NewCheckInDate.contains(" ")) {
                    System.out.printf("%62s", m.RED + "Invalid input! Check in date cannot be empty." + m.RESET);
                    continue;
                }
                else if (m.returnMatch.matches()) {
                    g.BookingConfirmation(room, bookinginfo);
                    return null;
                }
                // Date check, dd/MM/yyyy
                if (!NewCheckInDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    continue;
                }
                try {
                    LocalDate newDate = LocalDate.parse(NewCheckInDate, m.dateformat);
                    if (newDate.isBefore(currentDate)) {
                        System.out.printf("%71s", m.RED + "Check-in date cannot be in the past. Please try again." + m.RESET);
                        continue;
                    }
                    NewCheckInDate = newDate.format(m.dateformat);
                    System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                    break;
                }
                catch (Exception e) {
                    System.out.printf("\n%49s", m.RED + "Invalid date! Please try again." + m.RESET);
                }
            }
            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                }
                if (m.userInput.equals("1")) {
                    BookingInfo updatedInfo = new BookingInfo(bookinginfo.getGName(), 
                    bookinginfo.getGAge(), bookinginfo.getGcontactNum(), 
                    LocalDate.parse(NewCheckInDate, m.dateformat), bookinginfo.getcheckInTime(), 
                    bookinginfo.getcheckOutDate(), bookinginfo.getcheckOutTime());
                    g.BookingConfirmation(room, updatedInfo);
                    return updatedInfo;
                }
                else if (m.userInput.equals("2")) {
                    BookingDetailsModify(room, bookinginfo);
                    return null;
                }
                else {
                    System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                }
            }
        }
        else if (Guest.getModifyOption().equals("5")) { // Check In Time Modify
            System.out.printf("\n%101s", m.GREEN + "-Check-in time must be between 07:00 and 23:59. Use HH:mm (24-hour) format-");
            System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
            System.out.printf("%31s %-5s", "Original Check In Time:", OriginalCheckInTime);
            while (true) {
                System.out.printf("\n%17s %14s", "Change to", ": ");
                NewCheckInTime = m.input.nextLine();
                m.returnMatch = m.returnCommand.matcher(NewCheckInTime);
                if (NewCheckInTime.contains(" ")) {
                    System.out.printf("%62s", m.RED + "Invalid input! Check in time cannot be empty." + m.RESET);
                    continue;
                }
                else if (m.returnMatch.matches()) {
                    g.BookingConfirmation(room, bookinginfo);
                    return null;
                }
                // Time checking, (00-23) for hour, (00-59) for minute
                if (!NewCheckInTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    continue;
                }
                try {
                    LocalTime earliesttime = LocalTime.of(7, 0);
                    LocalTime latesttime = LocalTime.of(23, 59);
                    LocalTime newtTime = LocalTime.parse(NewCheckInTime, m.timeformat);
                    if (newtTime.isBefore(earliesttime) || newtTime.isAfter(latesttime)) {
                        System.out.printf("%63s", m.RED + "Check-in time must be between 07:00 and 23:59." + m.RESET);
                        continue;
                    }
                    NewCheckInTime = newtTime.format(m.timeformat);
                    System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                    break;
                }
                catch (Exception e) {
                    System.out.printf("%48s", m.RED + "Invalid time! Please try again." + m.RESET);
                }
            }
            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                }
                if (m.userInput.equals("1")) {
                    BookingInfo updatedInfo = new BookingInfo(bookinginfo.getGName(), 
                    bookinginfo.getGAge(), bookinginfo.getGcontactNum(), 
                    bookinginfo.getcheckInDate(), LocalTime.parse(NewCheckInTime, m.timeformat), 
                    bookinginfo.getcheckOutDate(), bookinginfo.getcheckOutTime());
                    g.BookingConfirmation(room, updatedInfo);
                    return updatedInfo;
                }
                else if (m.userInput.equals("2")) {
                    BookingDetailsModify(room, bookinginfo);
                    return null;
                }
                else {
                    System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                }
            }
        }
        else if (Guest.getModifyOption().equals("6")) { // Check Out Date Modify
            System.out.printf("\n%93s", m.GREEN + "-Check-out date must be after check-in date (Use dd/MM/yyyy format)-");
            System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
            System.out.printf("%32s %-10s", "Original Check Out Date:", OriginalCheckOutDate);
            while (true) {
                System.out.printf("\n%17s %15s", "Change to", ": ");
                NewCheckOutDate = m.input.nextLine();
                m.returnMatch = m.returnCommand.matcher(NewCheckOutDate);
                if (NewCheckOutDate.contains(" ")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Check ouy date cannot be empty." + m.RESET);
                    continue;
                }
                else if (m.returnMatch.matches()) {
                    g.BookingConfirmation(null, bookinginfo);
                    return null;
                }
                if (!NewCheckOutDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    continue;
                }
                try {
                    LocalDate newDate2 = LocalDate.parse(NewCheckOutDate, m.dateformat);
                    if (newDate2.isBefore(bookinginfo.checkInDate)) {
                        System.out.printf("%62s", m.RED + "Invalid date! Please follow the instructions." + m.RESET);
                        continue;
                    }
                    NewCheckOutDate = newDate2.format(m.dateformat);
                    System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                    break;
                }
                catch (Exception e) {
                    System.out.printf("\n%49s", m.RED + "Invalid date! Please try again." + m.RESET);
                }
            }
            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                }
                if (m.userInput.equals("1")) {
                    BookingInfo updatedInfo = new BookingInfo(bookinginfo.getGName(), 
                    bookinginfo.getGAge(), bookinginfo.getGcontactNum(), 
                    bookinginfo.getcheckInDate(), bookinginfo.getcheckInTime(), 
                    LocalDate.parse(NewCheckOutDate, m.dateformat), bookinginfo.getcheckOutTime());
                    g.BookingConfirmation(room, updatedInfo);
                    return updatedInfo;
                }
                else if (m.userInput.equals("2")) {
                    BookingDetailsModify(room, bookinginfo);
                    return null;
                }
                else {
                    System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                }
            }
        }
        else if (Guest.getModifyOption().equals("7")) { // Check Out Time Modify
            System.out.printf("\n%83s", m.GREEN + "-Check-out time must use HH:mm (24-hour) format-");
            System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
            System.out.printf("%32s %-5s", "Original Check Out Time:", OriginalCheckOutTime);
            while (true) {
                System.out.printf("\n%17s %15s", "Change to", ": ");
                NewCheckOutTime = m.input.nextLine();
                m.returnMatch = m.returnCommand.matcher(NewCheckOutTime);
                if (NewCheckOutTime.contains(" ")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Check out time cannot be empty." + m.RESET);
                    continue;
                }
                else if (m.returnMatch.matches()) {
                    g.BookingConfirmation(room, bookinginfo);
                    return null;
                }
                if (!NewCheckOutTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                    System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    continue;
                }
                try {
                    LocalTime newtTime = LocalTime.parse(NewCheckOutTime, m.timeformat);
                    NewCheckOutTime = newtTime.format(m.timeformat);
                    System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                    break;
                }
                catch (Exception e) {
                    System.out.printf("%48s", m.RED + "Invalid time! Please try again." + m.RESET);
                }
            }
            while (true) {
                System.out.print("Enter your choice (1 or 2): ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                }
                if (m.userInput.equals("1")) {
                    BookingInfo updatedInfo = new BookingInfo(bookinginfo.getGName(), 
                    bookinginfo.getGAge(), bookinginfo.getGcontactNum(), 
                    bookinginfo.getcheckInDate(), bookinginfo.getcheckInTime(), 
                    bookinginfo.getcheckOutDate(), LocalTime.parse(NewCheckOutTime, m.timeformat));
                    g.BookingConfirmation(room, updatedInfo);
                    return updatedInfo;
                }
                else if (m.userInput.equals("2")) {
                    BookingDetailsModify(room, bookinginfo);
                    return null;
                }
                else {
                    System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                }
            }
        }

        return new BookingInfo(NewName, NewAge, NewcontactNum, 
        LocalDate.parse(NewCheckInDate, m.dateformat), LocalTime.parse(NewCheckInTime, m.timeformat), 
        LocalDate.parse(NewCheckOutDate, m.dateformat), LocalTime.parse(NewCheckOutTime, m.timeformat));
    }

    public void GuestNameModify(Guest guest) {
        Main m = new Main();
        Database d = new Database();

        String sql = "SELECT Name FROM guest WHERE Guest_ID = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, guest.getGuestid());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String GuestName = rs.getString("Name");
                String UpdatedName;

                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();
                for (int i = 0; i < 109; i++) {
                  System.out.print("=");
                }
                System.out.printf("\n%89s", m.GREEN + "-Name must between 3 to 20 letters (no numbers or symbols)-");
                System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
                System.out.printf("%22s %-20s", "Original Name:", GuestName);
                while (true) {
                    System.out.printf("\n%17s %5s", "Change to", ": ");
                    UpdatedName = m.input.nextLine();
                    m.returnMatch = m.returnCommand.matcher(UpdatedName);
                    if (UpdatedName.contains(" ")) {
                        System.out.printf("%53s", m.RED + "Invalid input! Name cannot be empty." + m.RESET);
                        continue;
                    }
                    else if (m.returnMatch.matches()) {
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    if (UpdatedName.matches("[a-zA-Z\\s]{3,20}")) {
                        if (UpdatedName.replaceAll("\\s+", "").length() >= 3) {
                            System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                            break;
                        }
                        else {
                            System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                        }             
                    }
                    else {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    }
                }
                while (true) {
                    System.out.print("Enter your choice (1 or 2): ");
                    m.userInput = m.input.nextLine();
                    if (m.userInput.contains(" ")) {
                        System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                    }
                    if (m.userInput.equals("1")) {
                        String query = "UPDATE guest SET Name = ? WHERE Guest_ID = ?";
                        try {
                            PreparedStatement updateStatement = connection.prepareStatement(query);
                            updateStatement.setString(1, UpdatedName);
                            updateStatement.setInt(2, guest.getGuestid());

                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                        catch (Exception e) {
                            System.out.println("Error updating name: " + e.getMessage());
                        }
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    else if (m.userInput.equals("2")) {
                        GuestNameModify(guest);
                        return;
                    }
                    else {
                        System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Data Error");
        }
    }

    public void GuestAgeModify(Guest guest) {
        Main m = new Main();
        Database d = new Database();

        String sql = "SELECT Age FROM guest WHERE Guest_ID = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, guest.getGuestid());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String GuestAge = rs.getString("Age");
                String UpdatedAge;

                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();
                for (int i = 0; i < 109; i++) {
                    System.out.print("=");
                }
                System.out.printf("\n%101s", m.GREEN + "-Age must be greater than or equal to 18 and between 99 (no characters or symbols)-" + m.RESET);
                System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
                System.out.printf("%21s %-2s", "Original Age:", GuestAge);
                while (true) {
                    System.out.printf("\n%17s %4s", "Change to", ": ");
                    UpdatedAge = m.input.nextLine();
                    m.returnMatch = m.returnCommand.matcher(UpdatedAge);
                    if (UpdatedAge.contains(" ")) {
                        System.out.printf("%52s", m.RED + "Invalid input! Age cannot be empty." + m.RESET);
                        continue;
                    }
                    else if (m.returnMatch.matches()) {
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    if (!UpdatedAge.matches("[0-9]{2}")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    }
                    int New_Age = Integer.parseInt(UpdatedAge);
                    if (UpdatedAge.matches("[0-9]{2}") && New_Age >= 18 && New_Age <= 99) {
                        System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                        break;
                    }
                    else {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    }
                }
                while (true) {
                    System.out.print("Enter your choice (1 or 2): ");
                    m.userInput = m.input.nextLine();
                    if (m.userInput.contains(" ")) {
                        System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                    }
                    if (m.userInput.equals("1")) {
                        String query = "UPDATE guest SET Age = ? WHERE Guest_ID = ?";
                        try {
                            PreparedStatement updateStatement = connection.prepareStatement(query);
                            updateStatement.setString(1, UpdatedAge);
                            updateStatement.setInt(2, guest.getGuestid());

                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                        catch (Exception e) {
                            System.out.println("Error updating age: " + e.getMessage());
                        }
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    else if (m.userInput.equals("2")) {
                        GuestAgeModify(guest);
                        return;
                    }
                    else {
                        System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Data Error");
        }
    }

    public void GuestContactNumModify(Guest guest) {
        Main m = new Main();
        Database d = new Database();

        String sql = "SELECT Contact_Number FROM guest WHERE Guest_ID = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, guest.getGuestid());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String GuestContactNum = rs.getString("Contact_Number");
                String UpdatedContactNum;

                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();
                for (int i = 0; i < 109; i++) {
                    System.out.print("=");
                }
                System.out.printf("\n%80s", m.GREEN + "-Contact number must be within 10 numbers-" + m.RESET);
                System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
                System.out.printf("%36s %-10s", "Original Contact Number: +60", GuestContactNum);
                while (true) {
                    System.out.printf("\n%17s %5s", "Change to", ": ");
                    UpdatedContactNum = m.input.nextLine();
                    m.returnMatch = m.returnCommand.matcher(UpdatedContactNum);
                    if (UpdatedContactNum.contains(" ")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Contact number cannot be empty." + m.RESET);
                        continue;
                    }
                    else if (m.returnMatch.matches()) {
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    if (UpdatedContactNum.matches("[0-9]{9,10}")) {
                        System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                        break;                
                    }
                    else {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                    }
                }
                while (true) {
                    System.out.print("Enter your choice (1 or 2): ");
                    m.userInput = m.input.nextLine();
                    if (m.userInput.contains(" ")) {
                        System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                    }
                    if (m.userInput.equals("1")) {
                        String query = "UPDATE guest SET Contact_Number = ? WHERE Guest_ID = ?";
                        try {
                            PreparedStatement updateStatement = connection.prepareStatement(query);
                            updateStatement.setString(1, UpdatedContactNum);
                            updateStatement.setInt(2, guest.getGuestid());

                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                        catch (Exception e) {
                            System.out.println("Error updating contact number: " + e.getMessage());
                        }
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    else if (m.userInput.equals("2")) {
                        GuestContactNumModify(guest);
                        return;
                    }
                    else {
                        System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Data Error");
        }
    }

    public void GuestCheckInDateModify(Guest guest) {
        Main m = new Main();
        Database d = new Database();

        String sql = "SELECT Check_In_Date FROM guest WHERE Guest_ID = ?";
        LocalDate currentDate = LocalDate.now();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, guest.getGuestid());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                LocalDate GuestCheckInDate = rs.getDate("Check_In_Date").toLocalDate();
                String FormattedCheckInDate = GuestCheckInDate.format(m.dateformat);
                String UpdatedCheckInDate;

                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();
                for (int i = 0; i < 109; i++) {
                    System.out.print("=");
                }
                System.out.printf("\n%90s", m.GREEN + "-Check-in date cannot be in the past (Use dd/MM/yyyy format)-");
                System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
                System.out.printf("%31s %-10s", "Original Check In Date:", FormattedCheckInDate);
                while (true) {
                    System.out.printf("\n%17s %5s", "Change to", ": ");
                    UpdatedCheckInDate = m.input.nextLine();
                    m.returnMatch = m.returnCommand.matcher(UpdatedCheckInDate);
                    if (UpdatedCheckInDate.contains(" ")) {
                        System.out.printf("%62s", m.RED + "Invalid input! Check in date cannot be empty." + m.RESET);
                        continue;
                    }
                    else if (m.returnMatch.matches()) {
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    if (!UpdatedCheckInDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                        continue;
                    }
                    try {
                        LocalDate newDate = LocalDate.parse(UpdatedCheckInDate, m.dateformat);
                        if (newDate.isBefore(currentDate)) {
                            System.out.printf("%71s", m.RED + "Check-in date cannot be in the past. Please try again." + m.RESET);
                            continue;
                        }
                        UpdatedCheckInDate = newDate.format(m.dateformat);
                        System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                        break;
                    }
                    catch (Exception e) {
                        System.out.printf("\n%49s", m.RED + "Invalid date! Please try again." + m.RESET);
                    }
                }
                while (true) {
                    System.out.print("Enter your choice (1 or 2): ");
                    m.userInput = m.input.nextLine();
                    if (m.userInput.contains(" ")) {
                        System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                    }
                    if (m.userInput.equals("1")) {
                        String query = "UPDATE guest SET Check_In_Date = ? WHERE Guest_ID = ?";
                        try {
                            PreparedStatement updateStatement = connection.prepareStatement(query);
                            updateStatement.setString(1, UpdatedCheckInDate);
                            updateStatement.setInt(2, guest.getGuestid());

                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                        catch (Exception e) {
                            System.out.println("Error updating check-in date: " + e.getMessage());
                        }
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    else if (m.userInput.equals("2")) {
                        GuestCheckInDateModify(guest);
                        return;
                    }
                    else {
                        System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Data Error");
        }
    }

    public void GuestCheckInTimeModify(Guest guest) {
        Main m = new Main();
        Database d = new Database();

        String sql = "SELECT Check_In_Time FROM guest WHERE Guest_ID = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, guest.getGuestid());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                LocalTime GuestCheckInTime = rs.getTime("Check_In_Time").toLocalTime();
                String UpdatedCheckInTime;

                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();
                for (int i = 0; i < 109; i++) {
                    System.out.print("=");
                }
                System.out.printf("\n%101s", m.GREEN + "-Check-in time must be between 07:00 and 23:59. Use HH:mm (24-hour) format-");
                System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
                System.out.printf("%31s %-5s", "Original Check In Time:", GuestCheckInTime);
                while (true) {
                    System.out.printf("\n%17s %14s", "Change to", ": ");
                    UpdatedCheckInTime = m.input.nextLine();
                    m.returnMatch = m.returnCommand.matcher(UpdatedCheckInTime);
                    if (UpdatedCheckInTime.contains(" ")) {
                        System.out.printf("%62s", m.RED + "Invalid input! Check in time cannot be empty." + m.RESET);
                        continue;
                    }
                    else if (m.returnMatch.matches()) {
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    if (!UpdatedCheckInTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                        continue;
                    }
                    try {
                        LocalTime earliesttime = LocalTime.of(7, 0);
                        LocalTime latesttime = LocalTime.of(23, 59);
                        LocalTime newtTime = LocalTime.parse(UpdatedCheckInTime, m.timeformat);
                        if (newtTime.isBefore(earliesttime) || newtTime.isAfter(latesttime)) {
                            System.out.printf("%63s", m.RED + "Check-in time must be between 07:00 and 23:59." + m.RESET);
                            continue;
                        }
                        UpdatedCheckInTime = newtTime.format(m.timeformat);
                        System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                        break;
                    }
                    catch (Exception e) {
                        System.out.printf("%48s", m.RED + "Invalid time! Please try again." + m.RESET);
                    }
                }
                while (true) {
                    System.out.print("Enter your choice (1 or 2): ");
                    m.userInput = m.input.nextLine();
                    if (m.userInput.contains(" ")) {
                        System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                    }
                    if (m.userInput.equals("1")) {
                        String query = "UPDATE guest SET Check_In_Time = ? WHERE Guest_ID = ?";
                        try {
                            PreparedStatement updateStatement = connection.prepareStatement(query);
                            updateStatement.setString(1, UpdatedCheckInTime);
                            updateStatement.setInt(2, guest.getGuestid());

                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                        catch (Exception e) {
                            System.out.println("Error updating check-in time: " + e.getMessage());
                        }
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    else if (m.userInput.equals("2")) {
                        GuestCheckInTimeModify(guest);
                        return;
                    }
                    else {
                        System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Data Error");
        }       
    }

    public void GuestCheckOutDateModify(Guest guest) {
        Main m = new Main();
        Database d = new Database();
            
        String sql = "SELECT Check_Out_Date, Check_In_Date FROM guest WHERE Guest_ID = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, guest.getGuestid());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                LocalDate GuestCheckOutDate = rs.getDate("Check_Out_Date").toLocalDate();
                String FormattedCheckOutDate = GuestCheckOutDate.format(m.dateformat);
                String UpdatedCheckOutDate;

                LocalDate GuestCheckInDate = rs.getDate("Check_In_Date").toLocalDate();
                
                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();
                for (int i = 0; i < 109; i++) {
                    System.out.print("=");
                }
                System.out.printf("\n%93s", m.GREEN + "-Check-out date must be after check-in date (Use dd/MM/yyyy format)-");
                System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
                System.out.printf("%32s %-10s", "Original Check Out Date:", FormattedCheckOutDate);
                while (true) {
                    System.out.printf("\n%17s %15s", "Change to", ": ");
                    UpdatedCheckOutDate = m.input.nextLine();
                    m.returnMatch = m.returnCommand.matcher(UpdatedCheckOutDate);
                    if (UpdatedCheckOutDate.contains(" ")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Check out date cannot be empty." + m.RESET);
                        continue;
                    }
                    else if (m.returnMatch.matches()) {
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    if (!UpdatedCheckOutDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                        continue;
                    }
                    try {
                        LocalDate newDate2 = LocalDate.parse(UpdatedCheckOutDate, m.dateformat);
                        if (newDate2.isBefore(GuestCheckInDate)) {
                            System.out.printf("%62s", m.RED + "Invalid date! Please follow the instructions." + m.RESET);
                            continue;
                        }
                        UpdatedCheckOutDate = newDate2.format(m.dateformat);
                        System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                        break;
                    }
                    catch (Exception e) {
                        System.out.printf("\n%49s", m.RED + "Invalid date! Please try again." + m.RESET);
                    }
                }
                while (true) {
                    System.out.print("Enter your choice (1 or 2): ");
                    m.userInput = m.input.nextLine();
                    if (m.userInput.contains(" ")) {
                        System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                    }
                    if (m.userInput.equals("1")) {
                        String query = "UPDATE guest SET Check_Out_Date = ? WHERE Guest_ID = ?";
                        try {
                            PreparedStatement updateStatement = connection.prepareStatement(query);
                            updateStatement.setString(1, UpdatedCheckOutDate);
                            updateStatement.setInt(2, guest.getGuestid());

                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                        catch (Exception e) {
                            System.out.println("Error updating check-out date: " + e.getMessage());
                        }
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    else if (m.userInput.equals("2")) {
                        GuestCheckOutDateModify(guest);
                        return;
                    }
                    else {
                        System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Data Error");
        }
    }

    public void GuestCheckOutTimeModify(Guest guest) {
        Main m = new Main();
        Database d = new Database();

        String sql = "SELECT Check_Out_Time FROM guest WHERE Guest_ID = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, guest.getGuestid());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                LocalTime GuestCheckOutTime = rs.getTime("Check_Out_Time").toLocalTime();
                String UpdatedCheckOutTime;

                System.out.print("\033[H\033[2J");
                System.out.flush();
                m.Logo();
                for (int i = 0; i < 109; i++) {
                    System.out.print("=");
                }
                System.out.printf("\n%93s", m.GREEN + "-Check-out date must be after check-in date (Use dd/MM/yyyy format)-");
                System.out.printf("\n%88s", m.YELLOW + "Enter \"Return\" to return to the previous page\n\n" + m.RESET);
                System.out.printf("%32s %-10s", "Original Check Out Date:", GuestCheckOutTime);
                while (true) {
                    System.out.printf("\n%17s %15s", "Change to", ": ");
                    UpdatedCheckOutTime = m.input.nextLine();
                    m.returnMatch = m.returnCommand.matcher(UpdatedCheckOutTime);
                    if (UpdatedCheckOutTime.contains(" ")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Check out time cannot be empty." + m.RESET);
                        continue;
                    }
                    else if (m.returnMatch.matches()) {
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    if (!UpdatedCheckOutTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        System.out.printf("%63s", m.RED + "Invalid input! Please follow the instructions." + m.RESET);
                        continue;
                    }
                    try {
                        LocalTime newtTime = LocalTime.parse(UpdatedCheckOutTime, m.timeformat);
                        UpdatedCheckOutTime = newtTime.format(m.timeformat);
                        System.out.printf("\n%44s %10s %25s", "1. Confirm and continue", "|", "2. Continue edit\n\n");
                        break;
                    }
                    catch (Exception e) {
                        System.out.printf("%48s", m.RED + "Invalid time! Please try again." + m.RESET);
                    }
                }
                while (true) {
                    System.out.print("Enter your choice (1 or 2): ");
                    m.userInput = m.input.nextLine();
                    if (m.userInput.contains(" ")) {
                        System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
                    }
                    if (m.userInput.equals("1")) {
                        String query = "UPDATE guest SET Check_Out_Time = ? WHERE Guest_ID = ?";
                        try {
                            PreparedStatement updateStatement = connection.prepareStatement(query);
                            updateStatement.setString(1, UpdatedCheckOutTime);
                            updateStatement.setInt(2, guest.getGuestid());

                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                        catch (Exception e) {
                            System.out.println("Error updating check-out time: " + e.getMessage());
                        }
                        d.DetailsFromDatabase(guest);
                        break;
                    }
                    else if (m.userInput.equals("2")) {
                        GuestCheckOutTimeModify(guest);
                        return;
                    }
                    else {
                        System.out.print(m.RED + "Invalid input! Please enter 1 or 2 only.\n" + m.RESET);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Data Error");
        }
    }
}