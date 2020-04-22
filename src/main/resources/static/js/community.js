function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();

    if(!commentContent){
        alert("回复内容不能为空哦");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parent_id": questionId,
            "content": commentContent,
            "type": 1
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