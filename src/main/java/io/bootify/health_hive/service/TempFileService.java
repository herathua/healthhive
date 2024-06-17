package io.bootify.health_hive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class TempFileService {
    @Autowired
    private DataSource dataSource;

    private String sql = "SELECT * FROM share_files WHERE date_created < NOW() + interval -1 MINUTE;";

    public List<Long> getFiles() throws SQLException {
        List<Long> fileIds = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                fileIds.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            dataSource.getConnection().close();
        }
        return fileIds;
    }

    public List<Long> getUserFiles (Long userId) throws SQLException {
        List<Long> fileIds = new ArrayList<>();
        String sql = "SELECT * FROM share_files WHERE user_id = " + userId + ";";
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                fileIds.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            dataSource.getConnection().close();
        }
        return fileIds;
    }
}
