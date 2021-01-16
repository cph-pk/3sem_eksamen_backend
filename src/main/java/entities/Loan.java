package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Per
 */
@Entity
@Table(name = "loan")
@NamedQuery(name = "Loan.deleteAllRows", query = "DELETE from Loan")
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "checkoutdate")
    private String checkoutDate;
    
    @Column(name = "duedate")
    private String dueDate;
    
    @Column(name = "returneddate")
    private String returnedDate;

    @JoinColumn(name = "person_email")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Person person;
    
    @JoinColumn(name = "book_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Book book;
    
    public Loan() {
    }

    public Loan(String checkoutDate, String dueDate, String returnedDate) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    

    
    
}
