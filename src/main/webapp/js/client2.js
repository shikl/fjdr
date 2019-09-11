javascript:if(window.location.href == 'http://112.111.2.107/Executive/Character/ActiveDataAllList'){
	$(".g-head").css("display","none");
	$(".g-box").css("display","none");
	var div = document.createElement("div");
	div.innerHTML = '<iframe id="idFrame" name="idFrame" src="http://localhost:8080/fjdr/import.html" '+
	'height = "440" width = "840" frameborder="0" scrolling="auto" '+
	'></iframe>';
	document.body.appendChild(div);
}