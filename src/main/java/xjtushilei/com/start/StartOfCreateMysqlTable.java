package xjtushilei.com.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xjtushilei.com.entity.Url;
import xjtushilei.com.entity.User;
import xjtushilei.com.repository.UrlRepository;
import xjtushilei.com.repository.UserRepository;


/**
 * 服务启动执行的程序.不存在密码就执行。
 */

@Component
@Order(value = 1)
public class StartOfCreateMysqlTable implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>>>>>>>>>>>>>>服务启动执行，开始检查数据库操作<<<<<<<<<<<<<");
        if (userRepository.findAll().size() == 0) {
            logger.info("用户数据库中没有数据，开始插入默认用户密码");
            userRepository.save(new User("jcxcqc", "xcqcscb"));
            userRepository.save(new User("admin", "shilei"));
        }else {
            logger.info("存在默认用户密码");
        }
        if (urlRepository.findAll().size() == 0) {
            logger.info("两个链接是空的，开始填写默认链接");
            urlRepository.save(new Url("lianjie1", "http://mp.weixin.qq.com/s/wngZepcjZRj-i7t3hEkbfQ"));
            urlRepository.save(new Url("lianjie2", "https://h5.koudaitong.com/v2/feature/cs5udv0g"));
        }
        else {
            logger.info("存在默认两个链接");
        }
    }


}

