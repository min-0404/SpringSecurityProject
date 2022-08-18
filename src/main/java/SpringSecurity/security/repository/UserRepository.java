package SpringSecurity.security.repository;

import SpringSecurity.security.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 기본적인 CRUD 함수를 JpaRepository 가 들고 있음
// @Repository 어노테이션이 없어도 빈으로 등록 가능. JpaRepository 를 상속했기 때문에
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);
}


