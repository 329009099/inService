<%@page import="com.suyin.nouser.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common_resource.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<div class="easyui-panel" title="测试注册"   style="padding:10px;">
		<div style="padding:10px 10px 10px 10px">
	    <form id="dataForm"  method="post">
	    	<table cellpadding="3">

	    		<tr>
	    			<td>手机号码:</td>
	    			<td><input type="text" style="width:300px;height:30px;"  id="userPhone" name="userPhone" value=""/></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input type="text" style="width:300px;height:30px;"   name=userPassword  value=""/></td>
	    		</tr>
	    		<tr>
	    			<td>验证码:</td>
	    			<td><input type="text" style="width:220px;height:30px;"name="code"  id="code"  value=""/>
	    				<input type="button" value="获取验证码" id="yzm"/>
	    			</td>
	    		</tr>
	    		
	    		 <tr>
	    			<td>平台类型:</td>
	    			<td><input type="text" style="width:220px;height:30px;"name="regtype"    value=""/>
	    			</td>
	    		</tr>
	    		
	    		 <tr>
	    			<td>客户端版本:</td>
	    			<td><input type="text" style="width:220px;height:30px;"name="version"    value=""/>
	    			</td>
	    		</tr>
	    		<tr>
	    		<td colspan="2" align="center"> <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px;" onclick="zc()">测试注册</a></td>
				<td colspan="2" align="center"> <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px;" onclick="dl()">测试登录</a></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	
<div class="easyui-panel" title="找回密码"   style="padding:10px;">
		<div style="padding:10px 10px 10px 10px">
	    <form id="dataForm1"  method="post">
	    	<table cellpadding="3">

	    		<tr>
	    			<td>手机号码:</td>
	    			<td><input type="text" style="width:300px;height:30px;"  id="userPhone1" name="userPhone" value=""></input>
	    				<input type="button" value="验证手机号" id="yznumber" />
	    			</td>
	    		</tr>

	    		<tr>
	    			<td>验证码:</td>
	    			<td><input type="text" style="width:220px;height:30px;"name="code"  id="code1"  value=""></input>
	    				<input type="button" value="获取验证码" id="yzm1"/>
	    			</td>
	    		</tr>

	    		<tr>
	    	<td colspan="2" align="center"> <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:30px;" onclick="backpwd()">找回密码</a></td>
					
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	<script>
	
	function dl(){
		
		var	url="<c:url value='/nouser/login'/>"; 
		$.ajax({
			type : 'POST',
			url : url,
			data : $("#dataForm").serialize(),
			dataType : "json",
			success: function(data) {
				alert(data.message);

            } 
		});
	}
	
	function zc(){
			
		var	url="<c:url value='/nouser/regNouser'/>";
// 		var	url="<c:url value='/nouser/backpwd'/>"; 
// 			var isValid = $("#dataForm").form('validate');
// 			if(!isValid){
// 				return false;
// 			}
			$.ajax({
				type : 'POST',
				url : url,
				data : $("#dataForm").serialize(),
				dataType : "json",
				success: function(data) {
					alert(data.message);
 
	            } 
			});
		}
	
	//找回密码 
	function backpwd(){
		

		var	url="<c:url value='/nouser/backpwd'/>"; 
			$.ajax({
				type : 'POST',
				url : url,
				data : $("#dataForm1").serialize(),
				dataType : "json",
				success: function(data) {
					
					alert(data.message);
 
	            } 
			});
	}
		
		
		$(function(){
			//注册获取验证码 
		var	url="<c:url value='/nouser/requestNumber'/>"; 
			$("#yzm").bind("click",function(){
				var number=$("#userPhone").val()
				if(number.length<1){
					alert("手机号码不能为空 ！");
					return false;
				}
				var code=$("#code").val();

				$.ajax({
					type : 'POST',
					url : url,
					data : {"userPhone":number,"code":code},
					dataType : "json",
					success: function(data) {
						alert(data.message);
	     
		            } 
				});
				
			});
			//找回密码 验证码 
			var	url1="<c:url value='/nouser/backCodeNumber'/>"; 
			$("#yzm1").bind("click",function(){
	
				var number=$("#userPhone1").val()
				if(number.length<1){
					alert("手机号码不能为空 ！");
					return false;
				}
				var code=$("#code1").val();

				$.ajax({
					type : 'POST',
					url : url1,
					data : {"userPhone":number,"code":code},
					dataType : "json",
					success: function(data) {
				
						alert(data.message);
	     
		            } 
				});
				
			});
		
		var	url2="<c:url value='/nouser/validUserInfo'/>"; 
		$("#yznumber").bind("click",function(){
		
			var number=$("#userPhone1").val()
			if(number.length<1){
				alert("手机号码不能为空 ！");
				return false;
			}
			$.ajax({
				type : 'POST',
				url : url2,
				data : {"userPhone":number},
				dataType : "json",
				success: function(data) {
			
					alert(data.message);
     
	            } 
			});
			
		});
		});
	</script>
</body>
</html>