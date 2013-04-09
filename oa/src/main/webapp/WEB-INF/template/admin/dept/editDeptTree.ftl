<#include "/common/layout/common.ftl"/>
<script type="text/javascript">
      function zTreeBeforeRemove(treeId, treeNode) {
				if(confirm("确认要删除该节点吗？删除后该目录下的子菜单都会删除！"))
				{
					if(confirm("确认要这么做吗？"))
					{
						$.ajax({
								type:'POST',
								url:'${cpath}/deptManager/delMenus.html',
								data:'infoid='+treeNode.id,
								error:function(msg)
								{
									asyncbox.alert("请求错误！","确认信息",function(msg){return;})
								},
								success:function(msg)
								{
									asyncbox.alert("删除成功！","确认信息",function(msg){return true;})
								}
							});
							return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			function zTreeBeforeRename(treeId, treeNode, newName) {
				asyncbox.confirm("确认要将该节点的名字修改成"+newName+"吗？","确认信息",function(action){
					if(action=='ok')
					{
						$.ajax({
							type:'POST',
							url:'${cpath}/deptManager/modMenus.html',
							data:'infoid='+treeNode.id+"&name="+encodeURI(newName),
							error:function(msg)
							{
								asyncbox.alert("请求错误！","确认信息",function(msg){return;})
							},
							success:function(msg)
							{
								asyncbox.alert("修改成功！","确认信息",function(msg){return;})
							}
						});
					}
				});
			}
			var log, className = "dark";
			var newCount = 1;
			var setting={
					expandSpeed: "200",
					showLine: true,
					fontCss: setFontCss,
					view: {
						selectedMulti: false
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: 0
						}
					},
					callback: {
						beforeDrag: beforeDrag,
						beforeEditName: beforeEditName,
						beforeRemove: zTreeBeforeRemove,
						beforeRename: zTreeBeforeRename
					}
			};
			function beforeDrag(treeId, treeNodes) {
				return false;
			}
			function beforeEditName(treeId, treeNode) {
				className = (className === "dark" ? "":"dark");
				var zTree = $.fn.zTree.getZTreeObj("menus");
				zTree.selectNode(treeNode);
				return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
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
			function setEdit() {
				var zTree = $.fn.zTree.getZTreeObj("menus"),
				remove = true,
				rename = true,
				removeTitle = "删除节点",
				renameTitle = "修改节点";
				zTree.setting.edit.showRemoveBtn = remove;
				zTree.setting.edit.showRenameBtn = rename;
				zTree.setting.edit.removeTitle = removeTitle;
				zTree.setting.edit.renameTitle = renameTitle;
			}
			var treeNodes = [
				<#list depts as item>
					<#if !item_has_next>
						{"id":${item.id}, "pId":<#if item.pid!='NULL'>${item.pid}<#else>0</#if>, "name":"${item.name}" <#if item.leaf><#else>,isParent:true</#if>}
					<#else>
						{"id":${item.id}, "pId":<#if item.pid!='NULL'>${item.pid}<#else>0</#if>, "name":"${item.name}" <#if item.leaf><#else>,isParent:true</#if>},
					</#if>
				</#list>
			];
			$(function(){
				$.fn.zTree.init($("#menus"), setting, treeNodes);
				setEdit();
				$('#add').button().click(function(event) {
			        addNode();
			    });
			    $('#edit').button().click(function(event) {
			        editNode();
			    });
			    $('#del').button().click(function(event) {
			        
			    });
			    $('#moveup').button().click(function(event) {
			        moveup();
			    });
			    $('#movedown').button().click(function(event) {
			        movedown();
			    });
			});
			function moveup()
			{
				var zTree = $.fn.zTree.getZTreeObj("menus");
				var nodes = zTree.getSelectedNodes();
				var currNode = nodes[0];
				var preNode=null;
				if(currNode)
				{
					preNode = currNode.getPreNode();
					if(preNode)
					{
						$.ajax({
							type:'POST',
							url:'${cpath}/deptManager/orderTypes',
							data:{'currId':currNode.id,'changeId':preNode.id},
							error:function(msg)
							{
								alert('请求错误！');
							},
							success:function(msg)
							{
								zTree.moveNode(preNode,currNode,'prev',false);
							}
						});
					}
					else
					{
						alert('该栏目已经置顶了！无法移动！');
					}
				}
				else
				{
					alert('请先选择一个栏目！');
				}
			}
			function movedown()
			{
				var zTree = $.fn.zTree.getZTreeObj("menus");
				var nodes = zTree.getSelectedNodes();
				var currNode = nodes[0];
				var preNode=null;
				if(currNode)
				{
					preNode = currNode.getNextNode();
					if(preNode)
					{
						$.ajax({
							type:'POST',
							url:'${cpath}/deptManager/orderTypes',
							data:{'currId':currNode.id,'changeId':preNode.id},
							error:function(msg)
							{
								alert('请求错误！');
							},
							success:function(msg)
							{
								zTree.moveNode(preNode,currNode,'next',false);
							}
						});
					}
					else
					{
						alert('该栏目已经在最底端了！无法移动！');
					}
				}
				else
				{
					alert('请先选择一个栏目！');
				}
			}
			function addNode()
			{
				var zTree = $.fn.zTree.getZTreeObj("menus");
				var nodes = zTree.getSelectedNodes();
				var currNode = nodes[0];
				if(currNode)
				{
					$("#dept-dialog").attr("title","新增部门");
					$("#dept-dialog").dialog({
						bgiframe: true,
						autoOpen: false,
						resizable: false,
						width:200,	height:150,
						modal: true,
						overlay: {	backgroundColor: '#000', opacity: 0.5	},
						close: function() {	 
								// Remove the dialog elements
		                		// Note: this will put the original div element in the dom
								$(this).dialog("destroy");
		               			// Remove the left over element (the original div element)
								//$(this).remove(); 
						}
						//open:  function() {	 ... },
						,
						buttons: {
							'确定': function() {
								var treeName = $('#name')[0].value;
								$.ajax({
									type:"POST",
									url:'${cpath}/deptManager/addAjaxMenu',
									data:{'pId':currNode.id,'name':treeName},
									error:function(msg)
									{
										alert("请求错误！");
									},
									success:function(msg)
									{
										var zTree = $.fn.zTree.getZTreeObj("menus");
										zTree.addNodes(currNode, {id:msg, pId:currNode.id, name:treeName});
										 $('#name').val('');
										return false;
									}
								});
							},
							'取消': function() { 
								$(this).dialog('close');
							}
						}
					});
					$("#dept-dialog").html('<div style="text-align:center;width:100%;padding:10px 0 10px 0;"><input type="text" id="name" /></div>').dialog('open');
				}
				else
				{
					alert('请先选择一个栏目！');
				}
			}
			function editNode()
			{
				var zTree = $.fn.zTree.getZTreeObj("menus");
				var nodes = zTree.getSelectedNodes();
				var currNode = nodes[0];
				if(currNode)
				{
					var currName = currNode.name;
					var currId = currNode.id;
					$("#dept-dialog").attr("title","编辑部门");
					$("#dept-dialog").dialog({
						bgiframe: true,
						autoOpen: false,
						resizable: false,
						width:200,	height:150,
						modal: true,
						overlay: {	backgroundColor: '#000', opacity: 0.5	},
						close: function() {	 
								// Remove the dialog elements
		                		// Note: this will put the original div element in the dom
								$(this).dialog("destroy");
		               			// Remove the left over element (the original div element)
								//$(this).remove(); 
						}
						//open:  function() {	 ... },
						,
						buttons: {
							'确定': function() {
								var treeName = $('#name')[0].value;
								$.ajax({
									type:'POST',
									url:'${cpath}/deptManager/modMenus',
									data:'infoid='+currId+"&name="+encodeURI(treeName),
									error:function(msg)
									{
										alert("请求错误！");
									},
									success:function(msg)
									{	
										currNode.name=treeName;
										location.reload();
									}
								});
							},
							'取消': function() { 
								$(this).dialog('close');
							}
						}
					});
					$("#dept-dialog").html('<div style="text-align:center;width:100%;padding:10px 0 10px 0;"><input type="text" id="name" value="'+currName+'"/></div>').dialog('open');
				}
				else
				{
					alert('请先选择一个栏目！');
				}
			}
			function removeThisNode()
			{
				var zTree = $.fn.zTree.getZTreeObj("menus");
				var nodes = zTree.getSelectedNodes();
				var currNode = nodes[0];
				if(currNode)
				{	
					if(confirm("确认要删除该节点吗？删除后该目录下的子菜单都会删除！"))
					{
						if(confirm("确认要这么做吗？"))
						{
							$.ajax({
									type:'POST',
									url:'${cpath}/deptManager/delMenus.html',
									data:'infoid='+currNode.id,
									error:function(msg)
									{
										asyncbox.alert("请求错误！","确认信息",function(msg){return;})
									},
									success:function(msg)
									{
										asyncbox.alert("删除成功！","确认信息",function(msg){zTree.removeNode(currNode);})
									}
								});
						}
					}					
				}
				else
				{
					alert('请先选择一个栏目！');
				}
			}
</script>
<style type="text/css">
	.ui-widget-content{padding:0px;overflow:hidden;}
	.ui-button-text{padding-top:0px !important;padding-bottom:0px !important;};
</style>
</head>
<body>
	<div class="ui-layout-center">
		<div class="ui-layout-content ui-widget-content ui-state-active" style="text-align:center">
			<button id="add">新增</button>
			<button id="edit">修改</button>
			<button id="del">删除</button>
			<button id="moveup">上移</button>
			<button id="movedown">下移</button>
		</div>
		<br/>
		<div class="ui-layout-content ui-widget-content">
			<div class="ztree" id="menus"></div>
		</div>
		<div id="dept-dialog" style="display:none">
			
		</div>
	</div>
</body>
</html>
