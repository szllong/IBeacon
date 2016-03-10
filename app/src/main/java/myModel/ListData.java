package myModel;

/**
 * Created by cooper on 2016.3.7.
 */
public class ListData {
    private String content;
    private int flag;
    private String time;
    public static final int SEND = 1;
    public static final int RECEIVE = 2;

    public ListData(String content, int flag, String time) {
        this.content = content;
        this.flag = flag;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
