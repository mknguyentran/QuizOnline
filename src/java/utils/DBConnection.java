/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Mk
 */
public class DBConnection {
    public static Connection getConnection() throws Exception{
        InitialContext context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/QuizOnline");
        Connection conn = dataSource.getConnection();
        return conn;
    }
}
