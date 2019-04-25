<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>添加家访记录</title>
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
    <link rel="stylesheet" href="${base}/static/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
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
        /***
        *  ztree 图标变成黄色
        */
        .ztree .line{
            line-height: 0;
            border-top:none;
            float: none;
        }
        .ztree li span.button.ico_docu {
            background-position: -110px 0;
            margin-right: 2px;
            vertical-align: top;
        }
        .multiSelect{ line-height:normal; height:auto; padding:4px 10px; overflow:hidden;min-height:38px; margin-top:-38px; left:0; z-index:99;position:relative;background:none;}
        .multiSelect a{ padding:2px 5px; background:#908e8e; border-radius:2px; color:#fff; display:block; line-height:20px; height:20px; margin:2px 5px 2px 0; float:left;}
        .multiSelect a span{ float:left;}
        .multiSelect a i{ float:left; display:block; margin:2px 0 0 2px; border-radius:2px; width:8px; height:8px; background:url(${base}/static/images/close.png) no-repeat center; background-size:65%; padding:4px;}
        .multiSelect a i:hover{ background-color:#545556;}

        .layui-field-box a{ padding:2px 5px; background:#908e8e; border-radius:2px; color:#fff; display:block; line-height:20px; height:20px; margin:2px 5px 22px 0; float:left;}
        .layui-field-box a span{ float:left;}
        .boxadd{ float:left; display:block; margin:2px 0 0 2px; border-radius:2px; width:8px; height:8px; background-size:65%; padding:4px;}
    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <div class="layui-form-item">
        <label class="layui-form-label">学生姓名</label>
        <div class="layui-input-block">
            <input  type="text"  class="layui-input" name="studentName" lay-verify="required" placeholder="请输入学生姓名">
        </div>
    </div>
    <div id="outLinkUrl">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">添加时间</label>
        <div class="layui-input-block">
            <input type="text" name="publistTime" id="publistTime"   lay-verify="date" placeholder="请选择发布时间" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">内容</label>
        <div class="layui-input-block">
            <div id="content"></div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addVisit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/wangEditor.min.js"></script>
<script type="text/javascript" src="${base}/static/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script>
    layui.use(['form','jquery','layer','upload','laydate'],function(){
        var form      = layui.form,
                $     = layui.jquery,
                upload = layui.upload,
                E = window.wangEditor,
                laydate = layui.laydate,
                imageIndex,
                layer = layui.layer,
                zTreeObj,
                setting  = {callback:{
                    onClick : function (event, treeId, treeNode) {
                        $("#channelNameShow").val(treeNode.name);
                        $("input[name='channelId']").val(treeNode.id);
                    }
                }},
                outLinkUrlHtml = '<div class="layui-form-item">\n' +
                        '                <label class="layui-form-label">外链地址</label>\n' +
                        '                <div class="layui-input-block">\n' +
                        '                    <input  type="text" class="layui-input" lay-verify="required" value = "" name="outLinkUrl"  placeholder="请输入外链地址">\n' +
                        '                </div>\n' +
                        '            </div>';
        //普通图片上传
        var upload_showPic = upload.render({
            elem: '#test_showPic',
            url: '${base}/file/upload/',
            field:'test',
            before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#demo_showPic').attr('src', result); //图片链接（base64）
                });
                imageIndex = layer.load(2, {
                    shade: [0.3, '#333']
                });
            },
            done: function(res){
                layer.close(imageIndex);
                //如果上传失败
                if(res.success === false){
                    return layer.msg('上传失败');
                }
                $("#showPic").val(res.data.url);
            },
            error: function(){
                layer.close(imageIndex);
                //演示失败状态，并实现重传
                var demoText = $('#demoText_showPic');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    upload_showPic.upload();
                });
            }
        });


        //初始赋值
        laydate.render({
            elem: '#publistTime',
            value: new Date()
        });
        var content_editor = new E('#content');
                //图片上传
        content_editor.customConfig.uploadImgServer = '${base}/file/uploadWang';
        content_editor.customConfig.uploadFileName = 'test';
        // 自定义处理粘贴的文本内容(这里处理图片抓取)
        content_editor.customConfig.pasteTextHandle = function (content) {
            if(undefined === content){
                return content;
            }
            if(content.indexOf("src=")<=0){
                return content;
            }
            var loadContent = layer.load(2, {
                shade: [0.3, '#333']
            });
            $.ajax({
                url: "${base}/file/doContent/",
                type: "POST",
                async: false,
                data:{"content":content},
                dataType: "json",
                success:function(res){
                    layer.close(loadContent);
                    content = res.data;
                }
            });
            return content;
        };
        // 关闭粘贴样式的过滤
        content_editor.customConfig.pasteFilterStyle = false;
        content_editor.customConfig.customAlert = function (info) {
            // info 是需要提示的内容
            layer.msg(info);
        };
        content_editor.create();

        form.on('radio(category)', function(data){
            if(data.value === "1"){
                $("#outLinkUrl").empty();
            }
            if(data.value === "2"){
                $("#outLinkUrl").empty().append(outLinkUrlHtml);
            }
        });
        form.on("submit(addVisit)",function(data){
            var c = content_editor.txt.html(),
            ct=content_editor.txt.text();
            if(null === ct || "" === ct || undefined === ct){
                layer.msg("内容不能为空");
                return false;
            }
            if(null === c || "" === c || undefined === c){
                layer.msg("内容不能为空");
                return false;
            }
            c = c.replace(/\"/g, "'");
            ct = ct.replace(/\"/g, "'");
            data.field.content = c;
            data.field.text = ct;
            if(undefined === data.field.top || '0' === data.field.top || null === data.field.top){
                data.field.top = false;
            }else{
                data.field.top = true;
            }

            if(undefined === data.field.recommend || '0' === data.field.recommend || null === data.field.recommend){
                data.field.recommend = false;
            }else{
                data.field.recommend = true;
            }
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            $.ajax({
                url:"${base}/admin/visit/add",
                type:"POST",
                data:JSON.stringify(data.field),
                contentType:"application/json; charset=utf-8",
                dataType:"json",
                success: function(res){
                    layer.close(loadIndex);
                    if(res.success){
                        parent.layer.msg("家访记录添加成功！",{time:1000},function(){
                            parent.layer.close(parent.addIndex);
                            //刷新父页面
                            parent.location.reload();
                        });
                    }else{
                        layer.msg(res.message);
                    }
                }
            });
            return false;
        });

    });
</script>
</body>
</html>