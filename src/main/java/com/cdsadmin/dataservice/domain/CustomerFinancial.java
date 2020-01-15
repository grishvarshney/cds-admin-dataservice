package com.cdsadmin.dataservice.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CustomerFinancial.
 */
@Entity
@Table(name = "customer_financial")
public class CustomerFinancial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "toatl_asset")
    private Double toatlAsset;

    @Column(name = "cmp_flag")
    private String cmpFlag;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "mtg_14_family_amt")
    private Double mtg14FamilyAmt;

    @Column(name = "total_rfha")
    private Double totalRFHA;

    @Column(name = "mva_percentage")
    private Double mvaPercentage;

    @Column(name = "capital_compliant_level")
    private String capitalCompliantLevel;

    @Column(name = "watch_status")
    private String watchStatus;

    @Column(name = "credit_score")
    private Double creditScore;

    @Column(name = "watch_status_effective_date")
    private LocalDate watchStatusEffectiveDate;

    @Column(name = "total_eligibile_collateral_amount")
    private Double totalEligibileCollateralAmount;

    @Column(name = "security_eligibile_collateral_amount")
    private Double securityEligibileCollateralAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public CustomerFinancial customerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Double getToatlAsset() {
        return toatlAsset;
    }

    public CustomerFinancial toatlAsset(Double toatlAsset) {
        this.toatlAsset = toatlAsset;
        return this;
    }

    public void setToatlAsset(Double toatlAsset) {
        this.toatlAsset = toatlAsset;
    }

    public String getCmpFlag() {
        return cmpFlag;
    }

    public CustomerFinancial cmpFlag(String cmpFlag) {
        this.cmpFlag = cmpFlag;
        return this;
    }

    public void setCmpFlag(String cmpFlag) {
        this.cmpFlag = cmpFlag;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public CustomerFinancial updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Double getMtg14FamilyAmt() {
        return mtg14FamilyAmt;
    }

    public CustomerFinancial mtg14FamilyAmt(Double mtg14FamilyAmt) {
        this.mtg14FamilyAmt = mtg14FamilyAmt;
        return this;
    }

    public void setMtg14FamilyAmt(Double mtg14FamilyAmt) {
        this.mtg14FamilyAmt = mtg14FamilyAmt;
    }

    public Double getTotalRFHA() {
        return totalRFHA;
    }

    public CustomerFinancial totalRFHA(Double totalRFHA) {
        this.totalRFHA = totalRFHA;
        return this;
    }

    public void setTotalRFHA(Double totalRFHA) {
        this.totalRFHA = totalRFHA;
    }

    public Double getMvaPercentage() {
        return mvaPercentage;
    }

    public CustomerFinancial mvaPercentage(Double mvaPercentage) {
        this.mvaPercentage = mvaPercentage;
        return this;
    }

    public void setMvaPercentage(Double mvaPercentage) {
        this.mvaPercentage = mvaPercentage;
    }

    public String getCapitalCompliantLevel() {
        return capitalCompliantLevel;
    }

    public CustomerFinancial capitalCompliantLevel(String capitalCompliantLevel) {
        this.capitalCompliantLevel = capitalCompliantLevel;
        return this;
    }

    public void setCapitalCompliantLevel(String capitalCompliantLevel) {
        this.capitalCompliantLevel = capitalCompliantLevel;
    }

    public String getWatchStatus() {
        return watchStatus;
    }

    public CustomerFinancial watchStatus(String watchStatus) {
        this.watchStatus = watchStatus;
        return this;
    }

    public void setWatchStatus(String watchStatus) {
        this.watchStatus = watchStatus;
    }

    public Double getCreditScore() {
        return creditScore;
    }

    public CustomerFinancial creditScore(Double creditScore) {
        this.creditScore = creditScore;
        return this;
    }

    public void setCreditScore(Double creditScore) {
        this.creditScore = creditScore;
    }

    public LocalDate getWatchStatusEffectiveDate() {
        return watchStatusEffectiveDate;
    }

    public CustomerFinancial watchStatusEffectiveDate(LocalDate watchStatusEffectiveDate) {
        this.watchStatusEffectiveDate = watchStatusEffectiveDate;
        return this;
    }

    public void setWatchStatusEffectiveDate(LocalDate watchStatusEffectiveDate) {
        this.watchStatusEffectiveDate = watchStatusEffectiveDate;
    }

    public Double getTotalEligibileCollateralAmount() {
        return totalEligibileCollateralAmount;
    }

    public CustomerFinancial totalEligibileCollateralAmount(Double totalEligibileCollateralAmount) {
        this.totalEligibileCollateralAmount = totalEligibileCollateralAmount;
        return this;
    }

    public void setTotalEligibileCollateralAmount(Double totalEligibileCollateralAmount) {
        this.totalEligibileCollateralAmount = totalEligibileCollateralAmount;
    }

    public Double getSecurityEligibileCollateralAmount() {
        return securityEligibileCollateralAmount;
    }

    public CustomerFinancial securityEligibileCollateralAmount(Double securityEligibileCollateralAmount) {
        this.securityEligibileCollateralAmount = securityEligibileCollateralAmount;
        return this;
    }

    public void setSecurityEligibileCollateralAmount(Double securityEligibileCollateralAmount) {
        this.securityEligibileCollateralAmount = securityEligibileCollateralAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerFinancial)) {
            return false;
        }
        return id != null && id.equals(((CustomerFinancial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerFinancial{" +
            "id=" + getId() +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", toatlAsset=" + getToatlAsset() +
            ", cmpFlag='" + getCmpFlag() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", mtg14FamilyAmt=" + getMtg14FamilyAmt() +
            ", totalRFHA=" + getTotalRFHA() +
            ", mvaPercentage=" + getMvaPercentage() +
            ", capitalCompliantLevel='" + getCapitalCompliantLevel() + "'" +
            ", watchStatus='" + getWatchStatus() + "'" +
            ", creditScore=" + getCreditScore() +
            ", watchStatusEffectiveDate='" + getWatchStatusEffectiveDate() + "'" +
            ", totalEligibileCollateralAmount=" + getTotalEligibileCollateralAmount() +
            ", securityEligibileCollateralAmount=" + getSecurityEligibileCollateralAmount() +
            "}";
    }
}
