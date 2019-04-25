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
    <div class="layui-form-item">
        <label class="layui-form-label">获奖内容</label>
        <div class="layui-input-block">
            <textarea name="awardContent" lay-verify="required" placeholder="请输入评语内容" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">获奖学生</label>
        <div class="layui-input-block">
            <input  type="text"  class="layui-input" name="studentName"  placeholder="请输入获奖学生姓名">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">获奖地点</label>
        <div class="layui-input-block">

            <input  type="text"  class="layui-input" name="address"  placeholder="请输入获奖地点">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">获奖级别</label>
        <div class="layui-input-inline">
            <select name="s_rewardLevel">
                <option value="" selected="">请选择获奖级别</option>
                <option value="1" >班(年)级获奖</option>
                <option value="2" >重大获奖</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">教师评语</label>
        <div class="layui-input-block">

            <textarea name="teacherContent"  placeholder="请输入教师评语" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addReward">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script>
    layui.use(['form','jquery','layer'],function(){
        var form      = layui.form,
                $     = layui.jquery,
                layer = layui.layer;


        form.on("submit(addReward)",function(data){
                     if(undefined === data.field.s_rewardLevel || '1' === data.field.s_rewardLevel || null === data.field.s_isAdminReply){
                    data.field.rewardLevel = 1;
                }else{
                    data.field.rewardLevel = 2;
                }


            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            $.post("${base}/admin/reward/add",data.field,function(res){
                layer.close(loadIndex);
                if(res.success){
                    parent.layer.msg("教师评语添加成功！",{time:1000},function(){
                        parent.layer.close(parent.addIndex);
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