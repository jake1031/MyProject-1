package lion.jdbc.DAO;

import lion.jdbc.DTO.Product;
import lion.jdbc.common.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // 상품 등록
    public boolean insertProduct(Product product) throws Exception {
        String sql = "insert into product (name, price, stock, seller_id) values(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setLong(4, product.getSellerId());

            int count = ps.executeUpdate();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 상품 업데이트
    public boolean updateProduct(Product product) throws Exception {
        String sql = "update product set name = ?, price = ?, stock = ? where product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setLong(4, product.getProductId());

            int count = ps.executeUpdate();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(Long productId) throws Exception {
        String sql = "delete from product where product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            int count = ps.executeUpdate();
            if (count > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 전체 상품 조회
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        List<Product> productList = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                product.setProductId(rs.getLong("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setStock(rs.getInt("stock"));
                product.setSellerId(rs.getLong("seller_id"));
                product.setCreatedAt(rs.getTimestamp("created_at"));

                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
}