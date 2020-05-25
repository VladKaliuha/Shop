$(document).ready(function() {
    loadInfo();
});

function loadInfo(){
    let cart = JSON.parse(localStorage.getItem("cart"));
    $.ajax({
        url: '/items/cart',
        type: 'GET',
        data: {allId:cart},
        success: function(data, status, xhr) {
            showItems(data);
            $("#order-button").show();
        },
        error: function() {
            $("#items").append("There is nothing yet!");
        }
    });
}

function showItems(items){
    let totalPrice = 0;
    $("#items > table > tbody").empty();
    items.forEach(function(item){
        appendItem(item);
        totalPrice = totalPrice + parseInt(item.price);
    })
    $("#total-price")[0].innerHTML = "Total price: " + totalPrice +"$";
}

function appendItem(item){
    $("#items > table > tbody").append(
        `
            <tr>
              <th scope="row">` + item.code + `</th>
              <td>` + item.name + `</td>
              <td>` + item.model + `</td>
              <td>` + item.price + `$</td>
            </tr>
        `
    )
}

$("#order-button").click(function(){
    let cart = JSON.parse(localStorage.getItem("cart"));
    if(cart){
        $.ajax({
            url: '/items/cart',
            type: 'POST',
            data: {allId:cart},
            success: function(data, status, xhr) {
                localStorage.removeItem("cart");
                window.location = "/";
            }
        });
    }
})