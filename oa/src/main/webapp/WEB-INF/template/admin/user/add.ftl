<#include "/common/layout/common.ftl"/>
<script type="text/javascript">
        var eee;
        $(function ()
        {
            $.metadata.setType("attr", "validate");
            var v = $("form").validate({
            	focusInvalid: true,
            	onkeyup: true,
                errorPlacement: function (lable, element)
                {
                    if (element.hasClass("l-textarea"))
                    {
                        element.ligerTip({ content: lable.html(), target: element[0] }); 
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().ligerTip({ content: lable.html(), target: element[0] });
                    }
                    else
                    {
                        lable.appendTo(element.parents("td:first").next("td"));
                    }
                },
                rules: {
                	email:{
                		remote:{
                			url:'${cpath}/userManager/validateUserEmailExists',
                			type: "post",               //数据发送方式
                            dataType: "json",
                            data: {
                                email: function () {
                                    return $("#txtEmail").val();
                                }
                            },
                            error:function (a,s,d){
                                alert(s + " " + d);
                            }
                		}
                	},
                	mobile:{
                		remote:{
                			url:'${cpath}/userManager/validateUserMobileExists',
                			type: "post",               //数据发送方式
                            dataType: "json",
                            data: {
                                mobile: function () {
                                    return $("#txtMobile").val();
                                }
                            },
                            error:function (a,s,d){
                                alert(s + " " + d);
                            }
                		}
                	}
                },
                success: function (lable)
                {
                    lable.ligerHideTip();
                    lable.remove();
                },
                submitHandler: function (form)
                {
                    $("form .l-text,.l-textarea").ligerHideTip();
                    form.submit();
                }
            });
            $("form").ligerForm({'align':'center'});
            /*
            $(".l-button-test").click(function ()
            {
                alert(v.element($("#txtName")));
            });
            */
        });  
    </script>
    <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
    </style>

</head>

<body style="padding:10px">

    <form name="form1" method="post" action="${cpath}/userManager/add" id="form1" target="_parent">
<div>
</div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td">名字:</td>
                <td align="left" class="l-table-edit-td"><input name="username" type="text" id="txtName" ltype="text" validate="{required:true,minlength:2,maxlength:5}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">手机号:</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text" id="txtMobile" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">Email:</td>
                <td align="left" class="l-table-edit-td"><input name="email" type="text" id="txtEmail" ltype="text" validate="{required:true,email:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">部门:</td>
                <td align="left" class="l-table-edit-td">
	                <select name="deptid" id="ddlDepart" ltype="select">
						<option value="1">主席</option>
						<option value="2">研发中心</option>
						<option value="3">销售部</option>
						<option value="4">市场部</option>
						<option value="5">顾问组</option>
					</select>
                </td>
            </tr>
        </table>
 <br />
		<input type="submit" value="提交" id="Button1" class="l-button l-button-submit" /> 
    </form>

    
</body>
</html>