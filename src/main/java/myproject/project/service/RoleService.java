package myproject.project.service;

import lombok.AllArgsConstructor;
import myproject.project.entity.Role;
import myproject.project.entity.RoleType;
import myproject.project.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    public void initRole(){
        List<Role> roles = new ArrayList();
//        roles.add(Role.builder().name(RoleType.USER).build());
        roles.add(Role.builder().name(RoleType.ADMIN).build());
        roleRepository.saveAll(roles);
    }
}
