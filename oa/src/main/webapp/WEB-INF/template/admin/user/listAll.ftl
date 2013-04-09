<#include "/common/layout/common.ftl"/>
<script type="text/javascript">
      $(function(){
			jQuery("#infoContent").jqGrid({
				url:"${cpath}/userManager/listAll/getJson",
				datatype: "json",
				height:$(window).height()-85,
				width:$(window).width()-5,
			    colNames:['序号','姓名','电话','邮箱','部门'],
			   	colModel:[
	   				{name:'id',index:'id',sortable:true,editable: false},
	   				{name:'username',index:'username',sortable:true,editable: true,editrules:{minValue:2,maxvalue:5}},
	   				{name:'mobile',index:'mobile',sortable:true,editable: true},
	   				{name:'email',index:'email',sortable:true,editable: true,editrules:{email:true}},
	   				{name:'deptid',index:'deptid',edittype: 'select',sortable:true,editable: true,editoptions: {
			            dataUrl: '${cpath}/deptManager/listAll/getJson?page=1&rows=100',
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
		    	},
		    	ondblClickRow: function(rowid) {
				    jQuery(this).jqGrid('editGridRow', rowid);
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
			grid.jqGrid('navGrid',"#pInfoContent",{},editOptions,addOptions,{},{multipleSearch:true});
			re_pos();
      });
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
	</div>
</body>
</html>
