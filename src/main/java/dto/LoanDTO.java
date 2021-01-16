
package dto;

import entities.Loan;

/**
 *
 * @author Per
 */
public class LoanDTO {
    private String checkoutDate;
    private String dueDate;
    private String returnedDate;

    public LoanDTO(Loan loan) {
        this.checkoutDate = loan.getCheckoutDate();
        this.dueDate = loan.getDueDate();
        this.returnedDate = loan.getReturnedDate();
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }
    
    
}
