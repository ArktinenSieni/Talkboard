package talkboard.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by mcsieni on 19.1.2016.
 */
@Entity
public class PostThread extends AbstractPersistable<Long> {

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 50)
    private String name;

    @OneToMany
    private List<Post> posts;

    private Date modified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified() {
        this.modified = new Date();
    }
}
