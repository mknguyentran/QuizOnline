/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.AccountDTO;
import java.io.Serializable;
import utils.DBConnection;

/**
 *
 * @author Mk
 */
public class AccountDAO extends DAO implements Serializable {

    public static final String TABLE_NAME = "Account";

    public boolean addAccount(AccountDTO account) throws Exception {
        boolean successful = false;
        String sql = "Insert Into " + TABLE_NAME + "(Email, Name, Password, Role, Status) values (?,?,?,?,?)";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getName());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getRole());
            ps.setString(5, account.getStatus());
            successful = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return successful;
    }

    public boolean isExisted(String email) throws Exception {
        boolean existed = false;
        String sql = "Select Email From " + TABLE_NAME + " Where Email = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            existed = rs.next();
        } finally {
            closeConnection();
        }
        return existed;
    }

    public String checkLogin(String email, String password) throws Exception {
        String role = "invalid";
        String sql = "Select Role From " + TABLE_NAME + " Where Email = ? And Password = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                role = rs.getString("Role");
            }
        } finally {
            closeConnection();
        }
        return role;
    }
    
    public String getAccountName(String email) throws Exception {
        String name = null;
        String sql = "Select Name From " + TABLE_NAME + " Where Email = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } finally {
            closeConnection();
        }
        return name;
    }   
    
    public String getAccountStatus(String email) throws Exception {
        String status = null;
        String sql = "Select Status From " + TABLE_NAME + " Where Email = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getString("Status");
            }
        } finally {
            closeConnection();
        }
        return status;
    }   
}
