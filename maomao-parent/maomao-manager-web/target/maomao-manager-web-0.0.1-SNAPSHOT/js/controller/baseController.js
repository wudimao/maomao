app.controller('baseController',function($scope){
	
	//��ҳ�ؼ�����currentPage����ǰҳ��totalItems���ܼ�¼��itemsPerPage��ÿҳ��¼��
	//perPageOptions����ҳѡ�onChange����ҳ������Զ������ķ���
		$scope.paginationConf={
			currentPage:1,
			totalItems:10,
			itemsPerPage:10,
			perPageOptions:[10,20,30,40,50],
			onChange:function(){
				$scope.reloadList();
			}    				
		};	
		//ˢ��ҳ��
		$scope.reloadList=function(){
			$scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
		}
		
		$scope.selectIds=[];//�û���ѡ��id����
		$scope.updateSelection=function($event,id){
			if($event.target.checked){
			$scope.selectIds.push(id);//push�򼯺����Ԫ��
			}else{
				var index =$scope.ids.indexOf(id);
				$scope.ids.splice(index,1);
			}
		}
		
		$scope.jsonToString=function(jsonString,key){
			var json =JSON.parse(jsonString);
			var str ="";
			for(var i=0;i<json.length;i++){
				if(i>0){
					str+=",";
				}
				str+=json[i][key];
			}
			return str;
		}
		
		
		
});