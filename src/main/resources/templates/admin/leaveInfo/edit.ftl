<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>请假信息编辑</title>
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

    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input value="${leaveInfo.id}" name="id" type="hidden">
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${leaveInfo.studentName}" name="studentName"
                   placeholder="请输入学生姓名">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">学号</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${leaveInfo.studentNumber}" name="studentNumber"
                   placeholder="请输入学生学号">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">班级</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${leaveInfo.clazz}" name="clazz"
                   placeholder="请输入班级">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">原因</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${leaveInfo.reason}" name="reason"
                   placeholder="请输入原因">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">执行用户</label>
        <div class="layui-input-block">
            <select name="params">
                <option value="${currentUser.id}"><#if currentUser.nickName!''>${currentUser.nickName}<#else>${currentUser.loginName}</#if></option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea name="remarks" placeholder="请输入任务备注" class="layui-textarea">${leaveInfo.remarks}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addLeaveInfo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script>
    layui.use(['form', 'jquery', 'layer'], function () {
        var form = layui.form,
                $ = layui.jquery,
                layer = layui.layer;


        form.on("submit(addLeaveInfo)", function (data) {
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            //给角色赋值
            $.post("${base}/admin/leaveInfo/edit", data.field, function (res) {
                layer.close(loadIndex);
                if (res.success) {
                    parent.layer.msg("修改成功！", {time: 1000}, function () {
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