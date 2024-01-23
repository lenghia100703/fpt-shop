var categories = [
    {
      id: "000",
      name: "Tất cả sản phẩm",
    },
    {
      id: "111",
      name: "Tivi",
    },
    {
      id: "222",
      name: "Macbook",
    },
    {
      id: "333",
      name: "Iphone",
    },
    {
      id: "444",
      name: "Ipad"
    },
    {
      id: "555",
      name: "SamSung Galaxy"
    },
  ]
  
  var products = [
    {
      product_id: "111",
      image: "https://khangdev1998.github.io/storephone/image/product1.png",
      description: "Android Tivi Sony 49 inch 4K",
      price: "8000000",
      sale: "20",
      quantity: "50",
      brand: "Sony",
      address: "Thái Bình",
    },
    {
      product_id: "222",
      image: "https://cf.shopee.vn/file/2477942b1ded3ec5ad3108a6e3140f63_tn",
      description: "Apple MacBook Air M1 2020 8GB/256GB",
      price: "46000000",
      sale: "10",
      quantity: "39",
      brand: "Apple",
      address: "Hà Nội",
    },
    {
      product_id: "333",
      image: "https://khangdev1998.github.io/storephone/image/product3.png",
      description: "Điện thoại iPhone X 256GB Trắng",
      price: "11000000",
      sale: "13",
      quantity: "60",
      brand: "Apple",
      address: "Nam Định",
    },
    {
      product_id: "333",
      image: "https://cf.shopee.vn/file/6e9207cf129c15c67e7d049eb89e94db_tn",
      description: "Điện thoại iphone 12 pro max 256GB chính hãng full box",
      price: "16000000",
      sale: "8",
      quantity: "80",
      brand: "Apple",
      address: "TP.HCM"
    },
    {
      product_id: "444",
      image: "https://khangdev1998.github.io/storephone/image/product6.png",
      description: "iPad Pro 10.5 inch 64GB Wifi",
      price: "12000000",
      sale: "21",
      quantity: "33",
      brand: "LG",
      address: "Hòa Bình"
    },
    {
      product_id: "555",
      image: "https://khangdev1998.github.io/storephone/image/product9.png",
      description: "iPad Pro 10.5 inch 64GB Wifi",
      price: "8000000",
      sale: "28",
      quantity: "43",
      brand: "SamSung",
      address: "Thái Bình"
    },
    {
      product_id: "333",
      image: "https://cf.shopee.vn/file/019c3abc52fc23c2759093147e45e840_tn",
      description: "Điện thoại iphone 6 quốc tế",
      price: "4000000",
      sale: "20",
      quantity: "54",
      brand: "Apple",
      address: "Nam Định"
    }
  ]
  
  document.addEventListener("DOMContentLoaded", function () {
    renderCategories();
  })
  
  function renderCategories() {
    var html = "";
    for (var i = 0; i < categories.length; i++) {
      var category = categories[i];
      html += `
      <li><a class="product-link" onclick="onClickShowProducts(${category.id})">${category.name}</a></li>
      `
    }
    setHtml(".product-left", html);
    renderProducts(products);
  }
  
  function setHtml(selector, html) {
    var element = document.querySelector(selector);
    element.innerHTML = html;
  }
  
  function onClickShowProducts(index) {
    var productsSelector = products.filter(product => {
      return product.product_id == index;
    })
    var productsSelectorLength = productsSelector.length;
    return productsSelectorLength === 0 ? renderProducts(products) : renderProducts(productsSelector);
  }
  
  function renderProducts(products) {
    var html = "";
    for (var i = 0; i < products.length; i++) {
      var product = products[i];
      html += `
      <div class="product-right-items snip1268">
        <div class="product-effect">
          <img class="product-effect-image" src="${product.image}" alt="product-image">
          <div class="product-effect-group">
            <a href=""><i class="product-effect-group__icon fas fa-cart-plus"></i></a>
            <a href=""><i class="product-effect-group__icon fas fa-search-plus"></i></a>
          </div>
        </div>
        <div class="product-right__main">
          <a href="" class="product-link">${product.description}</a>
          <div class="product-price-wrap">
            <span class="product-price">${product.price * (100 - product.sale) / 100}₫</span>  
            <del class="line-through">${product.price}₫</del> 
          </div>
          <div class="product-quantity">Số lượng: ${product.quantity}</div>
          <div class="product-brand">${product.brand}</div>
          <div class="product-address">${product.address}</div>
        </div>
        <div class="discount">-${product.sale}%</div>
      </div>
      `
    }
    setHtml(".product-right", html);
  }
  