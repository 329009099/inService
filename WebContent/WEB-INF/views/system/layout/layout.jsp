<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../../../common_resource.jsp" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/js/easyUI1.4.2/themes/default.css'/>">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
	var _menus;
	function initMenu(){
		$.ajax({
			type : 'POST',
			url : SITE_BASE_PATH+'/permission/getPerMenuList?',
			dataType : "json",
			async:false,
			success: function(data) {
				if(data){
					_menus=convertEasyUIParentChildTree(data.jsonData1);
				}
			}
		});
	}
	
	function exists(rows, parentId){
	    for(var i=0; i<rows.length; i++){
	        if (rows[i].id == parentId) return true;
	    }
	    return false;
	}

	function convertEasyUIParentChildTree(rows){
	    
	    var nodes = [];
	    // get the top level nodes
	    for(var i=0; i<rows.length; i++){
	        var row = rows[i];
	        if (!exists(rows, row.parentId)){
	            nodes.push({
	            	menuid:row.id,
	            	menuname:row.nodeName,
	            	url:row.moduleUrl,
	            	icon:"icon-nav"
	            });
	        }
	    }
	    
	    var toDo = [];
	    for(var i=0; i<nodes.length; i++){
	        toDo.push(nodes[i]);
	    }
	    while(toDo.length){
	        var node = toDo.shift();    // the parent node
	        // get the children nodes
	        for(var i=0; i<rows.length; i++){
	            var row = rows[i];
	            if (row.parentId == node.menuid){
	                var child = {menuid:row.id,menuname:row.nodeName,url:row.moduleUrl,icon:"icon-nav"};
	                if (node.menus){
	                    node.menus.push(child);
	                } else {
	                    node.menus = [child];
	                }
	                toDo.push(child);
	            }
	        }
	    }
	    var nodes_=[];
	    nodes_.push({
	    	menuid:0,
	    	menuname:"菜单",
	    	menus:nodes
	    });
	    return nodes_;
	}
	
   //初始化左侧
function InitLeftMenu() {
	initMenu();
	$("#nav").accordion({animate:false,fit:true,border:false});
	var selectedPanelname = '';
    $.each(_menus[0].menus, function(i, n) {
		var menulist ='';
		menulist +='<ul class="navlist">';
        $.each(n.menus, function(j, o) {
			menulist += '<li><div ><a ref="'+o.menuid+'" href="#" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div> ';

			if(o.child && o.child.length>0)
			{
				li.find('div').addClass('icon-arrow');

				menulist += '<ul class="third_ul">';
				$.each(o.child,function(k,p){
					menulist += '<li><div><a ref="'+p.menuid+'" href="#" rel="' + p.url + '" ><span class="icon '+p.icon+'" >&nbsp;</span><span class="nav">' + p.menuname + '</span></a></div> </li>'
				});
				menulist += '</ul>';
			}

			menulist+='</li>';
        })
		menulist += '</ul>';

		$('#nav').accordion('add', {
            title: n.menuname,
            content: menulist,
				border:false,
            iconCls: 'icon ' + n.icon
        });

		if(i==0)
			selectedPanelname =n.menuname;

    });
	$('#nav').accordion('select',selectedPanelname);



	$('.navlist li a').click(function(){
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = $(this).find('.icon').attr('class');

		var third = find(menuid);
		if(third && third.child && third.child.length>0)
		{
			$('.third_ul').slideUp();

			var ul =$(this).parent().next();
			if(ul.is(":hidden"))
				ul.slideDown();
			else
				ul.slideUp();
		}
		else{
			addTab(tabTitle,url,icon);
			$('.navlist li div').removeClass("selected");
			$(this).parent().addClass("selected");
		}
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});
   }

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
		var tab = $('#tabs').tabs('getSelected');
		$('#tabs').tabs('update', {
			tab: tab,
			options: {
				content:createFrame(url)
			}
		});
	}
	tabClose();
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}


function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}
function getDate(){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var week=['日','一','二','三','四','五','六'];
	$("#curdate").html('今日：'+year+'年'+month+'月'+day+'日 '+' 星期'+week[date.getDay()]);
}
function gotoEditPwd(){
	addTab("密码修改","<c:url value='sysUser/gotoEditPwd'/>","icon-edit");
}
$(function(){
	InitLeftMenu();
	getDate();
});
/**
 * 注销登录
 */
