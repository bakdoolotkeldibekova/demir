package practice.demirbank.service;

import practice.demirbank.entity.Role;

public interface RoleService {
    Role create(Role role);
    Role getById(Long id);
}
