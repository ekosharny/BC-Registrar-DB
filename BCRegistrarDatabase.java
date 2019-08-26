import java.sql.DriverManager;
import java.sql.Statement;

public class BuildRegistrarDB {
   public static void main(String[] args) throws Exception {
       final String DB_URL = "jdbc:derby:RegistrarDB:create=true";

       try {
           Connection conn = DriverManager.getConnection(DB_URL);
           Statement stmt = conn.createStatement();

           System.out.println("Creating the Courses table...");
           stmt.execute("CREATE TABLE Courses ("
           + "CourseNumber INTEGER NOT NULL, "
           + "CourseName VARCHAR(40), "
           + "CourseTitle VARCHAR(20), "
           + "PRIMARY KEY (CourseNumber));");

           System.out.println("Creating the CourseRequesits table...");
           stmt.execute("CREATING TABLE CourseRequisits ("
           + "CourseRequisitNumber INTEGER NOT NULL, "
           + "CourseReqName VARCHAR(40), "
           + "CourseNumber INTEGER NOT NULL, "
           + "PRIMARY KEY (CourseRequisitNumber), "
           + "FOREIGN KEY (CourseNumber) REFERENCES Courses);");

           System.out.println("Creating the CourseOfferings table...");
           stmt.execute("CREATE TABLE CourseOfferings ("
           + "CourseOfferingNumber INTEGER NOT NULL, "
           + "OfferingYear INTEGER, "
           + "Semester VARCHAR(11), "
           + "SectionNumber VARCHAR(4), "
           + "Classroom VARCHAR (6), "
           + "PRIMARY KEY (CourseOfferingNumber);");
          
           System.out.println("Creating the CourseOfferingTiming table...");
           stmt.execute("CREATE TABLE CourseOfferingTiming ("
           + "CourseOfferingTimingNumber INTEGER NOT NULL, "
           + "CourseOfferingNumber INTEGER NOT NULL, "
           + "CourseTiming VARCHAR(25), "
           + "PRIMARY KEY (CourseOfferingTimingNumber), "
           + "FOREIGN KEY (CourseOfferingNumber) REFERENCES CourseOfferings);");

           System.out.println("Creating the Instructors table...");
           stmt.execute("CREATE TABLE Instructors ("
           + "InstructorNumber INTEGER NOT NULL, "
           + "CourseOfferingNumber INTEGER NOT NULL, "
           + "DepartmentName VARCHAR(20), "
           + "InstructorTitle VARCHAR(20), "
           + "InstructorName VARCHAR(30), "
           + "PRIMARY KEY (InstructorNumber), "
           + "FOREIGN KEY (CourseOfferingNumber) REFERENCES CourseOfferings);");

           System.out.println("Creating the Student table...");
           stmt.execute("CRATE TABLE Student ("
           + "StudentID INTEGER NOT NULL, "
           + "StudentName VARCHAR(30), "
           + "Program VARCHAR(20), "
           + "PRIMARY KEY (StudentID));");

           System.out.println("Creating the StudentGrade table...");
           stmt.execute("CREATE TABLE StudentGrade ("
           + "StudentGradeID INTEGER NOT NULL"
           + "CourseNumber INTEGER, "
           + "CourseGrade CHAR(2), "
           + "StudentID INTEGER NOT NULL, "
           + "PRIMARY KEY (StudentGradeID), "
           + "FOREIGN KEY (StudentID) REFERENCES Student);");

           System.out.println("Creating StudentEnrollmentCourses table...");
           stmt.execute("CREATE TABLE StudentEnrollmentCourses ("
           + "Enrolled VARCHAR(20) NOT NULL, "
           + "StudentID NOT NULL, "
           + "EnrolledCourses INTEGER, "
           + "PRIMARY KEY (Enrolled), "
           + "FOREIGN KEY (StudentID) REFERENCES Student;");

           stmt.close();
           conn.close();
           System.out.println("Done.");


       } catch (Exception e) {
           System.out.println("ERROR: " + e.getMessage());
       }
   }
}
	
































