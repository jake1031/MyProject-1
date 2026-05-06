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

    // 상품 생성용
    public Product(String name, int price, int stock, Long sellerId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sellerId = sellerId;
    }

    // 상품 업데이트용
    public Product(Long productId, String name, int price, int stock, Long sellerId) {
        this.productId = productId;
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
        return String.format(
                "--------------------------------\n" +
                        "상품번호   : %d\n" +
                        "상품명    : %s\n" +
                        "가격     : %d원\n" +
                        "재고     : %d개\n" +
                        "판매자ID  : %d\n" +
                        "등록일    : %s\n" +
                        "--------------------------------",
                productId, name, price, stock, sellerId,
                createdAt != null ? createdAt : "정보 없음"
        );
    }
}
