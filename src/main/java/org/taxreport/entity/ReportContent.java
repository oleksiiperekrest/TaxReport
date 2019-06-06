package org.taxreport.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.taxreport.utils.JsonUtil;

import java.io.IOException;

public class ReportContent {
    private int earnedIncome;
    private int unearnedIncome;
    private int taxRate;
    private String comment;
    private int tax;

    public ReportContent(int earnedIncome, int unearnedIncome, int taxRate) {
        this.earnedIncome = earnedIncome;
        this.unearnedIncome = unearnedIncome;
        this.taxRate = taxRate;
        this.tax = calculateTax(earnedIncome, unearnedIncome, taxRate);
    }

    public ReportContent(String json){

        ReportContent old = null;
        try {
            old = JsonUtil.getInstance().getObjectMapper().readValue(json, ReportContent.class);

        this.earnedIncome = old.earnedIncome;
            this.unearnedIncome = old.unearnedIncome;
            this.taxRate = old.taxRate;
            this.comment = old.comment;
            this.tax = calculateTax(earnedIncome, unearnedIncome, taxRate);
        } catch (IOException e) {
        e.printStackTrace();
    }
    }

    /**
     * private default constructor for JSON
     */
    private ReportContent(){}

    private int calculateTax(int earnedIncome, int unearnedIncome, int taxRate) {
        return (earnedIncome + unearnedIncome) * taxRate/100;
    }

    public int getEarnedIncome() {
        return earnedIncome;
    }

    public int getUnearnedIncome() {
        return unearnedIncome;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public String getComment() {
        return comment;
    }

    public int getTax() {
        return tax;
    }

    public void setEarnedIncome(int earnedIncome) {
        this.earnedIncome = earnedIncome;
        this.tax = calculateTax(earnedIncome, unearnedIncome, taxRate);
    }

    public void setUnearnedIncome(int unearnedIncome) {
        this.unearnedIncome = unearnedIncome;
        this.tax = calculateTax(earnedIncome, unearnedIncome, taxRate);
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
        this.tax = calculateTax(earnedIncome, unearnedIncome, taxRate);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

//    public void setTax(int tax) {
//        this.tax = tax;
//    }

    public String toJson() {
        try {
            return JsonUtil.getInstance().getObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
