package com.shop.config.eventbus;

import static com.shop.Application.APP_LOGGER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.AsyncEventBus;
import com.tmt.Constants;
import com.tmt.core.eventbus.EventBus;

/**
 * 消息队列的自动化配置
 * 
 * @author lifeng
 */
@Configuration
@ConditionalOnClass({ AsyncEventBus.class })
@EnableConfigurationProperties(EventBusProperties.class)
@ConditionalOnProperty(prefix = Constants.APPLICATION_PREFIX, name = "enableEventBus", matchIfMissing = true)
public class EventBusAutoConfiguration {

	@Autowired
	private EventBusProperties properties;

	public EventBusAutoConfiguration() {
		APP_LOGGER.debug("Loading EventBus");
	}

	/**
	 * 注册Event Bus
	 * 
	 * @return
	 */
	@Bean
	public EventBus eventBus() {
		return new EventBus(properties.getCoreThreads());
	}
}