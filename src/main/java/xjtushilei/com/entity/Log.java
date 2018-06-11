package xjtushilei.com.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * @author scriptshi
 * 2018/6/11
 */
@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    private Date time;
    @Lob
    private String log;

    public Log(String type, String log) {
        this.type = type;
        this.time = new Date();
        this.log = log;
    }

    public Log(String type) {
        this.type = type;
        this.time = new Date();
        this.log = "";
    }

    public Log() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}

