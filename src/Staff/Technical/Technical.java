package Staff.Technical;


import java.util.Date;

import Staff.Staff;

public class Technical extends Staff {

    private String technicalSkills;
    private String certifications;
    private String workLocation;
    private Date lastTrainingDate;


    public Technical() {
    }


    public Technical(int id, String firstName, String lastName, String email, String phoneNumber,
                     String address, Date hireDate, java.math.BigDecimal salary, String status,
                     String department, String jobTitle, String workingHours, String technicalSkills,
                     String certifications, String workLocation, Date lastTrainingDate) {
        super(id, firstName, lastName, email, phoneNumber, address, hireDate, salary, status, department, jobTitle, workingHours);
        this.technicalSkills = technicalSkills;
        this.certifications = certifications;
        this.workLocation = workLocation;
        this.lastTrainingDate = lastTrainingDate;
    }
   
    


    public String getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(String technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public Date getLastTrainingDate() {
        return lastTrainingDate;
    }

    public void setLastTrainingDate(Date lastTrainingDate) {
        this.lastTrainingDate = lastTrainingDate;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", Technical {" +
                "technicalSkills='" + technicalSkills + '\'' +
                ", certifications='" + certifications + '\'' +
                ", workLocation='" + workLocation + '\'' +
                ", lastTrainingDate=" + lastTrainingDate +
                '}';
    }
}
