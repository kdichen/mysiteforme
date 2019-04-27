<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>班级管理编辑</title>
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
    <link rel="stylesheet" href="${base}/static/layui/css/layui.css" media="all"/>
    <style type="text/css">
        .layui-form-item .layui-inline {
            width: 33.333%;
            float: left;
            margin-right: 0;
        }

        @media (max-width: 1240px) {
            .layui-form-item .layui-inline {
                width: 100%;
                float: none;
            }
        }

        .layui-form-item .role-box {
            position: relative;
        }

        .layui-form-item .role-box .jq-role-inline {
            height: 100%;
            overflow: auto;
        }

        .pre-href {
            text-align: right;
            text-align: end;
            font-weight: 900;
            font-size: 25px;
        }

        .href {
            font-weight: 900;
            font-size: 25px;
        }
    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input value="${blogChannel.id}" name="id" type="hidden">
    <div class="layui-form-item">
        <label class="layui-form-label">名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${blogChannel.name}" name="name" lay-verify="required"
                   placeholder="请输入名称">

        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addBlogChannel">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/pinyin.js"></script>
<script>
    var iconShow, $;
    layui.use(['form', 'jquery', 'layer'], function () {
        var form = layui.form,
                layer = layui.layer;
        $ = layui.jquery;

        form.on("submit(addBlogChannel)", function (data) {
            if (undefined === data.field.baseChannel || '0' === data.field.baseChannel || null === data.field.baseChannel) {
                data.field.baseChannel = false;
            } else {
                data.field.baseChannel = true;
            }
            if (undefined === data.field.canComment || '0' === data.field.canComment || null === data.field.canComment) {
                data.field.canComment = false;
            } else {
                data.field.canComment = true;
            }
            if (undefined === data.field.noName || '0' === data.field.noName || null === data.field.noName) {
                data.field.noName = false;
            } else {
                data.field.noName = true;
            }
            if (undefined === data.field.canAduit || '0' === data.field.canAduit || null === data.field.canAduit) {
                data.field.canAduit = false;
            } else {
                data.field.canAduit = true;
            }
            if (data.field.preHref != null && data.field.preHref !== undefined && data.field.preHref !== "") {
                data.field.href = data.field.preHref + data.field.href;
            }
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            //给角色赋值
            $.post("${base}/admin/blogChannel/edit", data.field, function (res) {
                layer.close(loadIndex);
                if (res.success) {
                    parent.layer.msg("班级栏目编辑成功！", {time: 1000}, function () {
                        parent.layer.close(parent.editIndex);
                        //刷新父页面
                        parent.location.reload();
                    });
                } else {
                    layer.msg(res.message);
                }
            });
            return false;
        });
    });

</script>
</body>
</html>