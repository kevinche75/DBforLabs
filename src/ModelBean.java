import org.hibernate.Session;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "modelBean", eager = true)
@ApplicationScoped

public class ModelBean {

    private Student currentUser;
    private List<Student> students;
    private String currentISUid;

    private int isuID;
    private String firstName;
    private String lastName;
    private String patronymicName;

    private int labNumber;
    private double labScore;
    private Part labFile;
    private Date labDate;

    public void download(Lab lab){
        File labFile = DAO.getLabFile(currentUser.getIsuID(), lab.getLabNumber(), lab.getLabName());
        if(!labFile.exists()){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "Couldn't find the file");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return;
        }
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext ec = ctx.getExternalContext();
        String fileName = labFile.getName();
        String contentType = ec.getMimeType(fileName);
        int contentLength = (int) labFile.length();

        ec.responseReset();
        ec.setResponseContentType(contentType);
        ec.setResponseContentLength(contentLength);
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try(OutputStream output = ec.getResponseOutputStream()){
            Files.copy(labFile.toPath(), output);
            ctx.responseComplete();
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "Couldn't begin downloading the file");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public String addStudent(){
        Student student = new Student(firstName,lastName,patronymicName, isuID);
        return DAO.addStudent(student);
    }

    public String addLab(){
        for(Lab lab : currentUser.getLabs()){
            if(lab.getLabNumber()==labNumber){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "The Student has already had such lab");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "";
            }
        }
        Lab lab = new Lab(labNumber, labScore, labFile.getSubmittedFileName(), labDate);
        return DAO.addLab(lab, currentUser, labFile);
    }

    public List<Integer> completeUser(String completeUser){
        List<Integer> list = new ArrayList<>();
        for (Student student : students) {
            if (Integer.toString(student.getIsuID()).startsWith(completeUser)) {
                list.add(student.getIsuID());
            }
        }
        currentISUid = completeUser;
        return list;
    }

    public String showStudentLabs(){
        for(Student student : students){
            if(currentISUid.equals(student.getIsuID().toString())){
                currentUser = student;
                return toLabs(student);
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "Can not find such student");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return "";
    }

    public String toLabs(Student student){
        currentUser = student;
        System.out.println(student.getIsuID());
        return "labsTable?faces-redirect=true";
    }

    public int getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(int labNumber) {
        this.labNumber = labNumber;
    }

    public String getCurrentISUid() {
        return currentISUid;
    }

    public void setCurrentISUid(String currentISUid) {
        this.currentISUid = currentISUid;
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
