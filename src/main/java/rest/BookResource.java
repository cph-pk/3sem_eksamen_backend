
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookDTO;
import dto.BooksDTO;
import dto.HobbiesDTO;
import dto.HobbyDTO;
import entities.Book;
import entities.Hobby;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.BookFacade;
import facades.HobbyFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Per
 */
@Path("book")
public class BookResource {
     
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    private static final BookFacade FACADE =  BookFacade.getBookFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookForAll() {
        return "{\"msg\":\"Hello from Book\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    public String countBooks() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery ("SELECT b from Book b", Book.class);
            List<Book> book = query.getResultList();
            return "[" + book.size() + "]";
        } finally {
            em.close();
        }
    }

    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public String getAllBooks() throws NotFoundException {
        BooksDTO booksDTO = FACADE.getAllBooks();
        return GSON.toJson(booksDTO);
    }
    
    
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getBook(@PathParam("id") long id)  throws NotFoundException {
        return GSON.toJson(FACADE.getBookById(id));
    }
    
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addBook(String book) throws MissingInputException {
        BookDTO b = GSON.fromJson(book, BookDTO.class);
        BookDTO bookDTO = FACADE.addBook(b.getIsbn(), b.getTitle(), b.getAuthors(), b.getPublisher(), b.getPublishYear());
        return GSON.toJson(bookDTO);
    }
    
    
    @PUT
    @Path("update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateBook(@PathParam("id") long id, String book) throws  MissingInputException, NotFoundException {
        BookDTO bookDTO = GSON.fromJson(book, BookDTO.class);
        bookDTO.setId(id);
        BookDTO bookNew = FACADE.editBook(bookDTO);
        return GSON.toJson(bookNew);
    }
   
    @DELETE
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteBook(@PathParam("id") long id) throws NotFoundException {
        BookDTO bookDelete = FACADE.deleteBook(id);
        return GSON.toJson(bookDelete);
    }
}
