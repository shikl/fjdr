javascript: if (window.location.href.indexOf('http://112.111.2.107/Executive/Student/StudentManage')>-1) {
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
	var stus = [];
	var ownerDocument=document;
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
	var hrefs = [];
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
	setTimeout(function(){
		layer.close(1);
	},10000);
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
	var years = $.map($('#grade_year option'), function(e) { return e.value; });
	for(var i=0;i<years.length;i++){
		var div = document.createElement("div");
		div.innerHTML = '<iframe id="idFrame_grade_'+years[i]+
		'" name="idFrameGrade" src="http://112.111.2.107/Executive/Student/StudentManage?grade_year='+years[i]+
		'" height = "0" width = "0" frameborder="0" scrolling="auto" '+
		' onload="createClasss(this,'+years[i]+');"></iframe>';
		document.body.appendChild(div);
	};
	/*
	if(classs.length>0){
		$(".g-bgn")
	}else{
		for(var i=0;i<years.length;i++){
			$('#grade_year').trigger("change");
		};
	}*/
}else{
	alert("不是学生页面");
}