var reply_func = {
    init : function () {
        var _this = this;
        $('#reply-submit').on('click', function () {
            _this.replySave();
        });

        $('.reply-delete').on('click', function () {
            var rno = $(this).attr("value");
            var replier = $(this).attr("replier");
            //alert("테스트: "+rno);
            _this.replyDelete(rno, replier);
        });

    },
    replySave : function () {
        var data = {
            id : $('#id').val(), // 게시글 번호
            replier: $('#loginName').val(),
            text: $('#reply-text').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/replies/posts/',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 등록되었습니다.');
            window.location.reload();
        }).fail(function (error) {
            alert('댓글 등록에 실패하였습니다. 로그인이 필요합니다.');
            //window.location.href = '/login';
            //alert(JSON.stringify(error));
        });
    },
    replyDelete : function (rno, replier) {
        var reply_number = rno;
        var username = $('#loginName').val();
        var reply_user = replier;
        var userRole = $('#userRole').val();
        if(username == reply_user || userRole == "ADMIN") {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/replies/'+reply_number,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('댓글이 삭제되었습니다.');
            window.location.reload();
        }).fail(function (error) {
            alert('댓글 삭제에 실패하였습니다. 로그인이 필요합니다.');
            //window.location.href = '/login';
            //alert(JSON.stringify(error));
        });
        } else {
            alert('삭제 권한이 없습니다. 작성자 또는 관리자만 삭제 가능합니다.');
        }
    }

};

reply_func.init();