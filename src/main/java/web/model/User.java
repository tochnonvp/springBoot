package web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @Size(min = 2, max = 10, message = "Размер имени должен быть в диапазоне от 2 до 10 символов")
    @NotNull(message = "поле name не может быть пустым")
    private String name;

    @NotNull
    @Size(min = 2, max = 10, message = "Размер имени должен быть в диапазоне от 2 до 10 символов")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "поле name не может быть пустым")
    @Column(name = "age")
    @Min(value = 0, message = "Возраст должен быть не меньше 18 лет")
    @Max(value = 130, message = "Возраст должен быть не больше 60 лет")
    private int age;
}
