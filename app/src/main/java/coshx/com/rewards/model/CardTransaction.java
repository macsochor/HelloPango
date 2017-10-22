package coshx.com.rewards.model;

/**
 * Created by MSMD on 10/22/17.
 */

public class CardTransaction {
    String amount, date, purchase;
    public CardTransaction(){}

    public CardTransaction(String amount, String date, String purchase){
        this.amount = amount;
        this.date = date;
        this.purchase = purchase;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }
}
