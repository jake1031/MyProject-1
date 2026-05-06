package lion.jdbc.Service;

import lion.jdbc.DAO.ProductDAO;
import lion.jdbc.DTO.Product;
import lion.jdbc.common.Session;

import java.util.List;
import java.util.Scanner;

public class ProductService {
    private final Scanner sc; // 재사용
    private final Session session;
    private final ProductDAO productDAO = new ProductDAO();

    public ProductService(Scanner sc, Session session) {
        this.sc = sc;
        this.session = session;
    }

    void insertProduct(MemberService memberService) {
        try {
            System.out.print("상품명: ");
            String name = sc.nextLine();
            int price = inputInt("가격", 0);
            int stock = inputInt("재고", 0);

            Long sellerId = memberService.getMemberIdByLoginId(inputString("판매자 아이디"));

            if (sellerId == null) {
                System.out.println("❌ 존재하지 않는 판매자 아이디입니다. 등록을 취소합니다.");
                return;
            }

            Product product = new Product(name, price, stock, sellerId);
            Long result = productDAO.insert(product);
            if (result != 0L) {
                System.out.println("고유번호 " + result + "번 상품 등록이 완료되었습니다.");
            } else {
                System.out.println("상품 등록이 실패했습니다..");
            }
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다..");
        }
    }

    void updateProduct() {
        try {
            Long productId = inputLong("업데이트하려는 상품의 고유번호를 입력해주십시오");
            Product product = productDAO.getProduct(productId);

            if (product != null) {
                System.out.println("======================================");
                System.out.println("업데이트하려는 상품의 내용을 아래에 입력해주십시오.");

                System.out.print("상품명: ");
                String name = sc.nextLine();
                int price = inputInt("가격", 0);
                int stock = inputInt("재고", 0);

                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);

                if (productDAO.update(product)) {
                    System.out.println("상품 업데이트가 완료되었습니다.");
                } else {
                    System.out.println("상품 업데이트 도중 문제가 발생했습니다..");
                }
            } else {
                System.out.println("해당 고유번호를 가진 상품이 존재하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("입력값이 잘못되었습니다..");
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다..");
        }
    }

    void deleteProduct() {
        try {
            Long productId = inputLong("삭제하려는 상품의 고유번호를 입력해주십시오");
            if (productDAO.delete(productId)) {
                System.out.println("\n고유번호 " + productId + "번 상품 삭제 성공!");
            } else {
                System.out.println("해당 고유번호의 상품은 없습니다..");
            }
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다..");
        }
    }

    void showProducts() {
        try {
            List<Product> productList = productDAO.findAll();
            if (productList.isEmpty()) {
                System.out.println("등록된 상품이 없습니다.");
                return;
            }

            System.out.println("======= [ 전체 상품 목록 ] =======");
            for (Product product : productList) {
                System.out.println(product);
            }
            System.out.println("===============================");

        } catch (Exception e) {
            System.out.println("상품 검색 중 오류가 발생했습니다..");
        }
    }

    // 숫자를 안전하게 입력받는 공통 메서드
    private int inputInt(String prompt, int min) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                int value = Integer.parseInt(sc.nextLine());
                if (value < min) {
                    System.out.println("⚠️ " + min + " 이상의 숫자를 입력해주세요.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 숫자만 입력 가능합니다.");
            }
        }
    }

    private Long inputLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 올바른 번호(숫자)를 입력해주세요.");
            }
        }
    }

    private String inputString(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }
}
