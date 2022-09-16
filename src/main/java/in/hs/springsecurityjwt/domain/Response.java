package in.hs.springsecurityjwt.domain;

public class Response {
    private String token;

    public Response(String token) {
        this.token = token;
    }

    public Response() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
