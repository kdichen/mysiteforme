<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>请假信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="description" content="${site.description}"/>
    <meta name="keywords" content="${site.keywords}"/>
    <meta name="author" content="${site.author}"/>
    <link rel="icon" href="${site.logo}">
    <link rel="stylesheet" href="${base}/static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
    <link rel="stylesheet" href="${base}/static/css/user.css" media="all" />
</head>
<body class="childrenBody">
<fieldset class="layui-elem-field">
  <legend>请假信息</legend>
  <div class="layui-field-box">
    <form class="layui-form" id="searchForm">
    <div class="layui-inline" style="margin-left: 15px">
            <label>学生名称:</label>
                <div class="layui-input-inline">
                <input type="text" value="" name="s_studentName" placeholder="请输入学生名称" class="layui-input search_input">
                </div>
    </div>
    <div class="layui-inline" style="margin-left: 15px">
            <label>学生状态:</label>
                <div class="layui-input-inline">
                    <select name="s_status">
                        <option value="" selected="">请选择学生状态</option>
                        <option value="0" >请假中</option>
                        <option value="1" >已回校</option>
                    </select>
                <#--<select name="s_status">
                    <option value="" selected="">请选择学生状态</option>
                    <@my type="quartz_task_status">
                    <#list result as r>
                        <option value="${r.value}" >${r.label}</option>
                    </#list>
                    </@my>
                </select>-->
                </div>
    </div>
        <div class="layui-inline">
            <a class="layui-btn" lay-submit="" lay-filter="searchForm">查询</a>
        </div>
        <div class="layui-inline" >
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
        <div class="layui-inline">
            <a class="layui-btn layui-btn-normal" data-type="addLeaveInfo">添加请假信息</a>
        </div>
    </form>
  </div>
</fieldset>
<div class="layui-form users_list">
    <table class="layui-table" id="test" lay-filter="demo"></table>
    <#--<script type="text/html" id="status">
        <@my type="quartz_task_status">
        <#list result as r>
        {{#  if(d.status == ${r.value}){ }}
        <span class="layui-badge layui-bg-green">${r.label}</span>
        {{#  } }}
        </#list>
        </@my>
    </script>-->
    <script type="text/html" id="quartz_task_status">
        {{#  if(d.status == 0){ }}
        <span class="layui-badge layui-bg-green">请假中</span>
        {{# }else{ }}
        <span class="layui-badge layui-bg-green">已回校</span>
        {{# } }}
    </script>
    <script type="text/html" id="barDemo">
        {{#  if(d.status == 0){ }}
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="paush">已回校</a>
        {{# }else{ }}
        <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="resume">请假中</a>
        {{# } }}
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>

    </script>
</div>
<div id="page"></div>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/tools.js"></script>
<script>
    layui.use(['layer','form','table','laydate'], function() {
        var layer = layui.layer,
                $ = layui.jquery,
                form = layui.form,
                laydate = layui.laydate,
                table = layui.table;


        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var editIndex = layer.open({
                    title : "编辑请假信息",
                    type : 2,
                    content : "${base}/admin/leaveInfo/edit?id="+data.id,
                    success : function(layero, index){
                        setTimeout(function(){
                            layer.tips('点击此处返回请假信息列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        },500);
                    }
                });
                //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
                $(window).resize(function(){
                    layer.full(editIndex);
                });
                layer.full(editIndex);
            }
            if(obj.event === "del"){
                layer.confirm("你确定要删除该请假信息么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("${base}/admin/leaveInfo/delete",{"ids":[data.id]},function (res){
                                if(res.success){
                                    layer.msg("删除成功",{time: 1000},function(){
                                        location.reload();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }

                            });
                        }
                )
            }
            if(obj.event === "paush"){
                layer.confirm("你确定要修改为请假中么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("${base}/admin/leaveInfo/paushStatusById",{"ids":[data.id]},function (res){
                                if(res.success){
                                    layer.msg("修改成功",{time: 1000},function(){
                                        location.reload();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }

                            });
                        }
                )
            }
            if(obj.event === "resume"){
                layer.confirm("你确定要修改为已回校么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("${base}/admin/leaveInfo/resumeStatusById",{"ids":[data.id]},function (res){
                                if(res.success){
                                    layer.msg("修改成功",{time: 1000},function(){
                                        location.reload();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }

                            });
                        }
                )
            }
        });

        var t = {
            elem: '#test',
            url:'${base}/admin/leaveInfo/list',
            method:'post',
            width: $(parent.window).width()-223,
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                groups: 2, //只显示 1 个连续页码
                first: "首页", //显示首页
                last: "尾页", //显示尾页
                limits:[3,10, 20, 30]
            },
            cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            cols: [[
                {type:'checkbox'},
                {field:'studentName', title: '姓名'},
                {field:'studentNumber', title: '学号'},
                {field:'clazz', title: '班级'},
                {field:'reason', title: '原因'},
                {field:'status', title: '请假状态',templet:'#quartz_task_status'},
                {field:'remarks',    title: '备注',width:'15%'},
                {field:'createDate',  title: '请假时间',width:'15%',templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true}, //单元格内容水平居中
                {fixed: 'right', title:'操作',  width: '15%', align: 'center',toolbar: '#barDemo'}
            ]]
        };
        table.render(t);

        var active={
            addLeaveInfo : function(){
                var addIndex = layer.open({
                    title : "添加请假信息",
                    type : 2,
                    content : "${base}/admin/leaveInfo/add",
                    success : function(layero, addIndex){
                        setTimeout(function(){
                            layer.tips('点击此处返回请假信息列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        },500);
                    }
                });
                //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
                $(window).resize(function(){
                    layer.full(addIndex);
                });
                layer.full(addIndex);
            }
        };

        $('.layui-inline .layui-btn-normal').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        form.on("submit(searchForm)",function(data){
            t.where = data.field;
            table.reload('test', t);
            return false;
        });

    });
</script>
</body>
</html>