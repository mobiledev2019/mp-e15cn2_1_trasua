package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Address;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class AddressDatabase {


    public List<Address> getAddressByIdStore(String id) {
        String sql = "SELECT * FROM address WHERE storeid=?";
        List<Address> addresses = new ArrayList<>();
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String rsid = rs.getString("id");
                String longtitude = rs.getString("longtitude");
                String latitude = rs.getString("latitude");
                String description = rs.getString("description");
                addresses.add(new Address(rsid, longtitude, latitude, description));

            }
            return addresses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

}