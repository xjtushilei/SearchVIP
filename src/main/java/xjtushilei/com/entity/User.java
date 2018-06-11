package xjtushilei.com.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author shilei
 * @Date 2017/5/16.
 */

@Entity
public class User {

    @Id
    private String name;
    private String passwd;

    public User() {
    }

    public User(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
