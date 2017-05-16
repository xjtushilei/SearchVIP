package xjtushilei.com.web;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xjtushilei.com.domain.Url;
import xjtushilei.com.domain.Vipcard;
import xjtushilei.com.repository.UrlRepository;
import xjtushilei.com.repository.VipcardRepository;
import xjtushilei.com.utils.ExcelUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shilei on 2017/3/13.
 */

@RestController
@RequestMapping("/adminAPI")
public class AdminController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private VipcardRepository vipcardRepository;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getUrl(String temp) {
        List<String> idList = new ArrayList<>();
        idList.add("lianjie1");
        idList.add("lianjie2");
        List<Url> urlList = urlRepository.findAll(idList);
        Map<String, String> result = new HashMap<>();
        urlList.forEach(url -> result.put(url.getUrlId(), url.getUrl()));
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public ResponseEntity setUrl(String url1, String url2, String temp) {

        try {
            urlRepository.save(new Url("lianjie1", url1));
            urlRepository.save(new Url("lianjie2", url2));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("尊敬的管理员！设置过程出现了不可预知的问题，请重试~");
        }
        return ResponseEntity.status(HttpStatus.OK).body("尊敬的管理员！设置链接地址已成功！");

    }

    @RequestMapping(value = "/submitExcel/post", method = RequestMethod.POST)
    public ResponseEntity getExcel(@RequestParam("excel") MultipartFile file) {

        try {
            ArrayList<Vipcard> infoList = ExcelUtils.readExcel(file.getInputStream());

            // 删库
            if (infoList.size() > 0) {
                vipcardRepository.deleteAll();
            }
            vipcardRepository.save(infoList);
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put("successNum", infoList.size());
            return ResponseEntity.status(HttpStatus.OK).body(hashMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("尊敬的管理员！从excel插入到系统的数据库出错出现了不可预知的问题，请重试~");
        }


    }

}
