# RESTful API for Docsavior
The Spring Boot project for Docsavior is dedicated to creating a robust RESTful API that powers the Docsavior mobile application. This API facilitates seamless access to and management of a vast library of free documents, ensuring users can easily find, retrieve, and utilize the resources they need. Designed with scalability and performance in mind, the project provides a solid backend infrastructure to support the various functionalities of Docsavior, enhancing the user experience and ensuring reliable document delivery.

## Docsavior reference
For detailed information about our Docsavior mobile application, please visit our repository. Your feedback and contributions are appreciated. Docsavior repository: [docsavior](https://github.com/votranphi/docsavior)

## Entities
The entities are the persistence objects stores as a record in the database. Here are all the entities, also the tables in MySQL database using in this project:

- **Comment:** store comments of every post in Newsfeed entity.
- **Friend:** store two usernames if they're friend of each other.
- **FriendRequest:** store friend requests.
- **LookUpHistory:** store look up history of a user.
- **Message:** store messages between two users.
- **Newsfeed:** store posts on Newsfeed page.
- **Notification:** store notifications of a user.
- **Otp:** store opt code when user registers account.
- **User:** store user's information.
- **UserInteract:** store the user's like/dislike information.

## Endpoints
API endpoint is a specific location within an API that accepts requests and sends back responses. For more information about API endpoints' usage, please visit this [OpenAPI Specification](https://docsavior-api-docsavior-apii.azuremicroservices.io/swagger-ui/index.html#).

Here are all the endpoints in this project:

1. **Comment**:
    - **/comment/add (POST):** add new post's comment to the table.
    - **/comment/comment_on_post (GET):** get all comments on a post specified by its ID.
2. **Friend**:
    - **/friend/all (GET):** get all user's friends based on user's username.
    - **/friend/add (POST):** add new friend relationship to the table.
    - **/friend/delete (DELETE):** delete a friend relationship from the table.
    - **/friend/look_up (POST):** find and return all friends defined by "lookup string".
3. **FriendRequest**:
    - **/friend_request/all (GET):** get all user's friend requests based on user's username.
    - **/friend_request/add (POST):** add new friend request to the table.
    - **/friend_request/delete (DELETE):** delete a friend request from the table.
4. **LookUpHistory**:
    - **/look_up_history/add (POST):** add new look up history to the table.
    - **/look_up_history/delete (DELETE):** delete a look up history from the table.
    - **/look_up_history/me/chat (GET):** get all chat's lookup history defined by username.
    - **/look_up_history/me/friend (GET):** get all friend's lookup history defined by username.
    - **/look_up_history/me/post (GET):** get all post's lookup history defined by username.
5. **Message**:
    - **/message/add (POST):** add new message to the table.
    - **/message/all (GET):** get all messages between two users.
    - **/message/latest (GET):** get a latest message between two users.
    - **/message/messaged_user (GET):** get a list of username who has been messaged.
    - **/message/seen_to_true (POST):** update "seen status" of a specific message to true.
    - **/message/unseen (GET):** get all unseen messages between two users.
6. **Newsfeed**:
    - **/newsfeed/add (POST):** add new post to the table.
    - **/newsfeed/all (GET):** get all posts existed in the table.
    - **/newsfeed/comment (POST):** increase the post's comment number by 1.
    - **/newsfeed/dislike (POST):** increase the post's dislike number by 1.
    - **/newsfeed/found_size (GET):** get number of posts based on "lookup string".
    - **/newsfeed/id (GET):** get a post specified by its ID.
    - **/newsfeed/like (POST):** increase the post's like number by 1.
    - **/newsfeed/look_up (POST):** find and return posts defined by "lookup string".
    - **/newsfeed/me (GET):** get all posts defined by username.
    - **/newsfeed/me_size (GET):** get number of user's posts based on user's username.
    - **/newsfeed/post (GET):** get an array of post specified by "page number" and "page size". In other words, get posts with pagination.
    - **/newsfeed/size (GET):** get the size of the Newsfeed table (number of post).
    - **/newsfeed/uncomment (POST):** decrease the post's comment number by 1.
    - **/newsfeed/undislike (POST):** decrease the post's dislike number by 1.
    - **/newsfeed/unlike (POST):** decrease the post's like number by 1.
7. **Notification**:
    - **/notification/add (POST):** add new notification to the table.
    - **/notification/delete (DELETE):** delete a notification from the table.
    - **/notification/me (GET):** get all notifications specified by username.
8. **Otp**:
    - **/otp/check (POST):** check if the OTP code is expired, correct or incorrect.
    - **/otp/create_or_refresh (POST):** create or refresh user's OTP code then send it to user's email.
9. **User**:
    - **/user/add (POST):** add new user's information to the table (exclude avatar's information).
    - **/user/avatar (POST):** add new user's avatar information to the table.
    - **/user/avatar_data (GET):** get user's avatar data defined by user's username.
    - **/user/check (POST):** check if username, email and phone number are valid.
    - **/user/login (POST):** receive and check if the username and password is valid.
    - **/user/login_to_true (POST):** update the user's status to true (online).
    - **/user/logout (POST):** update the user's status to false (offline).
    - **/user/look_up (POST):** get all users specified by "lookup string".
    - **/user/me (GET):** get user's information based on user's username.
    - **/user/password_change (POST):** update new password received from user.
    - **/user/password_recovery (POST):** receive and check the user's information then send new password to user's email.
    - **/user/status (GET):** get user's status defined by user's username.
    - **/user/update_user_info (POST):** update user's information.
10. **UserInteract**:
    - **/user_interact/interact (POST):** add new user's interaction to the table.
    - **/user_interact/likeordislike (GET):** get the user's interaction on a post.

## Credits
This RESTful API project powers the Docsavior mobile application was completed by Group 10 of NT118.O22 - Mobile Application Development, University of Information Technology (UIT), Vietnam National University, Ho Chi Minh City (VNU-HCM). The group members are:
- Vo Tran Phi (Student ID: 22521081)  
Github link: [votranphi](https://github.com/votranphi) 
- Le Duong Minh Phuc (Student ID: 22521116)  
Github link: [minhphuc2544](https://github.com/minhphuc2544)
- Le Nguyen Thao Van (Student ID: 22521648)  
Github link: [KamiraKirai](https://github.com/KamiraKirai)
