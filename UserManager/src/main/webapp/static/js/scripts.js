var rootURL = "http://localhost:8080/Kinetic";

function loginUser(){
    username = $('#username').val();
    password = $('#password').val();
    //?username=' + username + '&password=' +password
    
    $.ajax({
        url: rootURL + '/api/user/login',
        type: 'POST',
        success: function (result) {
            closeOverlay();
        },
        error: function (jqXHR, exception) {
            closeOverlay();
            var msg = '';
            if (jqXHR.status === 404) {
                msg = 'Requested page not found. [404]';
            } else if (jqXHR.status === 500) {
                msg = 'Internal Server Error [500].';
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
        }
    });
    return false;
}