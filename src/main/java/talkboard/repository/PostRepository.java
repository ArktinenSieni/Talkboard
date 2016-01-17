package talkboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkboard.domain.Post;

/**
 * Created by mcsieni on 15.1.2016.
 */
public interface PostRepository extends JpaRepository<Post, Long>{
}
