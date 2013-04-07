<#include "/common/layout/common.ftl"/>
<link rel="stylesheet" type="text/css" href="${cpath}/jquery_ui/jquery.portlet.css" />
<script type="text/javascript" src="${cpath}/jquery_ui/jquery.portlet.min.js"></script>
<script>
	$(document).ready( function() {

		var myLayout = $('body').layout({
		});
		$('#rabbit-oa-main').portlet({
            sortable: true,
            create: function() {
                //alert('created portlet.');
            },
            removeItem: function() {
                alert('after remove');
            },
            columns: [{
                width: '50%',
                portlets: [{
                    attrs: {
                        id: 'feeds'
                    },
                    title: function() {
                        var d = new Date();
                        return '更新(' + (d.getMonth() + 1) + '-' + d.getDate() + '日)';
                    },
                    icon: 'ui-icon-comment',
                    content: {
                        //设置区域内容属性
                        style: {
                            height: '200px'
                        },
                        type: 'text',
                        text: '<ul><li>Feed item 1</li><li>Feed item 2</li></ul>',
                        beforeShow: function(aa) {
                            //alert('before show, content is: ' + aa);
                        },
                        afterShow: function() {
                            //alert('after show');
                        }
                    }
                }]
            }, {
                width: '50%',
                icon:'ui-icon-heart',
                portlets: [{
                    title: '联系我',
                    content: {
                        attrs: {
                            'class': 'highlight-content'
                        },
                        type: 'text',
                        text: '邮箱：286600136@qq.com<br/>交流群：13976541<br/>博客：<a href="https://zhmlvft-20120609.rhcloud.com/" target="_blank">https://zhmlvft-20120609.rhcloud.com/</a>'
                    }
                },
                {
                    attrs: {
                        id: 'features_panel'
                    },
                    title: '系统功能',
                    beforeRefresh: function() {
                        alert("before refresh");
                    },
                    afterRefresh: function(data) {
                        //alert("after refresh: " + data);
                    },
                    content: {
                        style: {
                            height: '200'
                        },
                        type: 'text',
                        text: function() {
                            return $('#features').html();
                        }
                    }
                }]
            }]
        });
	});
</script>
</head>
<body>
	<div class="ui-layout-center">
		<h3 class="ui-widget-header">我的首页</h3>
		<div class="ui-layout-content ui-widget-content">
			<div id='rabbit-oa-main'></div>
		</div>
		<div id="features" style="display:none">
			<ul>
				<li>
					多级部门，组织机构设置更清晰。
				</li>
				<li>
					权限更细致，精确到每个人的增删改查。
				</li>
				<li>
					工作流在线设计部署。
				</li>
				<li>
					强大的报表功能。
				</li>
				<li>
					一站式发文。
				</li>
				<li>
					自动建站。
				</li>
			</ul>
		</div>
	</div>
</body>
</html>