function add_to_cart(pId, pName, pPrice) {
    let cart = localStorage.getItem("cart");
    if (cart == null) {
        let products = [];
        let product = { productId: pId, productName: pName, productQuantity: 1, productPrice: pPrice };
        products.push(product);
        localStorage.setItem("cart", JSON.stringify(products));
        console.log("New product Added.!");
    } else {
        let pCart = JSON.parse(cart);
        let oldProduct = pCart.find((product) => product.productId == pId);
        if (oldProduct) {
            pCart.map((product) => {
                if (product.productId == oldProduct.productId) {
                    product.productQuantity = oldProduct.productQuantity + 1;
                }
                return product;
            });
            localStorage.setItem("cart", JSON.stringify(pCart));
            console.log("Same Product Added again.!");
        } else {
            let product = { productId: pId, productName: pName, productQuantity: 1, productPrice: pPrice };
            pCart.push(product);
            localStorage.setItem("cart", JSON.stringify(pCart));
            console.log("New product Added.!");
        }
    }
    updateCart();
}

function updateCart() {
    let cartString = localStorage.getItem("cart");
    let cart = JSON.parse(cartString);
    if(cart == null || cart.length == 0) {
        console.log("Cart is empty.!");
        $(".cart-items").html("( 0 )");
        $(".cart-body").html("<h3>Cart does not have any items</h3>");
        $(".checkout-button").addClass("disabled");
    } else {
        $(".cart-items").html("( " + cart.length + " )");
        console.log(cart);
        let table = `
            <table class='table'>
            <thead class='thread-light'>
                <tr>
                    <th>Item Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            
        `;

        table = table + `</table>`;
    }
}

$(document).ready(function () {
    updateCart();
})