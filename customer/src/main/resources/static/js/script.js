//Hàm Ready - Khi trang đã load sẵn sàng sẽ thực thi
$(function () {
    $("body").niceScroll({
        cursorcolor: "#7F7F7F",
        cursorwidth: "6px"
    });

    $('.owl-carousel').owlCarousel({
        items: 1,
        nav: true,
        dots: true,
        autoplay: false,
        loop: true,
        autoplayTimeout: 3000,
        smartSpeed: 1000,
        fluidSpeed: 500,
        responsive: {
            0: {
                dots: false,
                nav: false,
                stagePadding: 50,
                margin: 10
            },
            920: {
                dots: true,
                nav: false
            },
            1200: {
                dots: true,
                nav: true
            }
        }
    });

    $('#my-clock').jsRapClock({
        caption: 'Clock'
    });

    $(".zoom-box img").jqZoom({
        selectorWidth: 30,
        selectorHeight: 30,
        viewerWidth: 400,
        viewerHeight: 300
    });

    $('.main-menu, header').jstars({
        image_path: 'images/images',
        style: 'rand',
        frequency: 15,
        width: 40,
        height: 40
    });
/*
    $("#form-validate").validationEngine( 'attach',
        {
            validationEventTrigger: "blur",
            focusFirstField: true,

        }
    );
*/
$(".main-carousel .container").scrollAds({
    leftLink: "http://phongvu.vn", //Link quảng cáo bên trái
    rightLink: "http://phinam.com", //Link quảng cáo bên phải
    leftHTML: '<img alt="" src="Content/left-right-images/ads_left.png" width="100%" />',
   //Nội dung quảng cáo trái
    rightHTML: '<img alt="" src="Content/left-right-images/ads_right.png" width="100%" />',
   //Nội dung quảng cáo phải
    leftWidth: 160, //Chiều dài QC trái
    rightWidth: 160, //Chiều dài QC phải
    leftMargin: 5, //Lề QC trái
    rightMargin: 5, //Lề QC phải
    topMargin: 10 //Lề QC trên
    });
});