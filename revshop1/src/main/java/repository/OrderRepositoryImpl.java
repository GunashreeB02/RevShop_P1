package repository;

import dto.*;
import enumeration.ConnectionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public int createOrder(int buyerId, double totalAmount) {

        String insertSql =
                "INSERT INTO orders (buyer_id, total_amount, status) VALUES (?, ?, 'PLACED')";

        String selectSql =
                "SELECT MAX(order_id) FROM orders WHERE buyer_id = ?";

        try (Connection con = DriverManager.getConnection( ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement psInsert = con.prepareStatement(insertSql);
             PreparedStatement psSelect = con.prepareStatement(selectSql)) {

            psInsert.setInt(1, buyerId);
            psInsert.setDouble(2, totalAmount);
            psInsert.executeUpdate();

            psSelect.setInt(1, buyerId);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void addOrderItem(OrderItemDTO dto) {

        String sql =
                "INSERT INTO order_items (order_id, product_id, seller_id, quantity, price) " +
                        "VALUES (?, ?, ?, ?, ?)";
        String productSql =
                "SELECT product_name FROM product WHERE product_id = ?";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql);
        PreparedStatement psProduct = con.prepareStatement(productSql)){

            ps.setInt(1, dto.getOrderId());
            ps.setInt(2, dto.getProductId());
            ps.setInt(3, dto.getSellerId());
            ps.setInt(4, dto.getQuantity());
            ps.setDouble(5, dto.getPrice());

            ps.executeUpdate();

            // 2️⃣ Fetch product name
            psProduct.setInt(1, dto.getProductId());
            ResultSet rs = psProduct.executeQuery();

            String productName = "your product";
            if (rs.next()) {
                productName = rs.getString("product_name");
            }

            // 🔔 ORDER PLACED NOTIFICATION
            NotifyRepository notificationRepo = new NotifyRepositoryImpl();

            String msg = "📦 New order placed for \""+ dto.getProductId()+" " + productName +
                    "\" (Qty: " + dto.getQuantity() + ")";

            notificationRepo.addNotification(dto.getSellerId(), msg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reduceStock(int productId, int qty) {

        String sql =
                "UPDATE product SET stock = stock - ? " +
                        "WHERE product_id = ?";

        String checkSql = """
        SELECT stock, stock_threshold, seller_id, product_name
        FROM product
        WHERE product_id = ?
    """;

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, productId);
            ps.executeUpdate();

            // check stock
            PreparedStatement ps2 = con.prepareStatement(checkSql);
            ps2.setInt(1, productId);
            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                int threshold = rs.getInt("stock_threshold");
                int sellerId = rs.getInt("seller_id");
                String productName = rs.getString("product_name");

                if (stock <= threshold) {
                    NotifyRepository notificationRepo =
                            new NotifyRepositoryImpl();

                    String msg = "⚠ Low stock alert for " + productName +
                            ". Remaining stock: " + stock;

                    notificationRepo.addNotification(sellerId, msg);
                }
            }

        }


        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearCart(int buyerId) {

        String sql = "DELETE FROM cart WHERE buyer_id = ?";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ps.executeUpdate();
        } catch (SQLException e) {
e.printStackTrace();        }
    }


    @Override
    public void addOrderAddress(OrderAddressDTO dto) {

        String sql = "INSERT INTO order_address " +
                "(order_id, address_type, full_name, phone, " +
                "address_line1, address_line2, city, state, pincode) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dto.getOrderId());
            ps.setString(2, dto.getAddressType()); // SHIPPING / BILLING
            ps.setString(3, dto.getFullName());
            ps.setString(4, dto.getPhone());
            ps.setString(5, dto.getAddressLine1());
            ps.setString(6, dto.getAddressLine2());
            ps.setString(7, dto.getCity());
            ps.setString(8, dto.getState());
            ps.setString(9, dto.getPincode());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPayment(PaymentDTO dto) {

        String sql = "INSERT INTO payment " +
                "(order_id, payment_method, amount, payment_status) " +
                "VALUES ( ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dto.getOrderId());
            ps.setString(2, dto.getPaymentMethod());
            ps.setDouble(3, dto.getAmount());
            ps.setString(4,dto.getPaymentStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<OrderDTO> getOrdersByBuyer(int buyerId) {

        List<OrderDTO> orders = new ArrayList<>();

        String orderSql =
                "SELECT order_id, total_amount, status, order_date " +
                        "FROM orders WHERE buyer_id = ? ORDER BY order_date DESC";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(orderSql)) {

            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrderDTO order = new OrderDTO();
                int orderId = rs.getInt("order_id");

                order.setOrderId(orderId);
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                Timestamp ts = rs.getTimestamp("order_date");
                order.setOrderDate(ts.toLocalDateTime());
                // Fetch sub details
                order.setItems(getOrderItems(con, orderId));
                order.setShippingAddress(getAddress(con, orderId, "SHIPPING"));
                order.setBillingAddress(getAddress(con, orderId, "BILLING"));

                PaymentDTO payment = getPayment(con, orderId);
                if (payment != null) {
                    order.setPaymentMethod(payment.getPaymentMethod());
                    order.setPaymentStatus(payment.getPaymentStatus());
                }

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    // ---------------- HELPER METHODS ---------------- //

    private List<OrderItemDTO> getOrderItems(Connection con, int orderId) throws SQLException {

        List<OrderItemDTO> items = new ArrayList<>();

        String sql =
                "SELECT oi.product_id, p.product_name, oi.quantity, oi.price " +
                        "FROM order_items oi JOIN product p ON oi.product_id = p.product_id " +
                        "WHERE oi.order_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItemDTO item = new OrderItemDTO();
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }
        }
        return items;
    }

    private OrderAddressDTO getAddress(Connection con, int orderId, String type) throws SQLException {

        String sql =
                "SELECT full_name, phone, address_line1, address_line2, city, state, pincode " +
                        "FROM order_address WHERE order_id = ? AND address_type = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setString(2, type);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                OrderAddressDTO addr = new OrderAddressDTO();
                addr.setFullName(rs.getString("full_name"));
                addr.setPhone(rs.getString("phone"));
                addr.setAddressLine1(rs.getString("address_line1"));
                addr.setAddressLine2(rs.getString("address_line2"));
                addr.setCity(rs.getString("city"));
                addr.setState(rs.getString("state"));
                addr.setPincode(rs.getString("pincode"));
                return addr;
            }
        }
        return null;
    }

    private PaymentDTO getPayment(Connection con, int orderId) throws SQLException {

        String sql =
                "SELECT payment_method, payment_status, amount FROM payment WHERE order_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PaymentDTO payment = new PaymentDTO();
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setPaymentStatus(rs.getString("payment_status"));
                payment.setAmount(rs.getDouble("amount"));
                return payment;
            }
        }
        return null;
    }



    @Override
    public List<SellerOrderDTO> getOrdersForSeller(int sellerId) {

        List<SellerOrderDTO> list = new ArrayList<>();
        String sql = """
    SELECT
        o.order_id,
        o.order_date,
        o.status AS order_status,
        bd.full_name AS buyer_name,
        p.product_name,
        oi.quantity,
        oi.price
    FROM orders o
    JOIN order_items oi
        ON o.order_id = oi.order_id
    JOIN product p
        ON oi.product_id = p.product_id
    JOIN buyer_details bd
        ON o.buyer_id = bd.buyer_id
    WHERE p.seller_id = ?
    ORDER BY o.order_date DESC
    """;




        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SellerOrderDTO dto = new SellerOrderDTO();
                dto.setOrderId(rs.getInt("order_id"));
                dto.setBuyerName(rs.getString("buyer_name"));
                dto.setProductName(rs.getString("product_name"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setPrice(rs.getDouble("price"));
                dto.setOrderStatus(rs.getString("order_status"));
                dto.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }








}
