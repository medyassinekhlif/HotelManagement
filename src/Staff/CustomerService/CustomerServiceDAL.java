package Staff.CustomerService;

import Utils.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CustomerServiceDAL {

    // Method to insert a new CustomerService
    public boolean insertCustomerService(CustomerService customerService) {
        String sql = "INSERT INTO CustomerService (id, firstName, lastName, email, phoneNumber, address, hireDate, salary, status, department, jobTitle, workingHours, customerSatisfactionScore, languagesSpoken, shift, feedbackReceived) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters
            statement.setInt(1, customerService.getId());
            statement.setString(2, customerService.getFirstName());
            statement.setString(3, customerService.getLastName());
            statement.setString(4, customerService.getEmail());
            statement.setString(5, customerService.getPhoneNumber());
            statement.setString(6, customerService.getAddress());
            statement.setDate(7, new java.sql.Date(customerService.getHireDate().getTime()));
            statement.setBigDecimal(8, customerService.getSalary());
            statement.setString(9, customerService.getStatus());
            statement.setString(10, customerService.getDepartment());
            statement.setString(11, customerService.getJobTitle());
            statement.setString(12, customerService.getWorkingHours());
            statement.setBigDecimal(13, customerService.getCustomerSatisfactionScore());
            statement.setString(14, customerService.getLanguagesSpoken());
            statement.setString(15, customerService.getShift());
            statement.setString(16, customerService.getFeedbackReceived());

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a CustomerService by ID
    public CustomerService getCustomerServiceById(int id) {
        String sql = "SELECT * FROM CustomerService WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToCustomerService(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all CustomerServices
    public List<CustomerService> getAllCustomerServices() {
        List<CustomerService> customerServiceList = new ArrayList<>();
        String sql = "SELECT * FROM CustomerService";
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                customerServiceList.add(mapResultSetToCustomerService(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerServiceList;
    }

    // Method to update an existing CustomerService
    public boolean updateCustomerService(CustomerService customerService) {
        String sql = "UPDATE CustomerService SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, address = ?, hireDate = ?, salary = ?, status = ?, department = ?, jobTitle = ?, workingHours = ?, " +
                     "customerSatisfactionScore = ?, languagesSpoken = ?, shift = ?, feedbackReceived = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters
            statement.setString(1, customerService.getFirstName());
            statement.setString(2, customerService.getLastName());
            statement.setString(3, customerService.getEmail());
            statement.setString(4, customerService.getPhoneNumber());
            statement.setString(5, customerService.getAddress());
            statement.setDate(6, new java.sql.Date(customerService.getHireDate().getTime()));
            statement.setBigDecimal(7, customerService.getSalary());
            statement.setString(8, customerService.getStatus());
            statement.setString(9, customerService.getDepartment());
            statement.setString(10, customerService.getJobTitle());
            statement.setString(11, customerService.getWorkingHours());
            statement.setBigDecimal(12, customerService.getCustomerSatisfactionScore());
            statement.setString(13, customerService.getLanguagesSpoken());
            statement.setString(14, customerService.getShift());
            statement.setString(15, customerService.getFeedbackReceived());
            statement.setInt(16, customerService.getId());

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a CustomerService by ID
    public boolean deleteCustomerService(int id) {
        String sql = "DELETE FROM CustomerService WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to map ResultSet to CustomerService object
    private CustomerService mapResultSetToCustomerService(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phoneNumber");
        String address = resultSet.getString("address");
        java.util.Date hireDate = resultSet.getDate("hireDate");
        BigDecimal salary = resultSet.getBigDecimal("salary");
        String status = resultSet.getString("status");
        String department = resultSet.getString("department");
        String jobTitle = resultSet.getString("jobTitle");
        String workingHours = resultSet.getString("workingHours");
        BigDecimal customerSatisfactionScore = resultSet.getBigDecimal("customerSatisfactionScore");
        String languagesSpoken = resultSet.getString("languagesSpoken");
        String shift = resultSet.getString("shift");
        String feedbackReceived = resultSet.getString("feedbackReceived");

        return new CustomerService(id, firstName, lastName, email, phoneNumber, address, hireDate, salary, status, department, jobTitle, workingHours, customerSatisfactionScore, languagesSpoken, shift, feedbackReceived);
    }
}
