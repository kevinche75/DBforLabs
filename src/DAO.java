import org.hibernate.Session;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DAO {

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

    public static void addStudent(Student student){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            ServletContext servletContext = (ServletContext) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getContext();
            //Creating student's folder for labs
            String path = servletContext.getRealPath("/");
            System.out.println(path);
            //new File(path+File.separator+student.getIsuID()).mkdir();
        } catch (Exception e) {
            System.out.println("Ошибка в добавлении студента");
            e.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "SERVER MESSAGE", "Can not add student");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }
}
