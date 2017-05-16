package xjtushilei.com.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author shilei
 * @Date 2017/5/16.
 */

@Entity
public class Vipcard {

    @Id
    private String vipID;

    private String cardType;
    private String balance;
    private String remainingPoints;
    private String carNumber;
    private String phone;

    public Vipcard() {
    }

    public Vipcard(String vipID, String cardType, String balance, String remainingPoints, String carNumber, String phone) {
        this.vipID = vipID;
        this.cardType = cardType;
        this.balance = balance;
        this.remainingPoints = remainingPoints;
        this.carNumber = carNumber;
        this.phone = phone;
    }

    public String getVipID() {
        return vipID;
    }

    public void setVipID(String vipID) {
        this.vipID = vipID;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRemainingPoints() {
        return remainingPoints;
    }

    public void setRemainingPoints(String remainingPoints) {
        this.remainingPoints = remainingPoints;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
