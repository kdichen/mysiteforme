<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>获奖记录</title>
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
    <style>
        .detail-body{
            margin: 20px 0 0;
            min-height: 306px;
            line-height: 26px;
            font-size: 16px;
            color: #333;
            word-wrap: break-word;
        }
        /* blockquote 样式 */
        blockquote {
            display: block;
            border-left: 8px solid #d0e5f2;
            padding: 5px 10px;
            margin: 10px 0;
            line-height: 1.4;
            font-size: 100%;
            background-color: #f1f1f1;
        }
        /* code 样式 */
        code {
            display: inline-block;
            *display: inline;
            *zoom: 1;
            background-color: #f1f1f1;
            border-radius: 3px;
            padding: 3px 5px;
            margin: 0 3px;
        }
        pre code {
            display: block;
        }
    </style>
</head>
<body class="childrenBody">
<fieldset class="layui-elem-field">
  <legend>获奖记录</legend>
  <div class="layui-field-box">
    <form class="layui-form" id="searchForm">
    <div class="layui-inline" style="margin-left: 15px">
            <label>学生姓名:</label>
                <div class="layui-input-inline">
                <input type="text" value="" name="s_studentName" placeholder="请输学生" class="layui-input search_input">
                </div>
    </div>
    <div class="layui-inline" style="margin-left: 15px">
            <label>获奖级别:</label>
                <div class="layui-input-inline">
                <select name="s_rewardLevel">
                    <option value="" selected="">请选择获奖级别</option>
                    <option value="1" >班(年)级获奖</option>
                    <option value="2" >重大获奖</option>
                </select>
                </div>
    </div>
        <div class="layui-inline">
            <a class="layui-btn" lay-submit="" lay-filter="searchForm">查询</a>
        </div>
        <div class="layui-inline" >
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
        <div class="layui-inline">
            <a class="layui-btn layui-btn-normal" data-type="addReward">添加获奖记录</a>
        </div>
    </form>
  </div>
</fieldset>
<div class="layui-form users_list">
    <table class="layui-table" id="test" lay-filter="demo"></table>

    <script type="text/html" id="content">
        {{#  if(d.awardContent != "" && d.awardContent != null){ }}
        <span><button lay-event="showcontent" class="layui-btn layui-btn-warm layui-btn-sm">查看获奖</button></span>
        {{#  } else { }}
        <span ></span>
        {{#  } }}
    </script>

    <script type="text/html" id="replyContent">
        {{#  if(d.teacherContent != "" && d.teacherContent != null){ }}
        <span><button lay-event="showReplyContent" class="layui-btn layui-btn-warm layui-btn-sm">查看评语</button></span>
        {{#  } else { }}
        <span ></span>
        {{#  } }}
    </script>
    <script type="text/html" id="isAdminReply">
        {{#  if(d.adminReply == 1){ }}
        <span>班(年)级获奖</span>
        {{# }else{ }}
        <span>重大获奖</span>
        {{# } }}
    </script>

    <script type="text/html" id="barDemo">
        {{# if(d.replyId == null){ }}
        <a class="layui-btn layui-btn-xs" lay-event="edit">修改评语</a>
        {{# } }}
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
</div>
<div id="page"></div>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/tools.js?t=${.now?long}"></script>
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
                    title : "评论获奖",
                    type : 2,
                    content : "${base}/admin/reward/edit?id="+data.id,
                    success : function(layero, index){
                        setTimeout(function(){
                            layer.tips('点击此处返回获奖列表', '.layui-layer-setwin .layui-layer-close', {
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
                layer.confirm("你确定要删除该获奖记录么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("${base}/admin/reward/delete",{"id":data.id},function (res){
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
            if(obj.event === "showcontent"){
                layer.open({
                    type: 1,
                    title: '获奖内容',
                    closeBtn: 0,
                    shadeClose: true,
                    area: ['700px', '500px'],
                    content: '<div class="detail-body" style="margin:20px;">'+data.awardContent+'</div>'
                });
            }
            if(obj.event === "showReplyContent"){
                layer.open({
                    type: 1,
                    title: '教师评语',
                    closeBtn: 0,
                    shadeClose: true,
                    area: ['700px', '500px'],
                    content: '<div class="detail-body" style="margin:20px;">'+data.teacherContent+'</div>'
                });
            }
        });

        var t = {
            elem: '#test',
            url:'${base}/admin/reward/list',
            method:'post',
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                groups: 2, //只显示 1 个连续页码
                first: "首页", //显示首页
                last: "尾页", //显示尾页
                limits:[3,10, 20, 30]
            },
            cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            cols: [[
                {type:'checkbox'},
                {field:'studentName', title: '获奖学生'},
                {field:'awardContent', title: '获奖内容',templet:'#content'},
                {field:'address', title: '获奖地点'},
                {field:'rewardLevel', title: '获奖级别',templet:'#isAdminReply'},
                {field:'teacherContent', title: '教师评语',templet:'#replyContent'},
                {field:'createDate',  title: '获奖时间',width:'15%',templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true}, //单元格内容水平居中
                {fixed: 'right', title:'操作',  width: '15%', align: 'center',toolbar: '#barDemo'}
            ]]
        };
        table.render(t);

        var active={
            addReward : function(){
                var addIndex = layer.open({
                    title : "添加获奖记录",
                    type : 2,
                    content : "${base}/admin/reward/add",
                    success : function(layero, addIndex){
                        setTimeout(function(){
                            layer.tips('点击此处返回获奖列表', '.layui-layer-setwin .layui-layer-close', {
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