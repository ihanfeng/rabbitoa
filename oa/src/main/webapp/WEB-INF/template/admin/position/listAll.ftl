<#include "/common/layout/common.ftl"/>
<script type="text/javascript">
      $(function(){
			jQuery("#infoContent").jqGrid({
				treeGrid: true,  
			    treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关  
			    ExpandColumn : 'name',   
				url:"${cpath}/positionManager/listAll/getJson",
				datatype: "json",
				height:$(window).height()-85,
				width:$(window).width()-5,
			    colNames:['职位','序号'],
			   	colModel:[
			   		{name:'name',index:'name',sortable:false},
	   				{name:'id',index:'id',sortable:false}
			   	],
			   pager: "#pInfoContent", 
			   viewrecords: true,   
			   multiselect: false,
			   ondblClickRow:function(rowid, iRow, iCol, e)
			   {
			   	  showPositionUser();
			   },
			   jsonReader : {
			        root: "rows",
			        page: "page",
			        total: "total",
			        records: "records",
			        repeatitems: false,
			        cell: "cell",
			        id: "id"
		    	},
		    	treeReader : {  
			      level_field: "level",  
			      parent_id_field: "pid",   
			      leaf_field: "leaf",  
			      expanded_field: "expanded" 
			    }, 
			    rowNum : "-1" 
		    	
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
	        var parameters={
	        	'caption':'查看人员',
	        	'buttonicon':'none',
	        	'onClickButton':showPositionUser,
	        	'title':'查看人员', 
	        	'id' :'deptDetailBtn'
	        };
	        var mgParameters={
	        	'caption':'编辑职位',
	        	'buttonicon':'none',
	        	'onClickButton':showPRoleManagerPanel,
	        	'title':'编辑职位', 
	        	'id' :'deptManagerBtn'
	        };
	        var separator_parameters={
	        	sepclass : "ui-separator",sepcontent: ''
	        }
			grid.jqGrid('navGrid',"#pInfoContent",{'add':false,'edit':false,'del':false,'search':false},addOptions,editOptions,{},{multipleSearch:true});
			grid.jqGrid("navSeparatorAdd","#pInfoContent",separator_parameters);
			grid.jqGrid('navButtonAdd',"#pInfoContent",parameters);
			grid.jqGrid("navSeparatorAdd","#pInfoContent",separator_parameters);
			grid.jqGrid('navButtonAdd',"#pInfoContent",mgParameters);
			re_pos();
			
      });
      function showPositionUser()
      {
      	var gr = jQuery("#infoContent").jqGrid('getGridParam','selrow');
      	if(gr)
		{
			var rowData = jQuery('#infoContent').jqGrid('getRowData',gr);
			$("#user-dialog").attr("title",rowData.name+"--人员列表    (双击行数据可编辑部门人员)");
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
				/*
				,
				buttons: {
					'确定': function() {$(this).dialog('close'); },
					'取消': function() { 
						$(this).dialog('close');
					}
				}
				*/
			});
			$("#user-dialog").html($("<iframe />").attr("src", "${cpath}/userManager/listByPositionId?positionid="+gr).attr("width","99%").attr("height","100%").attr("frameBorder","0")).dialog('open');
		}
		else
		{
			alert('请选择一行再进行操作！');
		}
      }
      function showPRoleManagerPanel()
      {
      		$("#user-dialog").attr("title","编辑职位");
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
				/*
				,
				buttons: {
					'确定': function() {$(this).dialog('close'); },
					'取消': function() { 
						$(this).dialog('close');
					}
				}
				*/
			});
			$("#user-dialog").html($("<iframe />").attr("src", "${cpath}/positionManager/editPositionTree").attr("width","99%").attr("height","100%").attr("frameBorder","0")).dialog('open');
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
		})
</script>
<style type="text/css">
	.ui-widget-content{padding:0px;overflow:hidden;}
	.ui-button-text{padding-top:0px !important;padding-bottom:0px !important;};
</style>
</head>
<body>
	<div class="ui-layout-center">
		<h3 class="ui-widget-header">
			职位列表&nbsp;&nbsp;(双击行数据可查看职位人员)
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
