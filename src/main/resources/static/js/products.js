$(document).ready(function() {
    var categoryId = $("#CategoryId").val();
    //var token = $("meta[name='_csrf']").attr("content");
    //var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({

        url: 'http://localhost:8081/api/products/findAllByCategory?categoryId=' + categoryId,
        type: 'GET',
        dataType:'json',
        async: true,
//        contentType: 'jsonp',
        //beforeSend: function( xhr ) {
        //  xhr.setRequestHeader(header, token);
        //},
        success: function(response) {

            // Clear the existing product list
            $('#productList').empty();

            console.log(response);

            $.each(response, function(index, product) {
                var productHtml = '<div class="col-md-4 col-sm-12 col-xs-12 mb-3">' +
                    '<div class="card p-3 h-100" id="productCard-' + product.id + '">' +
                    '<div class="card-body d-flex flex-column">' +
                    '<div class="d-flex justify-content-center align-items-center">' +
                    '<div class="image">' +
                    '<a href="/product?productId=' + product.id + '"><img src="/display?id=' + product.image.id + '" class="w-100"></a>' +
                    '</div>' +
                    '</div>' +
                    '<a class="nav-link mt-auto" href="/product?productId=' + product.id + '">' +
                    '<div class="card-title text-center mt-2 mb-2 text-dark">' +
                    '<h5><strong>' + product.name + '</strong></h5>' +
                    '<span class="text-center ' + (product.isInStock ? 'text-success' : 'text-danger') + '">' + (product.isInStock ? 'En stock' : 'Rupture de stock') + '</span>' +
                    '</div>' +
                    '<p class="text-center text-secondary">' + (product.minSellingPrice != null ? 'à partir de <strong>' + product.minSellingPrice + ' Dh</strong>' : '') + '</p>' +
                    '<p></p>' +
                    '</a>' +
                    '<button class="btn btn-secondary mt-auto"><i class="fas fa-shopping-cart"></i> Add to cart</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>';

                // Append the product HTML to the product list
                $('#productList').append(productHtml);
            });
        },
        error: function(error) {
            console.error('Error fetching product data:', error);
        }
    });
});