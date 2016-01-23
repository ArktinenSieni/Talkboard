package talkboard.library;

/**
 * Created by mcsieni on 17.1.2016.
 */
public enum FileLocation {

    POSTFORM ("post/postForm"),
    POSTEDITFORM ("post/postEditForm"),
    POSTS ("post/posts"),
    THREADS ("thread/threads"),
    THREAD ("thread/thread"),
    THREAD_FORM ("thread/newThreadForm"),
    THREAD_EDIT ("thread/editThreadForm");


    private final String location;

    FileLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location;
    }
}
