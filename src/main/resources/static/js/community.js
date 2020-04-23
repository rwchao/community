/**
 * 提交一级评论回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,content,1);
}

/**
 * 提交二级评论回复
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId,content,2);
}

function comment2target(targetId,content,type) {
    if(!content){
        alert("回复内容不能为空哦");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parent_id": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if(response.code == 200){
                // $("#comment_section").hide();
                window.location.reload();
            }else {
                if (response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=8ce9fb87a359b3370803&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}
/**
 * 展开二级评论
 * collapse:hides content
 * collapse.in:shows content
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comment = $("#comment-"+id);
    if( comment.hasClass("in") ){
        comment.removeClass("in");
        e.classList.remove("active");
    }else {
        $.getJSON( "/comment/"+id, function( data ) {
            console.log(data);
        });
        comment.addClass("in");
        e.classList.add("active");
    }
}