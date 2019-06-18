package com.chaudq.milktea.db;

import com.chaudq.milktea.model.*;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class OrderDatabase {

    public boolean addOrder(Order1 order) {

        //Ghi ds item vào trong csdl
        AddressShipDatabase addressShipDatabase = new AddressShipDatabase();
        addressShipDatabase.addAddressShip(order.getAddressShipId());
        //ghi địa chỉ ship
        PaymentDatabase paymentDatabase = new PaymentDatabase();
        paymentDatabase.addPayment(order.getPaymentId());
        //ghi thông tin payment

        String sql = "INSERT INTO tea.order (Id, AddressShipId, PaymentId,Date, UserId,StoreId) VALUES (?, ?, ?, ?,?,?)";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, order.getId());
            ps.setString(2, order.getAddressShipId().getId());
            ps.setString(3, order.getPaymentId().getId());
            ps.setString(4, order.getDate());
            ps.setString(5, order.getUser().getId());
            ps.setString(6, order.getStore().getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        MilkTeaDatabase milkTeaDatabase = new MilkTeaDatabase();
        milkTeaDatabase.addMilkTea(order);
        addStatusOrder(order);
        return true;
    }

    public Order1 getOrderById(String orderId) {
        AddressShipDatabase addressShipDatabase = new AddressShipDatabase();
        PaymentDatabase paymentDatabase = new PaymentDatabase();
        List<Milktea> milkteaList = new ArrayList<>();
        String sql = "SELECT * FROM tea.order WHERE Id=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String addressshipid = rs.getString("addressshipid");
                String paymentid = rs.getString("paymentid");
                String date = rs.getString("Date");
                String storeId = rs.getString("storeid");
                Store store = new StoreDatabase().getStoreById(storeId);
                Addressship addressship = addressShipDatabase.getAddressshipById(addressshipid);
                Payment payment = paymentDatabase.getPaymentById(paymentid);
                milkteaList = new MilkTeaDatabase().getMilkTeaById(orderId);
                Order1 order1 = new Order1();
                order1.setId(orderId);
                order1.setAddressShipId(addressship);
                order1.setPaymentId(payment);
                order1.setDate(date);
                order1.setStore(store);
                order1.setMilkteaList(milkteaList);
                return order1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Statusorder> getNotDoneOrderByUserId(String userid) {

        String sql = "SELECT * FROM tea.order WHERE UserId=" + userid+"ORDER BY ID";
        List<Statusorder> statusorders = new ArrayList<>();

        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                Order1 order = getOrderById(id);
                Statusorder statusorder = getStatusOrderById(id);
                statusorder.setOrderId(order);
                if (statusorder.getStatus().equalsIgnoreCase("Đã hoàn thành") == false)
                    statusorders.add(statusorder);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusorders;
    }

    private boolean addStatusOrder(Order1 order) {
        Statusorder statusorder = new Statusorder();
        statusorder.setId(UUID.randomUUID().toString());
        statusorder.setOrderId(order);
        statusorder.setStatus("Đã đặt đơn");
        String sql = "INSERT INTO tea.statusorder (id,orderid,status) VALUES(?,?,?)";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, statusorder.getId());
            ps.setString(2, statusorder.getOrderId().getId());
            ps.setString(3, statusorder.getStatus());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Statusorder getStatusOrderById(String orderId) {
        String sql = "SELECT *FROM tea.statusorder WHERE orderId=?";
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String status = rs.getString("Status");
                Statusorder statusorder = new Statusorder();
                statusorder.setStatus(status);
                statusorder.setId(id);
                return statusorder;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Statusorder> getDoneOrderByUserId(String userid) {

        String sql = "SELECT * FROM tea.order WHERE UserId=" + userid+"ORDER BY ID";
        List<Statusorder> statusorders = new ArrayList<>();
        try {
            PreparedStatement ps = UserDatabase.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String id = rs.getString("id");
                Order1 order = getOrderById(id);
                Statusorder statusorder = getStatusOrderById(id);
                statusorder.setOrderId(order);
                if (statusorder.getStatus().equalsIgnoreCase("Đã hoàn thành"))
                    statusorders.add(statusorder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusorders;
    }
}
