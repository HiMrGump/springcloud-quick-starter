package com.project.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 验证码登录,如果存在code和手机字符串参数
 *
 * @ClassName: ValidateCodeGatewayFilterFactory
 * @Author: WangQingYun
 * @Date: Created in 2019/6/21 9:58
 * @Version: 1.0
 */
@Component
public class ValidatorCodeGatewayFilterFactory extends AbstractGatewayFilterFactory<ValidatorCodeGatewayFilterFactory.Config> {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain) -> {
            if (config.getMode() == 0) {
                return chain.filter(exchange);
            }
            // 读取RequestBody数据
            ServerRequest serverRequest = new DefaultServerRequest(exchange);
            // mediaType
            MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
            System.out.println( "mediaType { " + mediaType + " }...");
            System.out.println( "serverRequest { " + serverRequest + " }...");

            Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap((body) -> {
                System.out.println("body = " + body);
                if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
                    System.out.println("普通表单提交,不作修改");
                }else if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                    System.out.println("json提交");
                    JSONObject obj = JSON.parseObject(body);
                    if (obj == null) {
                        return Mono.empty();
                    }
                    String code = obj.getString("code");
                    String phone = obj.getString("phone");
                    System.out.println("接收到手机号:" + phone + ",验证码：" + code);
                    return Mono.just(obj.toJSONString());
                }
                return Mono.just(body); //不做修改
            });


            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());

            // the new content type will be computed by bodyInserter
            // and then set in the request decorator
            headers.remove(HttpHeaders.CONTENT_LENGTH);

            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);

            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                                exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                long contentLength = headers.getContentLength();
                                HttpHeaders httpHeaders = new HttpHeaders();
                                httpHeaders.putAll(super.getHeaders());
                                if (contentLength > 0) {
                                    httpHeaders.setContentLength(contentLength);
                                } else {
                                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                }
                                return httpHeaders;
                            }

                            @Override
                            public Flux<DataBuffer> getBody() {
                                System.out.println("准备插入的body：" + outputMessage);
                                return outputMessage.getBody();
                            }

                        };

                            return chain.filter(exchange.mutate().request(decorator).build());

                        }));
        };
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    @Data
    public static class Config {
        // 控制开启验证码验证模式
        // 0 - 不开启
        // 1 - 选择性开启（有就校验，没有直接放行）
        // 2 - 强制性开启（没有数据就校验失败）
        private int mode;
        //是否短信验证,0 - 普通验证码 1 - 短信
        private int smsMode;

    }

}
