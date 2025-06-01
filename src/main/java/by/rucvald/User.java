package by.rucvald;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_SEQ", allocationSize = 1)
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "birth_date")
    private LocalDate dateOfBirth;
    @Column(name = "age")
    private int age;

    public User(String userName, String lastName, String firstName, LocalDate dateOfBirth, int age) {
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User ) o;
        return age == user.age &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(dateOfBirth, user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, lastName, firstName, dateOfBirth, age);
    }

    public int getId() {
        return id;
    }


}
