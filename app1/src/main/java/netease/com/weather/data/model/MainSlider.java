package netease.com.weather.data.model;

/**
 * Created by liu_shuai on 16/3/17.
 */
public class MainSlider {
    private String title;
    private String article_url;
    private String board;
    private String board_url;
    private String section;

    public MainSlider(String title, String article_url, String board, String board_url) {
        this.title = title;
        this.article_url = article_url;
        this.board = board;
        this.board_url = board_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBoard_url() {
        return board_url;
    }

    public void setBoard_url(String board_url) {
        this.board_url = board_url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
