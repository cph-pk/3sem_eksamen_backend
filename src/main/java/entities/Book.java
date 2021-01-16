
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Per
 */
@Entity
@Table(name = "book")
@NamedQuery(name = "Book.deleteAllRows", query = "DELETE from Book")

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "isbn")
    private String isbn;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "authors")
    private String authors;
    
    @Column(name = "publisher")
    private String publisher;
    
    @Column(name = "publisherYear")
    private String publishYear;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "book")
    private List<Loan> loanList = new ArrayList<>();
    
    @JoinColumn(name = "library_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Library library;
    
    public Book() {
    }

    public Book(String isbn, String title, String authors, String publisher, String publishYear) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public void addLoan(Loan loan) {
        if (loan != null) {
            loanList.add(loan);
            loan.setBook(this);
        }
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
    
    
}
