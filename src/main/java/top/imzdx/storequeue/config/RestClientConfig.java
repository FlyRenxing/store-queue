package top.imzdx.storequeue.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.http.HttpHeaders;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {
    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials("elastic", "*knngrZtMV0XskQyoUkf"));

            Path caCertificatePath = Paths.get("C:\\Users\\Renxing\\AppData\\Roaming\\JetBrains\\IntelliJIdea2022.2\\docker\\es8\\usr\\share\\elasticsearch\\config\\certs\\http_ca.crt");
            CertificateFactory factory =
                    CertificateFactory.getInstance("X.509");
            Certificate trustedCa;
            try (InputStream is = Files.newInputStream(caCertificatePath)) {
                trustedCa = factory.generateCertificate(is);
            }
            KeyStore trustStore = KeyStore.getInstance("pkcs12");
            trustStore.load(null, null);
            trustStore.setCertificateEntry("ca", trustedCa);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, null);
            final SSLContext sslContext = sslContextBuilder.build();

            RestClientBuilder builder = RestClient.builder(
                            new HttpHost("localhost", 9200, "https"))
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(
                                HttpAsyncClientBuilder httpClientBuilder) {
                            Header header1 = new BasicHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
                            Header header2 = new BasicHeader("Content-Type", "application/vnd.elasticsearch+json;"
                                    + "compatible-with=7");
                            Header[] headers = new Header[]{header1, header2};
                            return httpClientBuilder.setSSLContext(sslContext)
                                    .setDefaultCredentialsProvider(credentialsProvider)
                                    .setDefaultHeaders(Arrays.asList(headers));
                        }
                    });
            RestClient restClient = builder.build();
            return new RestHighLevelClientBuilder(restClient).build();

        } catch (Exception e) {
            return null;
        }

    }
}
