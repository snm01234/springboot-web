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

        $('#btn-uploadSave').on('click', function () {
            _this.uploadImageSave();
        });
        $('#btn-uploadUpdate').on('click', function () {
            _this.uploadImageUpdate();
        });

        $('#btn-deleteFile').on('click', function () {
            _this.deleteImageUpdate();
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
            //alert(JSON.stringify(error));
            alert(JSON.stringify(error));
            markingErrorField(error);
        });
    },
    update : function () {

        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
            fileName: $('#file-name').val()
        };
        var author = $('#author').val();
        var username = $('#loginName').val();
        var id = $('#id').val();
        var userRole = $('#userRole').val();


        if(username == author || userRole == "ADMIN") {
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
                markingErrorField(error);
                //alert(JSON.stringify(error));
            });
        } else {
            alert('수정 권한이 없습니다.');

        }
    },
    delete : function () {
        var id = $('#id').val();
        var author = $('#author').val();
        var username = $('#loginName').val();
        var userRole = $('#userRole').val();
        var _this = this;
        if(username == author || userRole == "ADMIN") {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
            }).done(function() {
                _this.deleteImage();
                alert('글이 삭제되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            alert('삭제 권한이 없습니다.');
        }
    },
    uploadImageSave : function () {
        var file = $('#img')[0].files[0];
        var formData = new FormData();
        formData.append('data', file);
        var _this = this;

        $.ajax({
            type: 'POST',
            url: '/api/v1/upload',
            data: formData,
            processData: false,
            contentType: false
        }).done(function (data) {
            $('#result-image').attr("src", data);
            $('#file-name').attr("value", data);
            _this.save();
        }).fail(function (error) {
            _this.save();
            //alert("첨부할 파일이 없습니다.");
            //alert(error);
        });
    },
    uploadImageUpdate : function () {
        var file = $('#img')[0].files[0];
        var formData = new FormData();
        formData.append('data', file);
        var author = $('#author').val();
        var username = $('#loginName').val();
        var userRole = $('#userRole').val();
        var _this = this;

        if(username == author || userRole == "ADMIN") {
        _this.deleteImage();
        $.ajax({
            type: 'POST',
            url: '/api/v1/upload',
            data: formData,
            processData: false,
            contentType: false
        }).done(function (data) {
            $('#result-image').attr("src", data);
            $('#file-name').attr("value", data);
            _this.update();
        }).fail(function (error) {
            _this.update();
            //alert("첨부할 파일이 없습니다.");
            //alert(error);
        });
        } else {
            alert('수정 권한이 없습니다.');
        }
    },
    deleteImage : function () {
        var author = $('#author').val();
        var username = $('#loginName').val();
        var fileName = $('#file-name').val();
        var Params = "?fileName=";
        var userRole = $('#userRole').val();
        Params += fileName;
        if(username == author || userRole == "ADMIN") {
            if(fileName != "") {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/deleteFile'+Params,
                dataType: 'text',
                contentType: "application/json; charset=utf-8"
            }).done(function () {
                //alert("파일이 삭제되었습니다. ");
                $('#file-name').attr("value", "");
                $('#result-image').attr("src", "");
            }).fail(function (error) {
                //alert("삭제할 파일이 없습니다.")
                //alert(JSON.stringify(error));
            });
            }
        } else {
            alert('삭제 권한이 없습니다.');
        }

    },
    deleteImageUpdate : function () {
        var author = $('#author').val();
        var username = $('#loginName').val();
        var fileName = $('#file-name').val();
        var Params = "?fileName=";
        var userRole = $('#userRole').val();
        Params += fileName;
        var _this = this;
        if(username == author || userRole == "ADMIN") {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/deleteFile'+Params,
            dataType: 'text',
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            //alert("파일이 삭제되었습니다. ");
            $('#file-name').attr("value", "");
            $('#result-image').attr("src", "");
            _this.update();
        }).fail(function (error) {
            alert("삭제할 파일이 없습니다.")
            //alert(JSON.stringify(error));
        });
        } else {
            alert('삭제 권한이 없습니다.');
        }
    }

};

main.init();