public abstract class PersonalInfo {
    protected Main m = new Main();
    protected String name;
    protected String age;
    protected String contactNumber;

    public abstract void displayMenu();

    protected boolean NameValidate(String name) {
        if (name.isEmpty()) {
            System.out.print(m.RED);
            System.out.printf("%51s","Invalid input! Option cannot be empty.\n");
            System.out.print(m.RESET);
            return false;
        }
        else if (m.returnMatch.matches()) {
            return false;
        }
        if (name.matches("[a-zA-Z\\s]{3,20}")) { // Ensures input = a-z & space and it is between 3-20 long
            if (name.replaceAll("\\s+", "").length() >= 3) { // Ensures input contains at least 3 non-space characters
                m.setName(name);
                return true;
            }
        }
        else {
            System.out.print(m.RED);
            System.out.printf("%93s","Invalid input! Your must enter between 3 and 20 letters (no numbers or symbols).\n");
            System.out.print(m.RESET);
            return false;
        }
        return false;
    }

    protected boolean AgeValidate(String age) {
        if (age.contains(" ")) {
            System.out.print(m.RED);
            System.out.printf("%51s", "Invalid input! Option cannot be empty.\n");
            System.out.print(m.RESET);
            return false;
        }
        else if (m.returnMatch.matches()) {
            return false;
        }
        if (!age.matches("[0-9]{2}")){
            System.out.print(m.RED);
            System.out.printf("%110s","Invalid input! Age must be greater than or equal to 18 and between 60 (no characters or symbols).\n");
            System.out.print(m.RESET);
            return false;
        }
        int ageNum  = Integer.parseInt(age);
        if (age .matches("[0-9]{2}") && ageNum  >= 18 && ageNum  <= 60) {
            return true;
        }
        else {
            System.out.print(m.RED);
            System.out.printf("%83s","Invalid input! Age must be greater than or equal to 18 and between 60.\n");
            System.out.print(m.RESET);
            return false;
        }
    }

    protected boolean ContactValidate(String contactNumber) {
        if (contactNumber.contains(" ")) {
            System.out.print(m.RED);
            System.out.printf("%51s", "Invalid input! Option cannot be empty.\n");
            System.out.print(m.RESET);
            return false;
        }
        if (contactNumber.matches("[0-9]{9,10}")) {
            return true;
        }
        else {
            System.out.print(m.RED);
            System.out.printf("%69s", "Invalid input! Contact number must be within 10 numbers.\n");
            System.out.print(m.RESET);
            return false;
        }
    }
}