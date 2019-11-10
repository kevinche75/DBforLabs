import javax.persistence.*;
import java.util.Date;

@Entity

public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int labNumber;

    @Column(nullable = false)
    private double labScore;

    @Column(nullable = false)
    private String labName;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    public Lab() {
    }

    public Lab(int labNumber, double labScore, Date date) {
        this.labNumber = labNumber;
        this.labScore = labScore;
        this.date = date;
    }

    public Lab(int labNumber, double labScore, String labName, Date date) {
        this.labNumber = labNumber;
        this.labScore = labScore;
        this.labName = labName;
        this.date = date;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }
}
