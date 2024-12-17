package Staff.Technical;

import Utils.DbConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TechnicalDAL {

    // Insert a new Technical record
    public boolean insertTechnical(Technical technical) {
        String query = "INSERT INTO technical (id, firstName, lastName, email, phoneNumber, address, hireDate, salary, " +
                "status, department, jobTitle, workingHours, technicalSkills, certifications, workLocation, lastTrainingDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, technical.getId());
            statement.setString(2, technical.getFirstName());
            statement.setString(3, technical.getLastName());
            statement.setString(4, technical.getEmail());
            statement.setString(5, technical.getPhoneNumber());
            statement.setString(6, technical.getAddress());
            statement.setDate(7, new java.sql.Date(technical.getHireDate().getTime()));
            statement.setBigDecimal(8, technical.getSalary());
            statement.setString(9, technical.getStatus());
            statement.setString(10, technical.getDepartment());
            statement.setString(11, technical.getJobTitle());
            statement.setString(12, technical.getWorkingHours());
            statement.setString(13, technical.getTechnicalSkills());
            statement.setString(14, technical.getCertifications());
            statement.setString(15, technical.getWorkLocation());
            statement.setDate(16, new java.sql.Date(technical.getLastTrainingDate().getTime()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get a Technical by ID
    public Technical getTechnicalById(int id) {
        String query = "SELECT * FROM technical WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractTechnicalFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get all Technical records
    public List<Technical> getAllTechnicals() {
        List<Technical> technicals = new ArrayList<>();
        String query = "SELECT * FROM technical";

        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                technicals.add(extractTechnicalFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return technicals;
    }

    // Update a Technical record
    public boolean updateTechnical(Technical technical) {
        String query = "UPDATE technical SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, address = ?, hireDate = ?, " +
                "salary = ?, status = ?, department = ?, jobTitle = ?, workingHours = ?, technicalSkills = ?, certifications = ?, " +
                "workLocation = ?, lastTrainingDate = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, technical.getFirstName());
            statement.setString(2, technical.getLastName());
            statement.setString(3, technical.getEmail());
            statement.setString(4, technical.getPhoneNumber());
            statement.setString(5, technical.getAddress());
            statement.setDate(6, new java.sql.Date(technical.getHireDate().getTime()));
            statement.setBigDecimal(7, technical.getSalary());
            statement.setString(8, technical.getStatus());
            statement.setString(9, technical.getDepartment());
            statement.setString(10, technical.getJobTitle());
            statement.setString(11, technical.getWorkingHours());
            statement.setString(12, technical.getTechnicalSkills());
            statement.setString(13, technical.getCertifications());
            statement.setString(14, technical.getWorkLocation());
            statement.setDate(15, new java.sql.Date(technical.getLastTrainingDate().getTime()));
            statement.setInt(16, technical.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a Technical record
    public boolean deleteTechnical(int id) {
        String query = "DELETE FROM technical WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Helper method to map ResultSet to Technical object
    private Technical extractTechnicalFromResultSet(ResultSet resultSet) throws SQLException {
        Technical technical = new Technical();
        technical.setId(resultSet.getInt("id"));
        technical.setFirstName(resultSet.getString("firstName"));
        technical.setLastName(resultSet.getString("lastName"));
        technical.setEmail(resultSet.getString("email"));
        technical.setPhoneNumber(resultSet.getString("phoneNumber"));
        technical.setAddress(resultSet.getString("address"));
        technical.setHireDate(resultSet.getDate("hireDate"));
        technical.setSalary(resultSet.getBigDecimal("salary"));
        technical.setStatus(resultSet.getString("status"));
        technical.setDepartment(resultSet.getString("department"));
        technical.setJobTitle(resultSet.getString("jobTitle"));
        technical.setWorkingHours(resultSet.getString("workingHours"));
        technical.setTechnicalSkills(resultSet.getString("technicalSkills"));
        technical.setCertifications(resultSet.getString("certifications"));
        technical.setWorkLocation(resultSet.getString("workLocation"));
        technical.setLastTrainingDate(resultSet.getDate("lastTrainingDate"));

        return technical;
    }
}
