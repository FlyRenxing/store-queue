package top.imzdx.storequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
public class StoreQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreQueueApplication.class, args);
    }

}
