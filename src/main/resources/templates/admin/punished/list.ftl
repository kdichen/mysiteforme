<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>受罚记录</title>
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
    <link href="" rel="stylesheet">
    <link rel="stylesheet" href="https://static.mysiteforme.com/layui-treetable/layui/css/layui.css">
    <style>
        .layui-table .value_col{
            text-align: center;
        }
    </style>
</head>

<body style="margin:10px 10px 0;">
<fieldset class="layui-elem-field">
    <legend>受罚记录</legend>
    <div class="layui-field-box">
        <div class="layui-inline">
            <a class="layui-btn layui-btn-normal" data-type="addUser">添加</a>
        </div>
    </div>
</fieldset>
<div class="layui-form users_list">
    <div id="demo"></div>
</div>
<div class="layui-form users_list">
    <table class="layui-table" id="test" lay-filter="demo"></table>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
</div>
</body>
<script type="text/javascript" src="${base}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/tools.js"></script>
<script type="text/javascript">
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
                    title : "编辑受罚记录",
                    type : 2,
                    content : "${base}/admin/punished/edit?id="+data.id,
                    success : function(layero, index){
                        setTimeout(function(){
                            layer.tips('点击此处返回收受罚记录列表', '.layui-layer-setwin .layui-layer-close', {
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
                layer.confirm("你确定要删除该受罚记录么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("${base}/admin/punished/delete",{"ids":[data.id]},function (res){
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
        });

        var t = {
            elem: '#test',
            url:'${base}/admin/punished/list',
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
                {field:'studentName', title: '学生名称'},
                {field:'studentNumber', title: '学号'},
                {field:'reason', title: '受罚原因'},
                {field:'createDate',  title: '评价时间',width:'15%',templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true}, //单元格内容水平居中
                {field:'updateDate',  title: '修改时间',width:'15%',templet:'<div>{{ layui.laytpl.toDateString(d.updateDate) }}</div>',unresize: true}, //单元格内容水平居中
                {fixed: 'right', title:'操作',  width: '15%', align: 'center',toolbar: '#barDemo'}
            ]]
        };
        table.render(t);




        var active={
            addUser : function(){
                var addIndex = layer.open({
                    title : "添加受罚信息",
                    type : 2,
                    content : "${base}/admin/punished/add",
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

</html>