$(function (){
    loadTable();
    $('#videoCreated').click(function () {
        add();
    });
});



/**
 * 新增
 */
function add(){
  location.href = globalPath + 'video/add';
}

/**
 * 审核
 * @param id
 */
function audit(id){
    if (id) {
        var status = mediaStatusCheck(id);
        if (status) {
            if (status != 0) {
                Tips.warn({msg:'已经审核'})
                return false;
            }
        }
        Mbtn.postSubmitParam({action:globalPath + 'video/edit/',param:id});
    }
}

/**
 * 状态检查
 * @param id
 */
function mediaStatusCheck(id) {
    var result;
    if (id) {
        Ajax.post({
            url:globalPath + 'video/checkStatus',
            data:{mediaId:id},
            async: false,
            success:function(response){
                if (response.meta.success){
                    result = response.data;
                }
            }
        });
    }
    return result;
}

/**
 * 删除
 */
function delMedia(id){
    var status = mediaStatusCheck(id);
    if (status) {
        if (status != 0 || status != 2) {
            Tips.warn({msg:'视频已审核通过或已排课, 不能删除'})
            return false;
        }
    }

    Tips.confirm({
        title:'提示',
        msg:'您确认要删除吗?',
        callback:function($this,type,ev){
            if (type === "yes"){
                delMediaAjax(id);
            }
        }
    });
}

/**
 * 删除视频
 * @param id
 * @param mark
 * @returns
 */
function delMediaAjax(id){
    if (id){
        Ajax.post({
            url:globalPath + 'video/del',
            data:{mediaId:id},
            success:function(response){
                if (response.meta.success){
                    Tips.ok({msg:'删除成功'});
                    $('#videoTable').bootstrapTable('refresh',{url:globalPath + 'video/listData'});
                } else {
                    Tips.error({msg: response.meta.message});
                }
            }
        });
    }
}

/**
 * 查看
 * @param id
 */
function viewMedia(id){
    if (id) {
        Mbtn.postSubmitParam({action:globalPath + 'video/view/',param:id});
    }
}