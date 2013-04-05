<#include "/common/layout/tablecommon.ftl"/>
<script type="text/javascript">
        function itemclick(item)
        {
            alert(item.text);
        }
        $(function ()
        {
            window['g'] =
            $("#maingrid").ligerGrid({
                height:'100%',
                columns: [
                { display: '用户名', name: 'userid', align: 'left', width: 100, minWidth: 60 },
                { display: '邮箱', name: 'email', minWidth: 120 },
                 { display: '手机号', name: 'mobile', minWidth: 120 },
                { display: '姓名', name: 'username', minWidth: 140 },
                { display: '部门', name: 'deptid' }
                ], url:'${cpath}/userManager/listAll/getJson.html',  pageSize:30 ,rownumbers:true,
                dataAction: 'server', //服务器排序
                usePager: true,       //服务器分页
                toolbar: { items: [
                { text: '增加', click: itemclick, icon: 'add' },
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
