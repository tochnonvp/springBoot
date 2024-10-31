package web.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    //:TODO - ПРОВАЛИДИРУЙ ДТОШКУ
    private String name;
    private String surname;
    private Integer age;
    private Integer companyId;
}
