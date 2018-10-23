import java.util.Arrays;

public class SplitTest {

    public static void main(String[] args) {
        String http = "HTTP/1.1 200 OK\n" +
                "Date: Tue, 23 Oct 2018 16:40:44 GMT\n" +
                "Server: Apache/2.4.27 (Ubuntu)\n" +
                "Vary: Accept-Encoding\n" +
                "Content-Encoding: gzip\n" +
                "Content-Length: 5989\n" +
                "Keep-Alive: timeout=5, max=98\n" +
                "Connection: Keep-Alive\n" +
                "Content-Type: text/html; charset=UTF-8\n\nhey";
        System.out.println(Arrays.toString(http.split("\n")));
    }
}
