import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String patronymicName;

    @Column(nullable = false, unique = true)
    private Integer isuID;

    @Column(nullable = false)
    private int numberOfLabs;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Lab> labs;

    public Student() {
    }

    public Student(String firstName, String lastName, String patronymicName, Integer isuID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.isuID = isuID;
        labs = new ArrayList<>();
        numberOfLabs = 0;
    }

    public Student(String firstName, String lastName, Integer isuID, List<Lab> labs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isuID = isuID;
        this.labs = labs;
        numberOfLabs = labs.size();
    }

    public Student(String firstName, String lastName, String patronymicName, Integer isuID, int numberOfLabs, List<Lab> labs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.isuID = isuID;
        this.numberOfLabs = numberOfLabs;
        this.labs = labs;
    }

    public void addLab(Lab lab){
        lab.setStudent(this);
        labs.add(lab);
    }

    public int getNumberOfLabs() {
        return numberOfLabs;
    }

    public void setNumberOfLabs(int numberOfLabs) {
        this.numberOfLabs = numberOfLabs;
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
}
