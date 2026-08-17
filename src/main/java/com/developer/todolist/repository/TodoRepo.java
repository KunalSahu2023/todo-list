package todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todolist.entity.Todos;
import todolist.entity.User;

import java.util.List;
import java.util.Optional;

public interface TodoRepo extends JpaRepository<Todos, Long> {

    List<Todos> findByUser(User user);

    Optional<Todos> findByIdAndUser(Long id, User user);
}
