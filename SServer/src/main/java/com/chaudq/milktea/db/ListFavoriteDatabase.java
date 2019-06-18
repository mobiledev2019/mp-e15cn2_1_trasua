package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Listfavorite;
import com.chaudq.milktea.model.Store;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ListFavoriteDatabase {
    public boolean addListFavorite(Listfavorite listfavorite) {
        String sql = "INSERT INTO listfavorite (id) VALUES (?)";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, listfavorite.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Listfavorite getListfavoriteById(String id) {
        String sql = "SELECT * FROM listfavorite_store WHERE listfavoriteid=?";
        StoreDatabase storeDatabase = new StoreDatabase();
        List<Store> storeList = new ArrayList<>();
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String storeid = rs.getString("storeid");
                Store store = storeDatabase.getStoreById(storeid);
                storeList.add(store);
            }

            return new Listfavorite(id, storeList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
