import java.io.File;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.HashSet;

public class FrontEnd {
    private UserInterface ui;
    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private BackEnd backend;
    private User user;

    private String DATA_DIRECTORY;

    public FrontEnd(UserInterface ui, BackEnd backend) {
        this.ui = ui;
        this.backend = backend;
        this.DATA_DIRECTORY = backend.getServerStorageDir();;
    }
    
    public boolean auth(String authInfo){
        // TODO sub-problem 1
        try {
            String[] idPassword = authInfo.split("\n");

            this.user = new User(idPassword[0], idPassword[1]);
            String passwordPath = DATA_DIRECTORY + user.id + "/password.txt";
            List<String> password = backend.readLines(passwordPath);
            String check = idPassword[1];
            if (check.equals(password.get(0))) {
                return true;
            }
        } catch (Exception WrongFileError) {
            return false;
        }
        return false;
    }

    public void post(List<String> titleContentList) {
        // TODO sub-problem 2
        String postPath = DATA_DIRECTORY + user.getID() + "/post/";

        String POST_ID = backend.getPostID(postPath);
        String postFilePath = DATA_DIRECTORY + user.getID() + "/post/" + POST_ID;

        LocalDateTime today = LocalDateTime.now();
        String dateOfPost = today.format(formatter);
        titleContentList.add(0, dateOfPost);

        String likes = "like-number 0\n";
        titleContentList.add(3, likes);

        String trimmedContent = titleContentList.get(titleContentList.size() - 1);

        titleContentList.set(titleContentList.size()-1, trimmedContent.trim());

        if (backend.createFile(postFilePath)) {
            backend.writeLines(postFilePath, titleContentList);
        } else {
            System.out.print("There is a problem with the post ID");
        }
    }
    
    public void recommend(int N) {
        // TODO sub-problem 3
        try {
            String friendPath = DATA_DIRECTORY + user.getID() + "/friend.txt";
            LinkedList<String> friends = backend.readLines(friendPath);

            LinkedList<Post> friendPostList = backend.findFriendPosts(friends);
            Pair<LinkedList<Post>, LinkedList<Post>> pair = backend.sortByAdverstising(friendPostList);

            LinkedList<Post> advertisedPosts = pair.key;
            LinkedList<Post> nonAdvertisedPosts = pair.value;

            Comparator<Post> dateComparator = new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return o1.getLocalDateTime().isBefore(o2.getLocalDateTime()) ? 1 : -1;
                }
            };
            Collections.sort(advertisedPosts, dateComparator);
            Collections.sort(nonAdvertisedPosts, dateComparator);

            writeRecommendedPosts(advertisedPosts, nonAdvertisedPosts, N);

        } catch (Exception WrongFileError) {
            System.out.println("WrongFileError");
        }
    }

    public void writeRecommendedPosts(LinkedList<Post> advertisedPosts, LinkedList<Post> nonAdvertisedPosts, int N) {
        LinkedList<Post> recommendedPosts = new LinkedList<>();

        if (advertisedPosts.size() <= N) {
            advertisedPosts.addAll(nonAdvertisedPosts.subList(0, N-advertisedPosts.size()));
            recommendedPosts.addAll(advertisedPosts);
        } else if (advertisedPosts.size() >= N) {
            recommendedPosts.addAll(advertisedPosts.subList(0, N));
        }
        for (Post post: recommendedPosts) {
            ui.println(post.toString());
        }
    }

    public LinkedList<String> extractKeywords(String command) {
        String StringOfKeywords = command.replace("search ", "").replace("\n", "");
        String[] keywordsWithDuplicates = StringOfKeywords.split("\\s+");
        LinkedHashSet keywordSet = new LinkedHashSet();
        for (String keyword: keywordsWithDuplicates) {
            keywordSet.add(keyword);
        }
        LinkedList<String> keywords = new LinkedList(keywordSet);
        return keywords;
    }

    public void search(String command) {
        // TODO sub-problem 4
        try {
            LinkedList<String> keywords = extractKeywords(command);
            int threshold = Integer.parseInt(keywords.get(keywords.size() - 1));
            keywords.removeLast();
            String dirPath = backend.getServerStorageDir() + user.getID() + "/likedposts.txt";
            LinkedList<Post> filteredPosts = backend.filterPostByKeywords(keywords, threshold);

            LinkedList<Post> userLikedPost = backend.getUserLike(dirPath, keywords, threshold);
            LinkedList<Post> remainingPosts = backend.delete(filteredPosts, userLikedPost);

            Comparator<Post> likeComparator = new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.getLikeNum() > o2.getLikeNum()) {
                        return -1;
                    } else if (o2.getLikeNum() > o1.getLikeNum()) {
                        return 1;
                    } else {
                        return o1.getLocalDateTime().isBefore(o2.getLocalDateTime()) ? 1 : -1;
                    }
                }
            };
            Collections.sort(userLikedPost, likeComparator);
            Collections.sort(remainingPosts, likeComparator);

            if (userLikedPost.size() < 10) {
                for (Post post: userLikedPost) {
                    ui.println(post.getSummary());
                }
                if (remainingPosts.size() > 10 - userLikedPost.size()) {
                    for (Post post: remainingPosts.subList(0, 10 - userLikedPost.size())) {
                        ui.println(post.getSummary());
                    }
                } else {
                    for (Post post: remainingPosts) {
                        ui.println(post.getSummary());
                    }
                }

            } else {
                List<Post> electedTen = userLikedPost.subList(0, 10);
                for (Post post: electedTen) {
                    ui.println(post.getSummary());
                }
            }


        } catch (Exception e) {
            System.out.println("Wrong command");
        }




    }
    
    User getUser(){
        return user;
    }
}
