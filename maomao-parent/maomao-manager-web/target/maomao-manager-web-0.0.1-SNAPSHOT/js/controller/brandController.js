app.controller('brandController',function($scope,brandService,$controller){
	
		$controller('baseController',{$scope:$scope});
	
    		$scope.findAll=function(){
    			brandService.findAll().success(
    					function(respone){
        					$scope.list=respone;
        					}
    					);  	
    				}
    		
    			$scope.findPage=function(page,size){
    				brandService.findPage(page,size).success(
    					function(response){
    						$scope.list=response.rows;
    						$scope.paginationConf.totalItems=response.total;
    					}		
    				);
    			}
    			$scope.save=function(){
    				var object=null;
    				if($scope.entity.id!=null){
    					object=brandService.update($scope.entity);
    				}
    				else{
    					object=brandService.add($scope.entity);
    				}
    				object.success(
    					function(respone){
    						if(respone.success){
    							$scope.reloadList();	
    						}else{
    							alert(respone.message);
    						}
    					}		
    				);
    			}
    			
    			$scope.findById=function(id){
    				brandService.findById(id).success(
    					function(respone){
    						$scope.entity=respone;
    					}		
    				);
    			}
 				
    			
    			$scope.delete=function(){
    				brandService.delete($scope.selectIds).success(
    					function(respone){
    						if(respone.success){
    							$scope.reloadList();	
    						}else{
    							alert(respone.message);
    						}
    					}		
    				);
    			}
    			//������ѯ
    			$scope.searchEntity={};
    			$scope.search=function(page,size){
    				brandService.search(page,size,$scope.searchEntity).success(
        					function(response){
        						$scope.list=response.rows;
        						$scope.paginationConf.totalItems=response.total;
        					}		
        				);
    			}
    			
    			
    	});