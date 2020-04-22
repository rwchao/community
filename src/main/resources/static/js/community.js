function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    // console.log(questionId);
    // console.log(commentContent);
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
            if(response.code = 200){
                $("#comment_section").hide();
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}