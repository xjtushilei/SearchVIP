package xjtushilei.com.web;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xjtushilei.com.entity.Log;
import xjtushilei.com.entity.Vipcard;
import xjtushilei.com.repository.LogRepository;
import xjtushilei.com.repository.VipcardRepository;

import java.util.HashMap;

/**
 * Created by shilei on 2017/3/13.
 */

@RestController
@RequestMapping("/searchAPI")
public class SearchController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private VipcardRepository vipcardRepository;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity get(String vipID) {
        logRepository.save(new Log("search", vipID));
        try {
            Vipcard vipcard;
            if (vipID.length() == 11) {
                vipcard = vipcardRepository.findByPhone(vipID);
            } else {
                vipcard = vipcardRepository.findByVipID(vipID);
            }
            if (vipcard == null) {
                HashMap<String, String> error = new HashMap<>();
                error.put("state", "您输入的会员卡号不存在！请重新输入进行查询~");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(vipcard);
            }

        } catch (Exception e) {
            logRepository.save(new Log("error", e.getMessage()));
            HashMap<String, String> error = new HashMap<>();
            error.put("state", "不好意思！查询数据库出错！请重试或者联系管理员~");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }


    }

}
