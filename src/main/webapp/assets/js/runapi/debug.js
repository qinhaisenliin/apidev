	//显示历史记录数据
	function createTableRow(id,jsonText,apiId){
		if(jsonText){
	    	try{
		    	var arr=JSON.parse(jsonText);
		    	if(arr.length>0)
		    		$("#"+id).children('tr').remove();
				for(var i in arr){
					var disable=arr[i].disable;
					var name=arr[i].name;
					var value=arr[i].value;
					var type=arr[i].type;
					var require=arr[i].require;
					var remark=arr[i].remark;
					
					if(!name)
						continue;
					
					if(id=='body_data_list_'+apiId)
						addBodyDataRow(name,type,require,remark,apiId);
					else if(id=='form_data_list_'+apiId)
						addFormDataRow(disable,name,type,value,require,remark,apiId);
					else if(id=='headers_data_list_'+apiId)
						addHeadersDataRow(disable,name,type,value,require,remark,apiId);
					else if(id=='query_data_list_'+apiId)
						addQueryDataRow(disable,name,type,value,require,remark,apiId);
					else if(id=='path_data_list_'+apiId)
						addPathDataRow(disable,name,type,value,require,remark,apiId);
					else if(id=='success_data_list_'+apiId)
						addSuccessDataRow(name,type,remark,apiId);
					else if(id=='failuer_data_list_'+apiId)
						addFailuerDataRow(name,type,remark,apiId);
				}
	        }catch(e){
				console.log(e)
	        }
	   	}
	}

        
    //textarea 自适应高度
    function autoHeight(ele, minHeight) {
        minHeight = minHeight || 16;
        if (ele.style.height) {
            ele.style.height = (parseInt(ele.style.height) - minHeight ) + "px";
        }
        ele.style.height = ele.scrollHeight + "px";
    }

    function isSelected(type,num){
    	if(type=='string'&&num==1)
    		return 'selected="selected"';
    	else if(type=='number'&&num==2)
    		return 'selected="selected"';
    	else if(type=='array'&&num==3)
    		return 'selected="selected"';
    	else if(type=='object'&&num==4)
    		return 'selected="selected"';
    	else if(type=='boolean'&&num==5)
    		return 'selected="selected"';
    	else if(type=='0'&&num==6)
        	return 'selected="selected"';
    	else if(type==true&&num==7)
        	return 'checked="checked"';
    	else
    		return '';
    }

    function addBodyDataRow(name,type,require,remark,apiId){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected3=isSelected(type,3);
        var	selected4=isSelected(type,4);
        var selected5=isSelected(type,5);
        var selected6=isSelected(require,6);
        var tr='<tr>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
            '                                    <td class="td-min">\n' +
            '                                        <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                          <option value="array" '+selected3+'>array</option>\n' +
            '                                          <option value="object" '+selected4+'>object</option>\n' +
            '                                          <option value="file">file</option>\n' +
            '                                          <option value="int">int</option>\n' +
            '                                          <option value="long">long</option>\n' +
            '                                          <option value="date">date</option>\n' +
            '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                    <td class="td-min1">\n' +
            '                                        <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                            <option value="1">必填</option>\n' +
            '                                            <option value="0" '+selected6+'>选填</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
            '                                    <td class="td-btn"><button class="add-tr-btn" onclick="addTr(this)" type="button"><i class="layui-icon layui-icon-addition"></i></button>\n'+
		    '                         				<button class="remove-btn" onclick="removeTr(this)" type="button"><i class="layui-icon layui-icon-subtraction"></i></button></td>\n' +
            '                                </tr>';
        $("#body_data_list_"+apiId).append(tr);
        addBtnHover();
    }

    function addFormDataRow(disabled,name,type,value,require,remark,apiId){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected3=isSelected(type,3);
        var	selected4=isSelected(type,4);
        var selected5=isSelected(type,5);
        var selected6=isSelected(require,6);
        var checked=isSelected(disabled,7)

        var tr='<tr>\n' +
            '                                    <td class="move-row form-data"><input type="checkbox" class="" lay-ignore  name="disable" value="1" '+checked+'/></td>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
            '                                    <td class="td-min">\n' +
            '                                        <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                          <option value="array" '+selected3+'>array</option>\n' +
            '                                          <option value="object" '+selected4+'>object</option>\n' +
            '                                          <option value="file">file</option>\n' +
            '                                          <option value="int">int</option>\n' +
            '                                          <option value="long">long</option>\n' +
            '                                          <option value="date">date</option>\n' +
            '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="value" value="'+value+'" placeholder="参数值"/></td>\n' +
            '                                    <td class="td-min">\n' +
            '                                        <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                            <option value="1">必填</option>\n' +
            '                                            <option value="0" '+selected6+'>选填</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
            '                                    <td class="td-btn"><button class="add-tr-btn" onclick="addTr(this)" type="button"><i class="layui-icon layui-icon-addition"></i></button>\n'+
		    '                         				<button class="remove-btn" onclick="removeTr(this)" type="button"><i class="layui-icon layui-icon-subtraction"></i></button></td>\n' +
            '                                </tr>';
        $("#form_data_list_"+apiId).append(tr);
        addBtnHover();
    }

    function addQueryDataRow(disabled,name,type,value,require,remark,apiId){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected5=isSelected(type,5);
        var selected6=isSelected(require,6);
        var checked=isSelected(disabled,7)
        var tr='<tr>\n' +
            '								   <td class="move-row"><input type="checkbox" class="" lay-ignore  name="disable" value="" '+checked+'/></td>\n'+
            '                                  <td><input type="text" class="form-data-param input-max" oninput="appendParaUrl(\''+apiId+'\')" onporpertychange="appendParaUrl(\''+apiId+'\')" name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                          <option value="int">int</option>\n' +
            '                                          <option value="long">long</option>\n' +
            '                                          <option value="date">date</option>\n' +
            '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max" oninput="appendParaUrl(\''+apiId+'\')" onporpertychange="appendParaUrl(\''+apiId+'\')" name="value" value="'+value+'" placeholder="参数值"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="1">必填</option>\n' +
            '                                          <option value="0" '+selected6+'>选填</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
            '                                    <td class="td-btn"><button class="add-tr-btn" onclick="addTr(this)" type="button"><i class="layui-icon layui-icon-addition"></i></button>\n'+
		    '                         				<button class="remove-btn" onclick="removeTr(this)" type="button"><i class="layui-icon layui-icon-subtraction"></i></button></td>\n' +
            '                              </tr>';
        $("#query_data_list_"+apiId).append(tr);
        addBtnHover();
    }
    
        function addPathDataRow(disabled,name,type,value,require,remark,apiId){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected5=isSelected(type,5);
        var selected6=isSelected(require,6);
        var checked=isSelected(disabled,7)
        var tr='<tr>\n' +
            '								   <td class="move-row"><input type="checkbox" class="" lay-ignore  name="disable" value="" '+checked+'/></td>\n'+
            '                                  <td><input type="text" class="form-data-param input-max" oninput="appendParaUrl(\''+apiId+'\')" onporpertychange="appendParaUrl(\''+apiId+'\')" name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                          <option value="int">int</option>\n' +
            '                                          <option value="long">long</option>\n' +
            '                                          <option value="date">date</option>\n' +
            '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max" oninput="appendParaUrl(\''+apiId+'\')" onporpertychange="appendParaUrl(\''+apiId+'\')" name="value" value="'+value+'" placeholder="参数值"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="1">必填</option>\n' +
            '                                          <option value="0" '+selected6+'>选填</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
            '                                    <td class="td-btn"></td>\n' +
            '                              </tr>';
        $("#path_data_list_"+apiId).append(tr);
        addBtnHover();
    }

    function addHeadersDataRow(disabled,name,type,value,require,remark,apiId){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected6=isSelected(require,6);
    	var checked=isSelected(disabled,7)
        var tr='<tr>\n' +
            '                                  <td><input type="checkbox" class="" lay-ignore  name="disable" value="1" '+checked+'/></td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="Headers名"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="value" value="'+value+'" placeholder="Headers值"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="1">必填</option>\n' +
            '                                          <option value="0" '+selected6+'>选填</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。Headers描述，便于生成文档"/></td>\n' +
            '                                  <td class="td-btn"><button class="add-tr-btn" onclick="addTr(this)" type="button"><i class="layui-icon layui-icon-addition"></i></button>\n'+
		    '                         				<button class="remove-btn" onclick="removeTr(this)" type="button"><i class="layui-icon layui-icon-subtraction"></i></button></td>\n' +
            '                              </tr>';
        $("#headers_data_list_"+apiId).append(tr);
        addBtnHover();
    }

    function addCookiesDataRow(apiId){
        var tr='<tr>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="name" value="" placeholder="cookies名"/></td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="value" value="" placeholder="cookies值"/></td>\n' +
            '                                  <td class="td-btn"><button class="add-tr-btn" onclick="addTr(this)" type="button"><i class="layui-icon layui-icon-addition"></i></button>\n'+
		    '                         				<button class="remove-btn" onclick="removeTr(this)" type="button"><i class="layui-icon layui-icon-subtraction"></i></button></td>\n' +
            '                              </tr>';
        $("#cookies_data_list_"+apiId).append(tr);
        addBtnHover();
    }

	function createTrHtml(name,type,remark){
		var selected1=isSelected(type,1);
	    var selected2=isSelected(type,2);
	    var selected3=isSelected(type,3);
	    var	selected4=isSelected(type,4);
	    var selected5=isSelected(type,5);
		var tr='<tr>\n' +
	     '                                  <td class="move-row"><i class="layui-icon layui-icon-template-1" title="可拖动排序"></i></td>\n' +
	     '                                  <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
	     '                                  <td class="td-min">\n' +
	     '                                      <select name="type" class="form-data-param input-max" lay-ignore>\n' +
	     '                                          <option value="string" '+selected1+'>string</option>\n' +
	     '                                          <option value="number" '+selected2+'>number</option>\n' +
	     '                                          <option value="array" '+selected3+'>array</option>\n' +
	     '                                          <option value="object" '+selected4+'>object</option>\n' +
	     '                                          <option value="int">int</option>\n' +
	     '                                          <option value="long">long</option>\n' +
	     '                                          <option value="date">date</option>\n' +
	     '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
	     '                                      </select>\n' +
	     '                                  </td>\n' +
	     '                                  <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
         '                                    <td class="td-btn"><button class="add-tr-btn" onclick="addTr(this)" type="button"><i class="layui-icon layui-icon-addition"></i></button>\n'+
		 '                         				<button class="remove-btn" onclick="removeTr(this)" type="button"><i class="layui-icon layui-icon-subtraction"></i></button></td>\n' +
	     '                              </tr>';
	     return tr;
	}

    function addSuccessDataRow(name,type,remark,apiId){
        var tr=createTrHtml(name,type,remark);
        $('#success_data_list_'+apiId).append(tr);
    }

    function addFailuerDataRow(name,type,remark,apiId){
        var tr=createTrHtml(name,type,remark);
        $("#failuer_data_list_"+apiId).append(tr);
    }

    function removeTr(obj){
        $(obj).parent().parent().remove();
    }

	function addTr(obj){
		// 获取当前行  
	    var tr = $(obj).closest('tr');   
	
	    // 克隆当前行  
	    var newRow = tr.clone();   
	
	    // 清空输入框和选择框的值  
	    newRow.find('input').val('');  
	    newRow.find('select').prop('selectedIndex', 0);  
	
	    // 将新行添加到当前行之后  
	    tr.after(newRow);  
	    addBtnHover();
	}
	
	function addBtnHover(){
		/*
		$('.add-tr-btn').hover(function(){
	        var title="插入行";
	        layer.tips(title, this,{
	            tips:1,
	            time:1000
	        });
	    },function(){
	    });
	     $('.remove-btn').hover(function(){
	        var title="删除行";
	        layer.tips(title, this,{
	            tips:1,
	            time:1000
	        });
	    },function(){
	    })
	    */
	}
      //鼠标拖拉tr上下移动
    function moveTableTr(tableBodyId) {
        $("#"+tableBodyId).sortable({
            cursor: "move",
            items: "tr",//只是tr可以拖动
            opacity: 1.0,//拖动时，透明度为0.6
            revert: false,//释放时，增加动画
            start: function(event, ui) {//开始移动
                let str = event.srcElement.innerHTML;//鼠标点击移动时获取的html内容
            },
            update: function(event, ui) {//移动之后更新排序之后
                let str = event.srcElement.innerHTML;
            }
        });
        $("#"+tableBodyId).disableSelection();
    }

    /**
     * 发送请求
     */
    function sendRequest(apiId){

		appendParaUrl(apiId);
        var url = $('#requestUrl_'+apiId).val();
        var method=$('#requestMode_'+apiId).val();
        var contentType=$('#body_type_'+apiId).val();
        if(url==''){
			$('#requestUrl_tips_'+apiId).show();
			return;
		}
        var bodyJson=$('#bodyJson_'+apiId).val();
        //form-data方式发起请求
        var data=getFormDataList(apiId);
        var pathData=getPathDataList(apiId);
        if(pathData){
			for (var key in pathData) {  
		       	url=url.replace('{'+key+'}', encodeURIComponent(pathData[key] || ''));
		    }  
		}
		$('#req_msg_method_'+apiId).html(method);
		$('#req_msg_method_'+apiId).addClass(method);
		$('#req_msg_content_type_'+apiId).html(contentType);
		//json方式发起请求
		if(contentType=='json'||contentType=='raw'||contentType=='xml'){
        	data=bodyJson;
        }
        // 标记发送状态
        apiIdSendReq[apiId] = true;
        showAndHideResponse(apiId,false);
        
		var startTime;
		var origin=window.location.origin;
		var str=url.split("/");
		var host=str[0]+"//"+str[2];
		if(origin==host||str[0]==""){
			 $('#req_msg_body_json_'+apiId).val(formatJson(JSON.stringify(data)));
			 $('#req_msg_url_'+apiId).html(origin+url);
			 $.ajax({
		            type: method,
		            url: url,
		            data:data,
		            xhrFields: {
		                withCredentials: true,// 允许携带cookie
		            },
		            beforeSend: function(request) {
		            	setRequestCookies(apiId);
		                setRequestHeaders(request,contentType,apiId);
		                startTime=new Date().getTime();
		            },
		            complete: function(xhr,data){
		            	var endTime=new Date().getTime();
		            	var time=endTime-startTime;
		            	$('#response_info_'+apiId).removeClass('layui-hide');
		            	$('#status_'+apiId).html(xhr.status);
		            	
		            	if(time>1000)
		            		time=time/1000 + ' s';
		            	else
		            		time+=' ms';
		            	$('#time_'+apiId).html(time);
		            	
		            	var contentLength = xhr.getResponseHeader('Content-Length'); 
						$('#size_'+apiId).html(contentLength);
						
		                $('#responseHeaders_'+apiId).val(xhr.getAllResponseHeaders());

		            	var contentType=xhr.getResponseHeader("content-type");
		                setResponse(xhr.responseText,contentType,apiId);
		            }
		        });
		}else{
			var body={
					"url":url,
					"body":bodyJson,
					"queryPara":getFormDataList(apiId),
					"header":getHeaders(apiId),
					"method":method,
					"contentType":contentType,
					"cookies":setRequestCookies(apiId)
			};
			 $('#req_msg_url_'+apiId).html(origin+_path+"/apidev/api/sendRequest");
			 $('#req_msg_body_json_'+apiId).val(formatJson(JSON.stringify(body)));
			 $.ajax({
		            type: 'post',
		            url: _path+'/apidev/api/sendRequest',
		            data:JSON.stringify(body),
		            dataType:'json',
		            contentType:"application/json",
		            timeout:30000, //设置超时的时间30s
		            beforeSend: function(request) {
		                startTime=new Date().getTime();
		            },
		            complete: function(xhr,data){
		            	var endTime=new Date().getTime();
		            	var time=endTime-startTime;
		            	$('#response_info_'+apiId).removeClass('layui-hide');
		            	$('#status_'+apiId).html(xhr.status);

		            	var json=xhr.responseJSON;
		            	if(json&&'fail'==json.state){
		            		var msg=json.msg;
		            		if(msg.indexOf('FileNotFoundException')!=-1||msg.indexOf('java.net.ConnectException')!=-1
		            				||msg.indexOf('connect timed out'!=-1))
		            			$('#status_'+apiId).html("404");
		            		if(msg.indexOf('Server returned HTTP response code: 500')!=-1)
		            			$('#status_'+apiId).html("500");
		            	}
		            	if(time>1000)
		            		time=time/1000 + ' s';
		            	else
		            		time+=' ms';
		            	$('#time_'+apiId).html(time);

						var contentLength = xhr.getResponseHeader('Content-Length'); 
						$('#size_'+apiId).html(contentLength);
						
		                $('#responseHeaders_'+apiId).val(xhr.getAllResponseHeaders());

		            	var contentType=xhr.getResponseHeader("content-type");
		                setResponse(xhr.responseText,contentType,apiId);
		            }
		        });
		}
    }

    function getFormDataList(apiId){
    	var formData = {};
    	$("#form_data_list_"+apiId).find("tr").each(function(){
	        var tdArr = $(this).children();
	    	var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var formDataName = tdArr.eq(1).find("input").val(); //  参数名
	        var formDataValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled&&formDataName){
				formData[formDataName]=formDataValue;
			}
	    });
	    return formData;
    }
    
    function setRequestCookies(apiId){
		var cookies="";
    	$("#cookies_data_list_"+apiId).find("tr").each(function(){
	        var tdArr = $(this).children();
	        var cookiesName = tdArr.eq(0).find("input").val(); //  参数名
	        var cookiesValue = tdArr.eq(1).find("input").val();//  参数值
			if(cookiesName&&cookiesValue){
				setCookie(apiId,cookiesName,cookiesValue);
				cookies+=cookiesName+"="+cookiesValue+";"
			}
	    });
	    return cookies;
    }

   //写cookies
    function setCookie(name,value) {
        var Days = 1;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
    }
    
    function setRequestHeaders(request,contentType,apiId){
    	if(contentType=='form-data')
    		request.setRequestHeader("Content-type","multipart/"+contentType+"; boundary=apidevcn; charset=utf-8");
    	else if(contentType == 'raw')
    		request.setRequestHeader("Content-type","text/plain; charset=utf-8");
    	else
    		request.setRequestHeader("Content-type","application/"+contentType+"; charset=utf-8");
    	var cookies=setRequestCookies(apiId);
    	$('#req_msg_header_'+apiId).children('tr').remove();
    	if(cookies){
			createReqMsgHeader(apiId,'Cookie',cookies);
		}
    	$("#headers_data_list_"+apiId).find("tr").each(function(){
	        var tdArr = $(this).children();
	        var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var headerName = tdArr.eq(1).find("input").val(); //  参数名
	        var headerValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled&&headerName&&headerValue){
				 request.setRequestHeader(headerName,headerValue);
				 createReqMsgHeader(apiId,headerName,headerValue);
			}
	    });

    }
    
    function createReqMsgHeader(apiId,name,value){
		const contentRow = `  
		 <tr>  
		 <td>${name}</td>  
		 <td>${value}</td>  
		 </tr>`; 
		$('#req_msg_header_'+apiId).append(contentRow);		           	
	}
    
    function getHeaders(apiId){
    	var headers={};
    	$("#headers_data_list_"+apiId).find("tr").each(function(){
	        var tdArr = $(this).children();
	        var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var headerName = tdArr.eq(1).find("input").val(); //  参数名
	        var headerValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled&&headerName&&headerValue){
				 headers[headerName]=headerValue;
			}
	    });
    	return headers;
    }

    //处理请求结果
    function setResponse(resText,contentType,apiId){
        if(contentType&&contentType.indexOf("application/json")!=-1){
        	var resJson={};
            try{
            	resJson=JSON.parse(resText);
            	$('#responseBody1_'+apiId).hide();
    	       	$('#responseBody1_'+apiId).val(JSON.stringify(resJson,null,4));
    		   	$('#responseBodyJson_'+apiId).show();
    	       	$('#responseBodyJson_'+apiId).jsonViewer(resJson);
    	       	$('#responseBody2_'+apiId).val(resText);
            }catch(e){
            	$('#responseBodyJson_'+apiId).hide();
            	$('#responseBody1_'+apiId).show();
                $('#responseBody1_'+apiId).val(resText);
                $('#responseBody2_'+apiId).val(resText);
            }
	       	
        }else{
        	$('#responseBodyJson_'+apiId).hide();
        	$('#responseBody1_'+apiId).show();
            $('#responseBody1_'+apiId).val(resText);
            $('#responseBody2_'+apiId).val(resText);
        }

        //美化
        autoHeight(document.getElementById('responseBody1_'+apiId),100)

        //原生
        document.getElementById('responseBody1_'+apiId).style.maxHeight="300px";

     	// 预览
        reviewData(apiId)
    }

 	// 预览
	function reviewData(apiId){
		var _iframe = document.getElementById('response_frame_'+apiId).contentWindow;
	    var _div =_iframe.document.getElementById('responseBody3_'+apiId);
		var requestResult=$('#responseBody2_'+apiId).val();
		try{
			var resJson=JSON.parse(requestResult);
	    	_div.innerHTML='<pre>'+JSON.stringify(resJson,null,4)+'</pre>';
		}catch(e){
	    	_div.innerHTML=requestResult;
		}

	}
	
    //追加query参数追加到url中
    function appendParaUrl(apiId){
        var url = $('#requestUrl_'+apiId).val();
        url=url.split("?")[0];
		$("#query_data_list_"+apiId).find("tr").each(function(){
	        var tdArr = $(this).children();
	        var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var paraName = tdArr.eq(1).find("input").val(); //  参数名
	        var paraValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled && paraName){
				paraName+="=";
				if(url.indexOf("?")!=-1){
	                url+="&"+paraName+paraValue;
		        }else{
					url+='?';
					url+=paraName+paraValue;
		        }
			}
	    });
		$('#requestUrl_'+apiId).val(url);
		return url;
    }

    //从url中输入query参数生成参数列表
    function createQueryDataRow(apiId){
    	var url=$('#requestUrl_'+apiId).val();
    	if(url.split("?").length>1){
    		$("#query_data_list_"+apiId).children('tr').remove();
	    	var paras=url.split("?")[1];
			var queryParam=paras.split('&');
			for(var i in queryParam){
				var arr=queryParam[i].split('=');
				if(arr[0])
					addQueryDataRow(true,arr[0],'string',arr[1]||'','','',apiId);
			}
			
        }
        
		createPathDataRow(apiId); 	
    }
    
    // 从url中生成path参数变量
    function createPathDataRow(apiId){
	    var url=$('#requestUrl_'+apiId).val();
	 	// 提取变量  
		const variables = extractVariables(url);  
		if (variables.length > 0) {  
		   $("#path_data_list_"+apiId).children('tr').remove();
			for(var i in variables){
				var pkey=variables[i];
				if(pkey){
					addPathDataRow(true,pkey,'string','','','',apiId);
					$('.path-data-'+apiId).removeClass('layui-hide');
				}
			}
		} else {
			$("#path_data_list_"+apiId).children('tr').remove();
			$('.path-data-'+apiId).addClass('layui-hide');
		}
	}
	
	// 获取path参数变量
	function getPathDataList(apiId){
		var pathData = {};
    	$("#path_data_list_"+apiId).find("tr").each(function(){
	        var tdArr = $(this).children();
	    	var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var pathDataName = tdArr.eq(1).find("input").val(); //  参数名
	        var pathDataValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled&&pathDataName){
				pathData[pathDataName]=pathDataValue;
			}
	    });
	    return pathData;
	}

	function extractVariables(apiPath) {  
	    // 使用正则表达式匹配大括号中的内容  
	    const pattern = /\{(.*?)\}/g;  
	    const variables = [];  
	    let match;  
	
	    // 使用 exec 方法查找所有匹配  
	    while ((match = pattern.exec(apiPath)) !== null) {  
	        variables.push(match[1]); // match[1] 中包含变量名  
	    }  
	
	    return variables;  
	}  



  	//美化json数据
    function formatSuccessJson(apiId){
        var jsonText=$('#successDataJson_'+apiId).val();
        try {
            var json=JSON.parse(jsonText);
        }catch(e){
            layer.tips(e, '#formatSuccessJson_'+apiId,{
                time:4000,
                tips:3
            });
        }
        var jsonStr=JSON.stringify(JSON.parse(jsonText),null,4);
        $('#successDataJson_'+apiId).val(jsonStr);
    }

	

	// 美化失败示例的json数据
    function formatFailuerJson(apiId){
        var jsonText=$('#failuerDataJson_'+apiId).val();
        try {
            var json=JSON.parse(jsonText);
        }catch(e){
            layer.tips(e, '#formatFailuerJson_'+apiId,{
                time:4000,
                tips:3
            });
        }
        var jsonStr=JSON.stringify(JSON.parse(jsonText),null,4);
        $('#failuerDataJson_'+apiId).val(jsonStr);
    }

 	// 美化请求body的json数据
    function formatBodyJson(apiId){
        var jsonText=$('#bodyJson_'+apiId).val();
        try {
            var json=JSON.parse(jsonText);
        }catch(e){
            layer.tips(e, '#formatBodyJson_'+apiId,{
                time:4000,
                tips:3
            });
        }

        var jsonStr=formatJson(jsonText);
        $('#bodyJson_'+apiId).val(jsonStr);
    }
    
    function formatJson(jsonText){
		return JSON.stringify(JSON.parse(jsonText),null,4);
	}


	//复制数据到成功返回示例中
    function copySuccessJson(apiId){
        var json=$('#responseBody1_'+apiId).val();
        $('#successDataJson_'+apiId).val(json);
    }

    //复制数据到失败返回示例中
    function copyFailuerJson(apiId){
        var json=$('#responseBody1_'+apiId).val();
        $('#failuerDataJson_'+apiId).val(json);
    }
    
 	//记录已生成的参数，避免重复生成
    var bodyRow={};
    var successRow={};
    var failueRow={};

    function clearRowData(){
    	bodyRow={};
    	successRow={};
    	failueRow={};
    }
    
 	//生参数列表，回填历史数据 arr历史数据
    function addOrUpdateDataRow(id,arr,key,type,apiId){
		var name='';
		var remark='';
		var require=1;
		//查找匹配参数，回显说明信息
    	for(var i in arr){ 
    		name=arr[i].name;           			
			if(name==key){
				remark=arr[i].remark;
				type=arr[i].type;
				require=arr[i].require;
				break;
			}
		}
	
		if(id=='failuer_data_list_'+apiId && !failueRow[key]){
			addFailuerDataRow(key,type,remark,apiId);
			failueRow[key]=true;
		}else if(id=='success_data_list_'+apiId && !successRow[key]){
			addSuccessDataRow(key,type,remark,apiId);
			successRow[key]=true;
	 	}else if(id=='body_data_list_'+apiId && !bodyRow[key]){
			addBodyDataRow(key,type,require,remark,apiId);
			bodyRow[key]=true;	
	    }
 	}

    //    递归解析对象参数,obj当前对象数据，arr历史数据
    function loadObjectPara(obj,parentKey,id,arr,apiId){
    	if(obj.constructor==Array){
    		addOrUpdateDataRow(id,arr,parentKey,"array",apiId)
			for (var i = 0, l = obj.length; i < l; i++) {
				for(var key in obj[i]){
					addOrUpdateDataRow(id,arr,parentKey+'.'+key,typeof(obj[key]),apiId)
					if(obj[i][key] != null && typeof(obj[i][key]) == 'object'){
						loadObjectPara(obj[i][key],parentKey+'.'+key,id,arr,apiId);
					}
				}
			}
		}else if(obj.constructor==Object){
			addOrUpdateDataRow(id,arr,parentKey,"object",apiId)
			for (var key in obj) {
				addOrUpdateDataRow(id,arr,parentKey+'.'+key,typeof(obj[key]),apiId)
				if(obj[key] != null && typeof(obj[key])=='object'){
					loadObjectPara(obj[key],parentKey+'.'+key,id,arr,apiId);
				}
			}
		}
    }

    //一键清空
    function removeTrAll(tableBodyId){
        layer.confirm("确认要清空所有返回参数说明吗" ,{icon: 3, title:'提示'},function(index){
            $("#"+tableBodyId).children('tr').remove();
            layer.close(index);
        });
    }

    
    //获取表格行json数据
    function getTableJson(id){
        var arr=[];        
    	$("#"+id).find("tr").each(function(){
	        var tdArr = $(this).children();
	        var rowJson={};
	        for(var i=0;i<tdArr.length;i++){
		        var disabled = tdArr.eq(i).find("input:checkbox").prop('checked'); //  是否有效
	        	var name=tdArr.eq(i).find("input").attr('name');
	        	if(name==undefined)
	        		name=tdArr.eq(i).find("select").attr('name');
	        	
        		var value;
        		
		        if(disabled==true||disabled==false){
		        	value=disabled
			    }else{
		        	value=tdArr.eq(i).find("input").val();
		        	if(value==undefined)
		        		value=tdArr.eq(i).find('select').val();
				}
		        //参数名为空，则不保存该行数据
		        if(name=='name'&&value==''){
		        	rowJson={};
		        	break
		        }
        		if(name)
	        		rowJson[name]=value;
		    }
	        
	        if("{}" != JSON.stringify(rowJson))
	        	arr.push(rowJson)			
	    });
	    return JSON.stringify(arr);

    }
    
    
	
	// 显示隐藏响应信息
	function showAndHideResponse(apiId,isClick){
		  var $responseRows = $('.response-row-'+apiId);  
		  var $sresizableRow = $('.resizable-row-'+apiId);
		  var $apiTabBody=$('#apiTabBody'+apiId);
		  var $responseTitle = $('.resopnse-title-'+apiId);
		  var $titleIcon=$('.title-icon-'+apiId);
		  var $responseTab=$('.response-tab-'+apiId)
		  var $responseInfo=$('#response_info_'+apiId);
		  
		  if ($responseRows.hasClass('layui-show') && isClick ||(!apiIdSendReq[apiId] && apiClick[apiId]%2==0)) {  
			  	$apiTabBody.height("calc(100vh - 160px)");
		    	$responseRows.removeClass('layui-show');  
		    	$responseTitle.addClass('line-height');
		    	$sresizableRow.addClass("bottom");
		    	$titleIcon.addClass('layui-icon-up').removeClass("layui-icon-down");
		    	$responseTab.hide();
		    	$responseInfo.hide();
		  } else {
				$apiTabBody.height("440px");
		    	$sresizableRow.removeClass("bottom");
		    	$responseTitle.removeClass('line-height');
		    	$titleIcon.addClass("layui-icon-down").removeClass('layui-icon-up');
		    	if(apiIdSendReq[apiId]){
		    		$responseRows.addClass('layui-show'); 
			    	$responseTab.show();
			    	$responseInfo.show();
		    	}
		  }  

		apiClick[apiId]=apiClick[apiId]+1;
   }