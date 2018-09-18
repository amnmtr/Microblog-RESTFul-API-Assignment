package microblog.controllers;

import org.springframework.web.bind.annotation.RestController;

import microblog.models.Post;
import microblog.repositories.MicroblogRespository;
import microblog.services.AlreadyVotedException;
import microblog.services.PostService;
import microblog.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MicroblogController {
	
	@Autowired
	MicroblogRespository microblogRespository;
	
    @Autowired
    private PostService postService;

    @GetMapping("/post")
    public List<Post> index(){
        return microblogRespository.findAll();
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post/create")
    public Post create(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        return microblogRespository.save(new Post(id, title, content));
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/post/{id}/update")
    public Post update(@PathVariable String id, @RequestBody Map<String, String> body){
        int postId = Integer.parseInt(id);
        // getting post
        Post post = microblogRespository.findOne(postId);
        post.setTitle(body.get("title"));
        post.setContent(body.get("content"));
        return microblogRespository.save(post);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("post/{id}/delete")
    public boolean delete(@PathVariable String id){
        int postId = Integer.parseInt(id);
        microblogRespository.delete(postId);
        return true;
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/post/{id}/like")
    public @ResponseBody String like(@PathVariable int id) {
        try {
            postService.vote(id, true);
        } catch (AlreadyVotedException e) {
            return "already_voted";
        }

        return "ok";
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/post/{id}/dislike")
    public @ResponseBody String dislike(@PathVariable int id) {
        try {
            postService.vote(id, false);
        } catch (AlreadyVotedException e) {
            return "already_voted";
        }

        return "ok";
    }
    
    
    @GetMapping(value = {"/posts/top"}, headers="Accept=application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody String getTopPostsList() {
        List<Post> posts = postService.getTopPostsList();

        return "[" + posts.stream().map(this::toJsonLink).collect(Collectors.joining(", \n")) + "]";
    }
    
    private String toJsonLink(Post post) {
        return "{" + JsonUtils.toJsonField("id", String.valueOf(post.getId())) + ", " + JsonUtils.toJsonField("title", post.getTitle()) + "}";
    }


}
