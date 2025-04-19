package myapp.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedirectHttpToHttpsConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return factory -> {
            // 8080 포트용 커넥터
            Connector connector1 = new Connector(Http11NioProtocol.class.getName());
            connector1.setScheme("http");
            connector1.setPort(8080);
            connector1.setSecure(false);
            connector1.setRedirectPort(8443); // HTTPS로 리디렉션

            // 8081 포트용 커넥터
            Connector connector2 = new Connector(Http11NioProtocol.class.getName());
            connector2.setScheme("http");
            connector2.setPort(8081);
            connector2.setSecure(false);
            connector2.setRedirectPort(8443); // HTTPS로 리디렉션

            factory.addAdditionalTomcatConnectors(connector1, connector2);
        };
    }
}