package com.tmt.wechat.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tmt.common.config.Globals;
import com.tmt.common.entity.AjaxResult;
import com.tmt.common.persistence.Page;
import com.tmt.common.persistence.PageParameters;
import com.tmt.common.persistence.QueryCondition;
import com.tmt.common.persistence.QueryCondition.Criteria;
import com.tmt.common.persistence.incrementer.IdGen;
import com.tmt.common.utils.Lists;
import com.tmt.common.utils.StringUtil3;
import com.tmt.common.utils.WebUtils;
import com.tmt.common.web.BaseController;
import com.tmt.wechat.entity.MetaText;
import com.tmt.wechat.service.AppServiceFacade;
import com.tmt.wechat.service.MetaTextServiceFacade;

/**
 * 文本回复 管理
 * 
 * @author 超级管理员
 * @date 2016-09-30
 */
@Controller("wechatMetaTextController")
@RequestMapping(value = "${spring.application.web.admin}/wechat/meta/text")
public class MetaTextController extends BaseController {

	@Autowired
	private MetaTextServiceFacade metaTextService;
	@Autowired
	private AppServiceFacade appService;

	/**
	 * 进入初始化页面
	 * 
	 * @param model
	 */
	@RequestMapping("list")
	public String list(MetaText metaText, Model model) {
		return "/wechat/MetaTextList";
	}

	/**
	 * 初始化页面的数据
	 * 
	 * @param metaText
	 * @param model
	 * @param page
	 * @return Page Json
	 */
	@ResponseBody
	@RequestMapping("page")
	public Page page(MetaText metaText, Page page) {
		QueryCondition qc = new QueryCondition();
		PageParameters param = page.getParam();
		Criteria c = qc.getCriteria();
		this.initQc(metaText, c);
		qc.setOrderByClause(param.orderBy("CREATE_DATE DESC"));
		return metaTextService.queryForPage(qc, param);
	}

	/**
	 * 表单
	 * 
	 * @param metaText
	 * @param model
	 */
	@RequestMapping("form")
	public String form(MetaText metaText, Model model) {
		if (metaText != null && !IdGen.isInvalidId(metaText.getId())) {
			metaText = this.metaTextService.get(metaText.getId());
		} else {
			if (metaText == null) {
				metaText = new MetaText();
			}
			metaText.setId(IdGen.INVALID_ID);
		}
		model.addAttribute("metaText", metaText);
		model.addAttribute("apps", appService.getAll());
		return "/wechat/MetaTextForm";
	}

	/**
	 * 保存
	 * 
	 * @param category
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("save")
	public String save(MetaText metaText, Model model, RedirectAttributes redirectAttributes) {
		this.metaTextService.save(metaText);
		addMessage(redirectAttributes, StringUtil3.format("%s'%s'%s", "修改文本回复", metaText.getKeyword(), "成功"));
		redirectAttributes.addAttribute("id", metaText.getId());
		return WebUtils.redirectTo(Globals.adminPath, "/wechat/meta/text/form");
	}

	/**
	 * 删除
	 * 
	 * @param idList
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public AjaxResult delete(Long[] idList, Model model, HttpServletResponse response) {
		List<MetaText> metaTexts = Lists.newArrayList();
		for (Long id : idList) {
			MetaText metaText = new MetaText();
			metaText.setId(id);
			metaTexts.add(metaText);
		}
		this.metaTextService.delete(metaTexts);
		return AjaxResult.success();
	}

	// 查询条件
	private void initQc(MetaText metaText, Criteria c) {
		if (StringUtil3.isNotBlank(metaText.getKeyword())) {
			c.andEqualTo("KEYWORD", metaText.getKeyword());
		}
	}
}
