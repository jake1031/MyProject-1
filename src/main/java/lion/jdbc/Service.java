package lion.jdbc;

import lion.jdbc.DAO.MemberDAO;
import lion.jdbc.DAO.ProductDAO;
import lion.jdbc.DTO.Member;
import lion.jdbc.DTO.Product;

import java.util.List;
import java.util.Scanner;

public class Service {
    private static Scanner sc = new Scanner(System.in); // 재사용
    private static MemberDAO memberDAO = new MemberDAO();
    private static ProductDAO productDAO = new ProductDAO();

    public static void main(String[] args) {
        while (true) {
            showMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine()); // nextInt()보다 에러 처리에 유리

                switch (choice) {
                    case 1 -> registerMember(); // 자바 14 이상이면 이 문법이 깔끔!
                    case 2 -> registerProduct();
                    case 3 -> showProducts();
                    case 4 -> deleteMember();
                    case 5 -> deleteProduct();
                    case 0 -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("1~5 사이의 숫자를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("️숫자만 입력 가능합니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private static void showMenu() {
        System.out.println("\n========= 판매 사이트 ==========");
        System.out.println("1. 판매자 등록  2. 상품 등록  3. 상품 조회");
        System.out.println("4. 판매자 삭제  5. 상품 삭제  0. 종료");
        System.out.print("선택: ");
    }

    private static void registerMember() throws Exception {
        System.out.print("아이디: ");
        String id = sc.nextLine();

        System.out.print("비번: ");
        String pw = sc.nextLine();

        System.out.print("이름: ");
        String name = sc.nextLine();

        Member member = new Member(id, pw, name);
        long member_id = memberDAO.insertMember(member);
        if (member_id > 0) {
            System.out.println("판매자 등록이 완료되었습니다.");
            System.out.println("해당 판매자의 고유 아이디는 " + member_id +"입니다.");
        } else {
            System.out.println("판매자 등록이 실패했습니다..");
        }
    }

    private static void registerProduct() throws Exception {
        System.out.print("상품명: ");
        String name = sc.nextLine();

        System.out.print("가격: ");
        int price = Integer.parseInt(sc.nextLine());

        System.out.print("재고: ");
        int stock = Integer.parseInt(sc.nextLine());

        System.out.print("판매자 아이디: ");
        Long sellerId = Long.parseLong(sc.nextLine());

        Product product = new Product(name, price, stock, sellerId);
        if (productDAO.insertProduct(product)) {
            System.out.println("상품 등록이 완료되었습니다.");
        } else {
            System.out.println("상품 등록이 실패했습니다..");
        }
    }

    private static void showProducts() {
        List<Product> productList = productDAO.findAll();
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    private static void deleteMember() throws Exception {
        System.out.print("삭제하려는 회원 고유번호를 입력해주십시오: ");
        Long memberId = Long.parseLong(sc.nextLine());
        memberDAO.deleteMember(memberId);
        System.out.println(memberId + "번 사용자 삭제 완료!");
    }

    private static void deleteProduct() throws Exception {
        System.out.print("삭제하려는 상품 고유번호를 입력해주십시오: ");
        Long productId = Long.parseLong(sc.nextLine());
        if (productDAO.deleteProduct(productId)) {
            System.out.println("\n고유번호 " + productId + "번 상품 삭제 성공!");
        } else {
            System.out.println("해당 고유번호의 상품은 없습니다..");
        }
    }
}