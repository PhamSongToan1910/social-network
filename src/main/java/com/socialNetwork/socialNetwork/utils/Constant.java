package com.socialNetwork.socialNetwork.utils;

public class Constant {

    public static final int PAGE_SIZE = 10;

    public class POST {
        public static final int PRIVATE_STATUS = 1;
        public static final int PUBLIC_STATUS = 3;
        public static final int FRIEND_STATUS = 2;
    }

    public class TYPE_POST {
        public static final int CREATE_POST = 1;
        public static final int SHARE_POST = 2;
    }

    public class SEND_REQUEST{
            public static final int ACCEPT = 1;
        public static final int PENDING = 0;
        public static final int DENY = -1;
    }

    public class PUSH_NOTIFICATION {
        public static final String REST_API_KEY = "ZjE4OTM2MTgtZmEwNC00OWNhLWEyMDYtZDIyMGM4ODM5NzAy";
        public static final String APP_ID = "b441d196-3dcd-4737-9bb0-9d7ee8c2a021";
    }

    public class NOTIFICATION_TYPE {
        public static final int FOLLOW_REQUEST = 1;
        public static final int ADD_POST = 2;
        public static final int CMT = 3;
        public static final int RESPONSE_CMT = 4;
        public static final int REACT_CMT = 5;
        public static final int ACCEPT_FOLLOW_REQUEST = 6;
    }

    public class COLLECTION {
        public static final String SEND_FOLLOW_REQUEST_COLLECTION = "send_follow_request";
        public static final String FOLLOW_COLLECTION = "follow";
        public static final String USER_COLLECTION = "user";
        public static final String NOTIFICATION_COLLECTION = "notification";
        public static final String ROLE_COLLECTION = "authorities";
        public static final String RESOURCE_COLLECTION = "resource";
        public static final String POST_COLLECTION = "post";


    }

    public class REACT {
        public static final int LIKE = 1;
        public static final int LOVE = 2;
        public static final int CARE = 3;
        public static final int WOW = 4;
        public static final int HAHA = 5;
        public static final int SAD = 6;
        public static final int ANGRY = 7;
    }

    public class REACT_POSITION {
        public static final int POST = 1;
        public static final int COMMENT = 2;
        public static final int CHAT = 3;
    }

    public static final String ADD_FRIEND_REQUEST_MESSAGE = " đã gửi lời mời kết bạn";

    public static final String ACCPET_ADD_FRIEND_REQUEST_MESSAGE = " đã chấp nhận lời mời kết bạn";

    public static final String CREATE_POST = " đã cập nhật một bài viết mới";

    public static final String SHARE_POST = " đã chia sẻ một bài viết mới";
    public static final String COMMENT_POST = " đã bình luận một bài viết của bạn";
    public static final String REPPLY_COMMENT_POST = " đã phản hồi một bình luận trong bài viết của ";

    public static final String LIKE_POST = " đã thích một bài viết của bạn";
    public static final String LOVE_POST = " đã yêu thích một bài viết của bạn";
    public static final String CARE_POST = " đã quan tâm một bài viết của bạn";
    public static final String HAHA_POST = " đã cười một bài viết của bạn";
    public static final String WOW_POST = " đã wow một bài viết của bạn";
    public static final String SAD_POST = " đã buồn một bài viết của bạn";
    public static final String ANGRY_POST = " đã buồn một bài viết của bạn";

}
