package server;

import course.*;
import utils.Config;
import utils.ErrorCode;
import utils.Pair;

import java.awt.image.DataBufferDouble;
import java.util.*;
import java.util.Scanner;
import java.io.*;


public class Server {
    public static LinkedList<Course> getAllCourses() {
        // Return all the courses in a directory
        LinkedList<Course> allCourses = new LinkedList<>();

        File folder = new File(Config.DATA_DIR);
        File[] subjects = folder.listFiles();

        for (File subject: subjects) {
            String collegeName = subject.getName();
            if (subject.isDirectory()) {
                File[] courses = subject.listFiles();
                for (File courseFile: courses) {
                    int courseId = Integer.parseInt(courseFile.getName().replace(".txt", ""));
                    Course course = Unwrappers.unwrap(courseFile, courseId, collegeName);
                    allCourses.add(course);
                }
            }
        }
        return allCourses;
    }

    private boolean checkCondition(Course course, Map<String, Object> searchConditions) {
        boolean pass = true;

        if (!(searchConditions == null || searchConditions.isEmpty())) {
            for (String key: searchConditions.keySet()) {
                if (key.equals("ay")) {
                    int year = (int) searchConditions.get(key);
                    if (course.academicYear > year) {
                        pass = false;
                    }
                } else if (key.equals("degree")) {
                    String degree = (String) searchConditions.get(key);
                    if (!course.academicDegree.equals(degree)) {
                        pass = false;
                    }
                } else if(key.equals("name")) {
                    String bigString = (String) searchConditions.get(key);
                    if (bigString.equals("")) {
                        pass = false;
                    } else {
                        String[] keywords = bigString.split("\\s+");

                        for (String word: keywords) {
                            if (!course.courseName.toLowerCase().contains(word.toLowerCase())) {
                                pass = false;
                            }
                        }
                    }
                }
            }
        }

        return pass;
    }


    private ArrayList<Course> sortByCriteria(ArrayList<Course> course, String sortCriteria) {
        if (!(sortCriteria == null) && !sortCriteria.equals("")) {
            if (sortCriteria.equals("id")) {
                Collections.sort(course, Comparators.idComparator);
            } else if (sortCriteria.equals("name")) {
                Collections.sort(course, Comparators.nameComparator);
            } else if (sortCriteria.equals("ay")) {
                Collections.sort(course, Comparators.ayComparator);
            } else if (sortCriteria.equals("degree")) {
                Collections.sort(course, Comparators.degreeComparator);
            }
        } else {
            Collections.sort(course, Comparators.idComparator);
        }
        return course;
    }

    public List<Course> search(Map<String,Object> searchConditions, String sortCriteria){
        // TODO Problem 2-1
        LinkedList<Course> allCourses = getAllCourses();
        ArrayList<Course> searchedCourses = new ArrayList<>();

        for (Course course: allCourses) {
            if (checkCondition(course, searchConditions)) {
                searchedCourses.add(course);
            }
        }
        ArrayList<Course> sortedList = sortByCriteria(searchedCourses, sortCriteria);

        return sortedList;
    }


