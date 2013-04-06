var User={
		add:function()
		{
			var win = $.ligerDialog.open
						({ url: cpath+'/userManager/preAdd',
						   width:500, height: 300, isResize: true,showMax :true,showMin :true
					    }); 
		}
}