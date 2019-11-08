import org.hibernate.Session;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "modelBean", eager = true)
@ApplicationScoped

public class ModelBean {

    private int currentUser;
    private List<User> users;
    private boolean isFalseISUid = true;
    private int isuID;
    private String firstName;
    private String lastName;
    private String patronymicName;

    public String addUser(){
        return "usersTable?faces-redirect=true";
    }

    public List<Integer> completeUser(String completeUser){
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<users.size(); i++){
            User user = users.get(i);
            if(Integer.toString(user.getIsuID()).startsWith(completeUser)){
                list.add(user.getIsuID());
                isFalseISUid = false;
            }
        }
        if(list.size()==0){
            isFalseISUid = true;
        }
        return list;
    }

    public String toLabs(int id){
        currentUser = id;
        return "labsTable?faces-redirect=true";
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

    public boolean getIsFalseISUid() {
        return isFalseISUid;
    }

    public void setIsFalseISUid(boolean falseISUid) {
        isFalseISUid = falseISUid;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            users =  (List<User>)session.createQuery("From User").list();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.users = users;
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
