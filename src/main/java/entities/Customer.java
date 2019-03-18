package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMERS", schema = "MA_STUDENT")
public class Customer implements Serializable, ModelInterface {
    private BigDecimal custNum;
    private String company;
    private Salesrep custRep;
    private BigDecimal creditLimit;
    private Set<Order> orders = new HashSet<>();

    public Customer() {
    }

    public Customer(BigDecimal custNum, String company, Salesrep custRep, BigDecimal creditLimit, Set<Order> orders) {
        this.custNum = custNum;
        this.company = company;
        this.custRep = custRep;
        this.creditLimit = creditLimit;
        this.orders = orders;
    }

    @Id
    @Column(name = "cust_num")
    public BigDecimal getCustNum() {
        return custNum;
    }

    public void setCustNum(BigDecimal custNum) {
        this.custNum = custNum;
    }

    @OneToMany(mappedBy = "cust", fetch = FetchType.EAGER)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cust_rep")
    public Salesrep getCustRep() {
        return custRep;
    }

    public void setCustRep(Salesrep custRep) {
        this.custRep = custRep;
    }

    @Column(name = "credit_limit")
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }



    @Override
    public void setAll(ModelInterface mi) {
        Customer c = (Customer) mi;
        this.custNum = c.custNum;
        this.company = c.company;
        this.custRep = c.custRep;
        this.creditLimit = c.creditLimit;
        this.orders = c.orders;
    }

    @Override
    public String toString() {
        return "\nCustomer{" +
                " custNum=" + custNum +
                " company='" + company + '\'' +
                " custRep=" + custRep.getEmplNum() +
                " creditLimit=" + creditLimit +
                " orders=" + orders +
                "}";
    }
}
