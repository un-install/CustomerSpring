package springdata.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CustomerDetails {
    @NotEmpty(message = "8")
    @Pattern(regexp = "[a-zA-z]{5}", message = "9")
    private String company;

    public CustomerDetails() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerDetails{");
        sb.append("company='").append(company).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
