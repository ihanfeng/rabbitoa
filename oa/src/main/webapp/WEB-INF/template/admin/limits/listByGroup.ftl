<#include "/common/layout/common.ftl"/>
<script type="text/javascript">
      $(function(){
			jQuery("#infoContent").jqGrid({
				url:"${cpath}/groupManager/listAll/getJson",
				datatype: "json",
				height:$(window).height()-85,
				width:$(window).width()-5,
				ajaxSelectOptions: {
			        data: {
			            page: '1',
			            rows:'100'
			        }
			    },
			    colNames:['序号','名称'],
			   	colModel:[
	   				{name:'id',index:'id',sortable:true,editable: false,search:true,searchoptions:{sopt:['eq','ne']}},
	   				{name:'name',index:'name',sortable:true,editable: true,editrules:{minValue:2,maxvalue:8},search:true}
			   	],
			   rowNum:20,
			   rowList:[10,20,30],
			   pager: '#pInfoContent',
			   sortname: 'id',
			   viewrecords: true,
			   sortorder: "asc",
			   editurl:"${cpath}/groupManager/editData",
			   jsonReader : {
			        root: "rows",
			        page: "page",
			        total: "total",
			        records: "records",
			        repeatitems: false,
			        cell: "cell",
			        id: "id"
		    	}
		    	,
		    	ondblClickRow: function(rowid) {
		    		showGroupLimitsPanel();
				}
			});
			var grid = jQuery("#infoContent");
			var addOptions={
				beforeShowForm: function(form) {
	                 // "editmodlist"
	                 var dlgDiv = $("#editmod" + grid[0].id);
	                 var parentDiv = dlgDiv.parent();
	                 var dlgWidth = dlgDiv.width();
	                 var parentWidth = parentDiv.width();
	                 var dlgHeight = dlgDiv.height();
	                 var parentHeight = parentDiv.height();
	                 // TODO: change parentWidth and parentHeight in case of the grid
	                 //       is larger as the browser window
	                 dlgDiv[0].style.top = Math.round((parentHeight-dlgHeight)/2) + "px";
	                 dlgDiv[0].style.left = Math.round((parentWidth-dlgWidth)/2) + "px";
	             },
				 serializeEditData: function(data){ 
				    return $.param($.extend({},data,{id:0}));
			     }
			}
			var editOptions={
				beforeShowForm: function(form) {
	                 // "editmodlist"
	                 var dlgDiv = $("#editmod" + grid[0].id);
	                 var parentDiv = dlgDiv.parent();
	                 var dlgWidth = dlgDiv.width();
	                 var parentWidth = parentDiv.width();
	                 var dlgHeight = dlgDiv.height();
	                 var parentHeight = parentDiv.height();
	                 // TODO: change parentWidth and parentHeight in case of the grid
	                 //       is larger as the browser window
	                 dlgDiv[0].style.top = Math.round((parentHeight-dlgHeight)/2) + "px";
	                 dlgDiv[0].style.left = Math.round((parentWidth-dlgWidth)/2) + "px";
	             }
	          }
	          var mgParameters={
	        	  'caption':'设置权限',
	        	  'buttonicon':'none',
	        	  'onClickButton':showGroupLimitsPanel,
	        	  'title':'设置权限', 
	        	  'id' :'deptManagerBtn'
	          };
	          var separator_parameters={
	        	  sepclass : "ui-separator",sepcontent: ''
	          }
			  grid.jqGrid('navGrid',"#pInfoContent",{'add':false,'edit':false,'del':false,'search':false},addOptions,editOptions,{},{multipleSearch:true});
			  grid.jqGrid("navSeparatorAdd","#pInfoContent",separator_parameters);
			  grid.jqGrid('navButtonAdd',"#pInfoContent",mgParameters);
			  re_pos();
      });
      function showGroupLimitsPanel()
      {
      	var gr = jQuery("#infoContent").jqGrid('getGridParam','selrow');
      	if(gr)
		{
			var rowData = jQuery('#infoContent').jqGrid('getRowData',gr);
			$("#user-dialog").attr("title",rowData.name+"--权限列表 ");
			$("#user-dialog").dialog({
				bgiframe: true,
				autoOpen: false,
				resizable: false,
				width:$(window).width()-80,	height:$(window).height()-50,
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
						var iframeObj = window.frames["menusPanel"];
						var treeObj = iframeObj.$.fn.zTree.getZTreeObj("menus");
						var nodes = treeObj.getCheckedNodes(true);
						var menuid="";
						$.each(nodes, function(key, val) {
							menuid=menuid+val.id+"|";
						});
						$.ajax({
							type:'POST',
							url:'${cpath}/lismitsManager/saveLimits',
							data:{'typeid':gr,'menuids':menuid,type:${type}},
							error:function(msg)
							{
								alert("请求错误！");
							},
							success:function(msg)
							{
								alert("操作成功！");
								location.reload();
							}
						});
					 },
					'取消': function() { 
						$(this).dialog('close');
					}
				}
				
			});
			$("#user-dialog").html($("<iframe />").attr("name","menusPanel").attr("src", "${cpath}/limitsManager/listByGroupId?groupid="+gr).attr("width","99%").attr("height","100%").attr("frameBorder","0")).dialog('open');
		}
		else
		{
			alert('请选择一行再进行操作！');
		}
      }
		function re_pos()
		{
			if($('#infoContent')[0])
			{
				$("#infoContent").setGridHeight($(window).height()-85);
				$("#infoContent").setGridWidth($(window).width()-5);
				
			}
		}
		var timer = null;		
		$(window).resize(function(){
			timer && clearTimeout(timer);
			timer = setTimeout(function()
			{
				re_pos();
			},200);
		});
</script>
<style type="text/css">
	.ui-widget-content{padding:0px;overflow:hidden;}
	.ui-button-text{padding-top:0px !important;padding-bottom:0px !important;};
</style>
</head>
<body>
	<div class="ui-layout-center">
		<h3 class="ui-widget-header">
			用户群组列表&nbsp;&nbsp;(双击行数据可设置该用户群权限)
		</h3>
		<div class="ui-layout-content ui-widget-content">
			<table id="infoContent">
    	 	</table>
    	 	<div id="pInfoContent"></div>
		</div>
		<div id="user-dialog" style="display:none">
			
		</div>
	</div>
</body>
</html>
