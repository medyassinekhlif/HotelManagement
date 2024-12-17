package Staff.Operational;


import java.math.BigDecimal;

import Staff.Staff;

public class Operational extends Staff {

    private String shiftType;
    private String location;
    private String responsibilityLevel;
    private int taskCount;
    private BigDecimal performanceRating;

    public Operational() {
    }

    public Operational(int id, String firstName, String lastName, String email, String phoneNumber,
                       String address, java.util.Date hireDate, BigDecimal salary, String status,
                       String department, String jobTitle, String workingHours, String shiftType,
                       String location, String responsibilityLevel, int taskCount, BigDecimal performanceRating) {
        super(id, firstName, lastName, email, phoneNumber, address, hireDate, salary, status, department, jobTitle, workingHours);
        this.shiftType = shiftType;
        this.location = location;
        this.responsibilityLevel = responsibilityLevel;
        this.taskCount = taskCount;
        this.performanceRating = performanceRating;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResponsibilityLevel() {
        return responsibilityLevel;
    }

    public void setResponsibilityLevel(String responsibilityLevel) {
        this.responsibilityLevel = responsibilityLevel;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public BigDecimal getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(BigDecimal performanceRating) {
        this.performanceRating = performanceRating;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Operational {" +
                "shiftType='" + shiftType + '\'' +
                ", location='" + location + '\'' +
                ", responsibilityLevel='" + responsibilityLevel + '\'' +
                ", taskCount=" + taskCount +
                ", performanceRating=" + performanceRating +
                '}';
    }
}
