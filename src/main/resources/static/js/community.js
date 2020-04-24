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
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comment.addClass("in");
            // 标记二级评论展开状态
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comment.addClass("in");
                // 标记二级评论展开状态
                e.classList.add("active");
            });
        }
    }
}