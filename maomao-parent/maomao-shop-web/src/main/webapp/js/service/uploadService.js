app.service('uploadService',function($http){
	//文件上传
	this.uploadFile=function(){
		var formdata=new FormData();
		formdata.append('file',file.files[0]);//file文件上传框的name
		
		return $http({
			url:'../upload.do',
			method:'post',
			data:formdata,
			headers:{'Content-Type':undefined},
			transformRequest:angular.identity
			
		});
	}
});