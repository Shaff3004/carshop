function addItemToCart(id, price) {
    $.ajax({
        url: '/cart_operation',
        type: 'POST',
        data: {carId: id},
        dataType: 'text',
        success: incrementQuantity(id, price)
    });
}

function reduceQuantity(id, price){
    $.ajax({
        url: '/cart_operation',
        type: 'PUT',
        data:{carId: id},
        dataType:'text',
        success: decrementQuantity(id, price)
    });
}

function deleteItemFromCart(id){
    $.ajax({
        url: '/cart_operation',
        type: 'DELETE',
        data:{carId: id},
        dataType: 'text',
        success: deleteDiv(id)
    });
}

function incrementQuantity(id, price){
     var elementId = "#quantity" + id;
     var incrementedQuantity = parseInt($(elementId).html()) + 1;
     $(elementId).html(incrementedQuantity);
     changeSum(incrementedQuantity, id, price);
     changeTotalCost();
}

function decrementQuantity(id, price){
     var elementId = "#quantity" + id;
     var currentQuantity = parseInt($(elementId).html());
     if(currentQuantity === 1){
        deleteDiv(id);
     } else{
        var incrementedQuantity = currentQuantity - 1;
        $(elementId).html(incrementedQuantity);
     }
     changeSum(incrementedQuantity, id, price);
     changeTotalCost();
}

function deleteDiv(id){
   var elementId = "#close" + id;
        $(elementId).remove();
        changeTotalCost();
}

function changeSum(quantity, id, price){
    var priceId = "#price" + id;
    var changedValue = quantity * price;
    $(priceId).html(changedValue);
}

function changeTotalCost(){
    var IDs = $(".product-right1 b[id]").map(function(){
        return this.id;
    }).get();

    var totalCost = 0;
    for(var i = 0; i < IDs.length; i++){
        var id = "#" + IDs[i];
        totalCost += parseInt($(id).html());
    }

    $("#total").html("summary : " + totalCost);
}