import java.sql.*;

public class RegistrarDBManager {
   public final String DB_URL = "jdbc:derby:RegistrarDB";
   private Connection conn;

   public RegistrarDBManager() throws SQLException {
       conn = DriverManager.getConnection(DB_URL);
   }

   public int getCourseNumber(String courseName){
       int courseNumber;
       Statement stmt = conn.createStatement();
       ResultSet resultSet = stmt.executeQuery("SELECT CourseNumber FROM Courses WHERE CourseName = " + courseName + "");

       if (resultSet.next()){
           courseNumber = Integer.parseInt(result.getString("CourseNumber"));
           conn.close();
           stmt.close();
       }

       return courseNumber;
   }

   public String getCourseName(int courseNumber){
       String courseName;
       Statement stmt = conn.createStatement();
       ResultSte resultSet = stmt.executeQuery("SELECT CourseName FROM Courses WHERE CourseNumber =" + courseNumber + "");

       if(resultSet.next()){
           courseName = result.getString("CourseName");
           conn.close();
           stmt.close();
       }

       return courseName;
   }

   public String getCourseTitle(String courseName){
       String courseTitle;
       Statement stmt = conn.createStatement();
       ResultSet resultSet = stmt.executeQuery("SELECT CourseTitle FROM Courses WHERE CourseName =" + courseName + "");
        if (resultSet.next()){
           courseTitle = result.getString("CourseTitle");
           conn.close();
           stmt.close();
        }

        return courseTitle;
   }

   public int getCourseCredits(String courseName){
       int courseCredits;
       Statement stmt = conn.createStatement();
       ResultSet resultSet = stmt.executeQuery("SELECT CourseCredits FROM Course WHERE Coursename =" + courseName + "");
       if (resultSet.next()){
           courseTitle = resultSet.getString("CourseCredits");
           conn.close();
           stmt.close();
       }

       return courseCredits;
   }

