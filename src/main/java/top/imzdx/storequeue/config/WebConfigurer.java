package top.imzdx.storequeue.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.imzdx.storequeue.interceptor.AdminInterceptor;
import top.imzdx.storequeue.interceptor.LoginInterceptor;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author Renxing
 * @description
 * @date 2021/4/11 13:59
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/**");
    }


    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

//    @Bean
//    public ElasticsearchClient elasticsearchClient() throws Exception {
//        final CredentialsProvider credentialsProvider =
//                new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("elastic", "*knngrZtMV0XskQyoUkf"));
//
//        Path caCertificatePath = Paths.get("C:\\Users\\Renxing\\AppData\\Roaming\\JetBrains\\IntelliJIdea2022.2\\docker\\es8\\usr\\share\\elasticsearch\\config\\certs\\http_ca.crt");
//        CertificateFactory factory =
//                CertificateFactory.getInstance("X.509");
//        Certificate trustedCa;
//        try (InputStream is = Files.newInputStream(caCertificatePath)) {
//            trustedCa = factory.generateCertificate(is);
//        }
//        KeyStore trustStore = KeyStore.getInstance("pkcs12");
//        trustStore.load(null, null);
//        trustStore.setCertificateEntry("ca", trustedCa);
//        SSLContextBuilder sslContextBuilder = SSLContexts.custom()
//                .loadTrustMaterial(trustStore, null);
//        final SSLContext sslContext = sslContextBuilder.build();
//
//        RestClientBuilder builder = RestClient.builder(
//                        new HttpHost("localhost", 9200, "https"))
//                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                    @Override
//                    public HttpAsyncClientBuilder customizeHttpClient(
//                            HttpAsyncClientBuilder httpClientBuilder) {
//                        return httpClientBuilder.setSSLContext(sslContext)
//                                .setDefaultCredentialsProvider(credentialsProvider);
//                    }
//                });
//
//        RestClient restClient = builder.build();
//
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }
}
