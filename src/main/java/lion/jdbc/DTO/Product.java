package lion.jdbc.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Product {
    private Long productId;     // PK
    private String name;        // 상품명
    private int price;          // 가격
    private int stock;          // 재고
    private Long sellerId;      // FK (Member의 memberId)
    private LocalDateTime createdAt;

    // 기본 생성자 : 안씀
    public Product() {
    }

    public Product(String name, int price, int stock, Long sellerId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sellerId = sellerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt.toLocalDateTime();
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        System.out.println("상품 이름: \t" + getName());
        System.out.println("가격: \t\t" + getPrice() + "원");
        System.out.println("재고 수량: \t" + getStock() + "개");
        System.out.println("판매자: \t\t" + getSellerId());
        System.out.println("등록일:  \t\t" + getCreatedAt());

        return "";
    }
}
