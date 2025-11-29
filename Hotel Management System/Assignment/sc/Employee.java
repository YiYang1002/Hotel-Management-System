import java.sql.*;
import java.util.regex.Pattern;

public class Employee extends PersonalInfo {
    private String name;

    public String Emid;
    public int EmID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void displayMenu() {
        Main m = new Main();
        OptionSelect os = new OptionSelect();

        while(true) {
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
            System.out.printf("%19s %4s %17s %4s %28s %4s %25s", "1. Employee Login", "|", "2. Admin Panel", "|", "3. Register New Employeee", "|", "4. Return to Main Page");            
            System.out.print("\n\n\nEnter your choice (1, 2, 3 or 4): ");
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
                int chooseStaff = Integer.parseInt(m.userInput);

                if (chooseStaff == 1) {
                    EmployeeLogIn();
                }
                else if (chooseStaff == 2) {
                    ViewPermissions();
                }
                else if (chooseStaff == 3){
                    NewEmployee();
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
                System.out.println("Invalid option. Please enter 1, 2, 3 or 4.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
            }
        }
        m.input.close();
    }

    public void EmployeeMenu() {
        displayMenu();
    }

    public void ViewPermissions() {
        Main m = new Main();
        Employee em = new Employee();
        Database d = new Database();
        TotalRevenue tr = new TotalRevenue();

        while (true) {
          System.out.print("\033[H\033[2J");
          System.out.flush();
          m.Logo();
          for (int i = 0; i < 109; i++) {
            System.out.print("=");
          }
          System.out.println();
          System.out.print(m.GREEN);
          System.out.printf("%70s", "-Please enter your employee ID-\n");
          System.out.print(m.RESET);
          System.out.print(m.YELLOW);
          System.out.printf("%78s", "Enter \"Return\" to return to the previous page\n\n");
          System.out.print(m.RESET);
          System.out.printf("%33s", "Employee ID: ");
          Emid = m.input.nextLine();
          m.returnMatch = m.returnCommand.matcher(Emid);
          if (m.returnMatch.matches()) {
            em.EmployeeMenu();
            break;
          }
          else if (Emid.contains(" ")) {
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
    
            String query = "SELECT Role FROM employees WHERE Employee_ID = ?";
            EmID = Integer.parseInt(Emid);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, EmID);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
              String EmployeeRole = resultSet.getString("Role");
              switch (EmployeeRole) {
                case "Manager":
                  tr.ViewAccept();
                  return;
    
                case "Front Desk Staff":
                  tr.ViewAccept();
                  return;
              
                default:
                  tr.ViewDenied();
                  return;
              }
            }
            else {
              System.out.print("\033[H\033[2J");
              System.out.flush();
              System.out.println(m.RED + "Invalid input. Employee ID was not found." + m.RESET);
              System.out.print("Please press ENTER to continue...");
              m.key = m.input.nextLine();
            }
          }
          catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.print(m.RED);
            System.out.println("Invalid input. Employee ID was not found.");
            System.out.print(m.RESET);
            System.out.print("Please press ENTER to continue...");
            m.key = m.input.nextLine();
          }
        }
      }

    public void EmployeeLogIn() {
        Main m = new Main();
        
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.println();
            System.out.print(m.GREEN);
            System.out.printf("%70s", "-Please enter your employee ID-\n");
            System.out.print(m.RESET);
            System.out.print(m.YELLOW);
            System.out.printf("%78s", "Enter \"Return\" to return to the previous page\n\n");
            System.out.print(m.RESET);
            System.out.printf("%33s", "Employee ID: ");
            Emid = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(Emid);
            if (m.returnMatch.matches()) { // Checks if the entire input is exactly "Return"
                EmployeeMenu();
                break;
            }
            else if (Emid.contains(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print(m.RED);
                System.out.println("Invalid input! Input cannot be empty.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }

            // Define a block to tested for errors
            try {
                EmID = Integer.parseInt(Emid); // Convert String to Int
                if (Database.EmployeeIDchecking(EmID)) {
                    break;
                }
            }
            // If an error occurs in the 'try' block, execute a code
            catch (Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print(m.RED);
                System.out.println("Invalid input. Employee ID was not found.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
            }
        }
    }

    public void EmpLogInContinue() {
        Main m = new Main();

        while (true) {
            System.out.print("Enter R/r to return employee menu: ");
            m.userInput = m.input.nextLine();

            if (m.userInput.contains(" ")) {
                System.out.println(m.RED + "Invalid input! Spaces are not allowed." + m.RESET);
                continue;
            }

            if (m.userInput.matches("[R/r]")) {
                EmployeeMenu();
                break;
            }
            else {
                System.out.println(m.RED + "Invalid option. Please enter R/r to return to the employee page." + m.RESET);
            }
        }
        m.input.close();
    }

    public void NewEmployee() {
        Main m = new Main();
        Database d = new Database();

        Pattern GmailFormat = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$", Pattern.CASE_INSENSITIVE);

        String SName;
        String SGender;
        String SAge;
        String SConNumber;
        String SEmail;

        System.out.print("\033[H\033[2J");
        System.out.flush();

        m.Logo();
        for (int i = 0; i < 109; i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.print(m.GREEN);
        System.out.printf("%80s", "-Welcome newbie. Please fill in your information-\n");
        System.out.print(m.YELLOW);
        System.out.printf("%79s", "Enter \"Return\" to return to the previous page\n\n");
        System.out.print(m.RESET);
        while (true) { // Staff Name
            System.out.printf("%16s %13s", "Name", ": ");
            SName = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(SName);
            if (SName.isEmpty()) {
                System.out.print(m.RED);
                System.out.printf("%51s","Invalid input! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                EmployeeMenu();
                break;
            }
            if (SName.matches("[a-zA-Z\\s]{3,20}")) { // Ensures input = a-z & space and it is between 3-20 long
                if (SName.replaceAll("\\s+", "").length() >= 3) { // Ensures input contains at least 3 non-space characters
                    setName(SName);
                    break;
                }
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%93s","Invalid input! Your must enter between 3 and 20 letters (no numbers or symbols).\n");
                System.out.print(m.RESET);
            }
        }

        while (true) { // Staff Gender
            System.out.printf("%24s %5s", "Gender (M/F)", ": ");
            SGender = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(SGender);
            if (SGender.contains(" ")) {
                System.out.print(m.RED);
                System.out.printf("%51s","Invalid input! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                EmployeeMenu();
                break;
            }
            if (SGender.matches("[mfMF]")) {
                break;
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%69s", "Invalid input! Please enter M (Male) or F (Female) only.\n");
                System.out.print(m.RESET);
            }
        }

        while (true) { // Staff Age
            System.out.printf("%15s %14s", "Age", ": ");
            SAge = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(SAge);
            if (SAge.contains(" ")) {
                System.out.print(m.RED);
                System.out.printf("%51s","Invalid input! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                EmployeeMenu();
                break;
            }
            if (!SAge.matches("[0-9]{2}")){
                System.out.print(m.RED);
                System.out.printf("%110s","Invalid input! Age must be greater than or equal to 18 and between 60 (no characters or symbols).\n");
                System.out.print(m.RESET);
                continue;
            }
            int S_Age = Integer.parseInt(SAge);
            if (SAge.matches("[0-9]{2}") && S_Age >= 18 && S_Age <= 60) {
                break;
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%83s","Invalid input! Age must be greater than or equal to 18 and between 60.\n");
                System.out.print(m.RESET);
            }
        }

        while (true) { // Staff Contact Number
            System.out.printf("%26s \n%34s %7s", "Contact Number", m.PURPLE + "(without '-')" + m.RESET, ": +60");
            SConNumber = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(SConNumber);
            if (SConNumber.contains(" ")) {
                System.out.print(m.RED);
                System.out.printf("%52s","Invalid input!! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                EmployeeMenu();
                break;
            }
            if (SConNumber.matches("[0-9]{9,10}")) {
                break;
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%69s", "Invalid input! Contact number must be within 10 numbers.\n");
                System.out.print(m.RESET);
            }
        }

        while (true) { // Staff Email
            System.out.printf("%17s %12s", "Email", ": ");
            SEmail = m.input.nextLine();
            m.returnMatch = m.returnCommand.matcher(SEmail);
            m.FormatMatch = GmailFormat.matcher(SEmail);
            if (SEmail.contains(" ")) {
                System.out.print(m.RED);
                System.out.printf("%51s","Invalid input! Option cannot be empty.\n");
                System.out.print(m.RESET);
                continue;
            }
            else if (m.returnMatch.matches()) {
                EmployeeMenu();
                break;
            }
            if (m.FormatMatch.matches()) {
                break;
            }
            else {
                System.out.print(m.RED);
                System.out.printf("%60s", "Invalid input! Email must include '@gmail.com'.\n");
                System.out.print(m.RESET);
            }
        }

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.println();
            System.out.print(m.GREEN);
            System.out.printf("%77s", "-Please double confirm your personal details-\n\n");
            System.out.print(m.RESET);
            System.out.printf("%16s %13s", "Name", ": ");
            System.out.print(SName + "\n");
            System.out.printf("%24s %5s", "Gender (M/F)", ": ");
            System.out.print(SGender + "\n");
            System.out.printf("%15s %14s", "Age", ": ");
            System.out.print(SAge + "\n");
            System.out.printf("%26s \n%25s %7s", "Contact Number", "(without '-')", ": +60");
            System.out.print(SConNumber + "\n");
            System.out.printf("%17s %12s", "Email", ": ");
            System.out.print(SEmail + "\n\n");
            System.out.printf("%37s %16s %41s","1. Confirmed and continue", "|", "2. Return to previous page");
            System.out.print("\n\nEnter your choice (1, 2): ");
            m.userInput = m.input.nextLine();
            if (m.userInput.contains(" ")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print(m.RED);
                System.out.println("Invalid input! Input cannot be empty.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue;
            }
            if (m.userInput.matches("[12]")) {
                int chooseNewStaff = Integer.parseInt(m.userInput);
                if (chooseNewStaff == 1) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Generating your Employee ID......");
                    LoadingBar();
                    System.out.println("\nComplete!");
                    d.setEmployee(this);
                    d.NewEmployeeStore();
                    break;
                }
                else {
                    NewEmployee();
                    break;
                }
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
        m.input.close();
    }

    public void LoadingBar() {
        int total = 50;

        for (int i = 0; i <= total; i++) {
            int percentage = (i * 100) / total;

            // Manually create a repeated string
            StringBuilder bar = new StringBuilder();
            for (int j = 0; j < i; j++) bar.append("=");
            for (int j = i; j < total; j++) bar.append(" ");

            System.out.print("\r[" + bar + "] " + percentage + "%");
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("\nData error");
            } // Simulate loading
        }
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("\nData error");
        }
    }
}