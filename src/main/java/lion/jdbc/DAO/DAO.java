package lion.jdbc.DAO;

import java.util.List;

public interface DAO<T, K> {
    // 생성 시 생성된 ID를 반환하도록 설계
    Long insert(T entity) throws Exception;

    // 수정 및 삭제는 성공 여부(boolean) 반환
    boolean update(T entity) throws Exception;
    boolean delete(K id) throws Exception;

    // 조회 로직 (필요 시 추가)
//    List<T> findAll() throws Exception;
}
