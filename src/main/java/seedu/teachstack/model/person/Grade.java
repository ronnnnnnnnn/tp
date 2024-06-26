package seedu.teachstack.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.teachstack.commons.util.AppUtil.checkArgument;

import seedu.teachstack.storage.JsonSerializableUserData;

/**
 * Represents a Student's grade in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */

public class Grade implements Comparable<Grade> {

    private static Grade thresholdGrade = new Grade("C+");
    private static final String VALID_GRADES = "[A+, A, A-, B+, B, B-, C+, C, D+, D, F]";
    private static final String VALIDATION_REGEX = "A[+-]?|B[+-]?|C[+]?|D[+]?|F";

    //some regex here
    public static final String MESSAGE_CONSTRAINTS = "Grades should adhere to the following constraints:\n"
            + "1. The grade should be one of the valid grades:\n"
            + VALID_GRADES;

    public final String value;

    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        //TODO: write some tests for isValidGrade
        checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);
        value = grade;
    }

    /**
     * Returns if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * @return the int value corresponding to string grade
     */
    public int gradeToInt() {
        switch (value) {
        case ("A+"):
            return 11;
        case ("A"):
            return 10;
        case ("A-"):
            return 9;
        case ("B+"):
            return 8;
        case ("B"):
            return 7;
        case ("B-"):
            return 6;
        case ("C+"):
            return 5;
        case ("C"):
            return 4;
        case ("D+"):
            return 3;
        case ("D"):
            return 2;
        case ("F"):
            return 1;
        default:
            return -1;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        return value.equals(otherGrade.value);
    }

    @Override
    public int compareTo(Grade o) {
        return this.gradeToInt() - o.gradeToInt();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * @param value
     * @return the string value corresponding to int grade
     */
    public static String intToGrade(int value) {
        switch (value) {
        case 11:
            return "A+";
        case 10:
            return "A";
        case 9:
            return "A-";
        case 8:
            return "B+";
        case 7:
            return "B";
        case 6:
            return "B-";
        case 5:
            return "C+";
        case 4:
            return "C";
        case 3:
            return "D+";
        case 2:
            return "D";
        case 1:
            return "F";
        default:
            return "";
        }
    }

    /**
     * Returns whether grade constitutes weak or not
     */
    public boolean isWeak() {
        if (this.compareTo(thresholdGrade) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Modifies the grade threshold to the given grade.
     *
     * @param g The grade to be modified to.
     */
    public static void modifyThreshold(Grade g) {
        JsonSerializableUserData.setGradeThreshold(g);
        thresholdGrade = g;
    }

    public static Grade retrieveThreshold() {
        return thresholdGrade;
    }

}
