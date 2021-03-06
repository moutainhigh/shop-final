<%@ page contentType="text/html;charset=UTF-8" session="false" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/pageHead.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><sys:site/>管理后台</title>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
		<div class="top">
		    <form name="queryForm" id="queryForm">
				<div class="fl">
				  <div class="ui-btn-menu">
				      <span class="ui-btn ui-menu-btn ui-btn" style='vertical-align: middle;'>
				         <strong>点击查询</strong><b></b>
				      </span>
				      <div class="dropdown-menu" style="width: 320px;">
				           <div class="control-group formSep">
								<label class="control-label">组名称:</label>
								<div class="controls">
									<input type="text" class="input-txt" name="name"/>
								</div>
						   </div>
					       <div class="ui-btns"> 
				              <input class="btn btn-primary query" type="button" value="查询"/>
				              <input class="btn reset" type="button" value="重置"/>
				           </div> 
				      </div>
				  </div>
				  <input type="button" class="btn btn-primary" value="&nbsp;刷&nbsp;新&nbsp;" onclick="Public.doQuery()">
				</div>
				<div class="fr">
				   <input type="button" class="btn btn-primary" id="addBtn" value="&nbsp;添&nbsp;加&nbsp;">&nbsp;
				   <input type="button" class="btn" id="delBtn" value="&nbsp;删&nbsp;除&nbsp;">&nbsp;
				</div>
			</form>
		</div>
	</div>
	<div id="dataGrid" class="autoGrid">
		<table id="grid"></table>
		<div id="page"></div>
	</div> 
</div>
<%@ include file="/WEB-INF/views/include/list-footer.jsp"%>
<script type="text/javascript">
var THISPAGE = {
	_init : function(){
		this.loadGrid();
		this.addEvent();
	},
	loadGrid : function(){
		var init = Public.setGrid();
		var optionsFmt = function (cellvalue, options, rowObject) {
			return Public.billsOper(cellvalue, options, rowObject);
		};
		$('#grid').jqGrid(
			Public.defaultGrid({
				url: '${ctx}/system/group/page?timeid='+ Math.random(),
				height:init.h,
				shrinkToFit:!1, 
				rownumbers: !0,//序号
				multiselect:true,//定义是否可以多选
				multiboxonly: false,
				colNames: ['ID', '用户组名', '用户组编码', '备注', '创建人', '创建时间', '修改人', '最后修改时间', '操作'],
				colModel: [
                    {name:'id', index:'id', width:80,sortable:false,hidden:true},
	                {name:'name', index:'name', width:150,sortable:false},
	                {name:'code', index:'code', width:150,sortable:false},
	                {name:'remarks', index:'remarks', width:120, align:'left', sortable:false},
	                {name:'createName', index:'createName', width:120, align:'center', sortable:false},
	                {name:'createDate', index:'createDate', width:120, align:'center', sortable:false},
	                {name:'updateName', index:'updateName', width:120, align:'center', sortable:false},
	                {name:'updateDate', index:'updateDate', width:120, align:'center', sortable:false},
					{name:'options', index:'options',align:'center',width:80,sortable:false,formatter:optionsFmt}
				]
			})		
		);
		$('#grid').jqGrid('setFrozenColumns');
	},
	addEvent : function(){
		var that = this;
		Public.initBtnMenu();
		var delUserGroup = function(checkeds){
			if( !!checkeds  ) {
				var param = [];
				if(typeof(checkeds) === 'object'){
					$.each(checkeds,function(index,item){
						var userId = $('#grid').getRowData(item).id;
						param.push({name:'idList',value:userId});
					});
				} else {
					param.push({name:'idList',value:checkeds});
				}
				Public.deletex("确定删除选中的用户组？","${ctx}/system/group/delete",param,function(data){
					if(!!data.success) {
						Public.success('删除用户组成功');
						Public.doQuery();
					} else {
						Public.error(data.msg);
					}
				});
			} else {
				Public.error("请选择要删除的用户组!");
			}
		}
		$('#dataGrid').on('click','.edit',function(e){
			window.location.href = "${ctx}/system/group/form?id="+$(this).attr('data-id');
		});
		$('#dataGrid').on('click','.delete',function(e){
			delUserGroup($(this).attr('data-id'));
		});
		$(document).on('click','#addBtn',function(e){
			window.location.href = "${ctx}/system/group/form";
		});
		$(document).on('click','#delBtn',function(e){
			var checkeds = $('#grid').getGridParam('selarrrow');
			delUserGroup(checkeds);
		});
	}
};
$(function(){
	THISPAGE._init();
});
</script>
</body>
</html>