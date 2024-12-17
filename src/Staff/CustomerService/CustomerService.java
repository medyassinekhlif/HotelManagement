package Staff.CustomerService;

import java.math.BigDecimal;

import Staff.Staff;

public class CustomerService extends Staff {

    private BigDecimal customerSatisfactionScore;
    private String languagesSpoken;
    private String shift;
    private String feedbackReceived;

    public CustomerService() {
    }

    public CustomerService(int id, String firstName, String lastName, String email, String phoneNumber,
                           String address, java.util.Date hireDate, BigDecimal salary, String status, String department,
                           String jobTitle, String workingHours,  BigDecimal customerSatisfactionScore,
                           String languagesSpoken, String shift, String feedbackReceived) {
        super(id, firstName, lastName, email, phoneNumber, address, hireDate, salary, status, department, jobTitle, workingHours);
        this.customerSatisfactionScore = customerSatisfactionScore;
        this.languagesSpoken = languagesSpoken;
        this.shift = shift;
        this.feedbackReceived = feedbackReceived;
    }

    public BigDecimal getCustomerSatisfactionScore() {
        return customerSatisfactionScore;
    }

    public void setCustomerSatisfactionScore(BigDecimal customerSatisfactionScore) {
        this.customerSatisfactionScore = customerSatisfactionScore;
    }

    public String getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(String languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getFeedbackReceived() {
        return feedbackReceived;
    }

    public void setFeedbackReceived(String feedbackReceived) {
        this.feedbackReceived = feedbackReceived;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", CustomerService {" +
                "customerSatisfactionScore=" + customerSatisfactionScore +
                ", languagesSpoken='" + languagesSpoken + '\'' +
                ", shift='" + shift + '\'' +
                ", feedbackReceived='" + feedbackReceived + '\'' +
                '}';
    }
}
