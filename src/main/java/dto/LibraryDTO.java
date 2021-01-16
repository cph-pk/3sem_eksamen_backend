
package dto;

import entities.Library;

/**
 *
 * @author Per
 */
public class LibraryDTO {
    private Long id;
    private String name;

    public LibraryDTO(Library library) {
        this.id = library.getId();
        this.name = library.getName();
    }

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

    
    
    
    
}
