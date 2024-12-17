package Staff.Managerial;

import Staff.Staff;

public class Managerial extends Staff {

    private String officeLocation;
    private int teamSize;
    private String reportsTo;

    public Managerial() {
    }

    public Managerial(int id, String firstName, String lastName, String email, String phoneNumber,
                           String address, java.util.Date hireDate, java.math.BigDecimal salary,
                           String status, String department, String jobTitle, String workingHours,
                           String officeLocation, int teamSize, String reportsTo) {
        super(id, firstName, lastName, email, phoneNumber, address, hireDate, salary, status, department, jobTitle, workingHours);
        this.officeLocation = officeLocation;
        this.teamSize = teamSize;
        this.reportsTo = reportsTo;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", ManagerialStaff {" +
                "officeLocation='" + officeLocation + '\'' +
                ", teamSize=" + teamSize +
                ", reportsTo='" + reportsTo + '\'' +
                '}';
    }
}
