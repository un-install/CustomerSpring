package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SALESREPS", schema = "MA_STUDENT")
public class Salesrep implements Serializable, ModelInterface {
    private BigDecimal emplNum;
    private String name;
    private BigDecimal age;
    private Office repOffice;
    private String title;
    private Date hireDate;
    private Salesrep manager;
    private BigDecimal quota;
    private BigDecimal sales;
    private Set<Customer> customers = new HashSet<>();
    private Set<Order> orders = new HashSet<>();
    private Set<Office> managedOffices = new HashSet<>();
    private Set<Salesrep> managedEmployes = new HashSet<>();

    public Salesrep() {
    }

    public Salesrep(BigDecimal emplNum, String name, BigDecimal age, Office repOffice, String title, Date hireDate, Salesrep manager, BigDecimal quota, BigDecimal sales, Set<Customer> customers, Set<Order> orders) {
        this.emplNum = emplNum;
        this.name = name;
        this.age = age;
        this.repOffice = repOffice;
        this.title = title;
        this.hireDate = hireDate;
        this.manager = manager;
        this.quota = quota;
        this.sales = sales;
        this.customers = customers;
        this.orders = orders;
    }

    @Id
    @Column(name = "empl_num")
    public BigDecimal getEmplNum() {
        return emplNum;
    }

    public void setEmplNum(BigDecimal emplNum) {
        this.emplNum = emplNum;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age")
    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rep_office")
    public Office getRepOffice() {
        return repOffice;
    }

    public void setRepOffice(Office repOffice) {
        this.repOffice = repOffice;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "hire_date")
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager")
    public Salesrep getManager() {
        return manager;
    }

    public void setManager(Salesrep manager) {
        this.manager = manager;
    }

    @Column(name = "quota")
    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    @Column(name = "sales")
    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "custRep")
    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mgr")
    public Set<Office> getManagedOffices() {
        return managedOffices;
    }

    public void setManagedOffices(Set<Office> managedOffices) {
        this.managedOffices = managedOffices;
    }

    @OneToMany(mappedBy = "rep")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    public Set<Salesrep> getManagedEmployes() {
        return managedEmployes;
    }

    public void setManagedEmployes(Set<Salesrep> managedEmployes) {
        this.managedEmployes = managedEmployes;
    }

    @Override
    public void setAll(ModelInterface mi) {
        Salesrep sr = (Salesrep) mi;
        this.emplNum = sr.emplNum;
        this.name = sr.name;
        this.age = sr.age;
        this.repOffice = sr.repOffice;
        this.title = sr.title;
        this.hireDate = sr.hireDate;
        this.manager = sr.manager;
        this.quota = sr.quota;
        this.sales = sr.sales;
        this.customers = sr.customers;
        this.orders = sr.orders;
    }

    @Override
    public String toString() {
        return "\nSalesrep{" +
                " emplNum=" + emplNum +
                " name='" + name + '\'' +
                " age=" + age +
                " repOffice=" + repOffice.getOffice() +
                " title='" + title + '\'' +
                " hireDate=" + hireDate +
                " manager=" + manager.getEmplNum() +
                " quota=" + quota +
                " sales=" + sales +
                " customers=" + customers +
                " orders=" + orders +
                "}";
    }
}
