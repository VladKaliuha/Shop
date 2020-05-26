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
            <h5 class="card-title">` + item.name + `(Code: <div class="item-code">` + item.code + `</div>)</h5>
            <p class="card-text">Model: ` + item.model + `</p>
            <p class="card-text">` + addDescription(item) + `</p>` +
            addAmount(item) + `
            <div class="group" style="max-width: 30%;display: -webkit-box;margin-left: 30%;">
                <input type="number" value="1" class="amount-to-add" min="0" required>
                <span class="highlight"></span>
                <span class="bar" style="display: contents;"></span>
                <button class="btn btn-primary" onclick="addItem(this)" style="max-width: 40px; margin-left:10px;">+</button>
            </div>
      </div>
    </div>
   `
}

function addAmount(item){
    if(item.amount > 0){
        return `<big><div class="item-amount">Amount: ` + item.amount + `</div></big><br>`;
    } else {
        return `<big><div class="item-amount">Нет на складе</div></big><br>`;
    }
}

function addDescription(item){
    if(item.description.length > 50){
        return item.description.substring(0, 50) + '...';
    } else {
        return item.description;
    }
}

function addItem(button){
    let item = $(button).parent().parent().parent();
    let amountToAdd = $(item).find(".amount-to-add").val();
    let itemCode = $(item).find(".item-code")[0].innerHTML;
    let itemAmount = $(item).find(".item-amount")[0];
    $.ajax({
        url: '/items/{code}'.replace("{code}", itemCode),
        type: 'PUT',
        data: {
            amount: amountToAdd
        },
        success: function(data, status, xhr) {
            itemAmount.innerHTML = "Amount: " + data;
        }
    });
}