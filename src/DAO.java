import org.hibernate.Session;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    //Здесь мог быть ваш путь
    private static final String path = "C:\\Users\\kevin\\Desktop\\ПИП\\folder";

    public static List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            students =  (List<Student>)session.createQuery("From Student").list();
        } catch (Exception e){
            System.out.println("Ошибка в получении всех студентов");
            e.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "Can not get list of students");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        return students;
    }

    public static File getLabFile(int ISUid, int labNumber, String labName){
        return new File(path + File.separator + ISUid + File.separator + labNumber + File.separator + labName);
    }

    public static String addStudent(Student student){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            //Creating student's folder for labs
            System.out.println(path);
            new File(path+File.separator+student.getIsuID()).mkdir();
        } catch (Exception e) {
            System.out.println("Ошибка в добавлении студента");
            e.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "Can not add the student");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "";
        }
        return "usersTable?faces-redirect=true";
    }

    public static String addLab(Lab lab, Student student, Part labFile){
        try(Session session = HibernateUtil.getSessionFactory().openSession();
            InputStream input = labFile.getInputStream()
        ){
            student.addLab(lab);
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
            File folder = new File(path + File.separator + student.getIsuID() + File.separator + lab.getLabNumber());
            if(!folder.exists()){
                folder.mkdir();
            }
            String fileName = labFile.getSubmittedFileName();
            Files.copy(input, new File(path + File.separator + student.getIsuID() + File.separator + lab.getLabNumber(), fileName).toPath());
            return "labsTable?faces-redirect=true";
        } catch (Exception e){
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "Can not add the lab");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "";
        }
    }
}
