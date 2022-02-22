package com.app.dportshipper.model.response;

import java.util.List;

public class ResInvoice {

    private String currency;
    private String updated;
    private String created;
    private String failure_redirect_url;
    private String success_redirect_url;
    private boolean should_send_email;
    private boolean should_exclude_credit_card;
    private List<String> available_paylaters;
    private List<Available_ewallets> available_ewallets;
    private List<Available_retail_outlets> available_retail_outlets;
    private List<Available_banks> available_banks;
    private String invoice_url;
    private String expiry_date;
    private String description;
    private String payer_email;
    private int amount;
    private String merchant_profile_picture_url;
    private String merchant_name;
    private String status;
    private String user_id;
    private String external_id;
    private String id;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getFailure_redirect_url() {
        return failure_redirect_url;
    }

    public void setFailure_redirect_url(String failure_redirect_url) {
        this.failure_redirect_url = failure_redirect_url;
    }

    public String getSuccess_redirect_url() {
        return success_redirect_url;
    }

    public void setSuccess_redirect_url(String success_redirect_url) {
        this.success_redirect_url = success_redirect_url;
    }

    public boolean getShould_send_email() {
        return should_send_email;
    }

    public void setShould_send_email(boolean should_send_email) {
        this.should_send_email = should_send_email;
    }

    public boolean getShould_exclude_credit_card() {
        return should_exclude_credit_card;
    }

    public void setShould_exclude_credit_card(boolean should_exclude_credit_card) {
        this.should_exclude_credit_card = should_exclude_credit_card;
    }

    public List<String> getAvailable_paylaters() {
        return available_paylaters;
    }

    public void setAvailable_paylaters(List<String> available_paylaters) {
        this.available_paylaters = available_paylaters;
    }

    public List<Available_ewallets> getAvailable_ewallets() {
        return available_ewallets;
    }

    public void setAvailable_ewallets(List<Available_ewallets> available_ewallets) {
        this.available_ewallets = available_ewallets;
    }

    public List<Available_retail_outlets> getAvailable_retail_outlets() {
        return available_retail_outlets;
    }

    public void setAvailable_retail_outlets(List<Available_retail_outlets> available_retail_outlets) {
        this.available_retail_outlets = available_retail_outlets;
    }

    public List<Available_banks> getAvailable_banks() {
        return available_banks;
    }

    public void setAvailable_banks(List<Available_banks> available_banks) {
        this.available_banks = available_banks;
    }

    public String getInvoice_url() {
        return invoice_url;
    }

    public void setInvoice_url(String invoice_url) {
        this.invoice_url = invoice_url;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMerchant_profile_picture_url() {
        return merchant_profile_picture_url;
    }

    public void setMerchant_profile_picture_url(String merchant_profile_picture_url) {
        this.merchant_profile_picture_url = merchant_profile_picture_url;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
