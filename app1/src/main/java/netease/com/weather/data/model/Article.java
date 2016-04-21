package netease.com.weather.data.model;

import java.util.List;

/**
 * Created by user on 16-4-21.
 */
public class Article {
    /**
     * id : 5801563
     * group_id : 5801563
     * reply_id : 5801563
     * flag :
     * position : 0
     * is_top : false
     * is_subject : true
     * has_attachment : false
     * is_admin : false
     * title : 有个躺下就说梦话的室友是一种什么体验
     * user : {"id":"echozyk","user_name":"说什么王权富贵","face_url":"http://static.byr.cn/uploadFace/E/echozyk.4887.jpg","face_width":120,"face_height":120,"gender":"m","astro":"巨蟹座","life":365,"qq":"","msn":"","home_page":"","level":"用户","is_online":false,"post_count":1201,"last_login_time":1461213905,"last_login_ip":"117.136.38.*","is_hide":false,"is_register":true,"score":13552}
     * post_time : 1461168188
     * board_name : Talking
     * board_description : 谈天说地
     * reply_count : 75
     * last_reply_user_id : jingruoyu
     * last_reply_time : 1461224234
     * pagination : {"page_all_count":8,"page_current_count":1,"item_page_count":10,"item_all_count":75}
     * article : [{}]
     */

    private int id;
    private int group_id;
    private int reply_id;
    private String flag;
    private int position;
    private boolean is_top;
    private boolean is_subject;
    private boolean has_attachment;
    private boolean is_admin;
    private String title;
    private User user;
    private int post_time;
    private String board_name;
    private String board_description;
    private int reply_count;
    private String last_reply_user_id;
    private int last_reply_time;
    /**
     * page_all_count : 8
     * page_current_count : 1
     * item_page_count : 10
     * item_all_count : 75
     */

    private PaginationEntity pagination;
    /**
     * id : 5801563
     * group_id : 5801563
     * reply_id : 5801563
     * flag :
     * position : 0
     * is_top : false
     * is_subject : true
     * has_attachment : false
     * is_admin : false
     * title : 有个躺下就说梦话的室友是一种什么体验
     * user : {"id":"echozyk","user_name":"说什么王权富贵","face_url":"http://static.byr.cn/uploadFace/E/echozyk.4887.jpg","face_width":120,"face_height":120,"gender":"m","astro":"巨蟹座","life":365,"qq":"","msn":"","home_page":"","level":"用户","is_online":false,"post_count":1201,"last_login_time":1461213905,"last_login_ip":"117.136.38.*","is_hide":false,"is_register":true,"score":13552}
     * post_time : 1461168188
     * board_name : Talking
     * board_description : 谈天说地
     * content : 简直棒极了
     * --
     * <p/>
     * <p/>
     * attachment : {"file":[],"remain_space":"5MB","remain_count":20}
     */

    private List<ArticleEntity> article;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isIs_top() {
        return is_top;
    }

    public void setIs_top(boolean is_top) {
        this.is_top = is_top;
    }

    public boolean isIs_subject() {
        return is_subject;
    }

    public void setIs_subject(boolean is_subject) {
        this.is_subject = is_subject;
    }

    public boolean isHas_attachment() {
        return has_attachment;
    }

    public void setHas_attachment(boolean has_attachment) {
        this.has_attachment = has_attachment;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPost_time() {
        return post_time;
    }

    public void setPost_time(int post_time) {
        this.post_time = post_time;
    }

    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    public String getBoard_description() {
        return board_description;
    }

    public void setBoard_description(String board_description) {
        this.board_description = board_description;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getLast_reply_user_id() {
        return last_reply_user_id;
    }

    public void setLast_reply_user_id(String last_reply_user_id) {
        this.last_reply_user_id = last_reply_user_id;
    }

    public int getLast_reply_time() {
        return last_reply_time;
    }

    public void setLast_reply_time(int last_reply_time) {
        this.last_reply_time = last_reply_time;
    }

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public List<ArticleEntity> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleEntity> article) {
        this.article = article;
    }


    public static class PaginationEntity {
        private int page_all_count;
        private int page_current_count;
        private int item_page_count;
        private int item_all_count;

        public int getPage_all_count() {
            return page_all_count;
        }

        public void setPage_all_count(int page_all_count) {
            this.page_all_count = page_all_count;
        }

        public int getPage_current_count() {
            return page_current_count;
        }

        public void setPage_current_count(int page_current_count) {
            this.page_current_count = page_current_count;
        }

        public int getItem_page_count() {
            return item_page_count;
        }

        public void setItem_page_count(int item_page_count) {
            this.item_page_count = item_page_count;
        }

        public int getItem_all_count() {
            return item_all_count;
        }

        public void setItem_all_count(int item_all_count) {
            this.item_all_count = item_all_count;
        }
    }

    public static class ArticleEntity {
        private int id;
        private int group_id;
        private int reply_id;
        private String flag;
        private int position;
        private boolean is_top;
        private boolean is_subject;
        private boolean has_attachment;
        private boolean is_admin;
        private String title;
        /**
         * id : echozyk
         * user_name : 说什么王权富贵
         * face_url : http://static.byr.cn/uploadFace/E/echozyk.4887.jpg
         * face_width : 120
         * face_height : 120
         * gender : m
         * astro : 巨蟹座
         * life : 365
         * qq :
         * msn :
         * home_page :
         * level : 用户
         * is_online : false
         * post_count : 1201
         * last_login_time : 1461213905
         * last_login_ip : 117.136.38.*
         * is_hide : false
         * is_register : true
         * score : 13552
         */

        private User user;
        private int post_time;
        private String board_name;
        private String board_description;
        private String content;
        /**
         * file : []
         * remain_space : 5MB
         * remain_count : 20
         */

        private AttachmentEntity attachment;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean isIs_top() {
            return is_top;
        }

        public void setIs_top(boolean is_top) {
            this.is_top = is_top;
        }

        public boolean isIs_subject() {
            return is_subject;
        }

        public void setIs_subject(boolean is_subject) {
            this.is_subject = is_subject;
        }

        public boolean isHas_attachment() {
            return has_attachment;
        }

        public void setHas_attachment(boolean has_attachment) {
            this.has_attachment = has_attachment;
        }

        public boolean isIs_admin() {
            return is_admin;
        }

        public void setIs_admin(boolean is_admin) {
            this.is_admin = is_admin;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public int getPost_time() {
            return post_time;
        }

        public void setPost_time(int post_time) {
            this.post_time = post_time;
        }

        public String getBoard_name() {
            return board_name;
        }

        public void setBoard_name(String board_name) {
            this.board_name = board_name;
        }

        public String getBoard_description() {
            return board_description;
        }

        public void setBoard_description(String board_description) {
            this.board_description = board_description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public AttachmentEntity getAttachment() {
            return attachment;
        }

        public void setAttachment(AttachmentEntity attachment) {
            this.attachment = attachment;
        }

        public static class AttachmentEntity {
            private String remain_space;
            private int remain_count;
            private List<?> file;

            public String getRemain_space() {
                return remain_space;
            }

            public void setRemain_space(String remain_space) {
                this.remain_space = remain_space;
            }

            public int getRemain_count() {
                return remain_count;
            }

            public void setRemain_count(int remain_count) {
                this.remain_count = remain_count;
            }

            public List<?> getFile() {
                return file;
            }

            public void setFile(List<?> file) {
                this.file = file;
            }
        }
    }
}
