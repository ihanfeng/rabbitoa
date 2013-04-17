<#include "/common/layout/common.ftl"/>
<script src="${cpath}/js/admin/user.js" type="text/javascript"></script>
<script type="text/javascript">
        function itemclick(item)
        {
            alert(item.text);
        }
        $(function ()
        {
            window['g'] =
            $("#maingrid").ligerGrid({
                height:'99%',
                columns: [
                { display: '序号', name: 'id', align: 'left', width: 100, minWidth: 60,type:'int' },
                { display: '名字', name: 'email', minWidth: 120,type:'text' },
                 { display: '领导', name: 'mobile', minWidth: 120,type:'text' }
                ], url:'${cpath}/deptManager/listAll/getJson.html',  pageSize:30 ,rownumbers:true,
                dataAction: 'server', //服务器排序
                usePager: true,       //服务器分页
                toolbar: { items: [
                { text: '增加', click: User.add, icon: 'add' },
                { line: true },
                { text: '修改', click: itemclick, icon: 'modify' },
                { line: true },
                { text: '删除', click: itemclick, img: '${cpath}/ligerUI/skins/icons/delete.gif' }
                ]
                }
            });
             

            $("#pageloading").hide();
        });

        function deleteRow()
        {
            g.deleteSelectedRow();
        }

    </script>
</head>
<body style="overflow-x:hidden; padding:2px;">
<div class="l-loading" style="display:block" id="pageloading"></div>
 <a class="l-button" style="width:120px;float:left; margin-left:10px; display:none;" onclick="deleteRow()">删除选择的行</a>

 
 <div class="l-clear"></div>

    <div id="maingrid"></div>
   
  <div style="display:none;">
  
</div>
 
</body>
</html>
