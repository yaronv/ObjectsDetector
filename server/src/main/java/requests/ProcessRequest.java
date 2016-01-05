package requests;

/**
 * Created by yaron on 30/06/15.
 */
public class ProcessRequest {

    private String id;

    public ProcessRequest() {

    }

    public ProcessRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + this.getId();
    }
}
