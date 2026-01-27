package repository;

import dto.BuyerDTO;
import dto.LoginDTO;
import dto.SecurityQuestionDTO;
import dto.SellerDTO;
import enumeration.ConnectionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationRepoImpl implements RegistrationRepository {


    @Override
    public List<SecurityQuestionDTO> getAllQuestions() {

        List<SecurityQuestionDTO> list = new ArrayList<>();
        String sql = "SELECT question_id, question_text FROM security_questions";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new SecurityQuestionDTO(
                        rs.getInt("question_id"),
                        rs.getString("question_text")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public boolean registerBuyer(BuyerDTO dto)
    {
        String userSql =
                "INSERT INTO users (email, password, role, security_question_id, security_answer) VALUES (?, ?, ?, ?, ?)";

        String buyerSql =
                "INSERT INTO buyer_details (buyer_id, full_name, gender, date_of_birth, phone) " +
                        "VALUES (?, ?, ?, ?, ?)";

        Connection con = null;


        try {
            con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
            con.setAutoCommit(false);

            PreparedStatement pst =
                    con.prepareStatement(userSql);

            pst.setString(1, dto.getEmail());
            pst.setString(2, dto.getPassword());
            pst.setString(3, "BUYER");
            pst.setInt(4,dto.getSecurityQuestionId());
            pst.setString(5,dto.getSecurityAnswer());
            int rows = pst.executeUpdate();
            if(rows>0) {

                String fetchIdSql = "SELECT user_id FROM users WHERE email = ?";

                PreparedStatement psFetch = con.prepareStatement(fetchIdSql);
                psFetch.setString(1, dto.getEmail());

                ResultSet rs = psFetch.executeQuery();
                rs.next();
                int userId = rs.getInt("user_id");

                PreparedStatement psBuyer = con.prepareStatement(buyerSql);
                psBuyer.setInt(1, userId);
                psBuyer.setString(2, dto.getFullName());
                psBuyer.setString(3, dto.getGender());
                psBuyer.setDate(4, Date.valueOf(dto.getDob()));
                psBuyer.setString(5, dto.getPhone());

                psBuyer.executeUpdate();

                con.commit();
                return true;
            }

           return false;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean registerSeller(SellerDTO dto) {
        String userSql =
                "INSERT INTO users (email, password, role, security_question_id, security_answer) VALUES (?, ?, ?, ?, ?)";

        String sellerQuery =
                "INSERT INTO seller_details (seller_id, business_name, gst_number, address, phone) " +
                        "VALUES (?, ?, ?, ?, ?)";

        Connection con = null;


        try {
            con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
            con.setAutoCommit(false);

            PreparedStatement pst =
                    con.prepareStatement(userSql);

            pst.setString(1, dto.getEmail());
            pst.setString(2, dto.getPassword());
            pst.setString(3, "SELLER");
            pst.setInt(4,dto.getSecurityQuestionId());
            pst.setString(5,dto.getSecurityAnswer());
            int rows= pst.executeUpdate();

            if(rows>0) {
                String fetchIdSql = "SELECT user_id FROM users WHERE email = ?";

                PreparedStatement psFetch = con.prepareStatement(fetchIdSql);
                psFetch.setString(1, dto.getEmail());

                ResultSet rs = psFetch.executeQuery();
                rs.next();
                int userId = rs.getInt("user_id");
                PreparedStatement psSeller = con.prepareStatement(sellerQuery);
                psSeller.setInt(1, userId);
                psSeller.setString(2, dto.getBusinessName());
                psSeller.setString(3, dto.getGstNumber());
                psSeller.setString(4, dto.getAddress());
                psSeller.setString(5, dto.getPhone());

                psSeller.executeUpdate();

                con.commit();
                return true;

            }
            return false;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LoginDTO login(String email) {

        String sql =
                "SELECT user_id, role, password FROM users WHERE email = ?";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                String passwordFetched = rs.getString("password");

                return new LoginDTO(userId, role, passwordFetched, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new LoginDTO(0, null,null, false);
    }

    @Override
    public String getUserNameById(int userId,String role) {

        if(role.equals("BUYER"))
        {
            String sql =
                    "SELECT full_name FROM buyer_details WHERE buyer_id = ?";

            try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);


                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String buyerName = rs.getString("full_name");

                    return buyerName;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            String sql =
                    "SELECT business_name FROM seller_details WHERE seller_id = ?";

            try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);


                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String sellerName = rs.getString("business_name");

                    return sellerName;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    public String getPasswordByUserId(int userId) {

        String sql =
                "SELECT password FROM users WHERE user_id = ?";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String password = rs.getString("password");

                return password;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateUserPassword(int userId, String newPassword) {

        String sql =
                "update users set password = ? where user_id = ?";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,newPassword);
            ps.setInt(2, userId);


            return ps.executeUpdate() > 0;



        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public String getQuestionByEmail(String email) {
        String sql = "SELECT sq.question_text " +
                "FROM users u " +
                "JOIN security_questions sq " +
                "ON u.security_question_id = sq.question_id " +
                "WHERE u.email = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("question_text");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean verifyAnswer(String email, String answer) {
        String sql = "SELECT 1 FROM users WHERE email = ? AND LOWER(security_answer) = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, answer.trim().toLowerCase());
            return ps.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


