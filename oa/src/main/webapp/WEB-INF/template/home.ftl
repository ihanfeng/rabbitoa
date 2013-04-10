<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script type="text/javascript">
		var cpath='${cpath}';
	</script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="zh_CN" />

	<title>Rabbit OA系统</title>

	<link rel="stylesheet" type="text/css" href="${cpath}/jquery_ui_layout/css/layout-default-latest.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="${cpath}/jquery_ztree/css/zTreeStyle/zTreeStyle.css" />
	<!-- CUSTOMIZE/OVERRIDE THE DEFAULT CSS -->
	<style type="text/css">

	/* remove padding and scrolling from elements that contain an Accordion OR a content-div */
	.ui-layout-center ,	/* has content-div */
	.ui-layout-west ,	/* has Accordion */
	.ui-layout-east ,	/* has content-div ... */
	.ui-layout-east .ui-layout-content { /* content-div has Accordion */
		padding: 0;
		overflow: hidden;
	}
	.ui-layout-center P.ui-layout-content {
		line-height:	1.4em;
		margin:			0; /* remove top/bottom margins from <P> used as content-div */
	}
	.ui-accordion,.ui-accordion-content{padding:0px !important;}
	h3, h4 { /* Headers & Footer in Center & East panes */
		background:		#EEF;
		border:			1px solid #BBB;
		border-width:	0 0 1px;
		padding:		7px 10px;
		margin:			0;
	}
	.ui-layout-east h4 { /* Footer in East-pane */
		font-weight:	normal;
		border-width:	1px 0 0;
	}
	.jquery-ui-themeswitcher-trigger
	{
		height:20px !important;
	}
	</style>

	<!-- REQUIRED scripts for layout widget -->
	<script type="text/javascript" src="${cpath}/jquery/jquery-1.8.0.js"></script>
	<script type="text/javascript" src="${cpath}/jquery_ui/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${cpath}/jquery_ui_layout/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="${cpath}/jquery_ui_layout/jquery.layout.resizePaneAccordions-latest.js"></script>
	<!-- compressed: /lib/js/jquery.layout.resizePaneAccordions-latest.min.js -->

    <script type="text/javascript" src="${cpath}/jquery_ui/themeswitchertool.js"></script>
    <script src="${cpath}/jquery_ztree/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script> 
	<script type="text/javascript">
	$(document).ready( function() {
		$('#rollBackTheme').button().click(function(){
			removeUITheme();
			myLayout.resizeAll();
		});
		myLayout = $('body').layout({
			west__size:			300
		,	west__togglerTip_closed:	"展开"
		,	west__togglerTip_open:	"关闭"
		,	west__sliderTip:			"展开"
		,	west__slideTrigger_open:	"mouseover"
		,	east__size:			300
		,	east__togglerTip_closed:	"展开"
		,	east__togglerTip_open:	"关闭"
		,	east__sliderTip:			"展开"
		,	east__slideTrigger_open:	"mouseover"
		,	north__togglerTip_closed:	"展开"
		,	north__togglerTip_open:	"关闭"
		,	north__sliderTip:			"展开"
		,	south__togglerTip_closed:	"展开"
		,	south__togglerTip_open:	"关闭"
		,	south__sliderTip:			"展开"
		, east__initClosed:     true
		,	west__onresize:		$.layout.callbacks.resizePaneAccordions
		,	east__onresize:		$.layout.callbacks.resizePaneAccordions
		});

		// ACCORDION - in the West pane
		$("#accordion1").accordion({
			heightStyle:	"fill"
		});
		
		// ACCORDION - in the East pane - in a 'content-div'
		$("#accordion2").accordion({
			heightStyle:	"fill"
		});


		// THEME SWITCHER
		addThemeSwitcher('.ui-layout-north',{ top: '12px', right: '5px' });
		// if a new theme is applied, it could change the height of some content,
		// so call resizeAll to 'correct' any header/footer heights affected
		// NOTE: this is only necessary because we are changing CSS *AFTER LOADING* using themeSwitcher
		setTimeout( myLayout.resizeAll, 1000 ); /* allow time for browser to re-render with new theme */
		
		$.fn.zTree.init($("#menus"), setting, treeNodes);
		$.fn.zTree.init($("#menus1"), setting, treeNodes);
	});
	//ztree
		var setting={
				expandSpeed: "200",
				showLine: true,
				fontCss: setFontCss,
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: -1
					}
				},
				callback: {
					beforeDrag: beforeDrag,
					onClick:zTreeOnClick
				},
				view: {
					showLine: false,
					dblClickExpand : false 
				}
			};
			function zTreeOnClick(event, treeId, treeNode)
			{
					var isOpen = treeNode.open;
					var zTree = $.fn.zTree.getZTreeObj("menus");
					if(!isOpen)
					{
						zTree.expandNode(treeNode, true, false, true);
					}
					else
					{
						zTree.expandNode(treeNode, false, false, true);
					}
			}
			function beforeDrag(treeId, treeNodes) {
				return false;
			}
			function setFontCss(treeId, treeNode) {
				if (treeNode.level==0 && treeNode.oldName && treeNode.oldName != treeNode.name) {
					return {"color":"#666","font-size":"14px","font-weight":"bold"};
				} else if (treeNode.level==0||treeNode.level==1) {
					return {"color":"#666","font-size":"14px","font-weight":"bold"};
				} else {
					return {"color":"#666","font-size":"14px"};
				}
			}
		var treeNodes = [
			<#if adminMenus?exists>
				<#list adminMenus as item>
					<#if !item_has_next>
						{"id":${item.id}, "pId":${item.pid}, "name":"${item.name}","url":"<#if item.url?exists>${cpath+item.url}<#else>javascript:void(0)</#if>","target":"${item.targettype}" <#if item.pid==-1>,isParent:true</#if>}
					<#else>
						{"id":${item.id}, "pId":${item.pid}, "name":"${item.name}","url":"<#if item.url?exists>${cpath+item.url}<#else>javascript:void(0)</#if>","target":"${item.targettype}" <#if item.pid==-1>,isParent:true</#if>},
					</#if>
				</#list>
			<#else>
				 {"id":-1, "pId":-1, "name":"该角色无菜单"}
			</#if>
		];
	</script>

</head>
<body>

<div class="ui-layout-north ui-widget-content ui-state-active" style="display: none;">
		<div style="float: right; margin-right: 160px;">
			<button id="rollBackTheme">默认样式</button>
		</div>
		<span style="font-size:30px;font-weight:bold">Rabbit OA</span>
</div>

<div class="ui-layout-south ui-widget-content ui-state-default" style="display: none;text-align:center"> 
	邮箱：286600136@qq.com 博客：https://zhmlvft-20120609.rhcloud.com/
</div>

<iframe id="mainFrame" name="mainFrame" class="ui-layout-center ui-widget-content"
	width="100%" height="600" frameborder="0" scrolling="auto"
	src="${cpath}/home/deskPage.html"  ALLOWTRANSPARENCY="true"></iframe>

<div class="ui-layout-west" style="display: none;">
	<div id="accordion1" class="basic">

			<h3><a href="#">功能列表</a></h3>
			<div>
				
			</div>

			<h3><a href="#">后台管理</a></h3>
			<div>
				<div id="menus" class="ztree"></div>
			</div>

			<h3><a href="#">我的消息</a></h3>
			<div>
				
			</div>
	</div>
</div>

<div class="ui-layout-east" style="display: none;">
	<div class="ui-layout-content">
		<div id="accordion2" class="basic">

			<h3><a href="#">功能列表</a></h3>
			<div>
				
			</div>

			<h3><a href="#">后台管理</a></h3>
			<div>
				<div id="menus1" class="ztree"></div>
			</div>

			<h3><a href="#">我的消息</a></h3>
			<div>
				
			</div>
		</div>
	</div>
</div>

</body>
</html> 