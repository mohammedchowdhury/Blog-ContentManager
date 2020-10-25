package blog.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 *
 * @author mohammedchowdhury
 */
public class Tag {

    private int tagID;
    
    @NotBlank(message = "Tag must not be blank")
    @Size(max = 30, message = "Tag must be fewer than 30 characters and greater then 1 character")
    private String hashTag;

    public Tag() {
    }

    public Tag(int tagID, String hashTag) {
        this.tagID = tagID;
        this.hashTag = hashTag;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.tagID;
        hash = 47 * hash + Objects.hashCode(this.hashTag);
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
        final Tag other = (Tag) obj;
        if (this.tagID != other.tagID) {
            return false;
        }
        if (!Objects.equals(this.hashTag, other.hashTag)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tag{" + "tagID=" + tagID + ", hashTag=" + hashTag + '}';
    }
}
