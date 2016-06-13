$(function() {
    setCsrfTokenToAjaxHeader();

    $('#form').submit(function() {
        $('#button').prop('disabled', true);
        console.log('submit');
        // TODO ユーザ作成が成功ならログイン処理する
        createUser($('#un').val(), $('#pw').val()
        ).done(function(data) {
            // TODO 成功ならログイン処理する
            console.log('user created.')
            console.log(data);
            $('#form').off('submit');
            $('#form').submit();
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.log('jqXHR : ' + jqXHR);
            console.log('textStatus : ' + textStatus);
            console.log('errorThrown : ' + errorThrown);
            $('#errorMsg').text('ユーザが登録できませんでした。');
        });
        return false;
    })
});

/**
 * Spring SecurityのCSRF対策によるトークンをajax通信で受け渡しできるようにします。
 */
function setCsrfTokenToAjaxHeader() {
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

/**
 *  ユーザ名が登録されているかどうかを確認する
 */
function isUserNameDuplication(userName) {
    console.log(userName);
    
    var sendData = {
        userName : userName
    };
    $.ajax({
        method : 'POST',
        url : 'checkUserNameDuplication',
        contentType : 'application/json;charset=utf-8',
        data : JSON.stringify(sendData),
        dataType : 'json'
    }).done(function(data) {
        console.log('duplication check.');
        if (data == 'success') {
            $('#button').prop('disabled', false);
            console.log('You can create user.');
            $('#errorMsg').text('');
            return false;
        } else {
            $('#button').prop('disabled', true);
            console.log('You cannot create user');
            $('#errorMsg').text('既にそのユーザ名は登録されています。');
            return true;
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log('jqXHR : ' + jqXHR);
        console.log('textStatus : ' + textStatus);
        console.log('errorThrown : ' + errorThrown);
    });
}
/**
 * ユーザ・パスワードを登録する
 */
function createUser(userName, password) {
    console.log('createUser');
    // TODO formからユーザとパスワード取得してセットする
    var sendData = {
        userName : userName,
        password : password
    };
    return $.ajax({
        method : 'POST',
        url : 'createUser',
        contentType : 'application/json;charset=utf-8',
        data : JSON.stringify(sendData),
        dataType : 'json'
    })
}
