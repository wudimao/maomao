app.controller('indexController',function($scope,loginService){
	
	$scope.showLoginName=function(){
		loginService.loginName().success(
				function(respone){
					$scope.loginName=respone.loginName;
					}
				);  	
			}
});