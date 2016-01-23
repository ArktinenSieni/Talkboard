package talkboard.library;

/**
 * Created by mcsieni on 17.1.2016.
 * Do not forget to change the links at the annotations of controllers!
 */
public enum Link {

    POSTS ("/posts"),
    REDIRECT_POSTS ("redirect:" + Link.POSTS),
    REDIRECT_POSTFORM ("redirect:/thread/{threadId}/createPost"),
    REDIRECT_POSTEDIT ("redirect:" + Link.POSTS + "/{id}"),
    REDIRECT_THREADS ("redirect:/thread"),
    REDIRECT_NEWTHREAD ("redirect:/thread/new"),
    REDIRECT_EDITTHREAD ("redirect:/thread/{id}/edit"),
    REDIRECT_THREAD ("redirect:/thread/{threadId}");

    private final String link;

    Link(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return link;
    }
}
