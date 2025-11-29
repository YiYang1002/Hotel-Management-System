import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

// Type of exception that occurs when there's an error while parsing a date or time string.
import java.time.format.DateTimeParseException;

public class Guest extends PersonalInfo {
    private static String ModifyOption;
    private int Guestid;

    public void setGuestid(int Guestid) {
        this.Guestid = Guestid;
    }

    public int getGuestid() {
        return Guestid;
    }

    public void displayMenu() {
        Main m = new Main();
        OptionSelect os = new OptionSelect();
        Room r = new Room();

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.println();   
            System.out.print(m.GREEN);
            System.out.printf("%69s", "-Select the service you need-");
            System.out.print(m.RESET);
            System.out.println();
            System.out.printf("%29s %7s %27s %7s %28s", "1. View Available Rooms", "|", "2. Modify Reservation", "|", "3. Return to Main Page");
            System.out.print("\n\n\nEnter your choice (1, 2 or 3): ");
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
                int chooseCust = Integer.parseInt(m.userInput);

                if (chooseCust == 1) {
                    r.RoomMenu();
                }
                else if (chooseCust == 2) {
                    ModifyReservation();
                }
                else {
                    os.Welcome();
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
        m.input.close();
    }

    public void GuestMenu(){
        displayMenu();
    }

    public BookingInfo RoomBooking(Room room) {
        Main m = new Main();

        String GName = "";
        String GAge = "";
        String GcontactNum = "";
        LocalDate checkInDate;
        LocalTime checkInTime;
        LocalDate checkOutDate;
        LocalTime checkOutTime;

        System.out.print("\033[H\033[2J");
        System.out.flush();
        m.Logo();
        for (int i = 0; i < 109; i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.print(m.GREEN);
        System.out.printf("%72s", "-Please fill in your information-\n");
        System.out.print(m.YELLOW);
        System.out.printf("%90s", "*Please note that check-in is available only from 07:00 to 23:59 only*\n");
        System.out.printf("%79s", "Enter \"Return\" to return to the previous page\n\n");
        System.out.print(m.RESET);

        while (true) { // Guest Name
            System.out.printf("%12s %21s", "Name", ": ");
            GName = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(GName);
            if (GName.isEmpty()) {
                System.out.print(m.RED);
                System.out.printf("%47s","Invalid input! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                room.RoomMenu();
                return null;
            }
            if (GName.matches("[a-zA-Z\\s]{3,20}")) {
                // Remove input spaces for length check
                if (GName.replaceAll("\\s+", "").length() >= 3) {
                    break;
                }
                else {
                    System.out.print(m.RED);
                    System.out.printf("%88s","Invalid input! You must enter between 3 and 20 letters (no numbers or symbols).\n");
                    System.out.print(m.RESET);
                }
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%88s","Invalid input! You must enter between 3 and 20 letters (no numbers or symbols).\n");
                System.out.print(m.RESET);
            }
        }

        while (true) { // Guest Age
            System.out.printf("%11s %22s", "Age", ": ");
            GAge = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(GAge);
            if (GAge.contains(" ")) {
                System.out.print(m.RED);
                System.out.printf("%47s","Invalid input! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                room.RoomMenu();
                return null;
            }
            if (!GAge.matches("[0-9]{2}")) {
                System.out.print(m.RED);
                System.out.printf("%106s","Invalid input! Age must be greater than or equal to 18 and between 99 (no characters or symbols).\n");
                System.out.print(m.RESET);
                continue;
            }
            // Converts the string GAge to a primitive int value
            int G_Age = Integer.parseInt(GAge);
            if (GAge.matches("[0-9]{2}") && G_Age >= 18 && G_Age <= 99) {
                break;
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%64s","Invalid input! Age must be greater than or equal to 18.\n");
                System.out.print(m.RESET);
            }
        }

        while (true) { // Guest Contact Number
            System.out.printf("%22s \n%30s %15s", "Contact Number", m.PURPLE + "(without '-')" + m.RESET, ": +60");
            GcontactNum = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(GcontactNum);
            if (GcontactNum.contains(" ")) {
                System.out.print(m.RED);
                System.out.printf("%47s","Invalid input! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                room.RoomMenu();
                return null;
            }
            if (GcontactNum.matches("[0-9]{9,10}")) {
                break;
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%65s", "Invalid input! Contact number must be within 10 numbers.\n");
                System.out.print(m.RESET);
            }
        }

        // CheckIn/Out date and time
        checkInDate = getDate(m.input, "Check-In Date \n\t" + m.PURPLE + "(dd/MM/yyyy)" + m.RESET, true);

        // do validation 00:00 - 06:59 cannot
        checkInTime = getTime(m.input, "Check-In Time \n\t" + m.PURPLE + "(HH:mm)" + m.RESET);
        LocalTime earliesttime = LocalTime.of(7, 0);     // check the time 07:00
        LocalTime latesttime = LocalTime.of(23, 59); // check the time 23:59
        while (checkInTime.isBefore(earliesttime) || checkInTime.isAfter(latesttime)) {
            System.out.printf(m.RED + "%68s", "Check-in available 7:00 - 23:59 only. Please try again.\n" +m.RESET);
            checkInTime = getTime(m.input, "Check-In Time \n\t" + m.PURPLE + "(HH:mm)" + m.RESET);
        }
        checkOutDate = getDate(m.input, "Check-Out Date\n\t" + m.PURPLE + "(dd/MM/yyyy)" + m.RESET, false);

        // Check the time and date of "checkOut" is before "checkIn"
        while (!checkOutDate.isAfter(checkInDate)) {
            System.out.printf("%79s", m.RED + "Check-out date must be after check-in date. Please try again.\n" + m.RESET);
            checkOutDate = getDate(m.input, "Check-Out Date\n\t" + m.PURPLE + "(dd/MM/yyyy)" + m.RESET, false);
        }
        checkOutTime = getTime(m.input, "Check-Out Time\n\t" + m.PURPLE + "(HH:mm)" + m.RESET);
        System.out.printf("\n%34s", "Press ENTER to continue...");
        m.key = m.input.nextLine();

        BookingInfo bookingInfo = new BookingInfo(GName, GAge, GcontactNum, checkInDate, checkInTime, checkOutDate, checkOutTime);
        BookingConfirmation(room, bookingInfo);

        return bookingInfo;
    }
      
    private static LocalDate getDate (Scanner scanner, String prompt, boolean isCheckIn) { // To handle checkiIn & Out date
        Room r = new Room();
        Main m = new Main();
        LocalDate date = null;
        boolean validInput = false;
        LocalDate currentDate = LocalDate.now(); // Detect the current date

        while (!validInput) {
            System.out.printf("%45s", prompt);
            System.out.printf("%14s", ": ");
            String userInput = scanner.nextLine();
            m.returnMatch = m.returnCommand.matcher(userInput);
            if (userInput.contains(" ")) {
              System.out.printf("%55s", m.RED + "Invalid input! Input cannot be empty.\n" + m.RESET);
              continue;
            }
            else if (m.returnMatch.matches()) {
              r.RoomMenu();
              return null;
            }
            if (!userInput.contains("/")) {
              System.out.printf("%63s", m.RED + "Invalid format! Please use dd/MM/yyyy format.\n" + m.RESET);
              continue;
            }
            try {
              date = LocalDate.parse(userInput, m.dateformat); // Check user input is same with the date format or not
              if (isCheckIn && date.isBefore(currentDate)) {
                System.out.printf("%72s", m.RED + "Check-in date cannot be in the past. Please try again.\n" + m.RESET);
                continue;
              }
              validInput = true;
            }
            catch (DateTimeParseException e) {
              System.out.print(m.RED);
              System.out.printf("%40s", "Invalid date! Please try again.\n");
              System.out.print(m.RESET);
            }
        }
        return date;
    }

    private static LocalTime getTime (Scanner scanner, String prompt) { // To handle checkiIn & Out time
        Room r = new Room();
        Main m = new Main();
        LocalTime time = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.printf("%40s", prompt);
            System.out.printf("%19s", ": ");
            String userInput = scanner.nextLine();
            m.returnMatch = m.returnCommand.matcher(userInput);
            if (userInput.contains(" ")) {
              System.out.printf("%55s", m.RED + "Invalid input! Input cannot be empty.\n" + m.RESET);
              continue;
            }
            else if (m.returnMatch.matches()) {
              r.RoomMenu();
              return null;
            }
            if (!userInput.contains(":")) {
              System.out.printf("%58s", m.RED + "Invalid format! Please use HH:mm format.\n" + m.RESET);
              continue;
            }
            // Check user input is same with the time format or not
            try {
              time = LocalTime.parse(userInput, m.timeformat);
              validInput = true;
            }
            catch (DateTimeParseException e) {
              System.out.print(m.RED);
              System.out.printf("%40s", "Invalid time! Please try again.\n");
              System.out.print(m.RESET);
            }
        }
        return time;
    }

    public void BookingConfirmation(Room room, BookingInfo bookinginfo) {
        Main m = new Main();
        Payment p = new Payment();

        int MessageSpaceCal = 3 + 38 + bookinginfo.getGName().length();
        int FrontSpaceCal = ((109 - MessageSpaceCal) / 2);

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.println();
            for (int i = 0; i < FrontSpaceCal; i++) {
                System.out.print(" ");
            }
            System.out.print("Hi " + m.YELLOW + bookinginfo.getGName() + m.RESET + " .Please confirm your booking details.\n\n");
            System.out.printf("%9s %6s %12s %-19s", "[1]", "Name", ":", bookinginfo.getGName());
            System.out.printf("\n%9s %5s %13s %-2s", "[2]", "Age", ":", bookinginfo.getGAge());
            System.out.printf("\n%9s %16s %6s %-10s", "[3]", "Contact Number", ": +60", bookinginfo.getGcontactNum());
            System.out.printf("\n%9s %15s \n%33s %4s %-10s", "[4]", "Check-In Date", m.PURPLE + "(dd/MM/yyyy)" 
            + m.RESET, ":", bookinginfo.getFormattedCheckInDate());
            System.out.printf("\n%9s %15s \n%28s %9s %-5s", "[5]", "Check-In Time", m.PURPLE + "(HH:mm)" 
            + m.RESET, ":", bookinginfo.getFormattedCheckInTime());
            System.out.printf("\n%9s %16s \n%33s %4s %-10s", "[6]", "Check-Out Date", m.PURPLE + "(dd/MM/yyyy)" 
            + m.RESET, ":", bookinginfo.getFormattedCheckOutDate());
            System.out.printf("\n%9s %16s \n%28s %9s %-5s", "[7]", "Check-Out Time", m.PURPLE + "(HH:mm)" 
            + m.RESET, ":", bookinginfo.getFormattedCheckOutTime());
            System.out.printf("\n\n%40s %14s %41s", "(Y) Confirm and Proceed to Payment", "|", "(N) Clear All Data and Exit");
            System.out.print(m.YELLOW + "\n*If you need to change the information, please enter the number of the information you need to change*" 
            + m.RESET);
            System.out.print("\n\nEnter your choice (Y or N / 1 - 7): ");
            m.userInput = m.input.nextLine();
            if (m.userInput.contains(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED + "Invalid input! Option cannot be empty." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
            if (m.userInput.matches("[1234567yYnN]")) {
                if (m.userInput.equals("1")) {
                    ModifyOption = "1";
                    bookinginfo.BookingDetailsModify(room, bookinginfo);
                    break;
                }
                else if (m.userInput.equals("2")) {
                    ModifyOption = "2";
                    bookinginfo.BookingDetailsModify(room, bookinginfo);
                    break;
                }
                else if (m.userInput.equals("3")) {
                    ModifyOption = "3";
                    bookinginfo.BookingDetailsModify(room, bookinginfo);
                    break;
                }
                else if (m.userInput.equals("4")) {
                    ModifyOption = "4";
                    bookinginfo.BookingDetailsModify(room, bookinginfo);
                    break;
                }
                else if (m.userInput.equals("5")) {
                    ModifyOption = "5";
                    bookinginfo.BookingDetailsModify(room, bookinginfo);
                    break;
                }
                else if (m.userInput.equals("6")) {
                    ModifyOption = "6";
                    bookinginfo.BookingDetailsModify(room, bookinginfo);
                    break;
                }
                else if (m.userInput.equals("7")) {
                    ModifyOption = "7";
                    bookinginfo.BookingDetailsModify(room, bookinginfo);
                    break;
                }
                else if (m.userInput.equals("Y") || m.userInput.equals("y")) {
                    p.Invoice(room, bookinginfo);
                    break;
                }
                else {
                    room.RoomMenu();
                    break;
                }
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED + "Invalid option. Please enter Y or N / 1 - 7." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
            }
        }
    }

    public static String getModifyOption() {
        return ModifyOption;
    }

    public void ModifyReservation() {
        Main m = new Main();
        Database d = new Database();

        String Guestid;

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.println();
            System.out.print(m.GREEN);
            System.out.printf("%67s", "-Please enter your guest ID-\n");
            System.out.print(m.RESET);
            System.out.print(m.YELLOW);
            System.out.printf("%78s", "Enter \"Return\" to return to the previous page\n\n");
            System.out.print(m.RESET);
            System.out.printf("%30s", "Guest ID: ");
            Guestid = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(Guestid);
            if (m.returnMatch.matches()) {
                GuestMenu();
                break;
            }
            else if (Guestid.contains(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print(m.RED);
                System.out.println("Invalid input! Input cannot be empty.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }

            try {
                int intGuestID = Integer.parseInt(Guestid);
                if (Database.GuestIDChecking(intGuestID, this)) {
                    while (true) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        m.Logo();
                        for (int i = 0; i < 109; i++) {
                          System.out.print("=");
                        }
                        System.out.printf("\n%78s", m.GREEN + "-Select the service you need-\n" + m.RESET);
                        System.out.printf("%27s %6s %31s %6s %27s", "1. Change Room Type", "|", 
                        "2. Modify Booking Details", "|", "3. Return to Main Page");
                        System.out.print("\n\n\nEnter your choice (1-3): ");
                        m.userInput = m.input.nextLine();
                        if (m.userInput.contains(" ")) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(m.RED + "Invalid input! Option cannot be empty." + m.RESET);
                            System.out.print("Please press ENTER to continue...");
                            m.key = m.input.nextLine();
                            continue;
                        }
                        if (m.userInput.matches("[123]")) {
                            if (m.userInput.equals("1")) {
                                System.out.print("\033[H\033[2J");
                                System.out.flush();
                                System.out.println("Changing the room type is temporarily disabled. We appreciate your patience. QAQ");
                                System.out.print("Please press ENTER to continue...");
                                m.key = m.input.nextLine();
                                continue;
                            }
                            else if (m.userInput.equals("2")) {
                                d.DetailsFromDatabase(this);
                            }
                            else {
                                GuestMenu();
                            }
                            break;
                        }
                        else {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(m.RED + "Invalid input! Please enter 1, 2 or 3." + m.RESET);
                            System.out.print("Please press ENTER to continue...");
                            m.key = m.input.nextLine();
                            continue;
                        }
                    }
                    break;
                }
            }
            catch (Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print(m.RED);
                System.out.println("Invalid input. Guest ID was not found.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
            }
        }
    }
} 