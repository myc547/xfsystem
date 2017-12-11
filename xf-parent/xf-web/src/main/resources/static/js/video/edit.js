$(function (){

    /**
     * 校验
     */
    $("#mediaForm").bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            status: {
                group: '.col-lg-5',
                validators: {
                    notEmpty: {
                        message: '状态不能为空'
                    }
                }
            }
        }
    });

    /**
     * 保存
     */
    $("#saveMedia").click(function() {
        $("#mediaForm").bootstrapValidator('validate');
        var flag = $("#mediaForm").data("bootstrapValidator").isValid();
        var status = $("#status").val();
        var mediaId = $("#fileId").val();
        console.log(status);
        console.log(mediaId);
        if (flag){
            Ajax.post({
                url:  globalPath + 'video/update',
                data: {status: status, mediaId: mediaId},
                success: function (response) {
                    if (response.meta.success) {
                        location.href = globalPath + 'video/list';
                    } else {
                        Tips.error({msg:'提交失败'});
                    }
                }
            });
        }
    });

    /**
     * 取消
     */
    $("#cancel").click(function () {
        location.href = globalPath + 'video/list';
    });
    loadCss();
});


/**
 * 初始化页面
 * @returns
 */
function loadCss(){
    var opera = $("input[name='opera']").val();
    if (opera == 'view'){
        $("#status").attr("disabled",true);
        $("#saveMedia").css("display","none");
        $(".breadcrumb").find(".active").text('查看');
    }
}