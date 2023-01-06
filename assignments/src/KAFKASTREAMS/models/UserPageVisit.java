package KAFKASTREAMS.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "page",
    "pageStartTime",
    "pageEndTime",
    "server"
})
public class UserPageVisit {

    @JsonProperty("page")
    private String page;
    @JsonProperty("pageStartTime")
    private Object pageStartTime;
    @JsonProperty("pageEndTime")
    private Object pageEndTime;
    @JsonProperty("server")
    private String server;

    @JsonProperty("page")
    public String getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(String page) {
        this.page = page;
    }

    @JsonProperty("pageStartTime")
    public Object getPageStartTime() {
        return pageStartTime;
    }

    @JsonProperty("pageStartTime")
    public void setPageStartTime(Object pageStartTime) {
        this.pageStartTime = pageStartTime;
    }

    @JsonProperty("pageEndTime")
    public Object getPageEndTime() {
        return pageEndTime;
    }

    @JsonProperty("pageEndTime")
    public void setPageEndTime(Object pageEndTime) {
        this.pageEndTime = pageEndTime;
    }

    @JsonProperty("server")
    public String getServer() {
        return server;
    }

    @JsonProperty("server")
    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(UserPageVisit.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("page");
        sb.append('=');
        sb.append(((this.page == null)?"<null>":this.page));
        sb.append(',');
        sb.append("pageStartTime");
        sb.append('=');
        sb.append(((this.pageStartTime == null)?"<null>":this.pageStartTime));
        sb.append(',');
        sb.append("pageEndTime");
        sb.append('=');
        sb.append(((this.pageEndTime == null)?"<null>":this.pageEndTime));
        sb.append(',');
        sb.append("server");
        sb.append('=');
        sb.append(((this.server == null)?"<null>":this.server));
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
        result = ((result* 31)+((this.server == null)? 0 :this.server.hashCode()));
        result = ((result* 31)+((this.page == null)? 0 :this.page.hashCode()));
        result = ((result* 31)+((this.pageEndTime == null)? 0 :this.pageEndTime.hashCode()));
        result = ((result* 31)+((this.pageStartTime == null)? 0 :this.pageStartTime.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserPageVisit) == false) {
            return false;
        }
        UserPageVisit rhs = ((UserPageVisit) other);
        return (((((this.server == rhs.server)||((this.server!= null)&&this.server.equals(rhs.server)))&&((this.page == rhs.page)||((this.page!= null)&&this.page.equals(rhs.page))))&&((this.pageEndTime == rhs.pageEndTime)||((this.pageEndTime!= null)&&this.pageEndTime.equals(rhs.pageEndTime))))&&((this.pageStartTime == rhs.pageStartTime)||((this.pageStartTime!= null)&&this.pageStartTime.equals(rhs.pageStartTime))));
    }

}
