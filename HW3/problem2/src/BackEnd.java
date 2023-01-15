import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BackEnd extends ServerResourceAccessible {
    public BackEnd() { super(); }
    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    // Use getServerStorageDir() as a default directory
    // TODO sub-program 1 ~ 4 :
    // Create helper functions to support FrontEnd class
    public LinkedList<String> readLines(File file) throws WrongFileError {
        try {
            Scanner fileReader = new Scanner(file);
            LinkedList<String> data = new LinkedList<>();

            while (fileReader.hasNext()) {
                data.add(fileReader.nextLine());
            }
            return data;

        } catch (Exception e) {
            throw new WrongFileError();
        }
    }

    public LinkedList<String> readLines(String filePath) throws WrongFileError {
        File file = new File(filePath);
        return readLines(file);
    }

    public LinkedList<Post> delete(LinkedList<Post> list, LinkedList<Post> filter) {
        LinkedList<Post> result = new LinkedList<>();
        for (Post post1: list) {
            boolean duplicate = false;
            for (Post post2: filter) {
                if (post1.getId() == post2.getId()) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                result.add(post1);
            }
        }
        return result;
    }


    public LinkedList<Post> getUserLike(String filePath, LinkedList<String> keywords, int threshold) throws WrongFileError{
        LinkedList<Post> userLikedPost = new LinkedList<>();
        LinkedList<String> lines = readLines(filePath);
        for (String line: lines) {
            LinkedList<String> ids = new LinkedList(Arrays.asList(line.split("\\s+")));
            String user = ids.get(0);
            ids.remove(0);
            for (String id: ids) {
                File file = new File(getServerStorageDir() + user + "/post/" + id + ".txt");
                Post post = unwrap(file);
                boolean added = false;
                for (String keyword: keywords) {
                    int frequency = post.checkFrequency(keyword);
                    if (frequency >= threshold) {
                        added = true;
                    }
                }
                if (added) {
                    userLikedPost.add(post);
                }
            }
        }
        return userLikedPost;
    }


    public LinkedList<Post> filterPostByKeywords(LinkedList<String> keywords, int threshold) {
        FilenameFilter postDirFilter = new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return name.equals("post");
            }
        };
        LinkedList<Post> filteredPost = new LinkedList<>();
        File storageDir = new File(getServerStorageDir());
        File[] users = storageDir.listFiles();
        for (File user: users) {
            File folder = new File(user.getPath() + "/post/");
            File[] userPosts = folder.listFiles();
            for (File userPost: userPosts) {
                Post post = unwrap(userPost);
                boolean added = false;
                for (String keyword : keywords) {
                    int frequency = post.checkFrequency(keyword);
                    if (frequency >= threshold) {
                        added = true;
                    }
                }
                if (added) {
                    filteredPost.add(post);
                }
            }
        }
        return filteredPost;
    }


    public LinkedList<Post> findFriendPosts(LinkedList<String> friends) {
        LinkedList<Post> postList = new LinkedList<Post>();

        for (String friend: friends) {
            String friendDirPath = getServerStorageDir() + friend + "/post";
            File folder = new File(friendDirPath);
            File[] posts = folder.listFiles();
            for (File postFile: posts) {
                Post post = unwrap(postFile);
                postList.add(post);
            }
        }
        return postList;
    }

    public Pair<LinkedList<Post>, LinkedList<Post>> sortByAdverstising(LinkedList<Post> postList) {
        LinkedList<Post> advertisedPosts = new LinkedList<>();
        LinkedList<Post> nonAdvertisedPosts = new LinkedList<>();

        for (Post post: postList) {
            if (post.getAdvertising().equals("yes")) {
                advertisedPosts.add(post);
            } else if (post.getAdvertising().equals("no")) {
                nonAdvertisedPosts.add(post);
            }
        }

        Pair<LinkedList<Post>, LinkedList<Post>> pair = new Pair(advertisedPosts, nonAdvertisedPosts);
        return pair;
    }

    public String getContent(List<String> contentList) {
        String result = new String("");
        for(String string: contentList) {
            result += string + "\n";
        }
        return result;
    }

    public Post unwrap(File postFile) {
        try {
            LinkedList<String> attributes = readLines(postFile);
            int id = Integer.parseInt(postFile.getName().replace(".txt", ""));
            LocalDateTime dateTime = LocalDateTime.parse(attributes.get(0), formatter);
            String title = attributes.get(1);
            String advertising = attributes.get(2);
            String likeLine = attributes.get(3);
            int likeNum;
            String content = getContent(attributes.subList(4, attributes.size()));
            Post post;
            try {
                likeNum = Integer.parseInt(likeLine.substring(12, likeLine.length()));
                post = new Post(id, dateTime, advertising, likeNum, title, content);
            } catch (NumberFormatException e) {
                post = new Post(id, dateTime, advertising, title, content);
            }
            return post;
        } catch (Exception e) {
            System.out.println("File Error");
            e.printStackTrace();
            return null;
        }

    }

    public LinkedList<File> getAllPost() {
        LinkedList<File> allPost = new LinkedList<>();
        File mainDataDir = new File(getServerStorageDir());
        File[] users = mainDataDir.listFiles();

        for (File user: users) {
            String path = user.getPath() + "/post";
            File[] userPosts = new File(path).listFiles();
            for (File post: userPosts) {
                allPost.add(post);
            }
        }
        return allPost;
    }

    public String getPostID(String postDirPath) {
        LinkedList<File> posts = getAllPost();
        LinkedList<Integer> idList = new LinkedList<>();

        for (File post: posts) {
            String postName = post.getName().replace(".txt", "");
            int ID = Integer.parseInt(postName);
            idList.add(ID);
        }

        int maxID = Collections.max(idList);
        String newID = String.valueOf(maxID + 1);

        return newID;
    }

    public boolean createFile(String filePath) {
        try {
            File file = new File(filePath);
            boolean isCreated = file.createNewFile();
            return isCreated;

        } catch (IOException e) {
            System.out.println("The file could not be created.");
            e.printStackTrace();
            return false;
        }
    }

    public void writeLines(String filePath, List<String> content) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(filePath);

            for (String lines : content.subList(0, content.size()-1)) {
                fileWriter.write(lines + "\n");
            }
            fileWriter.write(content.get(content.size()-1));
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
