package server;

import course.Bidding;
import course.Course;

import java.util.Comparator;
import java.util.HashMap;

public class Comparators {
    public static Comparator<Course> idComparator = new Comparator<Course>() {
        @Override
        public int compare(Course o1, Course o2) {
            return o1.courseId < o2.courseId ? -1 : 1;
        }
    };

    public static Comparator<Course> nameComparator = new Comparator<Course>() {
        @Override
        public int compare(Course o1, Course o2) {
            if (o1.courseName.equals(o2.courseName)) {
                return idComparator.compare(o1, o2);
            }
            return o1.courseName.compareTo(o2.courseName);
        }
    };

    public static Comparator<Course> ayComparator = new Comparator<Course>() {
        @Override
        public int compare(Course o1, Course o2) {
            if (o1.academicYear == o2.academicYear) {
                return  idComparator.compare(o1, o2);
            }
            return o1.academicYear < o2.academicYear ? 1 : -1 ;
        }
    };

    public static Comparator<Course> degreeComparator = new Comparator<Course>() {
        @Override
        public int compare(Course o1, Course o2) {
            HashMap<String, Integer> degrees = new HashMap();
            degrees.put("Bachelor", 1);
            degrees.put("Master", 2 );
            degrees.put("Doctorate", 3);
            int degree1 = degrees.get(o1.academicDegree);
            int degree2 = degrees.get(o2.academicDegree);
            if (degree1 == degree2) {
                return idComparator.compare(o1, o2);
            } else {
                return degree1 < degree2 ? -1 : 1;
            }
        }
    };

    public static Comparator<Bidding> bidComparator = new Comparator<Bidding>() {
        @Override
        public int compare(Bidding o1, Bidding o2) {
            return o1.compareTo(o2);
        }
    };

    public static Comparator<Bidder> bidderComparator = new Comparator<Bidder>() {
        @Override
        public int compare(Bidder o1, Bidder o2) {
            if (o1.failedToEnroll && !o2.failedToEnroll) {
                return 1;
            } else if (!o1.failedToEnroll && o2.failedToEnroll) {
                return -1;
            } else if (o1.user.major.equals(o1.course.department) && !o2.user.major.equals(o1.course.department )) {
                return 1;
            } else if (!o1.user.major.equals(o1.course.department) && o2.user.major.equals(o1.course.department )) {
                return -1;
            } else if (o1.bidRatio > o2.bidRatio) {
                return 1;
            } else if (o1.bidRatio < o2.bidRatio) {
                return -1;
            } else {
                return o1.user.id.compareTo(o2.user.id);
            }

        }
    };
}
