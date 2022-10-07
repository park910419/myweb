/**
 * 2022.09.26 댓글 모듈
 */
 
 console.log("Reply Module start");
 
 var replyService = (function(){
	
	//ajax방식
	function add(reply, callback) {
		console.log("reply add..");
		
		$.ajax({
			type: 'post',
			url: '/reply/add',
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			success: function(result, status, xhr){
				if(callback){
					callback(result);
				}
			},
			error: function(xhr, status, er) {
				if(error){
					error(er);
				}
			}
		});
		
	}
	
	//getJSON방식
	function getList(param, callback, error){
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/reply/list/" + bno + "/" + page + ".json", function(data){
			if(callback){
				callback(data.replyCnt, data.list);
			}
		}).fail(function(xhr, status, err){
			if(error){
				error();
			}
		});
	}
	
	function get(rno, callback, error){
		$.get("/reply/" + rno + ".json", function(result){
			if(callback){
				callback(result);
			}
		}).fail(function(xhr, status, err){
			if(error){
				error();
			}
		});
	}
	
	function update(reply, callback, error){
		console.log("수정 댓글:" + reply.seqno);
		$.ajax({
			type: 'put', 
			url: "/reply/" + reply.seqno,
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			
			success: function(result, status, xhr){
				if(callback){
					callback(result);
				}
			},
			
			error: function(){
				if(error){
					error(er);
				}
			}
		});
	}
	
	function remove(rno, callback, error){
		console.log("삭제댓글:" + rno);
		$.ajax({
			type: 'delete', 
			url: "/reply/" + rno,
			
			success: function(result, status, xhr){
				if(callback){
					callback(result);
				}
			},
			
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}

	return {
		add:add, 
		getList:getList,
		get:get,
		update:update,
		remove:remove
	};
	
})();