<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>内容页</title>
    <script>
    	var cpath = '${cpath}';
    </script>
    <META http-equiv=Content-Type content="text/html; charset=utf-8">
    <meta name="language" content="zh_CN" />
    <link rel="stylesheet" type="text/css" href="${cpath}/jquery_ui_layout/css/layout-default-latest.css" />
    <link rel="stylesheet" type="text/css" href="${cpath}/jquery_ui/themes/start/jquery-ui.css" id="jquery-ui"/>
	<link rel="stylesheet" type="text/css" media="screen" href="${cpath}/jqgrid/css/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="${cpath}/jquery_ztree/css/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="${cpath}/css/main.css" />
	<script type="text/javascript" src="${cpath}/jquery/jquery-1.8.0.js"></script>
	<script type="text/javascript" src="${cpath}/jquery_ui/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${cpath}/jquery_ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
	<script type="text/javascript" src="${cpath}/jquery_ui_layout/jquery.layout-latest.min.js"></script>
	<script src="${cpath}/jqgrid/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
	<script src="${cpath}/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${cpath}/jquery_ztree/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${cpath}/jquery/cookie.js"></script>
	<style type="text/css">
	/* remove padding and scrolling from elements that contain an Accordion OR a content-div */
	.ui-layout-center ,	/* has content-div */
	.ui-layout-west ,	/* has Accordion */
	.ui-layout-east ,	/* has content-div ... */
	.ui-layout-east .ui-layout-content { /* content-div has Accordion */
		padding: 0;
		overflow: hidden;
	}
	.ui-layout-center P.ui-layout-content {
		line-height:	1.4em;
		margin:			0; /* remove top/bottom margins from <P> used as content-div */
	}
	h3, h4 { /* Headers & Footer in Center & East panes */
		background:		#EEF;
		border:			1px solid #BBB;
		border-width:	0 0 1px;
		padding:		7px 10px;
		margin:			0;
	}
	</style>
	<script type="text/javascript">
		var themeCookie = $.cookie('jquery-ui-theme');
		alert(themeCookie);
		if(themeCookie)
		{
			$('#jquery-ui').attr("href",themeCookie);
		}
	</script>
