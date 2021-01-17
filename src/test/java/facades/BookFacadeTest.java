package facades;

import dto.BookDTO;
import dto.BooksDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.Book;
import entities.Hobby;
import entities.Library;
import entities.Loan;
import entities.Person;
import entities.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author Per
 */
//@Disabled
public class BookFacadeTest {

    private static EntityManagerFactory emf;
    //private static FacadeExample facade;
    private static BookFacade facade;
    private Book b1, b2, b3;
   

    public BookFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BookFacade.getBookFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
//Delete existing users and roles to get a "fresh" database
            em.getTransaction().begin();
            
            em.createQuery("delete from Book").executeUpdate();
            

            b1 = new Book("978-93-5019-561-1", "Junior Level Books Introduction to Computer", "Amit Garg", "Reader's Zone", "2011");
            b2 = new Book("978-93-8067-432-2", "Client Server Computing", "Lalit Kumar", "Sun India Publications, New Delh", "2012");
            b3 = new Book("978-93-5163-389-1", "Data Structure Using C", "Sharad Kumar Verma", "Thakur Publications Lucknow", "2015");

           
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
           
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    
    /**
     * Test of getBookCount method, of class BookFacade.
     */
    @Test
    public void testGetBookCount() {
        assertEquals(3, facade.getBookCount(), "Expects three rows in the database");
    }

    /**
     * Test of getBookById method, of class BookFacade.
     */
    @Test
    public void testGetBookById() throws Exception {
       
        BookDTO bookDTO = facade.getBookById(b1.getId());

        assertEquals(b1.getId(), bookDTO.getId());
    }

    /**
     * Test of getAllBooks method, of class BookFacade.
     */
    @Test
    public void testGetAllBooks() throws Exception {
        BooksDTO booksDTO = facade.getAllBooks();
        List<BookDTO> list = booksDTO.getAll();
        System.out.println("Liste af books: " + list);
        assertThat(list, everyItem(Matchers.hasProperty("title")));
        assertThat(list, Matchers.hasItems(Matchers.<BookDTO>hasProperty("authors", is("Amit Garg")),
                Matchers.<BookDTO>hasProperty("publisher", is("Sun India Publications, New Delh"))
        ));

    }

    /**
     * Test of addBook method, of class BookFacade.
     */
    @Disabled
    @Test
    public void testAddBook() throws Exception {
        EntityManager em = emf.createEntityManager();;
        String isbn = "1234";
        String title = "test";
        String authors = "test";
        String publisher = "test";
        String publishYear = "1234";
        
        Book book = new Book(isbn, title, authors, publisher, publishYear);
       
        BookDTO bookDTO = new BookDTO(book);
        
        //facade.addBook(bookDTO);
        try {
            em.getTransaction().begin();

            em.find(Book.class, title);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Test of editBook method, of class BookFacade.
     */
    @Test
    public void testEditBook() throws Exception {
        EntityManager em = emf.createEntityManager();

        String title = "test1";
        String authors = "test2";
        String publisher = "test3";
        
        b1.setTitle(title);
        b1.setAuthors(authors);
        b1.setPublisher(publisher);
        
        BookDTO bookDTO = new BookDTO(b1);
        
        facade.editBook(bookDTO);
        
        try {
            em.getTransaction().begin();
            em.merge(b1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        assertEquals(b1.getTitle(), "test1");
    }

    /**
     * Test of deleteBook method, of class BookFacade.
     */
    @Test
    public void testDeleteBook() throws Exception {
       
        BookDTO bookDTO = facade.deleteBook(b1.getId());
        //assertThat(p1.getEmail(), is(not(personDTO.getEmail())));
        assertEquals(2, facade.getBookCount());
    }

}
