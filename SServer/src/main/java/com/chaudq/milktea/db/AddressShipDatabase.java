package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Addressship;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class AddressShipDatabase {
    public boolean addAddressShip(Addressship addressship){
        String sql="INSERT INTO addressship (id,userid,city,town,description) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps= UserDatabase.conn.prepareStatement(sql);
            ps.setString(1,addressship.getId());
            ps.setString(2,addressship.getUserId().getId());
            ps.setString(3,addressship.getCity());
            ps.setString(4,addressship.getTown());
            ps.setString(5,addressship.getDescription());
            boolean result = ps.execute();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public Addressship getAddressshipById(String id) {
        String sql = "SELECT * FROM addressship WHERE id=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                String userID=rs.getString("userid");
                String city=rs.getString("city");
                String town =rs.getString("town");
                String description=rs.getString("description");
                return new Addressship(id,city,town,description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
