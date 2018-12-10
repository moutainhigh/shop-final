package com.shop.config.server;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

/**
 * 自定义的 Servlet 服务器自动配置
 * 
 * @author lifeng
 */
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(ServletRequest.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(ServerProperties.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration.class)
public class ServletWebServerFactoryAutoConfiguration {

	/**
	 * 定义自定义对象
	 * 
	 * @param serverProperties
	 * @return
	 */
	@Bean
	@ConditionalOnClass(name = "org.apache.catalina.startup.Tomcat")
	public TomcatServletWebServerFactoryCustomizer shop_tomcatServletWebServerFactoryCustomizer() {
		return new TomcatServletWebServerFactoryCustomizer();
	}

	/**
	 * 自定义一些 tomcat 的配置
	 * 
	 * @author lifeng
	 */
	public static class TomcatServletWebServerFactoryCustomizer
			implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>, Ordered {

		private final Set<String> DEFAULT;

		public TomcatServletWebServerFactoryCustomizer() {
			Set<String> patterns = new LinkedHashSet<>();
			patterns.add("javax.*.jar");
			patterns.add("cglib-*.jar");
			patterns.add("asm-*.jar");
			patterns.add("fastjson-*.jar");
			patterns.add("kyro-*.jar");
			patterns.add("minlog-*.jar");
			patterns.add("objenesis-*.jar");
			patterns.add("ehcache-core-*.jar");
			patterns.add("jedis-*.jar");
			patterns.add("groovy-*.jar");
			patterns.add("poi-*.jar");
			patterns.add("xmlbeans-*.jar");
			patterns.add("stax-api-*.jar");
			patterns.add("activation-*.jar");
			patterns.add("httpclient-*.jar");
			patterns.add("httpcore-*.jar");
			patterns.add("httpmime-*.jar");
			patterns.add("jsoup-*.jar");
			patterns.add("core-*.jar");
			patterns.add("freemarker-*.jar");
			patterns.add("mybatis-*.jar");
			patterns.add("mysql-*.jar");
			patterns.add("sqlite-*.jar");
			patterns.add("druid-*.jar");
			patterns.add("HikariCP-.jar");
			patterns.add("lucene-*.jar");
			patterns.add("quartz-*.jar");
			patterns.add("ecj-*.jar");
			patterns.add("shop-*.jar");
			DEFAULT = Collections.unmodifiableSet(patterns);
		}
		
		@Override
		public int getOrder() {
			return 0;
		}

		@Override
		public void customize(TomcatServletWebServerFactory factory) {
			factory.getTldSkipPatterns().addAll(DEFAULT);
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/WEB-INF/views/error/401.jsp");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/views/error/404.jsp");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/WEB-INF/views/error/500.jsp");
			factory.addErrorPages(error401Page);
			factory.addErrorPages(error404Page);
			factory.addErrorPages(error500Page);
		}
	}
}