import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, String> {

    @Query("SELECT f.usernameFriend FROM Friend f WHERE f.username = :username")
    List<String> findFriendsByUsername(@Param("username") String username);

    @Query("SELECT f.username FROM Friend f WHERE f.usernameFriend = :username")
    List<String> findFriendsByFriendUsername(@Param("username") String username);

    
}
