package cn.apidev.share.service;

import cn.apidev.base.ApidevBaseService;

/**
 * 分享
 * @author qinhailin
 *
 */
public class ShareService extends ApidevBaseService{

	private static final String tableName="apidev_share";
	@Override
	protected String getTableName() {
		return tableName;
	}

}
