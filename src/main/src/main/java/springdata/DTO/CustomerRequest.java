package springdata.DTO;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CustomerRequest {
    @NotNull(message = "1")
    @Min(value = 1, message = "2")
    private BigDecimal custNum;
    @NotNull(message = "3")
    @Min(value = 100, message = "4")
    private BigDecimal custRep;
    @Min(value = 100, message = "5")
    @Max(value = 999999, message = "6")
    private BigDecimal creditLimit;
    @Valid
    @NotNull(message = "7")
    private CustomerDetails customerDetails;

    public CustomerRequest() {
    }

    public BigDecimal getCustNum() {
        return custNum;
    }

    public void setCustNum(BigDecimal custNum) {
        this.custNum = custNum;
    }

    public BigDecimal getCustRep() {
        return custRep;
    }

    public void setCustRep(BigDecimal custRep) {
        this.custRep = custRep;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerRequest{");
        sb.append("custNum=").append(custNum);
        sb.append(", custRep=").append(custRep);
        sb.append(", creditLimit=").append(creditLimit);
        sb.append(", customerDetails=").append(customerDetails);
        sb.append('}');
        return sb.toString();
    }
}
