var startListTag = "<ul>";
var endListTag = "</ul>";
var errorMessages = new Array();

function validateForm(){
    document.getElementById("error").innerHTML = "";

    validateName();
    validateLogin();
    validateEmail();
    validatePassword();
    if(errorMessages.length > 0){
        for(var i = 0; i <= errorMessages.length - 1; i++){
            document.getElementById("error").innerHTML += startListTag + errorMessages[i] + endListTag;
        }
        errorMessages.length = 0;
        return false;
    }
    return true;
}

function validateName(){
var name = document.forms["registration_form"]["name"].value;

    if(name== null || name == ""){
        errorMessages.push("Field name cannot be empty");
    }

    if(name.length < 5){
        errorMessages.push("Name length cannot be less than 5");
    }

    if(name.length > 40){
        errorMessages.push("Name length cannot be greater than 40");
    }

    var regexp = /^[a-zA-Zа-яА-Я-'ёЁ]+[ ]{1}[a-zA-Zа-яА-Я'-ёЁ]+$/;
    if (!regexp.test(name)) {
        errorMessages.push("Incorrect name. Example: [Surname] [Name]");
    }
}

function validateLogin(){
var login = document.forms["registration_form"]["login"].value;
    if(login == null || login == ""){
        errorMessages.push("Field login cannot be empty");
    }

    if(login.length < 4){
        errorMessages.push("Login length cannot be less than 4");
    }

    if(login.length > 16){
        errorMessages.push("Login length cannot be greater than 16");
    }

    var regexp = /^[a-zA-Z]+$/;
    if (!regexp.test(login)) {
        errorMessages.push("Login must have only a latin alphabet");
    }
}

function validateEmail(){
var email = document.forms["registration_form"]["email"].value;
    if(email == null || email == ""){
        errorMessages.push("Field email cannot be empty");
    }

    var regexp = /^([a-z0-9.-]+)@([a-z0-9.-]+).([a-z.]{2,6})$/;
    if (!regexp.test(email)) {
        errorMessages.push("Wrong format of email");
    }
}

function validatePassword(){
var password = document.forms["registration_form"]["password"].value;
var confirmPassword = document.forms["registration_form"]["confirmPassword"].value;
    if(password == null || password == ""){
        errorMessages.push("Field password cannot be empty");
    }

    if(password.length < 8){
        errorMessages.push("Password length cannot be less than 8");
    }

    if(password.length > 16){
        errorMessages.push("Password length cannot be greater than 16");
    }

    var regexp = /^[a-zA-Z0-9]+$/;
    if (!regexp.test(password)) {
        errorMessages.push("Password must have only a latin alphabet and digits");
    }

    if(confirmPassword == null || confirmPassword == ""){
        errorMessages.push("Confirmation password field cannot be empty");
    }

    if (password.length != confirmPassword.length) {
        errorMessages.push("Length of password doesn't coincide with confirm password length");
    }

    if (password != confirmPassword) {
        errorMessages.push("Password confirmation does not match.");
    }
}