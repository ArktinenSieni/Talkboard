package talkboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkboard.domain.PostThread;

import java.util.List;

/**
 * Created by mcsieni on 19.1.2016.
 */
public interface ThreadRepository extends JpaRepository<PostThread, Long> {
    public List<PostThread> findAllByOrderByModifiedDesc();
}
