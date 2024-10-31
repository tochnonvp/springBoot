package web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.model.User;
import web.service.UserService;

@Service
@RequiredArgsConstructor
public class UserSchedulerService {

    private final UserService userService;

    @Scheduled(fixedRate = 10000)
    public void fetchUsers() {
        User user = User.builder().name("Leha").surname("Rustemov").age(25).companyId(1).build();
        userService.save(user);
        System.out.println(user);
    }
}
