$("#create-item-button").unbind('click').bind('click', function(){
    let item = {
        code: $("#item-code").val(),
        name: $("#item-name").val(),
        model: $("#item-model").val(),
        description: $("#item-description").val(),
        price: $("#item-price").val(),
        amount: $("#item-amount").val(),
        image: $("#item-image").attr("src"),
    }
    $.ajax({
                url: '/items',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(item),
                success: function(data, status, xhr) {
                    window.location = '/';
                },
                error: function(xhr) {
                    $("#message-server-error").empty();
                    for (var key in xhr.responseJSON) {
                        $("#message-server-error").append(xhr.responseJSON[key]);
                        $("#message-server-error").append($("<br>"));
                    }
                    $("#message-server-error br").last().remove();
                    $("#div-server-error").show();
                }
            });
})

$("#img").change(function() {
  if (this.files && this.files[0]) {
    var FR= new FileReader();
    FR.addEventListener("load", function(e) {
      $("#item-image").attr("src", e.target.result);
      $("#item-image").show();
    });
    FR.readAsDataURL( this.files[0] );
  }
})
