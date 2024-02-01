/*<![CDATA[*/
const products = /*[[${productList}]]*/ [];

function filterProducts() {
    const selectedRanges = Array.from(document.querySelectorAll('#priceRangeForm input:checked'))
        .map(checkbox => checkbox.value);

    products.forEach(product => {
        const card = document.getElementById(`productCard-${product.id}`);
        if (matchesPriceRange(product, selectedRanges)) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}

function matchesPriceRange(product, selectedRanges) {
    const sellingPrice = product.minSellingPrice;

    return selectedRanges.some(range => {
        const [min, max] = range.split('-');
        return max === 'max' ? sellingPrice >= parseInt(min) : sellingPrice >= parseInt(min) && sellingPrice < parseInt(max);
    });
}
/*]]>*/