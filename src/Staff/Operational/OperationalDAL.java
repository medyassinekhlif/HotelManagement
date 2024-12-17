package Staff.Operational;

import Utils.DbConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationalDAL {

    // Insert a new Operational record into the database
    public boolean insertOperational(Operational operational) {
        String query = "INSERT INTO operational (id, firstName, lastName, email, phoneNumber, address, hireDate, salary, " +
                "status, department, jobTitle, workingHours, shiftType, location, responsibilityLevel, taskCount, performanceRating) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, operational.getId());
            stmt.setString(2, operational.getFirstName());
            stmt.setString(3, operational.getLastName());
            stmt.setString(4, operational.getEmail());
            stmt.setString(5, operational.getPhoneNumber());
            stmt.setString(6, operational.getAddress());
            stmt.setDate(7, new java.sql.Date(operational.getHireDate().getTime()));
            stmt.setBigDecimal(8, operational.getSalary());
            stmt.setString(9, operational.getStatus());
            stmt.setString(10, operational.getDepartment());
            stmt.setString(11, operational.getJobTitle());
            stmt.setString(12, operational.getWorkingHours());
            stmt.setString(13, operational.getShiftType());
            stmt.setString(14, operational.getLocation());
            stmt.setString(15, operational.getResponsibilityLevel());
            stmt.setInt(16, operational.getTaskCount());
            stmt.setBigDecimal(17, operational.getPerformanceRating());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve an Operational record by its ID
    public Operational getOperationalById(int id) {
        String query = "SELECT * FROM operational WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractOperationalFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all Operational records
    public List<Operational> getAllOperationals() {
        List<Operational> operationalList = new ArrayList<>();
        String query = "SELECT * FROM operational";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                operationalList.add(extractOperationalFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operationalList;
    }

    // Update an existing Operational record
    public boolean updateOperational(Operational operational) {
        String query = "UPDATE operational SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, address = ?, " +
                "hireDate = ?, salary = ?, status = ?, department = ?, jobTitle = ?, workingHours = ?, shiftType = ?, " +
                "location = ?, responsibilityLevel = ?, taskCount = ?, performanceRating = ? WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, operational.getFirstName());
            stmt.setString(2, operational.getLastName());
            stmt.setString(3, operational.getEmail());
            stmt.setString(4, operational.getPhoneNumber());
            stmt.setString(5, operational.getAddress());
            stmt.setDate(6, new java.sql.Date(operational.getHireDate().getTime()));
            stmt.setBigDecimal(7, operational.getSalary());
            stmt.setString(8, operational.getStatus());
            stmt.setString(9, operational.getDepartment());
            stmt.setString(10, operational.getJobTitle());
            stmt.setString(11, operational.getWorkingHours());
            stmt.setString(12, operational.getShiftType());
            stmt.setString(13, operational.getLocation());
            stmt.setString(14, operational.getResponsibilityLevel());
            stmt.setInt(15, operational.getTaskCount());
            stmt.setBigDecimal(16, operational.getPerformanceRating());
            stmt.setInt(17, operational.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete an Operational record by its ID
    public boolean deleteOperational(int id) {
        String query = "DELETE FROM operational WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to extract Operational object from ResultSet
    private Operational extractOperationalFromResultSet(ResultSet rs) throws SQLException {
        Operational operational = new Operational();

        operational.setId(rs.getInt("id"));
        operational.setFirstName(rs.getString("firstName"));
        operational.setLastName(rs.getString("lastName"));
        operational.setEmail(rs.getString("email"));
        operational.setPhoneNumber(rs.getString("phoneNumber"));
        operational.setAddress(rs.getString("address"));
        operational.setHireDate(rs.getDate("hireDate"));
        operational.setSalary(rs.getBigDecimal("salary"));
        operational.setStatus(rs.getString("status"));
        operational.setDepartment(rs.getString("department"));
        operational.setJobTitle(rs.getString("jobTitle"));
        operational.setWorkingHours(rs.getString("workingHours"));
        operational.setShiftType(rs.getString("shiftType"));
        operational.setLocation(rs.getString("location"));
        operational.setResponsibilityLevel(rs.getString("responsibilityLevel"));
        operational.setTaskCount(rs.getInt("taskCount"));
        operational.setPerformanceRating(rs.getBigDecimal("performanceRating"));

        return operational;
    }
}

