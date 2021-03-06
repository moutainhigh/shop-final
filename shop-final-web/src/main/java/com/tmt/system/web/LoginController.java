package com.tmt.system.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmt.Constants;
import com.tmt.core.codec.Encodes;
import com.tmt.core.config.Globals;
import com.tmt.core.entity.BaseEntity;
import com.tmt.core.exception.ErrorCode;
import com.tmt.core.security.context.AuthenticationToken;
import com.tmt.core.security.principal.Principal;
import com.tmt.core.security.utils.SecurityUtils;
import com.tmt.core.utils.StringUtils;
import com.tmt.core.utils.WebUtils;
import com.tmt.core.web.BaseController;
import com.tmt.core.web.ValidateCodeService;
import com.tmt.system.utils.UserUtils;

/**
 * 后台会员登录
 * 
 * @author lifeng
 *
 */
@Controller
@RequestMapping(value = "${spring.application.web.admin}")
public class LoginController extends BaseController {

	/**
	 * 首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		if (!UserUtils.isAuthenticated() && !UserUtils.isRemembered()) {
			return "redirect:" + Globals.adminPath + "/login";
		}
		return "system/SysIndex";
	}

	/**
	 * 引导到登录页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Byte isDialog, HttpServletRequest request, HttpServletResponse response, Model model) {
		String username = null;
		if (UserUtils.isRemembered()) {
			Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
			username = principal.getAccount();
		}
		if (username != null) {
			model.addAttribute(AuthenticationToken.username, username);
			model.addAttribute("isValidateCodeLogin", UserUtils.isValidateLoginCode(username, false));
		} else {
			model.addAttribute("isValidateCodeLogin", UserUtils.isValidateLoginCode(null, false));
		}
		if (isDialog != null && isDialog == BaseEntity.YES) {
			return "/system/SysLoginDialog";
		}

		// 显示原因
		String reason = WebUtils.getSafeParameter("reason");
		if (StringUtils.isNotBlank(reason)) {
			model.addAttribute("reason", Encodes.decodeBase64(reason, Constants.DEFAULT_ENCODING.toString()));
		}
		return "/system/SysLogin";
	}

	/**
	 * 登录错误才会走到这个页面
	 * 
	 * @param username
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(Byte isDialog, String username, String password, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute(AuthenticationToken.username, username);
		model.addAttribute(AuthenticationToken.password, password);
		model.addAttribute("isValidateCodeLogin", UserUtils.isValidateLoginCode(username, false));
		ErrorCode code = (ErrorCode) request.getAttribute(Constants.REQUEST_ERROR_CODE_PARAM);
		if (code != null) {
			model.addAttribute("error", code);
		}
		if (isDialog != null && isDialog == BaseEntity.YES) {
			return "/system/SysLoginDialog";
		}
		return "system/SysLogin";
	}

	/**
	 * 用户登录请求
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login/success", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/system/SysLoginSuccess";
	}

	/**
	 * 无权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "unauthorized")
	public String unauthorized() {
		return "/system/Unauthorized";
	}

	// 验证码服务
	private ValidateCodeService validateCodeService;

	/**
	 * 后端验证码服务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validate/code", method = RequestMethod.GET)
	public void validate_code(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (validateCodeService == null) {
			validateCodeService = new ValidateCodeService();
		}
		validateCodeService.doValidate(request, response);
	}
}
