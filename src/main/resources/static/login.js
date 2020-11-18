$(function(){
	
	console.log("LShangyi's Demo");
	
});

function doLogin(){
	var param = {};
	
	var username = $("#username").val();
	param.name = username;
	param.account = username;
	var password = $("#password").val();
	param.password = password;
	console.log("用户名称："+username + ",密码："+password);
	
	var path = "http://"+window.location.host;
	var flag = true;
	$.ajax({
		url: path + "/user/checkUserLogin",
		data:param,
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		async:false,
		success:function(result){
			if(result.code == "400"){
				alert("校验失败")
			}else if(result.code == "200"){
				if(result.object){
					flag = false;
					alert("检测到当前账号已登录，系统自动为您踢下线,请重新登录");
				}
			}
		}
	});
	
	if(flag){
		$.ajax({
			url: path + "/user/doLogin",
			data:param,
			type:"GET",
			contentType:"application/json",
			dataType:"json",
			async:true,
			success:function(result){
				if(result.code == "400"){
					alert("登录失败，检查一下用户名和密码吧")
				}else if(result.code == "200"){
					window.location.href = path + "/template.html?userName="+username;
				}
			}
		});
	}
}

function doRegister(){
	var param = {};
	
	var username = $("#username").val();
	param.name = username;
	param.account = username;
	var password = $("#password").val();
	param.password = password;
	console.log("用户名称："+username + ",密码："+password);
	
	var path = "http://"+window.location.host;
	
	$.ajax({
		url: path + "/user/insert",
		data:param,
		type:"GET",
		contentType:"application/json",
		dataType:"json",
		async:true,
		success:function(result){
			if(result == 1){
				alert("注册成功，返回登录吧");
				window.location.href = path + '/login.html';
			}
		}
	});
}