import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/friends")
public class FriendController {

    @Autowired
    public FriendService friendService = new FriendService;

    @GetMapping("/all")
    public List<String> getFriendsByUsername(@PathVariable String username) {
        var allFriends = friendService.getFriendsByUsername(username);

        JSONArray jsonArray = new JSONArray();

        for (String i : allFriends)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("usernameFriend", i);

            jsonArray.put(jsonObject);
        }
        return ResponseEntity.ok(jsonArray.toString());
    }
}
