package cn.apidev.share.ctrl;

import com.jfinal.aop.Inject;
import com.jfinal.core.Path;

import cn.apidev.base.ApidevBaseController;
import cn.apidev.share.service.ShareService;

/**
 * 分享
 * @author qinhailin
 *
 */
@Path("/apidev/share")
public class ShareController extends ApidevBaseController{

	@Inject
	ShareService shareService;
	
	public void index(){
		render("index.html");
	}
	
	public void list(){
		
	}
}
