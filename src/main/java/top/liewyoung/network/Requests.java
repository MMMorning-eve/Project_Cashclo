package top.liewyoung.network;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.liewyoung.agentTools.ChatRequest;
import top.liewyoung.agentTools.Message;
import top.liewyoung.agentTools.Role;

/**
 *
 * @author LiewYoung
 * @since 2025/12/1
 */


public final class Requests {
    private final URI uri;
    private final HttpClient client;
    private final String apiKey;

    /**
     *
     * @param uri 模型请求的base_url
     * @param apiKey 你自己的apiKey
     */
    public Requests(String uri,String apiKey){
        this.uri = URI.create(uri);
        this.apiKey = apiKey;
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }

    /**
     *
     * @param request 需要一个ChatRequest对象
     * @return  HttpResponse<String> 返回值
     * @throws IOException 读入错误
     * @throws InterruptedException 内部错误
     */
    public HttpResponse<String> post(ChatRequest request) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(request);

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + this.apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .timeout(Duration.ofSeconds(20))
                .build();

        return client.send(request1,HttpResponse.BodyHandlers.ofString());
    }

    //测试代码
    public static void main(String[] args) throws IOException, InterruptedException {
        Message message1 = new Message(Role.SYSTEM,"You are a helpful assistant");
        Message message2 = new Message(Role.USER,"Hello world");

        ChatRequest req = new ChatRequest("deepseek-chat",false);
        req.addMessage(message1);
        req.addMessage(message2);

    }
}


