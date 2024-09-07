package com.example.wheycenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

@Entity
@Table(name = "`order`")
public class Order {
    
    @Id
    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;
    
    @Column(name = "customer_first_name", nullable = false, length = 20)
    private String customerFirstName;
    
    @Column(name = "customer_last_name", nullable = false, length = 30)
    private String customerLastName;
    
    @Column(name = "company_name", length = 100)
    private String companyName;
    
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    
    @Column(name = "address2", length = 255)
    private String address2;
    
    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;
    
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "email", nullable = false, length = 256)
    private String email;
    
    @Column(name = "order_note", length = 1024)
    private String orderNote;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 50)
    private PaymentMethod paymentMethod;
    
    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;
    
    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;
    
    @Column(name = "paid_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidAt;
    
    @Column(name = "is_delivered", nullable = false)
    private Boolean isDelivered;
    
    @Column(name = "delivered_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveredAt;
    
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
        if (Boolean.TRUE.equals(isPaid)) {
            this.paidAt = new Date();
        } else {
            this.paidAt = null;
        }

        if (Boolean.TRUE.equals(isDelivered)) {
            this.deliveredAt = new Date();
        } else {
            this.deliveredAt = null;
        }
    }

    public static String generateOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
