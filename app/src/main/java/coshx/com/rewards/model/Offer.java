package coshx.com.rewards.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by MSMD on 10/21/17.
 */

@IgnoreExtraProperties
public class Offer {

    public String amount;
    public String merchantLogoUrl;
    public String backgroundUrl;
    public String merchantName;
    public String type;
    public String title;
    public String subtitle;
    public String index;


    public Offer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Offer(String amount, String merchant_logo_url, String backgroundUrl, String merchant_name, String title, String type, String index, String subtitle) {
        this.amount = amount;
        this.merchantLogoUrl = merchant_logo_url;
        this.backgroundUrl = backgroundUrl;
        this.merchantName = merchant_name;
        this.subtitle = subtitle;
        this.title = title;
        this.type = type;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "amount='" + amount + '\'' +
                ", merchantLogoUrl='" + merchantLogoUrl + '\'' +
                ", backgroundUrl='" + backgroundUrl + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", index=" + index +
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

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return Integer.valueOf(index);
    }
}