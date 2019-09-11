javascript: if (window.location.href == 'http://112.111.2.107/Executive/Index/index') {
	/*var cook = document.cookie;*/
	var ownerDocument=document;
	var impForm="http://localhost:8080/fjdr/import.html";
	/*学生信息*/
	var stus = [];
	var stuKeys = {};
	/*所有学生信息地址*/
	var hrefs = [];
	importInit();
	function importInit(){
		var div = ownerDocument.createElement("div");
		div.innerHTML = '<iframe id="studentManage" name="studentManage"'+
		' src="http://112.111.2.107/Executive/Student/StudentManage"'+
		' height = "0" width = "0" frameborder="0" scrolling="auto" onload="initStu(this);"'+
		'></iframe>';
		ownerDocument.body.appendChild(div);
		/*初始化提示*/
		layer.open({
		     title:'信息初始化....',
		     shadeClose:false,
		     shade:false,
		     shade: 0.8,
		     area:['470px', '240px'],
		     closeBtn:0,
		     btn:[],
		     content:"获取学生基本信息中...."
		});
		/*10s后关闭提示*/
		setTimeout(function(){
			layer.close(1);
			convertStus(stus);
			$("[name=idFrameGrade]").parent().remove();
			$("[name=idFrameClasss]").parent().remove();
			$("[name=idFramePages]").parent().remove();
			messageEventListener();
			showImport();
		},10000);
	};
	function over(){
		/*初始化提示*/
		layer.open({
		     title:'信息导入中....',
		     shadeClose:false,
		     shade:false,
		     shade: 0.8,
		     area:['470px', '240px'],
		     closeBtn:0,
		     btn:[],
		     content:"导入信息中...."
		});
		/*10s后关闭提示*/
		setTimeout(function(){
			layer.close(2);
			$(".g-head").css("display","");
			$(".g-box").css("display","");
			$("#idFrameImp").css("display","none");
		},10000);
	};
	function messageEventListener(){
		window.addEventListener('message',function(e){
			var submitData=e.data;
			var submitDatas = [];
			for(var i=0;i<submitData.length;i++){
				Object.keys(submitData[i]).forEach(function(key){
					submitDatas = submitDatas.concat(submitData[i][key]);
				});
			};
			for(var i=0;i<submitDatas.length;i++){
				submitImpData(stuKeys[submitDatas[i].stuNo],submitDatas[i]);
			};
			over();
		},false);
	};
	function submitImpData(stuId,submitData){
		var satate =1;
		var url = '/Executive/Character/saveActive.html';
		var para = "caucus_times=" + (submitData.caucus_times || 0) + "&caucus_hours="
		+ (submitData.caucus_hours || 0 ) + "&volunt_times=" + (submitData.volunt_times || 0)
		+ "&volunt_hours=" + (submitData.volunt_hours || 0 ) + "&assoc_times="
		+ (submitData.assoc_times || 0 ) + "&assoc_hours=" + (submitData.assoc_hours || 0)
		+ "&welfare_times=" + (submitData.welfare_times || 0) + "&welfare_hours="
		+ (submitData.welfare_hours || 0) + "&satate=" + satate
		+ "&student_id="+stuId;
		getApiData(url, para, submit_result);
	};
	function submit_result(){
	};
	function initStu(initFrame){
		var years = initFrame.contentWindow.$.map(initFrame.contentWindow.$('#grade_year option'), function(e) { return e.value; });
		for(var i=0;i<years.length;i++){
			var div = ownerDocument.createElement("div");
			div.innerHTML = '<iframe id="idFrame_grade_'+years[i]+
			'" name="idFrameGrade" src="http://112.111.2.107/Executive/Student/StudentManage?grade_year='+years[i]+
			'" height = "0" width = "0" frameborder="0" scrolling="auto" '+
			' onload="createClasss(this,'+years[i]+');"></iframe>';
			document.body.appendChild(div);
		};
	};
	/*创建班级方法*/
	function createClasss(frame,year){
		var classs = frame.contentWindow.$.map(frame.contentWindow.$('#class_id option'), function(e) { return e.value; });
		for(var i=0;i<classs.length;i++){
			var div = ownerDocument.createElement("div");
			var ifSrc = 'http://112.111.2.107/Executive/Student/StudentManage?grade_year='+year+'&class_id='+classs[i];
			div.innerHTML = '<iframe id="idFrame-class-'+classs[i]+
			'" name="idFrameClasss" src="'+ifSrc+'" height = "0" width = "0" frameborder="0" scrolling="auto" '+
			' onload="createPage(this,'+year+','+classs[i]+');"></iframe>';
			ownerDocument.body.appendChild(div);
			hrefs.push(ifSrc);
		};
	};
	/*创建分页方法*/
	function createPage(frame,year,classs){
		var pages = frame.contentWindow.$('.ui-pages a');
		for(var i=0;i<pages.length;i++){
			var hs=frame.contentWindow.$(pages[i]).attr("href");
			if(hrefs.indexOf(hs)==-1){
			    var div = ownerDocument.createElement("div");
				div.innerHTML = '<iframe id="idFrame-page-'+i+
				'" name="idFramePages" src="'+hs+'" height = "0" width = "0" frameborder="0" scrolling="auto" '+
				' onload="getStus(this)"></iframe>';
				ownerDocument.body.appendChild(div);
				hrefs.push(hs);
			}
		};
		getStus(frame);
	};
	/*获取学生信息*/
	function getStus(frame){
		frame.contentWindow.$(".g-bgn tr").each(function(i){
			var stu = {};
			$(this).children('td').each(function(j){
				if(j==0){
					stu.id=$($(this).children('input')[0]).val();
				};
				if(j==3){
					stu.xj=$(this).html();
				};
			});
			if(stu.id){
				stus.push(stu);
			};
		});
	};
	/*数据转换*/
	function convertStus(stus){
		for(var i=0;i<stus.length;i++){
			stuKeys[stus[i].xj]=stus[i].id;
		}
	}
	function showImport(){
		$(".g-head").css("display","none");
		$(".g-box").css("display","none");
		var div = document.createElement("div");
		div.innerHTML = '<iframe id="idFrameImp" name="idFrame" src="'+impForm+'" '+
		'height = "440" width = "840" frameborder="0" scrolling="auto" '+
		'></iframe>';
		document.body.appendChild(div);
	}
}else{
	alert("不是初始化页面");
}