package top.liewyoung.network.response;


import top.liewyoung.agentTools.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiewYoung
 * @since 2025/12/6
 */

/**
 * <b>本类的作用是封装请求</b>
 */

//TODO Liew.Y : 设置getter函数
public class ApiResponse {
    private String id;
    private String object;
    private String created;
    private String model;
    private Usage usage;
    private String system_fingerprint;
    private List<Choice> choices;

    public ApiResponse(){

    }

}


class Choice {
    private int index;
    public Message message;
    private String logprobs;
    private String finish_reason;

    public Choice() {
    }
}

class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
    private PromptTokensDetails prompt_tokens_details;
    private int prompt_cache_hit_tokens;
    private int prompt_cache_miss_tokens;

    public Usage(int prompt_tokens, int completion_tokens, int total_tokens, PromptTokensDetails prompt_tokens_details, int prompt_cache_hit_tokens, int prompt_cache_miss_tokens) {
        this.prompt_tokens = prompt_tokens;
        this.completion_tokens = completion_tokens;
        this.total_tokens = total_tokens;
        this.prompt_tokens_details = prompt_tokens_details;
        this.prompt_cache_hit_tokens = prompt_cache_hit_tokens;
        this.prompt_cache_miss_tokens = prompt_cache_miss_tokens;
    }

}

class PromptTokensDetails {
    private int cached_tokens;

    public PromptTokensDetails(int cached_tokens) {
        this.cached_tokens = cached_tokens;
    }
}