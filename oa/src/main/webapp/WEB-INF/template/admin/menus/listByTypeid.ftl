<#include "/common/layout/common.ftl"/>
<script type="text/javascript">
			var log, className = "dark";
			var newCount = 1;
			var setting={
					expandSpeed: "200",
					showLine: true,
					fontCss: setFontCss,
					view: {
						selectedMulti: false
					},
					check: {
						enable: true,
						chkStyle: "checkbox",
						chkboxType: { "Y": "ps", "N": "ps" }
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: -1
						}
					}
			};
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
				<#list menus as item>
					<#if !item_has_next>
						{"id":${item.id}, "pId":${item.pid}, "name":"${item.name}"<#if item.checked==1>,"checked":true</#if> }
					<#else>
						{"id":${item.id}, "pId":${item.pid}, "name":"${item.name}"<#if item.checked==1>,"checked":true</#if> },
					</#if>
				</#list>
			];
			$(function(){
				$.fn.zTree.init($("#menus"), setting, treeNodes);
				var treeObj = $.fn.zTree.getZTreeObj("menus");
				treeObj.expandAll(true);
			});
			
</script>
<style type="text/css">
	.ui-widget-content{padding:0px;overflow:hidden;}
	.ui-button-text{padding-top:0px !important;padding-bottom:0px !important;};
</style>
</head>
<body>
	<div class="ui-layout-center">
		<div class="ui-layout-content ui-widget-content">
			<div class="ztree" id="menus"></div>
		</div>
	</div>
</body>
</html>
