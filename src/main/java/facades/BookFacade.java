
package facades;

import dto.BookDTO;
import dto.BooksDTO;
import entities.Book;
import entities.Library;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Per
 */
public class BookFacade {
    
    private static EntityManagerFactory emf;
    private static BookFacade instance;

    public BookFacade() {
    }
    
    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }
    
    public long getBookCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long bookCount = (long) em.createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
            return bookCount;
        } finally {
            em.close();
        }
    }
    
    public BookDTO getBookById(long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        Book book = em.find(Book.class, id);
       
        if (book == null) {
            throw new NotFoundException("No book found with the given ID");
        } else {
            try {
                return new BookDTO(book);
            } finally {
                em.close();
            }
        }

    }
    
    public BooksDTO getAllBooks() throws NotFoundException {

        EntityManager em = emf.createEntityManager();
        BooksDTO booksDTO;
        try {
            booksDTO = new BooksDTO(em.createQuery("SELECT b FROM Book b").getResultList());
        } catch (Exception e) {
            throw new NotFoundException("No connection to the database");
        } finally {
            em.close();
        }
        return booksDTO;
    }
 
    public BookDTO addBook(String isbn, String title, String authors, String publisher, String publishYear) throws MissingInputException {
        if (isNameInValid(isbn, title, authors, publisher, publishYear)) {
            throw new MissingInputException("One or more fields are missing");
        }
        EntityManager em = emf.createEntityManager();
        Book book = new Book(isbn, title, authors, publisher, publishYear);
        
        Long id = 1L; // Not the correct way, but there is only one Library in DB with ID 1
        Library library = em.find(Library.class, id);
        library.addBook(book);
        
        System.out.println("LLLLL " + library.getId());
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.merge(library);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BookDTO(book);
    }
    
    public BookDTO editBook(BookDTO b) throws  MissingInputException, NotFoundException {
        if (isNameInValid(b.getIsbn(), b.getTitle(), b.getAuthors(), b.getPublisher(), b.getPublishYear())) {
            throw new MissingInputException("One or more fields are missing");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Book book = em.find(Book.class, b.getId());
            if (book == null) {
                throw new NotFoundException("No book with provided id found");
            } else {
                book.setIsbn(b.getIsbn());
                book.setTitle(b.getTitle());
                book.setAuthors(b.getAuthors());
                book.setPublisher(b.getPublisher());
                book.setPublishYear(b.getPublishYear());
            }
            em.getTransaction().commit();
            return new BookDTO(book);
        } finally {
            em.close();
        }
    }
    
    public BookDTO deleteBook(long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Book book = em.find(Book.class, id);
        if (book == null) {
            throw new NotFoundException("Could not delete, provided id does not exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(book);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new BookDTO(book);
        }
    }

    private static boolean isNameInValid(String isbn, String title, String authors, String publisher, String publishYear) {
        return (isbn.length() == 0) || (title.length() == 0) || (authors.length() == 0) || (publisher.length() == 0) || (publishYear.length() == 0);
    }
}
