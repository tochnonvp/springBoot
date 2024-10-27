package web.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import web.model.User;
import web.respons.ApiResponse;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    ResponseEntity<ApiResponse<User>> createUser(User user, BindingResult bindingResult);

    User update(Long id, User user);

    boolean delete(Long id);

}