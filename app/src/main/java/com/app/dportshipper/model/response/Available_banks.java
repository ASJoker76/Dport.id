package com.app.dportshipper.model.response;

public class Available_banks {
    private int identity_amount;
    private String account_holder_name;
    private String bank_branch;
    private int transfer_amount;
    private String collection_type;
    private String bank_code;

    public int getIdentity_amount() {
        return identity_amount;
    }

    public void setIdentity_amount(int identity_amount) {
        this.identity_amount = identity_amount;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public int getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(int transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public String getCollection_type() {
        return collection_type;
    }

    public void setCollection_type(String collection_type) {
        this.collection_type = collection_type;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }
}
