package microblog.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import microblog.models.Post;

import java.util.List;

@Repository
public interface MicroblogRespository extends JpaRepository<Post, Integer> {

    // custom query to search to microblog post by title or content
    List<Post> findByTitleContainingOrContentContaining(String text, String textAgain);
    
    @Query("SELECT p FROM Post p JOIN p.postRatings r GROUP BY p ORDER BY SUM(r.value) DESC")
    List<Post> findTopPosts(Pageable pageable);
    
}
