import org.hibernate.Session;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "modelBean", eager = true)
@ApplicationScoped

public class ModelBean {

    private Student currentUser;
    private List<Student> students;

    private int isuID;
    private String firstName;
    private String lastName;
    private String patronymicName;

    private int labNumber;
    private double labScore;
    private Part labFile;
    private Date labDate;

    public void addStudent(){
        Student student = new Student(firstName,lastName,patronymicName, isuID);
        DAO.addStudent(student);
    }

    public void addLab(){
        Lab lab = new Lab();
        //Todo
    }

    public List<Integer> completeUser(String completeUser){
        List<Integer> list = new ArrayList<>();
        for (Student student : students) {
            if (Integer.toString(student.getIsuID()).startsWith(completeUser)) {
                list.add(student.getIsuID());
            }
        }
        return list;
    }

    public String toLabs(Student student){
        currentUser = student;
        return "labsTable?faces-redirect=true";
    }

    public int getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(int labNumber) {
        this.labNumber = labNumber;
    }

    public double getLabScore() {
        return labScore;
    }

    public void setLabScore(double labScore) {
        this.labScore = labScore;
    }

    public Part getLabFile() {
        return labFile;
    }

    public void setLabFile(Part labFile) {
        this.labFile = labFile;
    }

    public Date getLabDate() {
        return labDate;
    }

    public void setLabDate(Date labDate) {
        this.labDate = labDate;
    }

    public int getIsuID() {
        return isuID;
    }

    public void setIsuID(int isuID) {
        this.isuID = isuID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public Student getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Student currentUser) {
        this.currentUser = currentUser;
    }

    public List<Student> getStudents() {
        students = DAO.getAllStudents();
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
