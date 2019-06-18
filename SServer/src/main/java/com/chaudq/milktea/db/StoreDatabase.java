package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Address;
import com.chaudq.milktea.model.Store;
import com.chaudq.milktea.model.Tea;
import com.chaudq.milktea.model.Topping;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class StoreDatabase {


    public List<Store> getAllStore(){
        System.out.println("GetAllSotre");
        AddressDatabase addressDatabase =new AddressDatabase();
        List<Address> addresses= new ArrayList<>();
        TeaDatabase teaDatabase = new TeaDatabase();
        ToppingDatabase toppingDatabase= new ToppingDatabase();
        List<Store> stores = new ArrayList<>();
        List<Topping> toppings = new ArrayList<>();
        List<Tea> teas= new ArrayList<>();
        String sql= "SELECT * FROM store";
        PreparedStatement ps = null;
        try {
            ps = UserDatabase.conn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                String id= rs.getString("id");
                toppings = toppingDatabase.getToppingByStoredId(id);
                teas=teaDatabase.getTeaByStoredId(id);
                addresses = addressDatabase.getAddressByIdStore(id);
                String name= rs.getString("name");
                stores.add(new Store(id,name,toppings,teas,addresses));

            }
            return  stores;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stores;
    }
    public Store getStoreById(String id){
        AddressDatabase addressDatabase =new AddressDatabase();
        List<Address> addresses= new ArrayList<>();
        TeaDatabase teaDatabase = new TeaDatabase();
        ToppingDatabase toppingDatabase= new ToppingDatabase();
        List<Topping> toppings = new ArrayList<>();
        List<Tea> teas= new ArrayList<>();
        String sql= "SELECT * FROM store WHERE Id=?";
        PreparedStatement ps = null;
        try {
            ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                toppings = toppingDatabase.getToppingByStoredId(id);
                teas=teaDatabase.getTeaByStoredId(id);
                addresses = addressDatabase.getAddressByIdStore(id);
                String name= rs.getString("name");
                return new Store(id,name,toppings,teas,addresses);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