function cancel(){
	window.location.href="<c:url value='/login/loginOut'/>";
}
/**
 * 角色切换
 */
function changeRole(userRoleId){
	//window.location.href="<c:url value='/layout?userRoleId="+userRoleId+"'/>";
	$("input[name='userRoleId']").val(userRoleId);
	$("#roleForm").attr("action","<c:url value='/layout?'/>");
	$("#roleForm").submit();
}
/**
 * 设置默认角色
 */
function setDefaultRole(obj){
	$.ajax({
		type : 'POST',
		url : SITE_BASE_PATH+'/sysUser/setDefaultRole?',
		dataType : "json",
		async:false,
		success: function(data) {
			if(data.result){
				$.messager.alert("操作提示","设置成功!");
				$(obj).hide();
			}
		}
	});
}
</script>
  </head>
<body class="easyui-layout">
	<div data-options="region:'north',split:false" style="height: 100px;">
		<div id="top">
			<div id="logo">
				<img src="<c:url value='/resources/images/logo11.jpg'/>">
			</div>
			<div class="toptitle">通用权限框架</div>
			<div id="topRight">
				<ul>
					<li onclick="gotoEditPwd()"><img src="<c:url value='/resources/images/ico_top1.gif'/>"><a
						class="handlink">修改密码</a></li>
					<li><a class="handlink" onclick="cancel();"><img src="<c:url value='/resources/images/ico_top3.gif'/>" >注销</a></li>
					<li><img src="<c:url value='/resources/images/ico_top4.gif'/>">欢迎&nbsp;<strong>${loginUser.loginName }</strong></li>
				</ul>
				<div class="curdate">
					<form id="roleForm" method="post">
						<ul>
							<li>登录角色：
								<input type="hidden" name="userRoleId"/>
								<select name="role"  id="role" style="width:120px;" onchange= "changeRole(this.options[this.options.selectedIndex].value)">
									<c:forEach items="${roleList }" var="role">
										<c:if test="${role.is_default==1&&loginUser.userRoleId==role.user_role_id }"><c:set var="isDefault" value="true"/></c:if>
										<option value="${role.user_role_id }" <c:if test="${loginUser.userRoleId==role.user_role_id }">selected="selected"</c:if>>${role.roleName }</option>
									</c:forEach>
								</select>
							
							<c:if test="${!isDefault }">
								<a href="javascript:void(0);" onclick="setDefaultRole(this);" style="color:red;">置为默认角色?</a>
							</c:if>
							</li>
							<li id="curdate"></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div data-options="region:'south',split:false" style="height: 30px;">
		<div class="footer_">微信服务平台版权所有</div>
	</div>
	<div data-options="region:'west',title:'菜单',split:false"
		style="width: 150px;">
		<div id='nav' class="easyui-accordion" fit="true" border="false"
			href="<c:url value='/layout/menu'/>">
			<!--  导航内容 -->

		</div>
	</div>
	<div data-options="region:'center'"
		style="padding: 5px; background: #eee;">
		<div id="tabs" class="easyui-tabs" fit="true">
			<div title="欢迎使用" style="padding: 20px;">
				<div id="indexCon" class="indexCon"></div>
				<%-- <img src="<c:url value='/resources/images/welcome.gif'/>"> --%>
	<h2>
	简介
</h2>
<p>
	<ul>
		<li>
			<span style="line-height:1.5;">采用springMVC+mybatis+easyUI开发</span>
		</li>
		<li>
			全部采用注解方式
		</li>
		<li>
			基础权限完成对菜单的分配 登录的拦截 以及对ajax请求方式拦截&nbsp;
		</li>
	</ul>
	<p>
		<br />
	</p>
	<h2>
 技术交流
	</h2>
	<p>
		<ul>
			<li>
				后续采用bootstrap 全面使用主流的响应式布局
			</li>
			<li>
				代码生成器开发&nbsp;<span style="font-family:FONT;font-size:medium;line-height:normal;">freemarker模板语言生产</span>
			</li>
			<li>
				<span><span style="line-height:normal;">数据权限和HTML元素权限完善</span></span>
			</li>
			<li>
				<span><span style="line-height:normal;">通用模块编写</span></span>
			</li>
			<li>
				<span><span style="line-height:normal;">封装java通信的工具包</span></span>
			</li>
		</ul>
	</p>
</p>
			</div>
		</div>
	</div>
</body>
</html>
