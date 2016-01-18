package talkboard.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by mcsieni on 15.1.2016.
 */
@Entity
public class Post extends AbstractPersistable<Long> {

    @NotNull
    @Length(min= 5, max = 30)
    private String name;

    @NotNull
    @NotEmpty
    @Length(min = 5)
    @Lob
    private String text;

    private Date created;

    private Date modified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated() {
        this.created = new Date();
    }

    public Date getModified() {
        return modified;
    }

    public void setModified() {
        this.modified = new Date();
    }
}
