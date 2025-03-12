package myapp.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;

@Configuration
public class RedirectHttpToHttpsConfig {

    @Bean
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return factory -> {
            Connector connector = new Connector(Http11NioProtocol.class.getName());
            connector.setScheme("http");
            connector.setPort(8080); // HTTP 포트
            connector.setSecure(false);
            connector.setRedirectPort(8443); // HTTPS 포트로 리디렉션
            factory.addAdditionalTomcatConnectors(connector);
        };
    }
}
