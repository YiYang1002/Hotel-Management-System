import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.time.temporal.ChronoUnit;

public class Payment {
    public String HotelName = "GOOD 9 HOTEL";
    public String HotelContactNumber = "1-696-69-0000";
    public String HotelEmail = "g9hotelcustomerservice@gmail.com";

    private double Amount;
    private long Nights;
    private double SST;
    private double ServiceCharge;

    private int PaymentID;
    private int GuestID;
    private int RoomID;
    private double TotalAmount;
    private String SelectedMethod;
    private int RateOfFeedback;
    private String TransactionStatus;


    private LocalDateTime DateTime = LocalDateTime.now();
    private DateTimeFormatter FormatDate = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
    private DateTimeFormatter FormatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

    private String formattedDate = DateTime.format(FormatDate);
    private String formattedTime = DateTime.format(FormatTime);

    public Payment() {
        // Initialize any required fields or leave empty
    }

    public Payment(int PaymentID, int GuestID, int RoomID, double TotalAmount, String SelectedMethod, String TransactionStatus) {
        this.PaymentID = PaymentID;
        this.GuestID = GuestID;
        this.RoomID = RoomID;
        this.TotalAmount = TotalAmount;
        this.SelectedMethod = SelectedMethod;
        this.TransactionStatus = TransactionStatus;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getAmount() {
        return Amount;
    }

    public void setNights(long Nights) {
        this.Nights = Nights;
    }

    public long getNights() {
        return Nights;
    }

    public void setSST(double SST) {
        this.SST = SST;
    }

    public double getSST() {
        return SST;
    }

    public void setServiceCharge(double ServiceCharge) {
        this.ServiceCharge = ServiceCharge;
    }

    public double getServiceCharge() {
        return ServiceCharge;
    }

    public void setTotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setSelectedMethod(String SelectedMethod) {
        this.SelectedMethod = SelectedMethod;
    }

    public String getSelectedMethod() {
        return SelectedMethod;
    }

    public void setRateOfFeedback(int RateOfFeedback) {
        this.RateOfFeedback = RateOfFeedback;
    }

    public int getRateOfFeedback() {
        return RateOfFeedback;
    }

    public void setPaymentID(int PaymentID) {
        this.PaymentID = PaymentID;
    }

    public int getPaymentID() {
        return PaymentID;
    }

    public void Invoice(Room room, BookingInfo bookinginfo) {
        Main m = new Main();
        Database d = new Database();
        Random rand = new Random();

        int InvoiceNumber;
        InvoiceNumber = 10000 + rand.nextInt(90000);

        if (room != null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connt = DriverManager.getConnection(d.url, d.username, d.password);
                PreparedStatement prepareStmt = connt.prepareStatement("SELECT * FROM room WHERE Room_ID = ?");

                // Check the RoomID is same with the guest choose at the Room Menu
                prepareStmt.setInt(1, room.getRoomID());
                try (ResultSet rs = prepareStmt.executeQuery()) {
                    if (rs.next()) {
                        room.setR_Type(rs.getString("Room_Type"));
                        room.setR_Price(rs.getDouble("Price_Per_Night"));
                    }
                    else {
                        connt.close();
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Data error");
            }
        }
        else {
            System.out.println("Error: Room object is null");
        }
        
        setNights(ChronoUnit.DAYS.between(bookinginfo.getcheckInDate(),bookinginfo.getcheckOutDate()));
        setAmount(Nights * room.getR_Price());
        setSST(getAmount() * 0.06);
        setServiceCharge(getAmount() * 0.10);
        setTotalAmount(getAmount() + getSST() + getServiceCharge());

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.printf("%12s %98s", m.WHITE_B + " ", " " + m.RESET);
        System.out.printf("\n%16s %92s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %38s %64s", m.WHITE_B + " " + m.RESET, "                ___     ___  ___", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %35s %67s", m.WHITE_B + " " + m.RESET, "| |\\  | \\    / |   | | |    |", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %38s %64s", m.WHITE_B + " " + m.RESET, "| | \\ |  \\  /  |   | | |    |---", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %38s", m.WHITE_B + " " + m.RESET, "| |  \\|   \\/   |___| | |___ |___");
        System.out.printf("%42s %5s %16s", "INVOICE NO.", InvoiceNumber, m.WHITE_B + " " + m.RESET);
        for (int i = 0; i < 2; i++) {
            System.out.printf("\n%16s %92s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        }
        System.out.printf("\n%16s %12s %-16s %8s %63s", m.WHITE_B + " " + m.RESET, "Date :", formattedDate + ",", formattedTime,  m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %92s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %16s %40s %45s", m.WHITE_B + " " + m.RESET, "Billed to:", "From:", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %5s %-20s %24s %-12s %38s", m.WHITE_B + " " + m.RESET, " ", bookinginfo.getGName(), " ", HotelName, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %9s %-10s %30s %-13s %37s", m.WHITE_B + " " + m.RESET, " +60", bookinginfo.getGcontactNum(), " ", HotelContactNumber, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %84s %18s", m.WHITE_B + " " + m.RESET, HotelEmail, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %92s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %6s", m.WHITE_B + " " + m.RESET, " ");
        for (int i = 0; i < 80; i++) {
          System.out.print("_");
        }
        System.out.printf("%17s \n%16s %8s", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, "|  ");
        for (int i = 0; i < 76; i++) {
          System.out.print("_");
        }
        System.out.printf("%3s %15s \n%16s %8s",  "  |", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, "| |");
        for (int i = 0; i < 76; i++) {
          System.out.print(" ");
        }
        System.out.printf("%3s %15s \n%16s %8s",  "| |", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, "| |");
        System.out.printf("%11s %13s %11s %21s %12s %6s %15s", "ROOM ID", "ROOM Type", "Nights", "Price Per Night(RM)", 
        "Amount(RM)", "| |",  m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %8s %78s %15s", m.WHITE_B + " " + m.RESET, "| |", "| |", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %8s %8s %4s %-6s %4s %8s %9s %-3.2f %6s %11.2f %7s %15s", m.WHITE_B + " " + m.RESET, "| |", room.getRoomID(), " ", 
        room.getR_Type(), "Room", getNights(), " ", room.getR_Price(), " ", getAmount(), "| |", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %8s", m.WHITE_B + " " + m.RESET, "| |");
        for (int i = 0; i < 76; i++) {
          System.out.print("_");
        }
        System.out.printf("%3s %15s", "| |", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %6s", m.WHITE_B + " " + m.RESET, "|");
        for (int i = 0; i < 80; i++) {
          System.out.print("_");
        }
        System.out.printf("%1s %15s \n%16s %92s %1s \n%16s %62s %4s %11.2f %23s",  "|", m.WHITE_B + " " + m.RESET, 
        m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, "Subtotal", " ", Amount, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %62s %4s %11.2f %23s", m.WHITE_B + " " + m.RESET, "SST (6%)", " ", getSST(), m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %62s %4s %11.2f %23s", m.WHITE_B + " " + m.RESET, "Service Charge (10%)", " ", getServiceCharge(), 
        m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %42s", m.WHITE_B + " " + m.RESET, " ");
        for (int i = 0; i < 45; i++) {
          System.out.print("_");
        }
        System.out.printf("%16s \n%16s %92s %1s", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %62s %4s %11.2f %23s", m.WHITE_B + " " + m.RESET, "Total Amount", " ", getTotalAmount(), m.WHITE_B + " " + m.RESET);
        for (int i = 0; i < 5; i++) {
          System.out.printf("\n%16s %92s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        }
        System.out.printf("\n%12s %98s", m.WHITE_B + " ", " " + m.RESET);

        System.out.printf("\n\n%78s", m.YELLOW + "*No refund will be made after payment*" + m.RESET);
        System.out.printf("\n\n%38s %11s %42s", "1. Confirm and continue", "|", "2. Cancel and exit to room menu\n\n");
        while (true) {
            System.out.printf("%34s", "Enter your choice (1 or 2): ");
            m.userInput = m.input.nextLine();
            if (m.userInput.contains(" ")) {
                System.out.printf("%53s", m.RED +"Invalid input! Option cannot be empty.\n" + m.RESET);
                continue;
            }
            if (m.userInput.matches("[12]")) {
                if (m.userInput.equals("1")) {
                    PaymentMethod(room, bookinginfo);
                }
                else {
                    System.out.printf("%56s \n%77s", m.YELLOW + "Are you sure you want to cancel your booking?", 
                    "It will clear all your booking info and return to room menu. " + m.RESET + "(o_O)?");
                    System.out.printf("\n\n%13s %5s %10s", "[Y] Yes", "|", "[N] No\n");
                    while (true) {
                        System.out.printf("\n%34s", "Enter your choice (Y or N): ");
                        m.userInput = m.input.nextLine();
                        if (m.userInput.contains(" ")) {
                            System.out.printf("%53s", m.RED +"Invalid input! Option cannot be empty." + m.RESET);
                            continue;
                        }
                        if (m.userInput.matches("[yYnN]")) {
                            if (m.userInput.equals("Y") || m.userInput.equals("y")) {
                                room.RoomMenu();
                                break;
                            }
                            else {
                                Invoice(room, bookinginfo);
                                break;
                            }
                        }
                        else {
                            System.out.printf("%51s", m.RED +"Invalid option. Please enter Y or N." + m.RESET);
                            continue;
                        }
                    }
                }
            }
            else {
                System.out.printf("%51s", m.RED +"Invalid option. Please enter 1 or 2.\n" + m.RESET);
            }
        }
    }

    public void PaymentMethod(Room room, BookingInfo bookinginfo) {
        Main m = new Main();

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.printf("\n%75s", m.GREEN + "-Choose Payment Method-" + m.RESET);
            System.out.printf("\n%14s %3s %26s %3s %21s %3s %33s", "1. Touch n' Go", "|", "2. Credit or Debit Card", "|", "3. Cash on Arrival", "|", "4. Cancel and Exit to Room Menu");
            System.out.print("\n\n\nEnter your choice (1, 2, 3 or 4): ");
            m.userInput = m.input.nextLine();
            if (m.userInput.equals(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED +"Invalid input! Option cannot be empty." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
            switch (m.userInput) {
                case "1":
                    setSelectedMethod("Touch n' Go");
                    TNGMethod(room, bookinginfo);
                    break;
                case "2":
                    setSelectedMethod("Credit or Debit Card");
                    CardMethod(room, bookinginfo);
                    break;
                case "3":
                    setSelectedMethod("Cash on Arrival");
                    CashMethod(room, bookinginfo);
                    break;
                case "4":
                    System.out.print(m.YELLOW + "Are you sure you want to cancel your booking?" +
                     "\nIt will clear all your booking info and return to room menu. " + m.RESET + "(o_O)?");
                    System.out.printf("%6s %5s %10s", "\n\n[Y] Yes", "|", "[N] No\n");
                    while (true) {
                        System.out.print("\nEnter your choice (Y or N): ");
                        m.userInput = m.input.nextLine();
                        if (m.userInput.contains(" ")) {
                            System.out.printf("%53s", m.RED +"Invalid input! Option cannot be empty." + m.RESET);
                            continue;
                        }
                        if (m.userInput.matches("[yYnN]")) {
                            if (m.userInput.equals("Y") || m.userInput.equals("Y")) {
                                room.RoomMenu();
                                break;
                            }
                            else {
                                PaymentMethod(room, bookinginfo);
                                break;
                            }
                        }
                        else {
                            System.out.print(m.RED + "Invalid option. Please enter Y or N." + m.RESET);
                            continue;
                        }
                    }
                    break;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(m.RED +"Invalid input! Please enter 1, 2, 3 or 4." + m.RESET);
                    System.out.print("Please press ENTER to continue...");
                    m.key = m.input.nextLine();
                    continue;
            }
        }
    }

    public void TNGMethod(Room room, BookingInfo bookinginfo) {
        Main m = new Main();
        String TNGNumber;

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.printf("\n%79s", m.GREEN + "-Method Selected: 'Touch n' Go'-" + m.RESET);
            System.out.printf("\n%87s", m.YELLOW + "Enter \"Return\" to return to the previous page." + m.RESET);
            System.out.printf("\n\n%22s \n%30s %10s", "Contact Number", m.PURPLE + "(without '-')" + m.RESET, ": +60");
            TNGNumber = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(TNGNumber);
            if (m.returnMatch.matches()) {
                PaymentMethod(room, bookinginfo);
                break;
            }
            else if (TNGNumber.contains(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED +"Invalid input! Option cannot be empty." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
            if (TNGNumber.matches("[0-9]{9,10}")) {
                PaymentConfirmation(room, bookinginfo);
                break;
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED + "Invalid input! Contact number must be within 10 numbers." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
        }
    }

    public void CardMethod(Room room, BookingInfo bookinginfo) {
        Main m = new Main();
        String CardNumber;
        String ExpiryDate;
        String CVV;
        String CardName;

        System.out.print("\033[H\033[2J");
        System.out.flush();
        m.Logo();
        for (int i = 0; i < 109; i++) {
            System.out.print("=");
        }
        System.out.printf("\n%84s", m.GREEN + "-Method Selected: 'Credit or Debit Card'-" + m.RESET);
        System.out.printf("\n%87s", m.YELLOW + "Enter \"Return\" to return to the previous page." + m.RESET);
        while (true) { // Card Number
            System.out.printf("\n%19s \n%38s %2s", "Card Number", m.PURPLE + "(XXXX-XXXX-XXXX-XXXX)" + m.RESET, ": ");
            CardNumber = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(CardNumber);
            if (CardNumber.contains(" ")) {
                System.out.printf("%60s", m.RED +"Invalid input! Card number cannot be empty." + m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                PaymentMethod(room, bookinginfo);
                break;
            }
            if (CardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) { // XXXX-XXXX-XXXX-XXXX format checking (\\d for digit 0-9)
                break;
            }
            else {
                System.out.printf("%72s", m.RED +"Invalid input! Please use (XXXX-XXXX-XXXX-XXXX) format." + m.RESET);
                continue;
            }
        }

        while (true) { // Expiry Date
            System.out.printf("%19s \n%24s %16s", "Expiry Date", m.PURPLE + "(MM/YY)" + m.RESET, ": ");
            ExpiryDate = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(ExpiryDate);
            if (ExpiryDate.contains(" ")) {
                System.out.printf("%61s", m.RED +"Invalid input! Card Number cannot be empty.\n" + m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                PaymentMethod(room, bookinginfo);
                break;
            }
            if (ExpiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
                // Get the local year last 2 digit
                int currentYear = java.time.Year.now().getValue() % 100;
                // Get the local month value
                int currentMonth = java.time.MonthDay.now().getMonthValue();
                
                int MonthValiyear = Integer.parseInt(ExpiryDate.substring(3,5)); // Take the digit after '/'
                int MonthValimonth = Integer.parseInt(ExpiryDate.substring(0, 2)); // Take the 'ExpiryDate' first 2 digit
                if (MonthValiyear > currentYear || (MonthValiyear == currentYear && MonthValimonth >= currentMonth)) {
                    break;
                }
                else {
                    System.out.printf("%75s", m.RED +"Invalid input! Card has expired. Please use a valid card.\n" + m.RESET);
                    continue;
                }
            }
            else {
                System.out.printf("%59s", m.RED +"Invalid input! Please use (MM/YY) format.\n" + m.RESET);
                continue;
            }
        }

        while (true) { // CVV
            System.out.printf("%11s %20s", "CVV", ": ");
            CVV = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(CVV);
            if (CVV.contains(" ")) {
                System.out.printf("%53s", m.RED +"Invalid input! CVV cannot be empty.\n" + m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                PaymentMethod(room, bookinginfo);
                break;
            }
            if (CVV.matches("[0-9]{3}")) {
                break;
            }
            else {
                System.out.printf("%71s", m.RED +"Invalid input! Please enter a valid 3-digit CVV code.\n" + m.RESET);
                continue;
            }
        }

        while (true) { // Card Name
            System.out.printf("%17s %14s", "Card Name", ": ");
            CardName = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(CardName);
            if (CardName.isEmpty()) {
                System.out.printf("%58s", m.RED +"Invalid input! Card name cannot be empty.\n" + m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                PaymentMethod(room, bookinginfo);
                break;
            }
            if (CardName.matches("[a-zA-Z\\s]{3,20}")) {
                if (CardName.replaceAll("\\s+", "").length() >= 3) {
                    break;
                }
                else {
                    System.out.printf("%97s", m.RED +"Invalid input! Card name must between 3 and 20 letters (no numbers or symbols).\n" + m.RESET);
                }
            }
            else {
                System.out.printf("%97s", m.RED +"Invalid input! Card name must between 3 and 20 letters (no numbers or symbols).\n" + m.RESET);
            }
        }
        
        PaymentConfirmation(room, bookinginfo);
    }

    public void CashMethod(Room room, BookingInfo bookinginfo) {
        Main m = new Main();

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.printf("\n%81s", m.GREEN + "-Method Selected: 'Cash on Arrival'-" + m.RESET);
            System.out.printf("\n%108s", m.YELLOW + "*Please note that it will lead to restrictions on future bookings if you do not show up*" + m.RESET);
            System.out.printf("\n\n%37s %10s %47s","Press 'C/c' to Continue", "|", "Press 'R/r' to Return to Previous Page");
            System.out.print("\n\nEnter your choice (C or R): ");
            m.userInput = m.input.nextLine();
            if (m.userInput.contains(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED +"Invalid input! Option cannot be empty." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
            if (m.userInput.matches("[cCRr]")) {
                if (m.userInput.equals("C") || m.userInput.equals("c")) {
                    Receipt(room, bookinginfo);
                    break;
                }
                else {
                    PaymentMethod(room, bookinginfo);
                    break;
                }
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED +"Invalid option! Please enter C/c or R/r." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
        }
    }

    public void PaymentConfirmation(Room room, BookingInfo bookinginfo) {
        Main m = new Main();
        Employee e = new Employee();

        System.out.printf("\n\n%81s", "Please confirm your payment details before proceeding.");
        System.out.printf("\n\n%43s %10s %48s", "1. Confirm and Complete the Payment", "|", "2. Cancel and Return to Previous Page\n\n");
        while (true) {
            System.out.print("Enter your choice (1 or 2): ");
            m.userInput = m.input.nextLine();
            if (m.userInput.contains(" ")) {
                System.out.print(m.RED + "Invalid input! Option cannot be empty.\n" + m.RESET);
            }
            if (m.userInput.matches("[12]")) {
                if (m.userInput.equals("1")) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Transaction in progress......");
                    e.LoadingBar();
                    System.out.println("\nPayment successful. Thank you!");
                    System.out.print("Please press ENTER to continue...");
                    m.key = m.input.nextLine();
                    Receipt(room, bookinginfo);
                    break;
                }
                else {
                    PaymentMethod(room, bookinginfo);
                    break;
                }
            }
            else {
                System.out.print(m.RED + "Invalid input! Please enter 1 or 2.\n" + m.RESET);
            }
        }
    }

    public void Receipt(Room room, BookingInfo bookinginfo) {
        Main m = new Main();
        Random rand = new Random();
        Database d = new Database();

        setPaymentID(1000 + rand.nextInt(9000)); // Generate the Payment ID

        d.GuestDetailStore(bookinginfo); // Store the guest details to the database
        d.RoomStatusUpdate(room); // Update the room (unavailable & booked)
        d.PaymentDetailsStore(this, room); // Store the payment details to the database

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.printf("%12s %80s", m.WHITE_B + " ", " " + m.RESET);
        System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %61s %23s", m.WHITE_B + " " + m.RESET, " ________ ________ ________ ________          ____", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %63s %21s", m.WHITE_B + " " + m.RESET, "|   _____|   __   |   __   |   ____  \\      /  __  \\", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %64s %20s", m.WHITE_B + " " + m.RESET, "|  |  ___|  |  |  |  |  |  |  |    |  |    |  |__|  /", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %63s %21s", m.WHITE_B + " " + m.RESET, "|  | |__ |  |  |  |  |  |  |  |    |  |     \\___   /", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %62s %22s", m.WHITE_B + " " + m.RESET, "|  |__|  |  |__|  |  |__|  |  |____|  |        /  /", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %61s %23s", m.WHITE_B + " " + m.RESET, "|________|________|________|_________/        /  /", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %60s %24s", m.WHITE_B + " " + m.RESET, " __    __ ________ ________ _______ __     /__/", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %52s %32s", m.WHITE_B + " " + m.RESET, "|  |  |  |   __   |__    __|   ____|  |", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %52s %32s", m.WHITE_B + " " + m.RESET, "|  |__|  |  |  |  |  |  |  |  |___ |  |", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %52s %32s", m.WHITE_B + " " + m.RESET, "|   __   |  |  |  |  |  |  |   ___||  |", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %56s %28s", m.WHITE_B + " " + m.RESET, "|  |  |  |  |__|  |  |  |  |  |____|  |____", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %57s %27s", m.WHITE_B + " " + m.RESET, "|__|  |__|________|  |__|  |_______|_______|", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %3s", m.WHITE_B + " " + m.RESET, " ");
        for (int i = 0; i < 68; i ++) {
          System.out.print("-");
        }
        System.out.printf("%14s \n%16s %74s %1s", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %43s %41s", m.WHITE_B + " " + m.RESET, HotelName, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %57s %27s", m.WHITE_B + " " + m.RESET, "EMAIL# " + HotelEmail, m.WHITE_B + " " + m.RESET);

        System.out.printf("\n%16s %46s %38s", m.WHITE_B + " " + m.RESET, "TEL# " + HotelContactNumber, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %8s %36s %25s %13s", m.WHITE_B + " " + m.RESET, "DATE#", formattedDate, formattedTime, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %14s %56s %13s", m.WHITE_B + " " + m.RESET, "PAYMENT ID#", getPaymentID(), m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %18s %52s %13s", m.WHITE_B + " " + m.RESET, "PAYMENT METHOD#", getSelectedMethod(), m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %74s %1s\n%16s %3s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, 
        " ");
        for (int i = 0; i < 68; i ++) {
          System.out.print("-");
        }
        System.out.printf("%14s \n%16s %14s %18s %23s %13s %13s", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, "Description", 
        "Nights", "Price per Night(RM)", "Amount(RM)", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %3s", m.WHITE_B + " " + m.RESET, " ");
        for (int i = 0; i < 68; i ++) {
          System.out.print("-");
        }
        System.out.printf("%14s \n%16s %18s %12s %18.2f %19.2f %14s", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, room.getRoomID() + 
        " " + room.getR_Type() + " Room", getNights(), room.getR_Price(), getAmount(), m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %11s %38s %19.2f %14s", m.WHITE_B + " " + m.RESET, "Subtotal", " ", getAmount(), m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %10s %39s %19.2f %14s", m.WHITE_B + " " + m.RESET, "SST(6%)", " ", SST, m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %22s %27s %19.2f %14s", m.WHITE_B + " " + m.RESET, "Service Charge(10%)", " ", 
        getServiceCharge(), m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %59s", m.WHITE_B + " " + m.RESET, " ");
        for (int i = 0; i < 12; i ++) {
          System.out.print("-");
        }
        System.out.printf("%14s \n%16s %74s %1s", m.WHITE_B + " " + m.RESET, m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %15s %54.2f %14s", m.WHITE_B + " " + m.RESET, "Total Amount", getTotalAmount(), m.WHITE_B + " " + m.RESET);
        for (int i = 0; i < 2; i ++) {
          System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        }
        System.out.printf("\n%16s %60s %24s", m.WHITE_B + " " + m.RESET, "Thank you for staying with us at GOOD 9 HOTEL!", 
        m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %64s %20s", m.WHITE_B + " " + m.RESET, "To track your booking, please use your Guest ID: " + 
        d.getGuestID() + ".", m.WHITE_B + " " + m.RESET);
        System.out.printf("\n%16s %56s %28s", m.WHITE_B + " " + m.RESET, "We look forward to welcoming you again.", m.WHITE_B + " " + m.RESET);
        for (int i = 0; i < 2; i ++) {
          System.out.printf("\n%16s %74s %1s", m.WHITE_B + " " + m.RESET, " ", m.WHITE_B + " " + m.RESET);
        }
        System.out.printf("\n%12s %80s", m.WHITE_B + " ", " " + m.RESET);
        System.out.printf("\n\n%32s", "Press Enter to continue...");
        m.key = m.input.nextLine();
        Feedback(bookinginfo);
    }

    public void Feedback(BookingInfo bookinginfo) {
        Main m = new Main();
        OptionSelect os = new OptionSelect();

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.printf("\n%94s", m.GREEN + "-How would you rate your experience with our hotel system?-" + m.RESET);
            System.out.printf("\n\n%58s %31s", m.YELLOW + "[1] [2] [3] [4] [5]" + m.RESET, "(1 = Very Poor, 5 = Excellent)");
            System.out.printf("\n\n%29s", "Enter your rating (1-5): ");
            m.userInput = m.input.nextLine();
            if (m.userInput.contains(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(m.RED +"Invalid input! Option cannot be empty." + m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
            switch (m.userInput) {
                case "1":
                    setRateOfFeedback(1);
                    ThankYou();
                    for (int i = 0; i < 76; i++) {
                        System.out.print("=");
                    }
                    System.out.println("\nCheck-out Details:");
                    System.out.printf("%10s %21s", "Time",": [" + bookinginfo.getFormattedCheckOutTime() + "]");
                    System.out.printf("\n%21s %65s", "Room Key Return",": [Front desk or key drop box (" + m.YELLOW + 
                    "located near the lobby" + m.RESET + ")]");
                    System.out.printf("\n%24s %17s", "Invoice & Receipt:","[Sent to +60 " + bookinginfo.getGcontactNum() + "]");
                    System.out.println("\n\nNeed Assistance?");
                    System.out.print("Call or Text: " + m.YELLOW + "HOTEL FRONT DESK: " + HotelContactNumber + m.RESET);
                    try {
                        Thread.sleep(12000);
                    } 
                    catch (Exception e) {
                        System.out.println("\nData error");
                    }
                    os.Welcome();
                    break;
                case "2":
                    setRateOfFeedback(2);
                    ThankYou();
                    for (int i = 0; i < 76; i++) {
                        System.out.print("=");
                    }
                    System.out.println("\nCheck-out Details:");
                    System.out.printf("%10s %21s", "Time",": [" + bookinginfo.getFormattedCheckOutTime() + "]");
                    System.out.printf("\n%21s %65s", "Room Key Return",": [Front desk or key drop box (" + m.YELLOW + 
                    "located near the lobby" + m.RESET + ")]");
                    System.out.printf("\n%24s %17s", "Invoice & Receipt:","[Sent to +60 " + bookinginfo.getGcontactNum() + "]");
                    System.out.println("\n\nNeed Assistance?");
                    System.out.print("Call or Text: " + m.YELLOW + "HOTEL FRONT DESK: " + HotelContactNumber + m.RESET);
                    try {
                        Thread.sleep(12000);
                    } 
                    catch (Exception e) {
                        System.out.println("\nData error");
                    }
                    os.Welcome();
                    break;
                case "3":
                    setRateOfFeedback(3);
                    ThankYou();
                    for (int i = 0; i < 76; i++) {
                        System.out.print("=");
                    }
                    System.out.println("\nCheck-out Details:");
                    System.out.printf("%10s %21s", "Time",": [" + bookinginfo.getFormattedCheckOutTime() + "]");
                    System.out.printf("\n%21s %65s", "Room Key Return",": [Front desk or key drop box (" + m.YELLOW + 
                    "located near the lobby" + m.RESET + ")]");
                    System.out.printf("\n%24s %17s", "Invoice & Receipt:","[Sent to +60 " + bookinginfo.getGcontactNum() + "]");
                    System.out.println("\n\nNeed Assistance?");
                    System.out.print("Call or Text: " + m.YELLOW + "HOTEL FRONT DESK: " + HotelContactNumber + m.RESET);
                    try {
                        Thread.sleep(12000);
                    } 
                    catch (Exception e) {
                        System.out.println("\nData error");
                    }
                    os.Welcome();
                    break;
                case "4":
                    setRateOfFeedback(4);
                    ThankYou();
                    for (int i = 0; i < 76; i++) {
                        System.out.print("=");
                    }
                    System.out.println("\nCheck-out Details:");
                    System.out.printf("%10s %21s", "Time",": [" + bookinginfo.getFormattedCheckOutTime() + "]");
                    System.out.printf("\n%21s %65s", "Room Key Return",": [Front desk or key drop box (" + m.YELLOW + 
                    "located near the lobby" + m.RESET + ")]");
                    System.out.printf("\n%24s %17s", "Invoice & Receipt:","[Sent to +60 " + bookinginfo.getGcontactNum() + "]");
                    System.out.println("\n\nNeed Assistance?");
                    System.out.print("Call or Text: " + m.YELLOW + "HOTEL FRONT DESK: " + HotelContactNumber + m.RESET);
                    try {
                        Thread.sleep(12000);
                    } 
                    catch (Exception e) {
                        System.out.println("\nData error");
                    }
                    os.Welcome();
                    break;
                case "5":
                    setRateOfFeedback(5);
                    ThankYou();
                    for (int i = 0; i < 76; i++) {
                        System.out.print("=");
                    }
                    System.out.println("\nCheck-out Details:");
                    System.out.printf("%10s %21s", "Time",": [" + bookinginfo.getFormattedCheckOutTime() + "]");
                    System.out.printf("\n%21s %65s", "Room Key Return",": [Front desk or key drop box (" + m.YELLOW + 
                    "located near the lobby" + m.RESET + ")]");
                    System.out.printf("\n%24s %17s", "Invoice & Receipt:","[Sent to +60 " + bookinginfo.getGcontactNum() + "]");
                    System.out.println("\n\nNeed Assistance?");
                    System.out.print("Call or Text: " + m.YELLOW + "HOTEL FRONT DESK: " + HotelContactNumber + m.RESET);
                    try {
                        Thread.sleep(12000);
                    } 
                    catch (Exception e) {
                        System.out.println("\nData error");
                    }
                    os.Welcome();
                    break;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(m.RED +"Invalid input! Please enter 1, 2, 3, 4 or 5." + m.RESET);
                    System.out.print("Please press ENTER to continue...");
                    m.key = m.input.nextLine();
                    continue;
            }
        }
    }

    public void ThankYou() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print(
        "     _______ _    _ ______ __    _ _   __    __    __ ________ _      _\n" +
        "    |__   __| |  | |  __  |  \\  | | | / /    \\ \\  / /|  ____  | |    | |\n" +
        "       | |  | |__| | |__| | \\ \\ | | |/ /      \\ \\/ / | |    | | |    | |\n" +
        "       | |  |  __  |  __  | |\\ \\| | | \\        |  |  | |    | | |    | |\n" +
        "       | |  | |  | | |  | | | \\ \\ | |\\ \\       |  |  | |____| | |____| |\n" +
        "       |_|  |_|  |_|_|  |_|_|  \\__|_| \\_\\      |__|  |________|________|   \n");
    }

    public String toString() {
        return String.format("\t\t\t\t\tPayment ID        : %d" + "\n" + "\t\t\t\t\tGuest ID          : %d" + "\n" + 
        "\t\t\t\t\tRoom ID           : %d" + "\n" + "\t\t\t\t\tTotal Amount      : RM%.2f" + "\n" + "\t\t\t\t\tPayment Method    : %s" + 
        "\n" + "\t\t\t\t\tTransaction Status: %s", PaymentID, GuestID, RoomID, TotalAmount, SelectedMethod, TransactionStatus);
    }
}