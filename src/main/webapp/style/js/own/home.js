var defaultUrl = "search?numberOfElements=5&mark=&model=&minYear=1970&maxYear=2018&minSpeed=0&maxSpeed=400&minPrice=0&maxPrice=100000&currentPage=";
var localhostUrl = "http://localhost:8888/";
var homeUrl = "http://localhost:8888/home";

/*Dear God, I'm sorry for creation of this ugly method*/
function getModifiedUrl(currentPage, pageNumber) {
    var url = window.location.href;
    currentPage += pageNumber;
    var changedUrl;
    if (url === localhostUrl || url === homeUrl) {
        changedUrl = defaultUrl + currentPage;
    } else {
        changedUrl = url.replace(/currentPage=[0-9]/, 'currentPage=' + currentPage);
    }

    if (pageNumber == 1) {
        document.getElementById("next").href = changedUrl;
    } else if (pageNumber == -1) {
        document.getElementById("previous").href = changedUrl;
    } else {
        var idName = "page" + currentPage;
        document.getElementById(idName).href = changedUrl;
    }
}

function notifyMessage() {
    if (Notification.permission !== "granted")
        Notification.requestPermission();
    else {
        var notification = new Notification('Car Shop', {
            icon: 'style/images/success.png',
            body: "Operation successful"
        });

        setTimeout(notification.close().bind(notification), 2000);
    }
}

function addToCartNotification(){
    var notification = new Notification("Car was added to cart", "You can buy it right now or continue purchasing");
}



function changeValue(input, output){
    document.getElementById(output).value = document.getElementById(input).value;
}

function dropAllTabs() {
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    //press first tab on reload
    /* document.getElementById("defaultOpen").click();*/
    if (sessionStorage.getItem("button_number") == null) {
        document.getElementsByClassName("tablinks")[0].click();
    } else {
        document.getElementsByClassName("tablinks")[sessionStorage.getItem("button_number")].click();
    }
}

function rememberButton(number) {
    sessionStorage.setItem("button_number", number);
}

function openTab(evt, tabName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

function changeHeading(newValue) {
    document.getElementById("heading").innerHTML = newValue;
}

function createNotification(title, body){
    var notification = new Notification(title, body);
}
