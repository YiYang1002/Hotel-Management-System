public class OptionSelect{
    public void Welcome() {
        Main m = new Main();
        Employee em = new Employee();
        Guest g = new Guest();
        Payment p = new Payment();

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            m.Logo();
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.printf("%-43s %-23s %43s", "\n|", "Welcome to GOOD 9 HOTEL", "|\n");
            for (int i = 0; i < 109; i++) {
                System.out.print("=");
            }
            System.out.println();
            System.out.print(m.GREEN);
            System.out.printf("%72s", "-Please select your login identity-");
            System.out.print(m.RESET);
            System.out.println();
            System.out.printf("%19s %12s %27s %17s %18s", "1. Staff", "|", "2. Customer", "|", "3. Exit");
            System.out.print("\n\n\nEnter your choice (1, 2 or 3): ");
            m.userInput = m.input.nextLine();

            if (m.userInput.contains(" ")) {
                // Restrict space for user input
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print(m.RED);
                System.out.println("Invalid input! Option cannot be empty.");
                System.out.print(m.RESET);
                System.out.print("Please press ENTER to continue...");
                m.key = m.input.nextLine();
                continue; // Restart the loop
            }
            
            // Check user input is valid number or not
            // Only allow "1", "2" or "3"
            if (m.userInput.matches("[123]")) {
                int chooseWel = Integer.parseInt(m.userInput); // String to integer

                if (chooseWel == 1) {
                    em.EmployeeMenu();
                }
                else if (chooseWel == 2) {
                    g.GuestMenu();
                }
                else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    p.ThankYou();
                    System.exit(0); // Exit the program
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
}