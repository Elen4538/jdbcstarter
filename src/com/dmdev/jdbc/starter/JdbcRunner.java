package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author elena
 * @project jdbcstarter
 * @date 09/05/2023
 */
public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

//        String flightId = "2"; // vulnerability SQL Injection
//        Long flightId = 2L;
//        var result = getTicketsByFlightId(flightId);
//        System.out.println(result);


        var result = getFlightsBetween(LocalDate.of(2020, 10, 1 ).atStartOfDay(),LocalDateTime.now());
        System.out.println(result);




        Class<Driver> driverClass = Driver.class;
 /*       String sql = """
                CREATE TABLE IF NOT EXISTS info(
                    id SERIAL PRIMARY KEY,
                    data TEXT NOT NULL
                );
                """; */
        /*
        String sql = """
                            INSERT INTO info(data)
                            VALUES 
                            ('autogenerated')
                           
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());

            var executeResult = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS); // .execute(sql) .executeUpdate(sql) .executeQuery(sql)

            // below this is for ResultSet executeResult.executeQuery()
//            while (executeResult.next()) {
//                System.out.println(executeResult.getLong("id"));
//                System.out.println(executeResult.getString("passenger_no"));
//                System.out.println(executeResult.getBigDecimal("cost"));
                var generatedKeys =statement.getGeneratedKeys();
                if(generatedKeys.next()){
                    var generatedId = generatedKeys.getInt(1);
                    System.out.println(generatedId);


                System.out.println("-------------");
            }

            //System.out.println(executeResult);
            //System.out.println(statement.getUpdateCount());
        } */
    }

    private static List<Long> getTicketsByFlightId(Long flightID) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                """; //%s     .formatted(flightID)

        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flightID);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //result.add(resultSet.getLong("id"));   can return null so we use .getObject()
                result.add(resultSet.getObject("id", Long.class)); // maybe use Optional?? NULL SAFE
            }
        }
        return result;
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                    SELECT id
                    FROM flight
                    WHERE departure_date BETWEEN ? AND ?
                    """;
        List<Long> result = new ArrayList<>();
        try(var connection = ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(sql)) {
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            final var resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                result.add(resultSet.getLong("id"));

            }
        }
        return result;

    }
}
