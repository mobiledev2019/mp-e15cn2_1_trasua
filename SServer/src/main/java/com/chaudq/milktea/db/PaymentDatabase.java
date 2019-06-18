package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDatabase {
    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payment (id,type,status) VALUES(?,?,?)";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, payment.getId());
            ps.setString(2, payment.getType());
            ps.setString(3, payment.getStatus());
            boolean result = ps.execute();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Payment getPaymentById(String id) {
        String sql = "SELECT * FROM payment WHERE id=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                String idrs=rs.getString("id");
                String type=rs.getString("type");
                String status=rs.getString("status");
                return new Payment(id,type,status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
