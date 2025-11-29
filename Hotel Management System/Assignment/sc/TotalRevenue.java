import java.sql.*;

public class TotalRevenue {
    public void ViewAccept() { // For Admin Panel
        Employee em = new Employee();
        Main m = new Main();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(m.GREEN + "----------------------------Access Accept----------------------------" + m.RESET);
        System.out.println("Generating the total revenue......");
        em.LoadingBar();
        System.out.println("\nComplete!");
        try {
            Thread.sleep(100);
            TotalRevenuePrint();
        } catch (Exception e) {
            System.out.println("\nData error");
        }
    }

    public void ViewDenied() { // For Admin Panel
        Employee em = new Employee();
        Main m = new Main();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(m.RED + "----------------------------Access Denied----------------------------" + m.RESET);
        System.out.println("Only the " + m.YELLOW + "Manager" + m.RESET + " and " + m.YELLOW + "Front Desk Staff" + m.RESET + " have permission to view the total revenue.");
        System.out.print("Please press ENTER to continue...");
        m.key = m.input.nextLine();
        em.EmployeeMenu();
    }

    public void TotalRevenuePrint() {
        Database d = new Database();
        Main m = new Main();
        Employee em = new Employee();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        m.Logo();
        for (int i = 0; i < 109; i++) {
            System.out.print("=");
        }
        System.out.printf("\n%104s", m.GREEN + "----------------------------Total Revenue----------------------------\n\n" + m.RESET);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(d.url, d.username, d.password);

            String query = "SELECT * FROM payment_transactions";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int paymentID = rs.getInt("Payment_ID");
                int guestID = rs.getInt("Guest_ID");
                int roomID = rs.getInt("Room_ID");
                double totalAmount = rs.getDouble("Amount_Paid");
                String selectedMethod = rs.getString("Payment_Method");
                String transactionStatus = rs.getString("Transaction_Status");

                Payment payment = new Payment(paymentID, guestID, roomID, totalAmount, selectedMethod, transactionStatus);

                System.out.println(payment.toString());
                System.out.printf("%34s", "");
                for (int i = 0; i < 46; i++) {
                    System.out.print("-");
                }
                System.out.println();
            }
            while (true) {
                System.out.print("\nEnter (R/r) to return to the employee page: ");
                m.userInput = m.input.nextLine();
                if (m.userInput.contains(" ")) {
                    System.out.print(m.RED);
                    System.out.print("Invalid input! Option cannot be empty.");
                    System.out.print(m.RESET);
                } 
                else if (m.userInput.matches("[rR]")) {
                    em.EmployeeMenu();
                } 
                else {
                    System.out.print(m.RED);
                    System.out.print("Invalid input! Please enter a valid option.");
                    System.out.print(m.RESET);
                }
                conn.close();
            }
        }
        catch (Exception e) {
            System.out.println("Data error: " + e.getMessage());
        }
    }
}