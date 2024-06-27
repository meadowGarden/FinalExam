package lt.techin.exam.repository;

import lt.techin.exam.entity.UsersComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCommentRepository extends JpaRepository<UsersComment, UUID> {


}
