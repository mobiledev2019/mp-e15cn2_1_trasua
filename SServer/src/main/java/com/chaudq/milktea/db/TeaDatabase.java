package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Tea;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TeaDatabase {


    public List<Tea> getTeaByStoredId(String storeid) {
        List<Tea> teas = new ArrayList<>();
        String sql = "SELECT * FROM store_tea WHERE storeid=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, storeid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("teaid");
                teas.add(getById(id));
            }
            return teas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teas;

    }

    public Tea getById(String id) {
        String sql = "SELECT * FROM tea WHERE id=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String rsid = rs.getString("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                Tea tea = new Tea(rsid, name, price);
                return tea;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tea> getAllTea() {
        List<Tea> teas = new ArrayList<>();
        String sql = "SELECT * FROM tea ";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String rsid = rs.getString("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                teas.add(new Tea(rsid, name, price));


            }
            return teas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teas;
    }
}
