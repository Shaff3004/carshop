var startListTag = "<ul>";
var endListTag = "</ul>";
var errorMessages = new Array();

$(document).ready(function () {
    $("form[name='registration_form']").submit(function () {
        $("#error").empty();

        var name = $("form[name='registration_form'] input[name='name']").val();
        var login = $("form[name='registration_form'] input[name='login']").val();
        var email = $("form[name='registration_form'] input[name='email']").val();
        var password = $("form[name='registration_form'] input[name='password']").val();
        var confirmPassword = $("form[name='registration_form'] input[name='confirmPassword']").val();
        var genderRadios = $("form[name='registration_form'] input[type='radio']");
        var captcha = $("form[name='registration_form'] input[name='captcha']").val();
        validateName(name);
        validateLogin(login);
        validateEmail(email);
        validatePassword(password);
        validateConfirmPassword(password, confirmPassword);
        validateGender(genderRadios);
        validateCaptcha(captcha);

        if (errorMessages.length > 0) {
            for (var i = 0; i <= errorMessages.length - 1; i++) {
                $("#error").append(startListTag + errorMessages[i] + endListTag)
            }
            errorMessages.length = 0;
            return false;
        }
        return true;
    });

    $("form[name='login_form']").submit(function () {
        $("#loginError").empty();

        var login = $("form[name='login_form'] input[name='login']").val();
        var password = $("form[name='login_form'] input[name='password']").val();
        var captcha = $("form[name='login_form'] input[name='captcha']").val();

        validateLogin(login);
        validatePassword(password);
        validateCaptcha(captcha);

        if (errorMessages.length > 0) {
            for (var i = 0; i <= errorMessages.length - 1; i++) {
                $("#loginError").append(startListTag + errorMessages[i] + endListTag)
            }
            errorMessages.length = 0;
            return false;
        }
        return true;
    });

    $("form[name='confirmation']").submit(function(){
        $("#confirmationError").empty();

        var name = $("form[name='confirmation'] input[name='firstname']").val();
        var email = $("form[name='confirmation'] input[name='email']").val();
        var cardNumber = $("form[name='confirmation'] input[name='cardnumber']").val();


        validateName(name);
        validateEmail(email);
        validateCardNumber(cardNumber);

        if (errorMessages.length > 0) {
            for (var i = 0; i <= errorMessages.length - 1; i++) {
                $("#confirmationError").append(startListTag + errorMessages[i] + endListTag)
            }
            errorMessages.length = 0;
            return false;
        }
        return true;
    });

});


function validateName(name) {
    if (name == null || name == "") {
        errorMessages.push("Field \"name\" cannot be empty");
        return false;
    }

    if (name.length < 5) {
        errorMessages.push("Name length cannot be less than 5");
        return false;
    }

    if (name.length > 40) {
        errorMessages.push("Name length cannot be greater than 40");
        return false;
    }

    var regexp = /^[a-zA-Zа-яА-Я-'ёЁ]+[ ]{1}[a-zA-Zа-яА-Я'-ёЁ]+$/;
    if (!regexp.test(name)) {
        errorMessages.push("Incorrect name. Example: [Surname] [Name]");
        return false;
    }
}

function validateLogin(login) {
    if (login == null || login == "") {
        errorMessages.push("Field \"login\" cannot be empty");
        return false;
    }

    if (login.length < 4) {
        errorMessages.push("Login length cannot be less than 4");
        return false;
    }

    if (login.length > 16) {
        errorMessages.push("Login length cannot be greater than 16");
        return false;
    }

    var regexp = /^[a-zA-Z]+$/;
    if (!regexp.test(login)) {
        errorMessages.push("Login must have only a latin alphabet");
        return false;
    }
}

function validateEmail(email) {
    if (email == null || email == "") {
        errorMessages.push("Field \"email\" cannot be empty");
        return false;
    }

    var regexp = /^([a-z0-9.-]+)@([a-z0-9.-]+).([a-z.]{2,6})$/;
    if (!regexp.test(email)) {
        errorMessages.push("Wrong format of email");
        return false;
    }
}

function validatePassword(password) {
    if (password == null || password == "") {
        errorMessages.push("Field \"password\" cannot be empty");
        return false;
    }

    if (password.length < 8) {
        errorMessages.push("Password length cannot be less than 8");
        return false;
    }

    if (password.length > 16) {
        errorMessages.push("Password length cannot be greater than 16");
        return false;
    }

    var regexp = /^[a-zA-Z0-9]+$/;
    if (!regexp.test(password)) {
        errorMessages.push("Password must have only a latin alphabet and digits");
        return false;
    }
}

function validateConfirmPassword(password, confirmPassword) {
    if (confirmPassword == null || confirmPassword == "") {
        errorMessages.push("Confirmation password field cannot be empty");
        return false;
    }

    if (password.length != confirmPassword.length) {
        errorMessages.push("Length of password doesn't coincide with confirm password length");
        return false;
    }

    if (password != confirmPassword) {
        errorMessages.push("Password confirmation does not match.");
        return false;
    }
}

function validateGender(radios) {
    var genderValue = false;
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked == true) {
            genderValue = true;
            break;
        }
    }
    if (!genderValue) {
        errorMessages.push("Choose your gender");
        return false;
    }
}

function validateCaptcha(captcha) {
    if (captcha == null || captcha == "") {
        errorMessages.push("Enter captcha code");
        return false;
    }

    var regexp = /^[0-9]+$/;
    if (!regexp.test(captcha)) {
        errorMessages.push("Captcha code must have only digits");
        return false;
    }

    if (captcha.length != 6) {
        errorMessages.push("Captcha code length must be 6");
        return false;
    }
}

function validateCardNumber(cardNumber){
    if(cardNumber == null || cardNumber == ""){
        errorMessages.push("Enter your card number");
        return false;
    }

    if (cardNumber.length < 15) {
        errorMessages.push("Card number length cannot be less than 16");
        return false;
    }

    if (cardNumber.length > 16) {
        errorMessages.push("Card number length cannot be greater than 16");
        return false;
    }

    var regexp = /^[0-9]+$/;
    if (!regexp.test(cardNumber)) {
        errorMessages.push("Card number have to contain only digits");
        return false;
    }
}