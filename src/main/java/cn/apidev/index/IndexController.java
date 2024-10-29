package cn.apidev.index;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Path;

import cn.apidev.base.ApidevBaseController;

/**
 * 首页
 * @author apidev.cn
 *
 */
@Path("/")
public class IndexController extends ApidevBaseController {

	public void index() {
		redirect("/apidev/api");
	}
	
	public void apidev() {
		redirect("/apidev/api");
	}
	
}
