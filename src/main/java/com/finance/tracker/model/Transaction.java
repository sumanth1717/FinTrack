package com.finance.tracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private int id;
    private String title;
    private BigDecimal amount;
    private String type; // INCOME or EXPENSE
    private Integer categoryId;
    private String categoryName;
    private LocalDate transactionDate;
    private String note;
    private Integer userId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}