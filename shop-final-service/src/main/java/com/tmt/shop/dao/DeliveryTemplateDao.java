package com.tmt.shop.dao; 

import org.springframework.stereotype.Repository;

import com.tmt.core.persistence.BaseDaoImpl;
import com.tmt.shop.entity.DeliveryTemplate;

/**
 * 快递单模板 管理
 * @author 超级管理员
 * @date 2016-02-23
 */
@Repository("shopDeliveryTemplateDao")
public class DeliveryTemplateDao extends BaseDaoImpl<DeliveryTemplate,Long> {}
