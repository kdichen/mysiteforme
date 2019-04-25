<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>体检记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${base}/static/layui/css/layui.css" media="all" />
    <style type="text/css">
        .layui-form-item .layui-inline{ width:33.333%; float:left; margin-right:0; }
        @media(max-width:1240px){
            .layui-form-item .layui-inline{ width:100%; float:none; }
        }
        .layui-form-item .role-box {
            position: relative;
        }
        .layui-form-item .role-box .jq-role-inline {
            height: 100%;
            overflow: auto;
        }

    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input type="hidden" name="id" value="${physicalExamination.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">学生姓名</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input layui-disabled" name="studentName" value="${physicalExamination.studentName}" disabled lay-verify="required" placeholder="请输入学生姓名">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">体检结果</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="physicalExaminationResult" value="${physicalExamination.physicalExaminationResult}" lay-verify="required" placeholder="请输入体检结果">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">复查结果</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="review" value="${physicalExamination.review}" lay-verify="required|number" placeholder="请输入排序值">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addUser">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script>
    layui.use(['form','jquery','layer'],function(){
       var form = layui.form,
           $    = layui.jquery,
           layer = layui.layer;

        form.on("submit(addUser)",function(data){
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            $.post("${base}/admin/physicalExamination/edit",data.field,function(res){
               layer.close(loadIndex);
               if(res.success){
                   parent.layer.msg("编辑成功!",{time:1000},function(){
                       //刷新父页面
                       parent.location.reload();
                   });
               }else{
                   layer.msg(res.message);
               }
            });
            return false;
        });

    });
</script>
</body>
</html>