<#include "/common/layout/common.ftl"/>
<script type="text/javascript">
      $(function(){
			jQuery("#infoContent").jqGrid({
				url:"${cpath}/userManager/listAll/getJson",
				datatype: "json",
				height:$(window).height()-85,
				width:$(window).width()-5,
				ajaxSelectOptions: {
			        data: {
			            page: '1',
			            rows:'100'
			        }
			    },
			    colNames:['序号','姓名','电话','邮箱','部门','职位'],
			   	colModel:[
	   				{name:'id',index:'id',sortable:true,editable: false,search:true,searchoptions:{sopt:['eq','ne']}},
	   				{name:'username',index:'username',sortable:true,editable: true,editrules:{minValue:2,maxvalue:5},search:true},
	   				{name:'mobile',index:'mobile',sortable:true,editable: true,search:true},
	   				{name:'email',index:'email',sortable:true,editable: true,editrules:{email:true},search:true},
	   				{name:'deptid',index:'deptid',stype:'select',sortable:true,search:true,searchoptions:{
	   					sopt:['eq','ne'],
	   					dataUrl: '${cpath}/deptManager/listAll/getJson',
			            buildSelect: function (data) {
				            var blankAry=['','&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'];
				            var retValue = $.parseJSON(data);
				            var response = retValue.rows;
				            var s = '<select id="searchid" name="searchid">';
				            s+='<option value="0">请选择</option>';
				            if (response && response.length) {
				                for (var i = 0, l = response.length; i < l; i++) {
				                	s += '<option value="' + response[i]["id"] + '">' + (blankAry[response[i]["level"]])+response[i]["name"] + '</option>';
				                }
				            }
				            return s + "</select>";
			            }
	   				},
	   				edittype: 'select',editable: true,
	   				editoptions: {
			            dataUrl: '${cpath}/deptManager/listAll/getJson',
			            buildSelect: function (data) {
			            var blankAry=['','&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'];
			            var retValue = $.parseJSON(data);
			            var response = retValue.rows;
			            var s = '<select id="myid" name="myid">';
			            if (response && response.length) {
			                for (var i = 0, l = response.length; i < l; i++) {
			                s += '<option value="' + response[i]["id"] + '">' + (blankAry[response[i]["level"]])+response[i]["name"] + '</option>';
			                }
			            }
			            return s + "</select>";
			            }
			        }},
			        {name:'positionid',index:'positionid',stype:'select',sortable:true,search:true,searchoptions:{
	   					sopt:['eq','ne'],
	   					dataUrl: '${cpath}/positionManager/listAll/getJson',
			            buildSelect: function (data) {
				            var blankAry=['','&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'];
				            var retValue = $.parseJSON(data);
				            var response = retValue.rows;
				            var s = '<select id="searchid" name="searchid">';
				            s+='<option value="0">请选择</option>';
				            if (response && response.length) {
				                for (var i = 0, l = response.length; i < l; i++) {
				                	s += '<option value="' + response[i]["id"] + '">' + (blankAry[response[i]["level"]])+response[i]["name"] + '</option>';
				                }
				            }
				            return s + "</select>";
			            }
	   				},
	   				edittype: 'select',editable: true,
	   				editoptions: {
			            dataUrl: '${cpath}/positionManager/listAll/getJson',
			            buildSelect: function (data) {
			            var blankAry=['','&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'];
			            var retValue = $.parseJSON(data);
			            var response = retValue.rows;
			            var s = '<select id="myid" name="myid">';
			            if (response && response.length) {
			                for (var i = 0, l = response.length; i < l; i++) {
			                s += '<option value="' + response[i]["id"] + '">' + (blankAry[response[i]["level"]])+response[i]["name"] + '</option>';
			                }
			            }
			            return s + "</select>";
			            }
			        }}
			   	],
			   rowNum:20,
			   rowList:[10,20,30],
			   pager: '#pInfoContent',
			   sortname: 'id',
			   viewrecords: true,
			   sortorder: "asc",
			   editurl:"${cpath}/userManager/editData",
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
		    		<@shiro.hasPermission name="/userManager/editData">
				   		 jQuery(this).jqGrid('editGridRow', rowid,editOptions);
				    </@shiro.hasPermission>
				    <@shiro.lacksPermission name="/userManager/editData">
				    	alert("您没有权限编辑！请联系管理员授权！");
				    </@shiro.lacksPermission>
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
	             reloadAfterSubmit:true, beforeSubmit:validate_edit,
	             reloadAfterSubmit:true, beforeSubmit:validate_edit,
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
	             },
	             reloadAfterSubmit:true, beforeSubmit:validate_edit,
	             reloadAfterSubmit:true, beforeSubmit:validate_edit
	          }
	          <@shiro.hasPermission name="/userManager/editData">
				grid.jqGrid('navGrid',"#pInfoContent",{},editOptions,addOptions,{},{multipleSearch:true});
			  </@shiro.hasPermission>
			  <@shiro.lacksPermission name="/userManager/editData">
				grid.jqGrid('navGrid',"#pInfoContent",{add:false,edit:false,del:false},editOptions,addOptions,{},{multipleSearch:true});
			  </@shiro.lacksPermission>
			  var parameters={
	        	'caption':'导出数据',
	        	'buttonicon':'none',
	        	'onClickButton':exportData,
	        	'title':'导出数据', 
	        	'id' :'exportDataBtn'
	          };
	          var separator_parameters={
	        	sepclass : "ui-separator",sepcontent: ''
	          }
	          grid.jqGrid("navSeparatorAdd","#pInfoContent",separator_parameters);
			  grid.jqGrid('navButtonAdd',"#pInfoContent",parameters);
			re_pos();
      });
      function validate_edit(postdata,obj)
      {
      	  var email = postdata.email;
      	  var mobile = postdata.mobile;
      	  var result=null;
      	  $.ajax({
      	  	async : false,
      	  	type:'POST',
      	  	url:'${cpath}/userManager/checkUserEmailMobileExists',
      	  	data:{"email":email,"mobile":mobile},
      	  	error:function(){return [false, "请求错误"];},
      	  	success:function(msg)
      	  	{
      	  		if(msg=='1')
      	  		{
      	  			result =  [false, '邮箱已经存在'];
      	  		}
      	  		else if(msg=='2')
      	  		{
      	  			result =  [false, '手机号码已经存在'];
      	  		}
      	  		else
      	  		{
      	  			result =  [true , ''];
      	  		}
      	  	}
      	  });
      	  return result;
      }
       function exportData()
       {
       	   $("#export-dialog").attr("title","导出数据    (请选择导出格式)");
			$("#export-dialog").dialog({
				bgiframe: true,
				autoOpen: true,
				resizable: false,
				width:330,	height:90,
				modal: true,
				overlay: {	backgroundColor: '#000', opacity: 0.5	},
				close: function() {	 
						// Remove the dialog elements
                		// Note: this will put the original div element in the dom
						$(this).dialog("destroy");
               			// Remove the left over element (the original div element)
						//$(this).remove(); 
				},
				open:  function() {
					$('#excelBtn').button().click(function(e){
						exportFinalData(1);
					});
					$('#wordBtn').button().click(function(e){
						exportFinalData(2);
					});
					$('#pdfBtn').button().click(function(e){
						exportFinalData(3);
					});
				}
			});
       }
       function exportFinalData(type)
       {
       	   var grid = jQuery("#infoContent");
       	   //获取查询条件
       	   var filters = grid.getGridParam("postData").filters;
       	   $('#export_type').val(type);
       	   $('#search_filters').val(filters);
       	   document.forms["exportForm"].submit();
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
			用户列表&nbsp;&nbsp;(双击行数据可编辑人员)
		</h3>
		<div class="ui-layout-content ui-widget-content">
			<table id="infoContent">
    	 	</table>
    	 	<div id="pInfoContent"></div>
		</div>
		<div id="export-dialog" style="display:none">
			<button id="excelBtn">Excel格式</button>
			<button id="wordBtn">网页格式</button>
			<button id="pdfBtn">PDF格式</button>
			<form name="exportForm" action="${cpath}/userManager/exportAll" method="POST" style="display:none" target="_blank">
				<input type="hidden" id="search_filters" name="search_filters" />
				<input type="hidden" id="export_type" name="export_type"/>
			</form>
		</div>
	</div>
</body>
</html>
