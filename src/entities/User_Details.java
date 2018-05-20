package entities;

/**
 * Describes the details of an employee for viewing.
 */
public class User_Details {

    private int employeeNumber;
    private String firstName;
    private String lastName;
    private char isAdmin;

    /**
     * Constructs a user
     *
     * @param employeeNumber the employee's employee number
     * @param firstName      the employee's first name
     * @param lastName       the employee's last name
     * @param isAdmin        whether the employee is an admin or not.
     */
    public User_Details(int employeeNumber, String firstName, String lastName, boolean isAdmin) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;

        if (isAdmin) {
            this.isAdmin = 'Y';
        } else {
            this.isAdmin = 'N';
        }
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getIsAdmin() {
        return isAdmin;
    }
}
