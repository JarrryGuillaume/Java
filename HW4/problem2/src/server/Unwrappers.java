package server;

import course.Bidding;
import course.Course;
import utils.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Unwrappers {
    public static Course unwrap(File courseFile, int courseId, String collegeName) {
        try {
            Scanner fileReader = new Scanner(courseFile);
            String content = fileReader.nextLine();

            String[] info = content.split("\\|");

            String departement = info[0];
            String academicDegree = info[1];
            int academicYear = Integer.parseInt(info[2]);
            String courseName = info[3];
            int credit = Integer.parseInt(info[4]);
            String location = info[5];
            String instructor = info[6];
            int quota = Integer.parseInt(info[7]);
            int maxMileage;
            if (info.length > 8) {
                maxMileage = Integer.parseInt(info[8]);
            } else {
                maxMileage = Config.MAX_MILEAGE_PER_COURSE;
            }

            Course course = new Course(courseId, collegeName, departement, academicDegree, academicYear, courseName, credit, location, instructor, quota, maxMileage);
            return course;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User unwrapUser(String userId){
        String userInfoPath = Config.DATA_DIR_USERS + userId + "/userinfo.txt";
        File file = new File(userInfoPath);
        return unwrapUser(file);
    }

    public static User unwrapUser(File file) {
        try {
            Scanner fileReader = new Scanner(file);
            String content = fileReader.nextLine();
            String[] info = content.split("\\|");

            String id = file.getName().replace("userinfo.txt", "").replace("Users/", "");

            String major = info[0];
            String degree = info[1];
            int year = Integer.parseInt(info[2]);

            User user = new User(id, major, degree, year);
            return user;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Bidding> unwrapBids(File file) throws Exception{
        try {
            Scanner fileReader = new Scanner(file);
            ArrayList<Bidding> biddings = new ArrayList<>();

            while (fileReader.hasNext()) {
                String rawData = fileReader.nextLine();
                String[] data = rawData.split("\\|");
                int courseId = Integer.parseInt(data[0]);
                int mileage = Integer.parseInt(data[1]);
                biddings.add(new Bidding(courseId, mileage));
            }
            return biddings;

        } catch(Exception e) {
            throw e;
        }
    }
}
