BEGIN  
    EXECUTE IMMEDIATE 'CREATE TABLE APIDEV_API (  
        ID VARCHAR2(64) NOT NULL,  
        ACTION_KEY VARCHAR2(500),  
        DESCRIPTION VARCHAR2(255),  
        CONTROLLER VARCHAR2(255),  
        REMARK VARCHAR2(255),  
        TITLE VARCHAR2(255),  
        REQUEST_URL VARCHAR2(500),  
        REQUEST_MODE VARCHAR2(50),  
        REQUEST_HEADERS CLOB,  
        REQUEST_BODY_TYPE VARCHAR2(50),  
        REQUEST_PARAM CLOB,  
        REQUEST_PARAM_EXPLAIN CLOB,  
        REQUEST_PATH_EXPLAIN CLOB,  
        REQUEST_RESULT CLOB,  
        REQUEST_BODY CLOB,  
        REQUEST_BODY_EXPLAIN CLOB,  
        REQUEST_FORM_DATA CLOB,  
        SUCCESS_DEMO CLOB,  
        SUCCESS_DEMO_EXPLAIN CLOB,  
        FAILUER_DEMO CLOB,  
        FAILUER_DEMO_EXPLAIN CLOB,  
        REQUEST_REMARK VARCHAR2(255),  
        INTERFACE_STATUS VARCHAR2(50),  
        RESPONSE_HEADERS CLOB,  
        RESPONSE_DEFINITION CLOB,  
        RESPONSE_DEMO CLOB,  
        SORT NUMBER DEFAULT 1,  
        CREATE_BY VARCHAR2(100),  
        CREATE_TIME TIMESTAMP,  
        UPDATE_BY VARCHAR2(100),  
        UPDATE_TIME TIMESTAMP,  
        TYPE VARCHAR2(255),  
        PARENT_ID VARCHAR2(64) DEFAULT ''-1'',  
        DEL NUMBER DEFAULT 0,  
        VISIBLE NUMBER DEFAULT 1,  
        SHARE_ID VARCHAR2(255),  
        PASSWORD VARCHAR2(100),  
        EXPIRET_TIME DATE,  
        PRIMARY KEY (ID)  
    )';  
EXCEPTION  
    WHEN OTHERS THEN  
        IF SQLCODE != -955 THEN  
            RAISE;  
        END IF;  
END;  

COMMENT ON COLUMN APIDEV_API.ID IS '主键';
COMMENT ON COLUMN APIDEV_API.ACTION_KEY IS '接口路径';
COMMENT ON COLUMN APIDEV_API.DESCRIPTION IS '接口描述';
COMMENT ON COLUMN APIDEV_API.CONTROLLER IS '控制器';
COMMENT ON COLUMN APIDEV_API.REMARK IS '备注';  
COMMENT ON COLUMN APIDEV_API.TITLE IS '接口标题';  
COMMENT ON COLUMN APIDEV_API.REQUEST_URL IS '请求路径';  
COMMENT ON COLUMN APIDEV_API.REQUEST_MODE IS '请求方式';  
COMMENT ON COLUMN APIDEV_API.REQUEST_HEADERS IS '请求HEADERS参数';  
COMMENT ON COLUMN APIDEV_API.REQUEST_BODY_TYPE IS '请求的BODYTYPE类型';  
COMMENT ON COLUMN APIDEV_API.REQUEST_PARAM IS '请求QUERY参数';  
COMMENT ON COLUMN APIDEV_API.REQUEST_PARAM_EXPLAIN IS '请求QUERY参数说明';  
COMMENT ON COLUMN APIDEV_API.REQUEST_PATH_EXPLAIN IS '请求PATH参数说明';  
COMMENT ON COLUMN APIDEV_API.REQUEST_RESULT IS '请求结果，保存最新记录';  
COMMENT ON COLUMN APIDEV_API.REQUEST_BODY IS '请求BODY参数';  
COMMENT ON COLUMN APIDEV_API.REQUEST_BODY_EXPLAIN IS '请求BODY参数说明';  
COMMENT ON COLUMN APIDEV_API.REQUEST_FORM_DATA IS '请求FORM-DATA的参数';  
COMMENT ON COLUMN APIDEV_API.SUCCESS_DEMO IS '请求成功返回示例';  
COMMENT ON COLUMN APIDEV_API.SUCCESS_DEMO_EXPLAIN IS '请求成功返回示例参数说明';  
COMMENT ON COLUMN APIDEV_API.FAILUER_DEMO IS '请求失败返回示例';  
COMMENT ON COLUMN APIDEV_API.FAILUER_DEMO_EXPLAIN IS '请求失败返回示例参数说明';  
COMMENT ON COLUMN APIDEV_API.REQUEST_REMARK IS '请求接口文档备注';  
COMMENT ON COLUMN APIDEV_API.INTERFACE_STATUS IS '接口状态';  
COMMENT ON COLUMN APIDEV_API.RESPONSE_HEADERS IS '响应头';  
COMMENT ON COLUMN APIDEV_API.RESPONSE_DEFINITION IS '响应定义';  
COMMENT ON COLUMN APIDEV_API.RESPONSE_DEMO IS '响应用例';  
COMMENT ON COLUMN APIDEV_API.SORT IS '排序';  
COMMENT ON COLUMN APIDEV_API.CREATE_BY IS '创建人';  
COMMENT ON COLUMN APIDEV_API.CREATE_TIME IS '创建时间';  
COMMENT ON COLUMN APIDEV_API.UPDATE_BY IS '修改人';  
COMMENT ON COLUMN APIDEV_API.UPDATE_TIME IS '最新修改时间';  
COMMENT ON COLUMN APIDEV_API.TYPE IS 'MENU:目录，API:接口，DEMO：用例，LINK：快捷请求';  
COMMENT ON COLUMN APIDEV_API.PARENT_ID IS '父级ID（1:是接口根目录，2：是快捷请求根目录）';  
COMMENT ON COLUMN APIDEV_API.DEL IS '0：恢复正常状态，1：标记已删除，显示在回收站，2：标记下级数据被删除，不显示回收站';  
COMMENT ON COLUMN APIDEV_API.VISIBLE IS '接口是否可见(0:不可见，1：可见)';  
COMMENT ON COLUMN APIDEV_API.SHARE_ID IS '分享ID';  
COMMENT ON COLUMN APIDEV_API.PASSWORD IS '访问密码';  
COMMENT ON COLUMN APIDEV_API.EXPIRET_TIME IS '访问过期时间';

