package todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import todolist.entity.Todos;
import todolist.entity.User;

import java.util.List;
import java.util.Optional;

public interface TodoRepo extends JpaRepository<Todos, Long> {

    Page<Todos> findByUser(User user, Pageable pageable);

    Optional<Todos> findByIdAndUser(Long id, User user);

    Page<Todos> findByUserAndCompleted(
            User user,
            boolean completed,
            Pageable pageable
    );

    Page<Todos> findByUserAndTitleContainingIgnoreCase(
            User user,
            String title,
            Pageable pageable
    );
}
