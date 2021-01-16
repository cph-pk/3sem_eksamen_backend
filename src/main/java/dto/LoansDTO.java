
package dto;

import entities.Loan;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Per
 */
public class LoansDTO {
    
    List<LoanDTO> all = new ArrayList();
    
    public LoansDTO(List<Loan> loanEntities) {
        loanEntities.forEach((l) -> {
            all.add(new LoanDTO(l));
        });
    }

    public List<LoanDTO> getAll() {
        return all;
    }

    public void setAll(List<LoanDTO> all) {
        this.all = all;
    }
}
