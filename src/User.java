import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String patronymicName;

    @Column(nullable = false, unique = true)
    private Integer isuID;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lab> labs;

    public User() {
    }

    public User(String firstName, String lastName, Integer isuID, List<Lab> labs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isuID = isuID;
        this.labs = labs;
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

    public Integer getIsuID() {
        return isuID;
    }

    public void setIsuID(Integer isuID) {
        this.isuID = isuID;
    }

    public List<Lab> getLabs() {
        return labs;
    }

    public void setLabs(List<Lab> labs) {
        this.labs = labs;
    }

    public Integer getId() {
        return id;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public int getSize(){
        return labs.size();
    }
}
