<#include "/common/layout/common.ftl"/>
 <script type="text/javascript">  
  $().ready(function() {  
   $('#add').click(function() {  
    return !$('#select1 option:selected').remove().appendTo('#select2');  
   });  
   $('#remove').click(function() {  
    return !$('#select2 option:selected').remove().appendTo('#select1');  
   });  
   $('#addAll').click(function() {  
    return !$('#select1 option').remove().appendTo('#select2');  
   }); 
   $('#removeAll').click(function() {  
    return !$('#select2 option').remove().appendTo('#select1');  
   }); 
   $('#select1').dblclick(function(){
   	 return !$('#select1 option:selected').remove().appendTo('#select2');  
   });
    $('#select2').dblclick(function(){
   	 return !$('#select2 option:selected').remove().appendTo('#select1');  
   });
   $('#select1').css("height",$(window).height()-30);
   $('#select2').css("height",$(window).height()-30);
   $('#add').button();
   $('#remove').button();
   $('#addAll').button();
   $('#removeAll').button();
  });  
 </script> 
 <style type="text/css">
	.ui-widget-content{padding:0px;padding-top:3px;padding-bottom:3px;overflow:hidden;}
	.ui-button-text{padding-top:5px !important;padding-bottom:5px !important;};
</style>
</head>
<body>
	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="40%">
				<div class="ui-layout-content ui-widget-content ui-state-active" style="text-align:center">
					未添加
				</div>
				<select style="width:100%;" multiple id="select1">
					<#list unSelUsers  as item>
						<option value="${item.id}">${item.username}</option>
					</#list>
				</select>
			</td>
			<td width="20%" align="center">
				<button id="add">添加></button><br/>
				<button id="remove"><删除</button><br/>
				<button id="addAll">全部添加>></button><br/>
				<button id="removeAll"><<全部删除</button>
			</td>
			<td width="40%">
				<div class="ui-layout-content ui-widget-content ui-state-active" style="text-align:center">
					已添加
				</div>
				<select style="width:100%;height:100%" multiple id="select2">
					<#list selUsers  as item>
						<option value="${item.id}">${item.username}</option>
					</#list>
				</select>
			</td>
		</tr>
	</table>
</body>
</html>