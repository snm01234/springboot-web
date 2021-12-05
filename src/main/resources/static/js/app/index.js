var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-upload').on('click', function () {
            _this.uploadImage();
        });

        $('#btn-deleteFile').on('click', function () {
            _this.deleteImage();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val(),
            fileName: $('#file-name').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {

        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        var author = $('#author').val();
        var username = $('#loginName').val();
        var id = $('#id').val();

        if(username == author) {
            $.ajax({
                type: 'PUT',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('글이 수정되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            alert('본인만 수정할 수 있습니다.');
            window.location.href = '/';
        }
    },
    delete : function () {
        var id = $('#id').val();
        var author = $('#author').val();
        var username = $('#loginName').val();
        if(username == author) {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
            }).done(function() {
                alert('글이 삭제되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            alert('본인만 삭제할 수 있습니다.');
            window.location.href = '/';
        }
    },
    uploadImage : function () {
        var file = $('#img')[0].files[0];
        var formData = new FormData();
        formData.append('data', file);


        $.ajax({
            type: 'POST',
            url: '/api/v1/upload',
            data: formData,
            processData: false,
            contentType: false
        }).done(function (data) {
            $('#result-image').attr("src", data);
            $('#file-name').attr("value", data);
        }).fail(function (error) {
            alert(error);
        });
    },
    deleteImage : function () {
        var fileName = $('#result-image').val();
        var Params = "?fileName=";
        Params += fileName;

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/deleteFile'+Params,
            dataType: 'json',
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            alert("파일이 삭제되었습니다. ");
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();