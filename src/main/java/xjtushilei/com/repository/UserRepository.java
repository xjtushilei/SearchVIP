package xjtushilei.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xjtushilei.com.domain.User;

/**
 * Created by shilei on 2017/3/13.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndPasswd(String name, String passwd);

}