   public String[] getCourseNames() throws SQLException {
       Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

       ResultSet resultSet = stmt.executeQuery("SELECT CourseName FROM Courses");

       resultSet.last();
       int numRows = resultSet.getRow();
       resultSet.first();

       String[] listData = new String[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = resultSet.getString("CourseName");
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }

   public String[] getCourseRequisits(String courseName) throws SQLException {
       Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT CourseReqName FROM CourseRequisits WHERE CourseName = " + courseName + "");

       resultSet.last();
       int numRows = resultSet.getRow();
       resultSet.first();

       String[] listData = new String[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = resultSet.getString("CourseReqName");
           resultSet.next();
       }
       conn.close();
       stmt.close();
      
       return listData;
   }

   public int getSectionNumber(int CourseOfferingNumber){
       int sectionNumber;
       Statement stmt = conn.createStatement();
       ResultSet resultSet = stmt.executeQuery("SELECT SectionNumber FROM CourseOfferings WHERE CourseOfferingNumber =" + courseOfferingNumber + "");
       if (resultSet.next()){
           sectionNumber = Integer.parseInt(result.getString("SectionNumber"));
           conn.close();
           stmt.close();
       }

       return sectionNumber;
   }

   public int[] getCourseOfferingsByYear(int offeringYear){
       Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT CourseOfferingNumber FROM CourseOfferings WHERE OfferingYear =" + year + "");

       resultSet.last();
       int numRows = resultSet.getRow();
       resultSet.first();

       int[] listData = new int[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = Integer.parseInt(result.getString("CourseOfferingNumber"));
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }

   public int[] getCourseOfferingsBySemester(String semester){
       Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT CourseOfferingNumber FROM CourseOfferings WHERE Semester =" + semester + "");

       resultSet.last();
       int numRows = resultSet.getRow();
       resultSet.first();

       int[] listData = new int[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = Integer.parseint(result.getString("CourseOfferingNumber"));
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }

   public int[] getCourseOfferingsByInstructor(String instructor){
       Statement stmt = conn.createStatement(Result.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT CourseOfferings.CourseOfferingNumber FROM CourseOfferings, Instructors WHERE CourseOfferings.CourseOfferingNumber = Instructors.CourseOfferingNumber AND Instructor =" + instructor + "");

       resultSet.last();
       int numRows = result.getRow();
       resultSet.first();

       int[] listData = new int[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = Integer.parseint(result.getString("CourseOfferingNumber"));
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData; 
   }

   public String[] getInstructors(){
       Statement stmt = conn.createStatement(Result.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT InstructorName FROM Instructors");

       resultSet.last();
       int numRows = result.getRow();
       resultSet.first();

       String[] listData = new String[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = result.getString();
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }

   public String[] getInstructorsByDepartment(String department){
       Statement stmt = conn.createStatement(Result.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT InstructorName FROM Instructors WHERE DepartmentName =" + department + "");

       resultSet.last();
       int numRows = result.getRow();
       resultSet.first();

       String[] listData = new String[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = result.getString();
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }

   public String[] getCourseOfferingTimings(){
       Statement stmt = conn.createStatement(Result.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT CourseTiming FROM CourseOfferingTimings");

       resultSet.last();
       int numRows = result.getRow();
       resultSet.first();

       String[] listData = new String[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = result.getString();
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }

   public int getStudentID(String studentName){
       int studentID;
       Statement stmt = conn.createStatement();
       ResultSet resultSet = stmt.executeQuery("SELECT StudentID FROM Students WHERE StudentName =" + studentName + "");
       if (resultSet.next()){
           studentID = Integer.parseInt(result.getString("StudentID"));
           conn.close();
           stmt.close();
       }

       return studentID;
   }

   public String getStudentName(int studentID){
       String studentName;
       Statement stmt = conn.createStatement();
       ResultSet resultSet = stmt.executeQuery("SELECT StudentName FROM Students WHERE StudentID =" + studentID + "");
       if (resultSet.next()){
           studentID = result.getString("StudentName");
           conn.close();
           stmt.close();
       }

       return studentName;
   }

   public String[] getGrades(String studentName){
       Statement stmt = conn.createStatement(Result.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT StudentGrades.CourseGrade FROM Student, StudentGrade WHERE Student.StudentID = StudentGrade.StudentID AND Student.StudentName =" + studentName + "");

       resultSet.last();
       int numRows = result.getRow();
       resultSet.first();

       String[] listData = new String[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = result.getString();
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }

   public String[] getEnrolledCourses(String studentName){
       Statement stmt = conn.createStatement(Result.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       ResultSet resultSet = stmt.executeQuery("SELECT StudentEnrollmentCourses.EnrolledCourses FROM Student, StudentEnrolledCourses WHERE Student.StudentID = StudentEnrollmentCourses.StudentID AND Student.StudentName =" + studentName + "");

       resultSet.last();
       int numRows = result.getRow();
       resultSet.first();

       String[] listData = new String[numRows];

       for (int i = 0; i < numRows; i++){
           listData[i] = result.getString();
           resultSet.next();
       }
       conn.close();
       stmt.close();

       return listData;
   }
  

}















import java.sql.*;

public class RegistrarDBQuery {
   public final String DB_URL = "jdbc:derby:RegistrarDB";

   private Connection conn;
   private String[][] tableData;
   private String[] colNames;

   private void getDatabaseConnection(){
       try {
           conn = DriverManager.getConnection(DB_URL);
       } catch (Exception e){
           e.printStackTrace();
           System.exit(0);
       }
   }

   public RegistrarDBQuery(String query){
       getDatabaseConnection();
       try{
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           ResultSet resultSet = stmt.executeQuery(query);

           resultSet.last();
           int numRows = resultSet.getRow();
           resultSet.first();

           ResultSetMetaData meta = resultSet.getMetaData();

           String[] colNames = new String[meta.getColumnCount()];
           for (int i = 0; i < meta.getColumnCount(); i++){
               colNames[i] = meta.getColumnLabel(i + 1);
           }

           String[][] tableData = new String[numRows][meta.getColumnCount()];
           for (int row = 0; row < numRows; row++){
               for (int col = 0; col < meta.getColumnCount(); col++){
                   tableData[row][col] = resultSet.getString(col + 1);
               }
               resultSet.next();
           }

           stmt.close();
           conn.close();

       } catch (SQLException e){
           e.printTraceStack();
       }
   }

   public String[] getColumnNames(){
       return colNames;
   }

   public String[][] getTableData(){
       return tableData;
   }
}




















import java.sql.*;
import java.util.Scanner;

public class RegistrarInserter{
   public static main(String[] args){
       int courseNumber;
       String courseName;
       String courseTitle;
       int courseCredits;

       final String DB_URL = "jdbc:derby:RegistrarDB";

       Scanner keyboard = new Scanner(System.in);

       try {
           Connection conn = DriverManager.getConnection(DB_URL);

           System.out.print("Enter the course number: ");
           courseNumber = keyboard.nextInt();
           System.out.print("Enter the course name: ");
           courseName = keyboard.nextLine();
           System.out.print("Enter the course title: ");
           courseTitle = keyboard.nextLine();
           System.out.print("Enter the course credits: ");
           courseCredits = keyboard.nextInt();

           Statement stmt = conn.createStatement();

           String sqlStatement = "INSERT INTO Courses VALUES (" + courseNumber + ", " + courseName + ", " + courseTitle + ", " + courseCredits + "";

           int rows = stmt.executeUpdate(sqlStatement);

           System.out.println(rows + "row(s) added to the table.");

           conn.close();

       } catch (Exception e){
           System.out.println("ERROR: " + e.getMessage());
       }
   }
}







































import java.sql.*;
import java.util.Scanner;

public class RegistrarDeleter {
   public static void main(String[] args){
       String prodNum;
       String sure;

       final String DB_URL = "jdbc:derby:RegistrarDB";

       Scanner keyboard = new Scanner(System.in);

       try {
           Connection conn = DriverManager.getConnection(DB_URL);
           Statement stmt = conn.createStatement();
           System.out.print("Enter the product number: ");
           prodNum = keyboard.nextLine();

           if (findAndDisplayCourse(stmt, courseName)){
               System.out.print("Are you sure you want to delete this item? (y/n): ");
               sure = keyboard.nextLine();

               if (Character.toUpperCase(sure.charAt(0)) == 'Y'){
                   deleteCourse(stmt, CourseName);
               } else {
                   System.out.println("The item was not deleted.");
               }
           } else {
               System.out.println("The course was not found.");
           }
           conn.close();
          
       } catch(Exception e) {
           System.out.println("ERROR: " + ex.getMessage());
       }
   }

   public static boolean findAndDisplayCourse(Statement stmt, String courseName) throws SQLException {
       boolean courseFound;
       String sqlStatement = "SELECT * FROM Courses WHERE CourseName =" + courseName + "";
       ResultSet result = stmt.executeQuery(sqlStatement);

       if (result.next()){
           System.out.println("Course Number: " + result.getString("CourseNumber"));
           System.out.println("Course Name: " + result.getString("CourseName"));
           System.out.println("Course Title: " + result.getString("CourseTitle"));
           System.out.println("Course Credits " + result.getString("Course Credits"));

           courseFound = true;
       } else {
           courseFound = true;
       }
       return courseFound;
   }

   public static void deleteCourse(Statement stmt, String courseName) throws SQLException {
       String sqlStatement = "DELETE FROM Courses WHERE CourseName =" + courseName + "";

       int rows = stmt.executeUpdate(sqlStatement);

       System.out.println(rows + " rows(s) deleted.");
   }
}
