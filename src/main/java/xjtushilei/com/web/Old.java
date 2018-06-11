package xjtushilei.com.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author scriptshi
 * 2018/6/11
 */
@Deprecated
@RestController
public class Old {


    @RequestMapping("/xingchi")
    void handle1(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://xingchi.xjtushilei.com");
    }

    @RequestMapping("/xingchi/admin.html")
    void handle2(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://xingchi.xjtushilei.com/admin.html");
    }

}
