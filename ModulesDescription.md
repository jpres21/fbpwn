All modules work on a selected profile URL (we'll call him bob), using a valid authenticated account (we'll call him mallory).


FBPwn modules are:


- AddVictimFriends: Request to add some or all friends of bob to increase the chance of bob accepting any future requests, after he finds that you have common friends.

- ProfileCloner: A list of all bob's friends is displayed, you choose one of them (we'll call him andy). FBPwn will change mallory's display picture, and basic info to match andy's. This will generate more chance that bob accepts requests from mallory as he thinks he is accepting from andy. Eventually bob will realize this is not andy's account, but probably it would be too late as all his info are already saved for offline checking by mallory.

- CheckFriendRequest: Check if mallory is already friend of bob, then just end execution. If not, the module tries to add bob as as a friend and poll waiting for him to accept. The module will not stop executing until the friend request is accepted.

- DumpFriends: Accessable friends of bob is saved for offline viewing. The output of the module depends on other modues, if mallory is not a friend of bob yet, the data  might not be accessable and nothing will be dumped.

- DumpImages: Accessable images (tagged and albums) are saved for offline viewing including comments under each image and album names. Same limitations of dump friends applies.

- DumpInfo: Accessable basic info are saved for offline viewing. Same limitations of dump friends applies.

- DumpWall: Dumps wall posts for offline viewing. Same limitations of dump friends applies.

- DictionaryBuilder: Builds a dictionary using words from comments under photos and wall posts.

- CloseFriendsFinder: Finds the victim's close circle of friends by counting number of comments,likes and tags under photos and wall posts with the ability to change the weights of the ranking criteria.