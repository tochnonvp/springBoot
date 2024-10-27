package web.respons;

import lombok.Data;
import java.util.List;

@Data

public class ApiResponse<T> {
    private T data;
    private List<String> errors;

    public ApiResponse(T data) {
        this.data = data;
    }
    public ApiResponse(List<String> errors) {
        this.errors = errors;
    }
}