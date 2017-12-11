
var Tips = {
   ok: function (options){
	   Lobibox.notify('success', {
           size: 'normal',
           title: '提示',
           msg:options.msg,
           sound: false,
           delayIndicator: false,
           delay: options.delay == null ? 2000 : options.delay
       });
   },
   
   error: function (options){
	   Lobibox.notify('error', {
           size: 'normal',
           title: options.title == null ? '错误' : options.title,
           msg:options.msg,
           sound: false,
           delayIndicator: false,
           delay: options.delay == null ? 10000 : options.delay
       });
   },
   
   warn :  function (options){
	   Lobibox.notify('warning', {
           size: 'normal',
           title: '警告',
           msg:options.msg,
           sound: false,
           delayIndicator: false,
           delay: options.delay == null ? 3000 : options.delay
       });
   },
   
   confirm :  function (options){
	   Lobibox.confirm({
		   title:options.title,
           msg:options.msg,
           buttons:{
			yes:{
				'class': 'btn btn-danger btn-sm',
				text:'确认',
				closeOnClick:true
			},
			no:{
				'class': 'btn btn-default btn-sm',
				text:'取消',
				closeOnClick:true
			}
		},
           iconClass:'',
           callback:options.callback
       });
   }
   
}