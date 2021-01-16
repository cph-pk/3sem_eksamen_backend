
package dto;

import entities.Book;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Per
 */
public class BooksDTO {
    List<BookDTO> all = new ArrayList();
    
    public BooksDTO(List<Book> bookEntities) {
        bookEntities.forEach((b) -> {
            all.add(new BookDTO(b));
        });
    }

   
}
