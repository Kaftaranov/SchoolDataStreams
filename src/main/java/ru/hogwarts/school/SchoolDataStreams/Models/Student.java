package ru.hogwarts.school.SchoolDataStreams.Models;
import javax.persistence.*;
import java.util.Objects;
@Entity
public class Student {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty")
    private Faculty faculty;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getFacultyId() {
        return faculty.getId();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", faculty=" + faculty.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return getId() == student.getId() && getAge() == student.getAge() && getName().equals(student.getName()) && Objects.equals(getFacultyId(), student.getFacultyId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getFacultyId());
    }
}
