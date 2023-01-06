package KAFKASTREAMS.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userName",
    "pageVisits"
})
public class User {

    @JsonProperty("userName")
    private String userName;
    @JsonProperty("pageVisits")
    private List<UserPageVisit> pageVisits = new ArrayList<UserPageVisit>();

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("pageVisits")
    public List<UserPageVisit> getPageVisits() {
        return pageVisits;
    }

    @JsonProperty("pageVisits")
    public void setPageVisits(List<UserPageVisit> pageVisits) {
        this.pageVisits = pageVisits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(User.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("userName");
        sb.append('=');
        sb.append(((this.userName == null)?"<null>":this.userName));
        sb.append(',');
        sb.append("pageVisits");
        sb.append('=');
        sb.append(((this.pageVisits == null)?"<null>":this.pageVisits));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.userName == null)? 0 :this.userName.hashCode()));
        result = ((result* 31)+((this.pageVisits == null)? 0 :this.pageVisits.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
        User rhs = ((User) other);
        return (((this.userName == rhs.userName)||((this.userName!= null)&&this.userName.equals(rhs.userName)))&&((this.pageVisits == rhs.pageVisits)||((this.pageVisits!= null)&&this.pageVisits.equals(rhs.pageVisits))));
    }

}
