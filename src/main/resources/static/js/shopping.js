window.onload = function() {
    loadInfo();
}

function loadInfo(){
    $.ajax({
        url: '/items',
        type: 'GET',
        success: function(data, status, xhr) {
            showItems(data);
        },
        error: function() {
            $("#items").append("There is nothing yet!");
        }
    });
}

function showItems(items){
    items.forEach(function(item){
        $("#items").prepend(createItemDiv(item));
    })
}

function createItemDiv(item){
    return `
    <div class="card">
        <img src=` + item.image + ` class="card-img-top" alt="...">
        <div class="card-body-bottom">
            <h5 class="card-title">` + item.name + `(Code: ` + item.code + `)</h5>
            <p class="card-text">Model: ` + item.model + `</p>
            <p class="card-text">` + addDescription(item) + `</p>
            <button onclick=addToCart(` + item.code + `) class="btn btn-primary">$ ` + item.price + `</button><br>` +
            addAmount(item) + `
        </div>
    </div>
   `
}

function addDescription(item){
    if(item.description.length > 50){
        return item.description.substring(0, 50) + '...';
    } else {
        return item.description;
    }
}

function addAmount(item){
    if(item.amount > 0){
        return `<small>Amount: ` + item.amount + `</small>`;
    } else {
        return `<small>Нет на складе</small>`;
    }
}

function addToCart(code){
    let cart = JSON.parse(localStorage.getItem("cart"));
    if(cart){
        cart.push(code);
    } else {
        cart = [];
        cart.push(code);
    }
    localStorage.setItem("cart", JSON.stringify(cart));
}