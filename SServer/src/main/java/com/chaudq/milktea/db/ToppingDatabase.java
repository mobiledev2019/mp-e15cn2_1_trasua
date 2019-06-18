package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Topping;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ToppingDatabase {

    public List<Topping> getToppingByStoredId(String storeid) {
        List<Topping> toppings = new ArrayList<>();
        String sql = "SELECT * FROM store_topping WHERE storeid=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, storeid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("toppingid");
                toppings.add(getById(id));
            }
            return toppings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toppings;

    }

    public Topping getById(String id){
        String sql = "SELECT * FROM topping WHERE id=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
                String rsid= rs.getString("id");
                String name= rs.getString("name");
                String price = rs.getString("price");
                Topping topping= new Topping(rsid,name,price);
                return topping;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Topping> getAllTopping(){
        List<Topping> toppings = new ArrayList<>();
        String sql = "SELECT * FROM topping ";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
                String rsid= rs.getString("id");
                String name= rs.getString("name");
                String price = rs.getString("price");
                toppings.add(new Topping(rsid,name,price));
            }
            return toppings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toppings;
    }
}
