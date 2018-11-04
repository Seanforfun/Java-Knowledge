package protobuf;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 2497464897604961378L;
    private Long id = null;
    private String name = null;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[UserInfo: id = "+ getId() +"; name = "+ getName()+"]";
    }
}
