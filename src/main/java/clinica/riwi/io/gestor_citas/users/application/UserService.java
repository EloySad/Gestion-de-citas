package clinica.riwi.io.gestor_citas.users.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.riwi.io.gestor_citas.users.domain.UserEntity;
import clinica.riwi.io.gestor_citas.users.domain.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity update(UserEntity user) {
    return userRepository.save(user);
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }

  public Optional<UserEntity> findById(Long id) {
    return userRepository.findById(id);
  }

  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }

}