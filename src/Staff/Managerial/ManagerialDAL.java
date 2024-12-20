package Staff.Managerial;


import Utils.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerialDAL {

    // Insert a new Managerial record
    public boolean insertManagerial(Managerial managerial) {
        String query = "INSERT INTO managerial (id, firstName, lastName, email, phoneNumber, address, hireDate, salary, status, department, jobTitle, workingHours, officeLocation, teamSize, reportsTo) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, managerial.getId());
            preparedStatement.setString(2, managerial.getFirstName());
            preparedStatement.setString(3, managerial.getLastName());
            preparedStatement.setString(4, managerial.getEmail());
            preparedStatement.setString(5, managerial.getPhoneNumber());
            preparedStatement.setString(6, managerial.getAddress());
            preparedStatement.setDate(7, new java.sql.Date(managerial.getHireDate().getTime())); // Proper conversion
            preparedStatement.setBigDecimal(8, managerial.getSalary());
            preparedStatement.setString(9, managerial.getStatus());
            preparedStatement.setString(10, managerial.getDepartment());
            preparedStatement.setString(11, managerial.getJobTitle());
            preparedStatement.setString(12, managerial.getWorkingHours());
            preparedStatement.setString(13, managerial.getOfficeLocation());
            preparedStatement.setInt(14, managerial.getTeamSize());
            preparedStatement.setString(15, managerial.getReportsTo());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get a Managerial record by ID
    public Managerial getManagerialById(int id) {
        String query = "SELECT * FROM managerial WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToManagerial(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all Managerial records
    public List<Managerial> getAllManagerials() {
        String query = "SELECT * FROM managerial";
        List<Managerial> managerialList = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                managerialList.add(mapResultSetToManagerial(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managerialList;
    }

    // Update a Managerial record
    public boolean updateManagerial(Managerial managerial) {
        String query = "UPDATE managerial SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, address = ?, hireDate = ?, salary = ?, status = ?, department = ?, jobTitle = ?, workingHours = ?, officeLocation = ?, teamSize = ?, reportsTo = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, managerial.getFirstName());
            preparedStatement.setString(2, managerial.getLastName());
            preparedStatement.setString(3, managerial.getEmail());
            preparedStatement.setString(4, managerial.getPhoneNumber());
            preparedStatement.setString(5, managerial.getAddress());
            preparedStatement.setDate(6, new java.sql.Date(managerial.getHireDate().getTime())); // Proper conversion
            preparedStatement.setBigDecimal(7, managerial.getSalary());
            preparedStatement.setString(8, managerial.getStatus());
            preparedStatement.setString(9, managerial.getDepartment());
            preparedStatement.setString(10, managerial.getJobTitle());
            preparedStatement.setString(11, managerial.getWorkingHours());
            preparedStatement.setString(12, managerial.getOfficeLocation());
            preparedStatement.setInt(13, managerial.getTeamSize());
            preparedStatement.setString(14, managerial.getReportsTo());
            preparedStatement.setInt(15, managerial.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a Managerial record by ID
    public boolean deleteManagerial(int id) {
        String query = "DELETE FROM managerial WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map a ResultSet to a Managerial object
    private Managerial mapResultSetToManagerial(ResultSet resultSet) throws SQLException {
        Managerial managerial = new Managerial();
        managerial.setId(resultSet.getInt("id"));
        managerial.setFirstName(resultSet.getString("firstName"));
        managerial.setLastName(resultSet.getString("lastName"));
        managerial.setEmail(resultSet.getString("email"));
        managerial.setPhoneNumber(resultSet.getString("phoneNumber"));
        managerial.setAddress(resultSet.getString("address"));
        managerial.setHireDate(resultSet.getDate("hireDate")); // Direct from ResultSet
        managerial.setSalary(resultSet.getBigDecimal("salary"));
        managerial.setStatus(resultSet.getString("status"));
        managerial.setDepartment(resultSet.getString("department"));
        managerial.setJobTitle(resultSet.getString("jobTitle"));
        managerial.setWorkingHours(resultSet.getString("workingHours"));
        managerial.setOfficeLocation(resultSet.getString("officeLocation"));
        managerial.setTeamSize(resultSet.getInt("teamSize"));
        managerial.setReportsTo(resultSet.getString("reportsTo"));
        return managerial;
    }
}
