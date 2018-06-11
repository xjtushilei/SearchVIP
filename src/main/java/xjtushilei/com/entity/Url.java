package xjtushilei.com.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author shilei
 * @Date 2017/5/16.
 */

@Entity
public class Url {

    @Id
    private String urlId;
    private String url;

    public Url() {
    }

    public Url(String urlId, String url) {
        this.urlId = urlId;
        this.url = url;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
