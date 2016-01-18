package talkboard.library;

/**
 * Created by mcsieni on 17.1.2016.
 */
public enum FileLocation {

    POSTFORM ("post/postForm"),
    POSTEDITFORM ("post/postEditForm"),
    POSTS ("post/posts");


    private final String location;

    FileLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location;
    }
}
