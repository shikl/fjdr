javascript: if (window.location.href == 'http://112.111.2.107/executive/user/login.html') {
	window.login_result = function(rs) {
		if (rs.resultId == 1) {
			var cook = document.cookie; /*
										 * window.location.href =
										 * "/Executive/Index/index";
										 */
			window.location.href = "http://112.111.2.107/Executive/Student/StudentManage";
		} else {
			if (rs.resultId == 2) {
				window.location.href = "/Executive/Index/passmodisimple.html";
			} else {
				msg_pop(rs.resultMsg, '');
				var src = $('#code_box').find('img').attr('src') + '?v='
						+ Date.parse(new Date());
				$('#code_box').find('img').attr('src', src);
			}
		}
	}
}