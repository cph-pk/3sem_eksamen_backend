
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Per
 */
@Entity
@Table(name = "library")
@NamedQuery(name = "Library.deleteAllRows", query = "DELETE from Library")
public class Library implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Library() {
    }

    public Library(String name) {
        this.name = name;
    }
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "library")
    private List<Book> bookList;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        if (book != null) {
            bookList.add(book);
            book.setLibrary(this);
        }
    }
    
}
