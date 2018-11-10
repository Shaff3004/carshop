function checkLogin(login) {
    $.ajax({
        url: '/crud_checker?login=' + login,
        dataType: 'text',
        success: function (data) {
            $("#loginExists").html(data)
        }
    });
}

function checkEmail(email) {
    $.ajax({
        url: '/crud_checker?email=' + email,
        dataType: 'text',
        success: function (data) {
            $("#emailExists").html(data)
        }
    });
}