package KAFKASTREAMS.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userKey",
    "expirationTimeStamp",
    "pageVisits"
})
public class ExpirationTimeUserRecord {

    @JsonProperty("userKey")
    private String userKey;
    @JsonProperty("expirationTimeStamp")
    private long expirationTimeStamp;
    @JsonProperty("pageVisits")
    private List<UserPageVisit> pageVisits = new ArrayList<UserPageVisit>();

    @JsonProperty("userKey")
    public String getUserKey() {
        return userKey;
    }

    @JsonProperty("userKey")
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    @JsonProperty("expirationTimeStamp")
    public long getExpirationTimeStamp() {
        return expirationTimeStamp;
    }

    @JsonProperty("expirationTimeStamp")
    public void setExpirationTimeStamp(long expirationTimeStamp) {
        this.expirationTimeStamp = expirationTimeStamp;
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
        sb.append(ExpirationTimeUserRecord.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("userKey");
        sb.append('=');
        sb.append(((this.userKey == null)?"<null>":this.userKey));
        sb.append(',');
        sb.append("expirationTimeStamp");
        sb.append('=');
        sb.append((this.expirationTimeStamp));
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
        result = ((result* 31)+((this.pageVisits == null)? 0 :this.pageVisits.hashCode()));
        result = ((result* 31)+((this.userKey == null)? 0 :this.userKey.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExpirationTimeUserRecord) == false) {
            return false;
        }
        ExpirationTimeUserRecord rhs = ((ExpirationTimeUserRecord) other);

        return ((((this.pageVisits == rhs.pageVisits)||((this.pageVisits!= null)&&this.pageVisits.equals(rhs.pageVisits)))&&((this.expirationTimeStamp == rhs.expirationTimeStamp)))&&((this.userKey == rhs.userKey)||((this.userKey!= null)&&this.userKey.equals(rhs.userKey))));
    }

}
