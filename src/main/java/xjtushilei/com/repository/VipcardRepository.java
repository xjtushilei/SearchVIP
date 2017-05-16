package xjtushilei.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xjtushilei.com.domain.Vipcard;

/**
 * Created by shilei on 2017/3/13.
 */
public interface VipcardRepository extends JpaRepository<Vipcard, Long> {

    Vipcard findByPhone(String phone);

    Vipcard findByVipID(String vipID);


}
