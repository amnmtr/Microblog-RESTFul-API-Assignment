package microblog.repositories;

import java.util.ArrayList;
import java.util.List;

import microblog.models.Post;

public class MicroblogMockedData {
	   //list of microblog posts
    private List<Post> posts;

    private static MicroblogMockedData instance = null;
    
    public static MicroblogMockedData getInstance(){
         if(instance == null){
             instance = new MicroblogMockedData();
         }
         return instance;
    }


    public MicroblogMockedData(){
    	posts = new ArrayList<Post>();
    	posts.add(new Post(1, "Go up, up and away with your Google Assistant",
                "With holiday travel coming up, and 2018 just around the corner, " +
                        "you may be already thinking about getaways for next year. Consider " +
                        "the Google Assistant your new travel buddy, ready at the drop of a hat—or a passport"));
    	posts.add(new Post(2, "Get local help with your Google Assistant",
                "No matter what questions you’re asking—whether about local traffic or " +
                        "a local business—your Google Assistant should be able to help. And starting " +
                        "today, it’s getting better at helping you, if you’re looking for nearby services " +
                        "like an electrician, plumber, house cleaner and more"));
    	posts.add(new Post(3, "The new maker toolkit: IoT, AI and Google Cloud Platform",
                "Voice interaction is everywhere these days—via phones, TVs, laptops and smart home devices " +
                        "that use technology like the Google Assistant. And with the availability of maker-friendly " +
                        "offerings like Google AIY’s Voice Kit, the maker community has been getting in on the action " +
                        "and adding voice to their Internet of Things (IoT) projects."));
    	posts.add(new Post(4, "Learn more about the world around you with Google Lens and the Assistant",
                "Looking at a landmark and not sure what it is? Interested in learning more about a movie as " +
                        "you stroll by the poster? With Google Lens and your Google Assistant, you now have a helpful " +
                        "sidekick to tell you more about what’s around you, right on your Pixel."));
    	posts.add(new Post(5, "7 ways the Assistant can help you get ready for Turkey Day",
                "Thanksgiving is just a few days away and, as always, your Google Assistant is ready to help. " +
                        "So while the turkey cooks and the family gathers, here are some questions to ask your Assistant."));
    }

    // return all posts
    public List<Post> fetchPosts() {
        return posts;
    }

    // return post by id
    public Post getPostById(int id) {
        for(Post b: posts) {
            if(b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    // search post by text
    public List<Post> searchPosts(String searchTerm) {
        List<Post> searchedPosts = new ArrayList<Post>();
        for(Post b: posts) {
            if(b.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
               b.getContent().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchedPosts.add(b);
            }
        }

        return searchedPosts;
    }

    // create Post
    public Post createPost(int id, String title, String content) {
    	Post newPost = new Post(id, title, content);
        posts.add(newPost);
        return newPost;
    }

    // update Post
    public Post updatePost(int id, String title, String content) {
        for(Post b: posts) {
            if(b.getId() == id) {
                int blogIndex = posts.indexOf(b);
                b.setTitle(title);
                b.setContent(content);
                posts.set(blogIndex, b);
                return b;
            }

        }

        return null;
    }

    // delete Post by id
    public boolean delete(int id){
        int postIndex = -1;
        for(Post b: posts) {
            if(b.getId() == id) {
            	postIndex = posts.indexOf(b);
                continue;
            }
        }
        if(postIndex > -1){
        	posts.remove(postIndex);
        }
        return true;
    }



}