    public void writeBid(String userId, List<Bidding> biddings) throws Exception{
        String path = Config.DATA_DIR_USERS + userId + "/bid.txt";
        BufferedWriter bufferedFileWriter = null;
        try {
            File file = new File(path);

            FileWriter fileWriter = new FileWriter(file);
            bufferedFileWriter = new BufferedWriter(fileWriter);
            for (Bidding bid: biddings) {
                String line = Integer.toString(bid.courseId) + "|" + Integer.toString(bid.mileage) + "\n";
                bufferedFileWriter.write(line);
            }
            bufferedFileWriter.close();

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (bufferedFileWriter != null) {
                    bufferedFileWriter.close();
                }
            } catch(Exception ex) {
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
    }

    public Course getCourseById(int id) {
        List<Course> courses = getAllCourses();
        for (Course course: courses) {
            if (course.courseId == id) {
                return course;
            }
        }
        return null;
    }

    public int bid(int courseId, int mileage, String userId){
        // TODO Problem 2-2
        if (mileage == 0) { return ErrorCode.SUCCESS;
        } else if (!Check.checkCourseId(courseId)) { return ErrorCode.NO_COURSE_ID;
        } else if (!Check.checkUserExistence(userId)) { return ErrorCode.USERID_NOT_FOUND;
        } else if (!Check.checkBidFile(userId)) { return ErrorCode.NO_BID_FILE; }

        Pair<Integer, List<Bidding>> pair = retrieveBids(userId);
        List<Bidding> biddings = pair.value;

        Course course = getCourseById(courseId);

        int sumOfBids = getMileage(biddings);

        boolean isChanged = false;

        int index = 0;
        if (pair.key == 0) {
            for(Bidding bid: biddings) {
                if (bid.courseId == courseId){
                    if (bid.mileage + mileage > course.maxMileage) {
                        return ErrorCode.OVER_MAX_COURSE_MILEAGE;
                    } else if (bid.mileage + mileage> 0){
                        bid.mileage += mileage;
                        biddings.set(index, bid);
                        isChanged = true;
                    }
                }
                index++;
            }
        }

        if (!isChanged && biddings.size() < Config.MAX_COURSE_NUMBER) {
            if (mileage < 0) {
                return ErrorCode.NEGATIVE_MILEAGE;
            } else if (mileage > course.maxMileage) {
                return ErrorCode.OVER_MAX_COURSE_MILEAGE;
            } else {
                Bidding bid = new Bidding(courseId, mileage);
                biddings.add(bid);
            }
        }

        if (sumOfBids + mileage > Config.MAX_MILEAGE ) {
            return ErrorCode.OVER_MAX_MILEAGE;
        }

        Collections.sort(biddings, Comparators.bidComparator);

        try {
            writeBid(userId, biddings);
        } catch (Exception e) {
            return ErrorCode.IO_ERROR;
        }
        return ErrorCode.SUCCESS;
    }

    public int getMileage(List<Bidding> biddings) {
        int mileage = 0;
        for (Bidding bid: biddings) {
            mileage += bid.mileage;
        }
        return mileage;
    }

    public Pair<Integer,List<Bidding>> retrieveBids(String userId){
        // TODO Problem 2-2
        if (!Check.checkUserExistence(userId)) {
            return new Pair<>(ErrorCode.USERID_NOT_FOUND, new ArrayList<>());
        }
        String bidPath = Config.DATA_DIR_USERS + userId + "/bid.txt";
        try {
            File bidFile = new File(bidPath);
            ArrayList<Bidding> biddings = Unwrappers.unwrapBids(bidFile);

            return new Pair<>(ErrorCode.SUCCESS, biddings);
        } catch (Exception e) {
            return new Pair<>(ErrorCode.IO_ERROR, new ArrayList<>());
        }
    }

    public List<User> getAllUser() {
        File folder = new File( Config.DATA_DIR_USERS);
        File[] userList = folder.listFiles();
        LinkedList<User> allUser = new LinkedList<>();

        for (File userDir: userList) {
            String path = userDir.getPath() + "/userinfo.txt";
            File file = new File(path);
            User user = Unwrappers.unwrapUser(file);
            allUser.add(user);
        }
        return allUser;

    }

    public List<Pair<User, List<Bidding>>> getUserBid(List<User> users) {
        LinkedList<Pair<User, List<Bidding>>> storage = new LinkedList<>();

        for (User user: users) {
            List<Bidding> biddings = retrieveBids(user.id).value;
            Pair<User, List<Bidding>> pair = new Pair<User, List<Bidding>>(user, biddings);
            storage.add(pair);
        }
        return storage;
    }

    public List<Integer> getFailedEnrollement(User user){
        List<Integer> ids = new LinkedList<>();
        String[] coursesId = user.failedEnrollement.split("|");
        for (String id: coursesId){
            ids.add(Integer.parseInt(id));
        }
        return ids;
    }

    public LinkedList<Bidder> getCourseBid(Course course, List<Pair<User, List<Bidding>>> userBiddings) {
        LinkedList<Bidder> bidders = new LinkedList<>();

        for (Pair<User, List<Bidding>> pair: userBiddings) {
            User user = pair.key;
            for (Bidding bid: pair.value ) {
                if (bid.courseId == course.courseId) {
                    float bidRatio = bid.mileage / user.mileage;
                    Bidder bidder = new Bidder(user, bidRatio, course);
                    bidders.add(bidder);
                }
            }
        }
        return bidders;
    }

    public LinkedList<Pair<Integer, String>> failedToEnroll () throws Exception{
        String path = Config.DATA_DIR + "failedToEnroll.txt";
        try {
            File file = new File(path);
            Scanner fileReader = new Scanner(file);
            LinkedList<Pair<Integer, String>> unhappyBunch = new LinkedList<>();

            while (fileReader.hasNext()) {
                String rawData = fileReader.nextLine();
                String[] word = rawData.split("|");
                int courseId = Integer.parseInt(word[0]);
                String userId = word[1];
                unhappyBunch.add(new Pair(courseId, userId));
            }
            return unhappyBunch;

        } catch(Exception e) {
            throw e;
        }
    }



    public void deleteAllBids() {
        String path = Config.DATA_DIR_USERS;
        File file = new File(path);
        File[] users = file.listFiles();
        for(File user: users) {
            try {
                File bid = new File(user.getPath() + "/bid.txt");
                bid.delete();
            } catch (Exception e) {
                System.out.println("One file escaped deletion");
            }
        }
    }

    public List<Bidder> addEnrollement(List<Bidder> incompleteBidders, List<String> failedToEnroll) {
        List<Bidder> bidders = new LinkedList<>();

        for (Bidder bidder: incompleteBidders) {
            for (String userId: failedToEnroll) {
                if (bidder.user.id.equals(userId)) {
                    bidder.setEnrollement(true);
                }
            }
            bidders.add(bidder);
        }
        return bidders;
    }

    public List<String> failedCourseEnrollement(List<Pair<Integer, String>> failedToEnroll, Course course) {
        List<String> users = new LinkedList<>();

        for(Pair failure: failedToEnroll) {
            if ((int) failure.key == course.courseId) {
                users.add((String) failure.value);
            }
        }
        return users;
    }


    public void saveRegistration(List<Bidder> bidders) {
        for (Bidder bidder: bidders) {

        }
    }

    public boolean confirmBids(){
        // TODO Problem 2-3
        try{
            List<Pair<User, List<Bidding>>> userBids = getUserBid(getAllUser());
            List<Course> courses = getAllCourses();
            List<Pair<Integer, String>> failedToEnroll = failedToEnroll();

            for (Course course: courses) {
                LinkedList<Bidder> partialCourseBidders = getCourseBid(course, userBids);
                List<String> failedCourseEnrollement = failedCourseEnrollement(failedToEnroll, course);
                List<Bidder> courseBidders = addEnrollement(partialCourseBidders, failedCourseEnrollement);

                Collections.sort(courseBidders, Comparators.bidderComparator);

                List<Bidder> registeredBidders;
                if (course.quota > courseBidders.size()) {
                    registeredBidders = courseBidders;
                } else {
                    registeredBidders = courseBidders.subList(0, course.quota);
                }
                saveRegistration(registeredBidders);
            }


        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Pair<Integer,List<Course>> retrieveRegisteredCourse(String userId){
        // TODO Problem 2-3
        return new Pair<>(ErrorCode.USERID_NOT_FOUND, new ArrayList<>());
    }
}