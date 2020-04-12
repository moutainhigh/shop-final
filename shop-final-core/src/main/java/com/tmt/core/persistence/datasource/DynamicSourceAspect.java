package com.tmt.core.persistence.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import com.tmt.core.utils.StringUtils;

/**
 * 如果需要在多数据源的配置文件种配置出来
 * 
 * @author lifeng
 */
@Aspect
public class DynamicSourceAspect implements Ordered {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 定义切入点
	 */
	@Pointcut(value = "@annotation(com.tmt.core.persistence.datasource.DS)")
	private void cut() {

	}

	@Around("cut()")
	public Object around(ProceedingJoinPoint point, DS datasource) throws Throwable {
		if (log.isDebugEnabled()) {
			log.debug("设置数据源为：" + datasource.value());
		}
		if (StringUtils.hasLength(datasource.value())) {
			DataSourceHolder.setDataSourceType(datasource.value());
		}
		try {
			return point.proceed();
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("清空数据源信息！");
			}
			DataSourceHolder.clearDataSourceType();
		}
	}

	/**
	 * Aop的顺序要早于spring的事务
	 * 注意Order序号要小于{@link org.springframework.aop.interceptor.ExposeInvocationInterceptor},
	 * PriorityOrdered.HIGHEST_PRECEDENCE + 1
	 */
	@Override
	public int getOrder() {
		return -1000;
	}
}