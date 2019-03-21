package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ORDERS", schema = "MA_STUDENT")
public class Order implements Serializable, ModelInterface {
    private BigDecimal orderNum;
    private Date date;
    private Customer cust;
    private BigDecimal proxyCust;
    private Salesrep rep;
    private BigDecimal proxyRep;
    private String mfr;
    private Products product;
    private String proxyProduct;
    private BigDecimal qty;
    private BigDecimal amount;


    public Order() {
    }


    public Order(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }

    public Order(BigDecimal orderNum, Date date, Customer cust, Salesrep rep, String mfr, BigDecimal qty, BigDecimal amount, Products product) {
        this.orderNum = orderNum;
        this.date = date;
        this.cust = cust;
        this.rep = rep;
        this.mfr = mfr;
        this.product = product;
        this.qty = qty;
        this.amount = amount;
    }

    @Id
    @Column(name = "ORDER_NUM")
    public BigDecimal getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cust")
    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "rep")
    public Salesrep getRep() {
        return rep;
    }

    public void setRep(Salesrep rep) {
        this.rep = rep;
    }

    @Column(name = "MFR")
    public String getMfr() {
        return mfr;
    }

    public void setMfr(String mfr) {
        this.mfr = mfr;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product")
    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    @Column(name = "QTY", precision = 22)
    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    @Column(name = "AMOUNT")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "cust", insertable = false, updatable = false)
    public BigDecimal getProxyCust() {
        return proxyCust;
    }

    public void setProxyCust(BigDecimal proxyCust) {
        this.proxyCust = proxyCust;
    }

    @Column(name = "rep", insertable = false, updatable = false)
    public BigDecimal getProxyRep() {
        return proxyRep;
    }

    public void setProxyRep(BigDecimal proxyRep) {
        this.proxyRep = proxyRep;
    }

    @Column(name = "product", insertable = false, updatable = false)
    public String getProxyProduct() {
        return proxyProduct;
    }

    public void setProxyProduct(String proxyProduct) {
        this.proxyProduct = proxyProduct;
    }

    @Override
    public void setAll(ModelInterface mi) {
        Order o = (Order) mi;
        this.orderNum = o.orderNum;
        this.date = o.date;
        this.cust = o.cust;
        this.rep = o.rep;
        this.mfr = o.mfr;
        this.product = o.product;
        this.qty = o.qty;
        this.amount = o.amount;
        this.proxyCust = o.proxyCust;
        this.proxyRep = o.proxyRep;
        this.proxyProduct = o.proxyProduct;
    }

    @Override
    public String toString() {
        return "\nOrder{" +
                " orderNum=" + orderNum +
                " date=" + date +
                " cust=" + proxyCust +
                " rep=" + proxyRep +
                " mfr='" + mfr + '\'' +
                " product=" + proxyProduct +
                " qty=" + qty +
                " amount=" + amount +
                "}";
    }
}
