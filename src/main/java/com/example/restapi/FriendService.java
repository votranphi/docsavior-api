import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    public List<String> getFriendsByUsername(String username) {
        List<String> friendsAsUser = friendRepository.findFriendsByUsername(username);
        List<String> friendsAsFriend = friendRepository.findFriendsByFriendUsername(username);
        return Stream.concat(friendsAsUser.stream(), friendsAsFriend.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
