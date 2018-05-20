package entities;

/**
 * Describes a user object, only used to create the active user.
 *
 * @author Ryan Junor
 */
public class Active_User {


    private int employeeNumber;
    private String firstName;
    private String lastName;
    private boolean isAdmin;

    /**
     * Constructs the active user
     *
     * @param employeeNumber the employee number of the active user
     * @param firstName      the first name of the active user
     * @param lastName       the last name of the active user
     * @param is_admin       whether the active user is an admin or not
     */
    public Active_User(int employeeNumber, String firstName, String lastName, boolean is_admin) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = is_admin;
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

    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
