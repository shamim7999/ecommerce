function add_to_cart(pId, pName, pPrice) {
    let cart = localStorage.getItem("cart");
    if (cart == null) {
        let products = [];
        let product = { productId: pId, productName: pName, productQuantity: 1, productPrice: pPrice };
        products.push(product);
        localStorage.setItem("cart", JSON.stringify(products));
        console.log("New product Added.!");
        toastMessage(product.productQuantity + " " + product.productName + " Added !");
    } else {
        let pCart = JSON.parse(cart);
        let oldProduct = pCart.find((product) => product.productId === pId);
        console.log("PID: ")
        console.log(pId)
        if (oldProduct) {
            pCart.map((product) => {
                if (product.productId === oldProduct.productId) {
                    product.productQuantity = oldProduct.productQuantity + 1;
                }
                return product;
            });
            localStorage.setItem("cart", JSON.stringify(pCart));
            console.log("Same Product Added again.!");
            //toastMessage(product.productQuantity + " " + product.productName + " Added !");
        } else {
            let product = { productId: pId, productName: pName, productQuantity: 1, productPrice: pPrice };
            pCart.push(product);
            localStorage.setItem("cart", JSON.stringify(pCart));
            console.log("New product Added.!");
            toastMessage(product.productQuantity + " " + product.productName + " Added !");
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
        $(".checkout-btn").attr("disabled", true);
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
        let totalPrice = 0;
        cart.map((product) => {
            table += `
                <tr>
                    <td>${product.productName}</td>
                    <td>${product.productPrice}</td>
                    <td>${product.productQuantity}</td>
                    <td>${product.productPrice * product.productQuantity}</td>
                    <td>
                        <button onclick='deleteFromCart(${product.productId});' type="submit" class="btn btn-danger btn-sm">Remove</button>                        
                    </td>
                </tr>
            `
            totalPrice += product.productPrice * product.productQuantity;
        })

        table = table + `
            <tr>
                <td colspan="5" style="text-align: right; font-weight: bold;"> Total Price: ${totalPrice} BDT</td>
            </tr>
            </table>
        `;
        $(".cart-body").html(table);
        $(".checkout-btn").attr("disabled", false);
    }
}

const deleteFromCart = (pId)=> {
    let cartString = localStorage.getItem("cart");
    let cart = JSON.parse(cartString);

    cart = cart.filter((p) => parseInt(p.productId) !== pId)

    localStorage.setItem("cart", JSON.stringify(cart));

    toastMessage(" Removed !");
    updateCart();
}

function toastMessage(msg) {
    // Get the snackbar DIV
    var x = document.getElementById("snackbar");

    // Set the text content to the new message
    x.textContent = msg;

    // Add the "show" class to DIV
    x.className = "show";

    // After 3 seconds, remove the show class from DIV
    setTimeout(function() {
        x.className = x.className.replace("show", "");
        // Clear the text content after hiding the message
        x.textContent = "";
    }, 4000);

}


$(document).ready(function () {
    updateCart();
})