package coshx.com.rewards.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by MSMD on 10/21/17.
 */

@IgnoreExtraProperties
public class Offer {

    public String amount;
    public String merchantLogoUrl;
    public String merchantName;
    public String title;


    public Offer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Offer(String amount, String merchant_logo_url, String merchant_name, String title) {
        this.amount = amount;
        this.merchantLogoUrl = merchant_logo_url;
        this.merchantName = merchant_name;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "amount='" + amount + '\'' +
                ", merchant_logo_url='" + merchantLogoUrl + '\'' +
                ", merchat_name='" + merchantName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMerchantLogoUrl() {
        return merchantLogoUrl;
    }

    public void setMerchantLogoUrl(String merchantLogoUrl) {
        this.merchantLogoUrl = merchantLogoUrl;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}