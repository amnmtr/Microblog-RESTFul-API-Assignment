package microblog.services;

import java.util.List;

import microblog.models.Post;

public interface PostService {
	
    public Post getPost(int id);
	
    public List<Post> getTopPostsList();

    public void vote(int postId, boolean like) throws AlreadyVotedException;
    
}