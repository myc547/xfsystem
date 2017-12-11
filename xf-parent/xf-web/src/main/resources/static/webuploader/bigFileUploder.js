(function( $ ){
    $(function(){
        var $queue = $("#fileList"),
        uploader = WebUploader.create({
            auto: false,
            swf: globalPath+ 'webuploader/Uploader.swf',
            server: globalPath +'fileUploader/bigFile',
            pick: {
                id: '#picker',
                multiple:false
            },
            chunked: true, //开启分块上传
            chunkSize: 10 * 1024 * 1024,
            chunkRetry: 3,//网络问题上传失败后重试次数
            threads: 5, //上传并发数
            fileSizeLimit: 2000 * 1024 * 1024,//最大2GB
            fileSingleSizeLimit: 2000 * 1024 * 1024,
            fileNumLimit:1,

            resize: false//不压缩
        });

        //加入队列之前
        uploader.on('beforeFileQueued', function(file){
            uploader.reset();
            cleanFiles();
            uploader.options.formData.guid = WebUploader.guid();
            if (!validateFileExt(file)){
                var $msg = $('<div class="item"><h4 class="red-color">不支持文件格式</h4></div>');
                $msg.appendTo( $queue );
            }

        });

        //加入队列
        uploader.on('fileQueued', function (file) {
            if (validateFileExt(file)){
                addFile( file );
            }
        });

        //状态更新
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo($li).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });

        //上传成功
        uploader.on('uploadSuccess', function (file) {
            updateStatus(file);
            setTimeout(function(){
                $('#' + file.id).find(".progress").fadeOut();
                $(".removeBtn").hide();
                $(".uploadBtn").hide();
            }, 2000);
        });

        //上传出错
        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");

            $('.uploadBtn').attr('fileId',file.id);
            $('.uploadBtn').text('重试');
            $(".uploadBtn").click(function () {
                uploader.retry(uploader.getFile($(this).attr("fileId")));
            });
        });

        //错误
        uploader.on('error', function(code){
            var msg;
            if (code == 'Q_EXCEED_NUM_LIMIT') {
                msg = '只能上传单个文件';
            }
            if (code == 'Q_EXCEED_SIZE_LIMIT') {
                msg = '文件不能超过2GB';
            }
            if ($('.red-color') && $('.red-color').length < 1) {
                var $msg = $('<div class="item"><h4 class="red-color">'+msg+'</h4></div>');
                $msg.appendTo( $queue );
            }
        });

        //添加文件
        function addFile(file){
            var $li = $('<div id="' + file.id + '" class="item">'
                +'<h4 class="info">' + file.name + '，文件大小：'+ WebUploader.formatSize(file.size)+'</h4>'
                +'<p class="state success-status">正在计算文件...请等待计算完毕后再点击上传！</p>'
                +'</div>');
            $li.appendTo( $queue );
            addUploaderBtn(file);
            $(".removeBtn").click(function () {
                uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
                cleanFiles();
            });
            md5Calculation(file);
        };

        //清楚队列
        function cleanFiles() {
            $queue.empty();
        };

        //添加上传按钮
        function addUploaderBtn(file){
            var $btn = $('<div class="btns">' +
                '<div class="removeBtn" fileId = "'+file.id+'">删除</div>' +
                '<div class="uploadBtn state-ready hidden">开始上传</div></div>');
            $btn.appendTo( $queue );
            $(".uploadBtn").click(function () {
                var status = $(this).attr("status");
                if (status == "suspend") {
                    $(this).html("继续上传");
                    $(this).attr("status", "continuous");
                    uploader.stop(true);
                } else {
                    $(this).html("暂停");
                    $(this).attr("status", "suspend");
                    uploader.upload();
                }
            });
        }

        //md5验证
        function md5Calculation(file){
            uploader.md5File(file)
                .then(function (fileMd5){
                    file.wholeMd5 = fileMd5;
                    uploader.options.formData.md5value = file.wholeMd5;
                    $('#' + file.id).find('p.state').text('文件计算完毕，可以点击上传了');
                    md5Validator(fileMd5, file);
                });
        };


        //md5异步校验
        function md5Validator(fileMd5, file){
            Ajax.post({
                url: globalPath + 'fileUploader/isMd5Exist',
                data:{fileMd5: fileMd5,fileName: file.name,fileID: file.id},
                success:function (result) {
                    if (result.meta.success){
                        $('.uploadBtn').removeClass('hidden');
                        if (result.data){
                            uploader.removeFile(file, true);
                        }
                    }
                }
            });
        };

        //更新文件状态
        function updateStatus(file) {
            $('#' + file.id).find('p.state').text('已上传');
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
        };

        //验证文件格式
        function validateFileExt(file){
            var ext = file.ext;
            ext = ext.toLowerCase();
            if (ext!="mp4" && ext!="rmvb" && ext!="avi" && ext!="wmv"
                && ext!="wma" && ext!="mid" && ext!="3gp" && ext!="mid") {
               return false;
            }
            return true;
        }

    });
})( jQuery );