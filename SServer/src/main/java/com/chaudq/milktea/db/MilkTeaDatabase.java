package com.chaudq.milktea.db;


import com.chaudq.milktea.model.Milktea;
import com.chaudq.milktea.model.Order1;
import com.chaudq.milktea.model.Tea;
import com.chaudq.milktea.model.Topping;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MilkTeaDatabase {


    public boolean addMilkTea(Order1 order){
        for(int i=0;i<order.getMilkteaList().size();i++)
        {
            String sql = "INSERT INTO milktea (id,teaid,size,toppingid,orderid) VALUES(?,?,?,?,?)";
            try {
                PreparedStatement ps= UserDatabase.conn.prepareStatement(sql);
                ps.setString(1,order.getMilkteaList().get(i).getId());
                ps.setString(2,order.getMilkteaList().get(i).getTeaId().getId());
                ps.setString(3,order.getMilkteaList().get(i).getSize());
                ps.setString(4,order.getMilkteaList().get(i).getTopping().getId());
                ps.setString(5,order.getId());
                boolean result=ps.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public List<Milktea> getMilkTeaById(String orderId){
        List<Milktea> milkteas=new ArrayList<>();
        TeaDatabase teaDatabase=new TeaDatabase();
        ToppingDatabase toppingDatabase=new ToppingDatabase();
        String sql= "SELECT * FROM milktea WHERE orderid=?";
        try{
            PreparedStatement ps= UserDatabase.conn.prepareStatement(sql);
            ps.setString(1,orderId);
            ResultSet resultSet= ps.executeQuery();
            while (resultSet.next()){
                String id= resultSet.getString("id");
                String teaid=resultSet.getString("teaid");
                String size=resultSet.getString("size");
                String toppingid=resultSet.getString("toppingid");
                Tea tea= teaDatabase.getById(teaid);
                Topping topping=toppingDatabase.getById(toppingid);
                Milktea milktea=new Milktea();
                milktea.setId(id);
                milktea.setSize(size);
                milktea.setTeaId(tea);
                milktea.setTopping(topping);
                milkteas.add(milktea);
            }
            return milkteas;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return milkteas;
    }
}
