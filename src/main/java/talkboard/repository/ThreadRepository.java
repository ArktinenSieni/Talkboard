package talkboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkboard.domain.Thread;

/**
 * Created by mcsieni on 19.1.2016.
 */
public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
