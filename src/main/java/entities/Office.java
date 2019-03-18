package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OFFICES", schema = "MA_STUDENT")
public class Office implements Serializable, ModelInterface {
    private BigDecimal office;
    private String city;
    private String region;
    private Salesrep mgr;
    private BigDecimal target;
    private BigDecimal sales;
    private Set<Salesrep> employees = new HashSet<>();

    public Office() {
    }

    public Office(BigDecimal office) {
        this.office = office;
    }

    public Office(BigDecimal office, String city, String region, Salesrep mgr, BigDecimal target, BigDecimal sales, Set<Salesrep> employees) {
        this.office = office;
        this.city = city;
        this.region = region;
        this.mgr = mgr;
        this.target = target;
        this.sales = sales;
        this.employees = employees;
    }

    @Id
    @Column(name = "OFFICE")
    public BigDecimal getOffice() {
        return office;
    }

    public void setOffice(BigDecimal office) {
        this.office = office;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "REGION")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mgr")
    public Salesrep getMgr() {
        return mgr;
    }

    public void setMgr(Salesrep mgr) {
        this.mgr = mgr;
    }

    @Column(name = "TARGET")
    public BigDecimal getTarget() {
        return target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    @Column(name = "SALES")
    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    @OneToMany(mappedBy = "repOffice")
    public Set<Salesrep> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Salesrep> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "\nOffice{" +
                "office=" + office +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", mgr=" + mgr.getEmplNum() +
                ", target=" + target +
                ", sales=" + sales +
                ", employees=" + employees +
                '}';
    }

    @Override
    public void setAll(ModelInterface mi) {
        Office of = (Office) mi;
        this.office = of.office;
        this.city = of.city;
        this.region = of.region;
        this.mgr = of.mgr;
        this.target = of.target;
        this.sales = of.sales;
    }
}