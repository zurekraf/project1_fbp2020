package pl.ske.project1.service;

import pl.ske.project1.entity.ApplicationUser;

public interface IUserService {
    ApplicationUser getByUsername(String username);
}
