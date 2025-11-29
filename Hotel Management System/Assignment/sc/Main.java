import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public Scanner input = new Scanner(System.in);

    public String key;
    public String userInput;
    public boolean bookingConfirmed = false;

    public String RESET = "\u001B[0m";
    public String RED = "\u001B[31m";
    public String GREEN = "\u001B[32m";
    public String YELLOW = "\u001B[33m";
    public String PURPLE = "\u001B[35m";
    public String BLUE = "\u001B[34m";
    public String GREEN_B = "\u001B[42m";
    public String WHITE_B = "\u001B[47m";
    public String CYAN_B = "\u001B[46m";
    public String BLACK_B = "\u001B[40m";

    // Always looking for the word "Return", same case for both upper and lower case
    public Pattern returnCommand = Pattern.compile("Return", Pattern.CASE_INSENSITIVE);
    public Matcher returnMatch;
    public Matcher FormatMatch;

    public DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm");

    public void Logo() {
        LocalDateTime DateTime = LocalDateTime.now();
        DateTimeFormatter FormatDate = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        DateTimeFormatter FormatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedDate = DateTime.format(FormatDate);
        String formattedTime = DateTime.format(FormatTime);

        String RESET = "\u001B[0m";
        String CYAN = "\u001B[36m";

        System.out.print(
        "\n     ________ ________ ________ ________          ____        __    __ ________ ________ _______ __" + 
        "\n    |   _____|   __   |   __   |   ____  \\      /  __   \\    |  |  |  |   __   |__    __|   ____|  |" +
        "\n    |  |  ___|  |  |  |  |  |  |  |    |  |    |  |__|  /    |  |__|  |  |  |  |  |  |  |  |___ |  |" +
        "\n    |  | |__ |  |  |  |  |  |  |  |    |  |     \\___   /     |   __   |  |  |  |  |  |  |   ___||  |" +
        "\n    |  |__|  |  |__|  |  |__|  |  |____|  |        /  /      |  |  |  |  |__|  |  |  |  |  |____|  |____" +
        "\n    |________|________|________|_________/        /  /       |__|  |__|________|  |__|  |_______|_______|" +
        "\n                                                 /__/\n");
        System.out.print("\nTime: " + CYAN + formattedTime + RESET);
        System.out.print("\nDate: " + CYAN + formattedDate + RESET + "\n");

    }

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        OptionSelect os = new OptionSelect();
        os.Welcome();
    }

    public void setName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }
}