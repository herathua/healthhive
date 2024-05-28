package io.bootify.health_hive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class TempFileService {
    @Autowired DataSource dataSource;
    String sql ;



    public void getfile() throws SQLException {

        Connection con = dataSource.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }

    }
}
