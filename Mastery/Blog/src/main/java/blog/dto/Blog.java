package blog.dto;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
/**
 *
 * @author mohammedchowdhury
 */
public class Blog {
    


    private int blogID;
   
    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Name must be fewer than 100 characters and greater then 1 character")
    private String title; 
    private String blogText;
    private LocalDate expirationDate;
    private LocalDate dateOfShow;
    private boolean varified; 
    private boolean  staticPage; 
    private int userID; 
    private List<Tag> listOfTags; 
    

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlogText() {
        return blogText;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getDateOfShow() {
        return dateOfShow;
    }

    public void setDateOfShow(LocalDate dateOfShow) {
        this.dateOfShow = dateOfShow;
    }

    public boolean isVarified() {
        return varified;
    }

    public void setVarified(boolean varified) {
        this.varified = varified;
    }

    public boolean isStaticPage() {
        return staticPage;
    }

    public void setStaticPage(boolean staticPage) {
        this.staticPage = staticPage;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Tag> getListOfTags() {
        return listOfTags;
    }

    public void setListOfTags(List<Tag> listOfTags) {
        this.listOfTags = listOfTags;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.blogID;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.blogText);
        hash = 97 * hash + Objects.hashCode(this.expirationDate);
        hash = 97 * hash + Objects.hashCode(this.dateOfShow);
        hash = 97 * hash + (this.varified ? 1 : 0);
        hash = 97 * hash + (this.staticPage ? 1 : 0);
        hash = 97 * hash + this.userID;
        hash = 97 * hash + Objects.hashCode(this.listOfTags);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Blog other = (Blog) obj;
        if (this.blogID != other.blogID) {
            return false;
        }
        if (this.varified != other.varified) {
            return false;
        }
        if (this.staticPage != other.staticPage) {
            return false;
        }
        if (this.userID != other.userID) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.blogText, other.blogText)) {
            return false;
        }
        if (!Objects.equals(this.expirationDate, other.expirationDate)) {
            return false;
        }
        if (!Objects.equals(this.dateOfShow, other.dateOfShow)) {
            return false;
        }
        if (!Objects.equals(this.listOfTags, other.listOfTags)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Blog{" + "blogID=" + blogID + ", title=" + title + ", blogText=" + blogText + ", expirationDate=" + expirationDate + ", dateOfShow=" + dateOfShow + ", varified=" + varified + ", staticPage=" + staticPage + ", userID=" + userID + ", listOfTags=" + listOfTags + '}';
    }
}