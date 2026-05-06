package lion.jdbc.DAO;

import lion.jdbc.DTO.Product;
import lion.jdbc.common.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product, Long> {
    // 상품 등록
    @Override
    public Long insert(Product product) throws Exception {
        String sql = "insert into product (name, price, stock, seller_id) values(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setLong(4, product.getSellerId());

            int count = ps.executeUpdate();

            if (count > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        return 0L;
    }

    // 상품 업데이트
    @Override
    public boolean update(Product product) throws Exception {
        String sql = "update product set name = ?, price = ?, stock = ? where product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setLong(4, product.getProductId());

            int count = ps.executeUpdate();
            return count > 0;
        }
    }

    public Product getProduct(Long productId) throws Exception {
        String sql = "select * from product where product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getLong("seller_id")
                );
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean delete(Long productId) throws Exception {
        String sql = "delete from product where product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            int count = ps.executeUpdate();
            return count > 0;
        }
    }

    // 전체 상품 조회
    public List<Product> findAll() throws Exception {
        String sql = "SELECT * FROM product";
        List<Product> productList = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getLong("seller_id")
                );
                product.setCreatedAt(rs.getTimestamp("created_at"));

                productList.add(product);
            }
        }
        return productList;
    }
}
