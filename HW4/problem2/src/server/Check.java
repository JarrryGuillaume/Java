package server;

import server.Server.*;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;

import course.*;
import utils.Config;

public class Check {
    public static boolean checkUserExistence(String userId) {
        File folder = new File(Config.DATA_DIR_USERS);
        File[] userDir = folder.listFiles();
        HashSet<String> names = new HashSet<>();
        for (File user: userDir) {
            String name = user.getName().replace(Config.DATA_DIR_USERS, "");
            names.add(name);
        }
        return names.contains(userId);
    }

    public static boolean checkCourseId(int id) {
        LinkedList<Course> allCourse = Server.getAllCourses();
        for (Course course: allCourse) {
            if (course.courseId == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkBidFile(String userId) {
        if (checkUserExistence(userId)) {
            String path = Config.DATA_DIR_USERS + userId + "/bid.txt";
            File file = new File(path);
            if (file.exists()) {
                return true;
            } else {
                try {
                    file.createNewFile();
                    System.out.print("activated");
                } catch (Exception e) {
                    System.out.print("Problem in creating the file");
                    e.printStackTrace();
                    return false;
                }
                return false;
            }
        }
        return false;

    }
}
