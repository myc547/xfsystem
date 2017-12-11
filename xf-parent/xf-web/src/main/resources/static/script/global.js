
/**
 * form提交
 */
var Mbtn = {
	postSubmit: function (options){
		var form = $('<form></form>');  
	    form.attr('action', options.action);
	    form.attr('method', 'post');  
	    $(document.body).append(form);
	    form.submit(); 
	    document.body.removeChild(form);
	},
	postSubmitParam: function (options){
		var form = $('<form></form>');  
	    form.attr('action', options.action);
	    form.attr('method', 'post');  
	    var type = $('<input type="text" name="type" value="'+options.type+'"/>'); 
	    var param = $('<input type="text" name="param" value="'+options.param+'"/>');
	    form.append(type);  
	    form.append(param);  
	    $(document.body).append(form);
	    form.submit(); 
	    document.body.removeChild(form);
	}
};

/**
 * ajax 提交
 */
var Ajax = {
    get: function (options) {
        $.ajax({
            url: options.url,
            type: 'GET',
            contentType : 'application/json',
            success: options.success,
            error: options.error
        });
    },
    post: function (options) {
        $.ajax({
            url: options.url,
            type: 'POST',
            async: options.async,
            contentType : 'application/json',
            data: JSON.stringify(options.data),
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    },
    postForm: function (options) {
        $.ajax({
            url: options.url,
            type: 'POST',
            contentType : 'application/x-www-form-urlencoded',
            data: options.data,
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    },
    put: function (options) {
        $.ajax({
            url: options.url,
            type: 'PUT',
            contentType : 'application/json',
            data: JSON.stringify(options.data),
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    },
    del: function (options) {
        $.ajax({
            url: options.url,
            type: 'DELETE',
            contentType : 'application/json',
            beforeSend: options.beforeSend,
            success: options.success,
            error: options.error
        });
    }
};