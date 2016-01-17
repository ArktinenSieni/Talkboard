package talkboard.library;

/**
 * Created by mcsieni on 17.1.2016.
 * Do not forget to change the links at the beginning of controllers!
 */
public enum Link {

    POSTS ("/posts"),
    REDIRECT_POSTS ("redirect:" + Link.POSTS);

    private final String link;

    Link(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return link;
    }
}
