package practice.demirbank.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.demirbank.entity.Role;
import practice.demirbank.exception.ResourceNotFoundException;
import practice.demirbank.repository.RoleRepository;
import practice.demirbank.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

}
