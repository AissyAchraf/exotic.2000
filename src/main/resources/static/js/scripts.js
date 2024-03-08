/*!
    * Start Bootstrap - SB Admin v6.0.2 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2020 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    (function($) {
    "use strict";

    // Add active state to sidbar nav links
    var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
        $("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function() {
            if (this.href === path) {
                $(this).addClass("active");
            }
        });

    // Toggle the side navigation
    $("#sidebarToggle").on("click", function(e) {
        e.preventDefault();
        $("body").toggleClass("sb-sidenav-toggled");
    });
})(jQuery);

$('.modal').on('shown.bs.modal', function() {
  $(this).find('[autofocus]').focus();
});

$('.toast').toast('show');

// Add product to Order script

/*!
   * On click .createOrderLineBtn get the product details
   * Set those details in the create order line Modal
   * Show the create order line Modal

*/
$('.createOrderLineBtn').click(function(){

    $("#VariantIdInput").val($(this).data('variant-id'));
    $("#VariantNameInput").val($(this).data('variant-name'));
    $("#VariantSellingPriceInput").val($(this).data('variant-selling-price'));

    updateTotal();
    updateToPay();
});


/*!
 * Function to update total value based on quantity and unit price
 */
function updateTotal() {
    let quantity = $("#QuantityInput").val();
    let unitPrice = $("#VariantSellingPriceInput").val();
    $("#TotalInput").val(quantity * unitPrice);
}

$('#QuantityInput').on('change', updateTotal);
$('#QuantityInput').on('change', updateToPay);

/*!
 * Function to update total to pay value based on quantity, unit price, if it is gift and discount
 */
function updateToPay() {
    let quantity = $("#QuantityInput").val();
    let unitPrice = $("#VariantSellingPriceInput").val();
    let total = $("#TotalInput").val();

    if($("#IsGiftCheckbox").is(':checked')) {
        $("#ToPayInput").val(0);
    } else if($("#IsHasDiscountCheckbox").is(':checked')) {
        let discountPercentage = $("#DiscountInput").val();
        let discountedTotal = total * (1 - discountPercentage / 100); // Convert percentage to decimal
        $("#ToPayInput").val(discountedTotal);
    } else {
        $("#ToPayInput").val(total);
    }
}

$('#IsGiftCheckbox').on('change', updateToPay);
$('#DiscountInput').on('change', updateToPay);
