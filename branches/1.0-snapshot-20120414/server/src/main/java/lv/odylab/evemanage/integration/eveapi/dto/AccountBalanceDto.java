package lv.odylab.evemanage.integration.eveapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountBalanceDto implements Serializable {
    private Long accountID;
    private Long accountKey;
    private BigDecimal balance;

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public Long getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(Long accountKey) {
        this.accountKey = accountKey;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
