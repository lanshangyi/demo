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
				alert("登录成功，欢迎你，"+result.object.name+" 先生");
			}
		}
	});
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