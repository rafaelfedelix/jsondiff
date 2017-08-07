package br.com.fedelix.jsondiff;

import br.com.fedelix.jsondiff.mappings.UrlMappings;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SparkBoot {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "prod");
        String configLocation = "META-INF/spring-context.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        UrlMappings urlMappings = context.getBean(UrlMappings.class);
        urlMappings.mapEndpoints();
    }
}