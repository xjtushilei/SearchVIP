package xjtushilei.com.web;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xjtushilei.com.entity.Log;
import xjtushilei.com.entity.User;
import xjtushilei.com.repository.LogRepository;
import xjtushilei.com.repository.UserRepository;

/**
 * Created by shilei on 2017/3/13.
 */

@RestController
@RequestMapping("/loginapi")
public class LoginController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String login1(String name, String passwd) {
        logRepository.save(new Log("login", name));
        User user = userRepository.findByNameAndPasswd(name, passwd);
        if (user != null) {
            return "登录成功";
        } else {
            return "密码错误";
        }
    }

}
