package lion.jdbc.Service;

import lion.jdbc.common.Session;

import java.util.Scanner;

public class Service {
    private static final Scanner sc = new Scanner(System.in);
    private static final Session session = new Session();
    private static final MemberService memberService = new MemberService(sc, session);
    private static final ProductService productService = new ProductService(sc, session);


    public static void start() {
        while (true) {
            showMenu();

            int choice = inputInt("선택", 0, 8);

            switch (choice) {
                case 1 -> memberService.insertMember();
                case 2 -> productService.insertProduct(memberService);
                case 3 -> productService.showProducts();
                case 4 -> memberService.updateMember();
                case 5 -> productService.updateProduct();
                case 6 -> memberService.login();
                case 7 -> memberService.deleteMember();
                case 8 -> productService.deleteProduct();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n========= 판매 사이트 ==========");
        System.out.println("1. 판매자 등록  2. 상품 등록  3. 상품 조회");
        System.out.println("4. 판매자 수정  5. 상품 수정  6. 로그인");
        System.out.println("7: 판매자 삭제  8: 상품 삭제  0: 종료");
    }

    private static int inputInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                int value = Integer.parseInt(sc.nextLine());
                if (value < min || value > max) {
                    System.out.println(min + "~" + max + " 사이의 숫자를 입력해주세요");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 숫자만 입력 가능합니다.");
            }
        }
    }
}