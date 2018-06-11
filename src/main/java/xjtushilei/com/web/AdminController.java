package xjtushilei.com.web;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xjtushilei.com.entity.Log;
import xjtushilei.com.entity.Url;
import xjtushilei.com.entity.Vipcard;
import xjtushilei.com.repository.LogRepository;
import xjtushilei.com.repository.UrlRepository;
import xjtushilei.com.repository.VipcardRepository;
import xjtushilei.com.utils.ExcelUtils;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by shilei on 2017/3/13.
 */

@RestController
@RequestMapping("/adminAPI")
public class AdminController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogRepository logRepository;


    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private VipcardRepository vipcardRepository;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getUrl() {
        logRepository.save(new Log("getUrl"));
        Map<String, String> result = Stream
                .of("lianjie1", "lianjie2")
                .map(url -> urlRepository.findById(url))
                .collect(
                        Collectors.toMap(urlOptional -> urlOptional.get().getUrlId(), urlOptional -> urlOptional.get().getUrl(), (a, b) -> b));

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public ResponseEntity setUrl(String url1, String url2) {
        logRepository.save(new Log("url1", url1));
        logRepository.save(new Log("url2", url2));
        try {
            urlRepository.save(new Url("lianjie1", url1));
            urlRepository.save(new Url("lianjie2", url2));
        } catch (Exception e) {
            logRepository.save(new Log("error", e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("尊敬的管理员！设置过程出现了不可预知的问题，请重试~");
        }
        return ResponseEntity.status(HttpStatus.OK).body("尊敬的管理员！设置链接地址已成功！");

    }

    @RequestMapping(value = "/submitExcel/post", method = RequestMethod.POST)
    public ResponseEntity getExcel(@RequestParam("excel") MultipartFile file) {
        try {
            Log log = logRepository.save(new Log("file", ""));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");
            log.setLog(System.getProperty("user.home") + "/.xingchi/" + format.format(new Date()) + ".xls");
            log = logRepository.save(log);
            System.out.println(log.getLog());
            FileOutputStream f = new FileOutputStream(log.getLog());
            IOUtils.write(file.getBytes(), f);
            f.close();

        } catch (Exception e) {
            logRepository.save(new Log("error", e.getMessage()));
        }
        try {
            ArrayList<Vipcard> infoList = ExcelUtils.readExcel(file.getInputStream());

            // 删库
            if (infoList.size() > 0) {
                vipcardRepository.deleteAll();
            }
            vipcardRepository.saveAll(infoList);
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put("successNum", infoList.size());
            return ResponseEntity.status(HttpStatus.OK).body(hashMap);
        } catch (Exception e) {
            logRepository.save(new Log("error", e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("尊敬的管理员！从excel插入到系统的数据库出错出现了不可预知的问题，请重试~");
        }


    }

}
