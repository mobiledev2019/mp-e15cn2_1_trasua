package com.chaudq.milktea.db;

import com.chaudq.milktea.model.Account;
import com.chaudq.milktea.model.Listfavorite;
import com.chaudq.milktea.model.Registrator;
import com.chaudq.milktea.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class UserDatabase {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/tea";
    public static Connection conn = null;


    @Bean
    public void intialize() {
        String USER = "root";
        String PASS = "15041997";
        try {
            // Buoc 2: Dang ky Driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Dang ket noi toi co so du lieu ...");
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException e) {

            }
            // Buoc 4: Thuc thi truy van
            System.out.println("Tao cac lenh truy van SQL ...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAllAccount() {
        Statement stmt = null;
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Lay du lieu boi su dung ten cot
                String id = rs.getString("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String phone =rs.getString("Phone");
                String email= rs.getString("Email");
                accounts.add(new Account(id, username, password,phone,email));

            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;

    }

    public User checkLogin(Account account) {

        try {
            String sql = "SELECT * FROM account WHERE username= ? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            String id = null;
            while (resultSet.next()) {

                id = resultSet.getString("id");
            }
            if (id == null) return null;
            sql = "SELECT * FROM user WHERE accountid= ?";
            preparedStatement.close();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account a = getAccountById(id);
                String listFavoriteid=resultSet.getString("ListFavoriteId");
                Listfavorite listfavorite = new ListFavoriteDatabase().getListfavoriteById(listFavoriteid);
                String fullname = resultSet.getString("fullname");
                String sex = resultSet.getString("sex");
                String dob = resultSet.getString("dob");
                int point = resultSet.getInt("point");
                return new User(id,fullname,sex,dob,point,listfavorite,a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public String registerAcccount(Account account) {
        System.out.println("Đã gọi");
        List<Account> accounts = getAllAccount();
        for (int i = 0; i < accounts.size(); i++) {
            if (account.getUsername().equalsIgnoreCase(accounts.get(i).getUsername()) || account.getEmail().equalsIgnoreCase(accounts.get(i).getEmail())) {
                return "Tài khoản hoặc email đã tồn tại";
            }
        }



        String sql = "INSERT INTO account (id, username, password, phone, email) VALUES (?, ?, ?, ?, ?) ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, account.getId());
            preparedStatement.setString(2, account.getUsername());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setString(4, account.getPhone());
            preparedStatement.setString(5, account.getEmail());
            Listfavorite listfavorite = new Listfavorite();
            listfavorite.setId(UUID.randomUUID().toString());
            new ListFavoriteDatabase().addListFavorite(listfavorite);
            Boolean result = preparedStatement.execute();
            if (!result) {
                sql = "INSERT INTO user (id,accountid,point,listfavoriteid) VALUES (?, ?,?,? )";
                preparedStatement.close();
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, account.getId());
                preparedStatement.setString(2, account.getId());
                preparedStatement.setString(3, "0");
                preparedStatement.setString(4,listfavorite.getId());
                result = preparedStatement.execute();


                return result.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Null";

    }

    public Account getAccountById(String id) {
        String sql = "SELECT * FROM account WHERE id= ?";
        Account user;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                user = new Account(id, username, password, phone, email);
                user.setEmail(email);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String updateUser(User user) {
        String sql = "UPDATE user SET fullname = ?, dob = ?,sex = ?,point = ? WHERE id= ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getFullname());
            preparedStatement.setString(2, user.getDob());
            preparedStatement.setString(3, user.getSex());
            preparedStatement.setString(4, String.valueOf(user.getPoint()));
            preparedStatement.setString(5, user.getAccountId().getId());
            boolean result = preparedStatement.execute();




        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updatePoint(User user){
        String sql = "UPDATE user SET point = ? WHERE id= ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(user.getPoint()));
            preparedStatement.setString(2, user.getAccountId().getId());
            boolean result = preparedStatement.execute();
            return result;


        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean updateRegisterID(Registrator registrator){
        String sql = "UPDATE account SET registerid = ? WHERE id= ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(registrator.getId()));
            preparedStatement.setString(2, registrator.getUser().getId());
            boolean result = preparedStatement.execute();
            return result;


        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateUserInfo(User user){
        updateUser(user);
        String sql = "UPDATE tea.account SET phone = ?, email = ? WHERE id= ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,user.getAccountId().getPhone());
            preparedStatement.setString(2,user.getAccountId().getEmail());
            preparedStatement.setString(3,user.getAccountId().getId());
            boolean result=preparedStatement.execute();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return true;
    }
}
