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
    private int labScore;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    public Lab() {
    }

    public Lab(int labNumber, int labScore, Date date) {
        this.labNumber = labNumber;
        this.labScore = labScore;
        this.date = date;
    }

    public int getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(int labNumber) {
        this.labNumber = labNumber;
    }

    public int getLabScore() {
        return labScore;
    }

    public void setLabScore(int labScore) {
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
