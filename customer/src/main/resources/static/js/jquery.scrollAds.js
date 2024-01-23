(function ($) {
    $.fn.extend({
        //Tên plugin
        scrollAds: function (options) {
            // Thiết lập các cấu hình mặc định
            var defaults = {
                leftLink: "#",   //Link quảng cáo bên trái
                rightLink: "#",  //Link quảng cáo bên phải
                leftHTML: "",    //Nội dung quảng cáo trái
                rightHTML: "",   //Nội dung quảng cáo phải
                leftWidth: 160,  //Chiều dài QC trái
                rightWidth: 160, //Chiều dài QC phải
                leftMargin: 20,  //Lề QC trái
                rightMargin: 20, //Lề QC phải
                topMargin: 10    //Lề QC trên
            };

            // Lưu trữ cấu hình
            var options = $.extend(defaults, options);

            var mainWith;

            var stayTopLeft = function () {
                var documentScrollTop = $(document).scrollTop();

                startRY = $("body").scrollTop() > 30 ? startLY = 3 : startLY = options.topMargin;
                floatRightObject.y += (documentScrollTop + startRY - floatRightObject.y) / 16;
                floatRightObject.sP(floatRightObject.x, floatRightObject.y);
                floatLeftObject.y += (documentScrollTop + startLY - floatLeftObject.y) / 16;
                floatLeftObject.sP(floatLeftObject.x, floatLeftObject.y);

                setTimeout(function () { stayTopLeft(); }, 10);
            };

            var floatTopDiv = function () {
                startLX = ($(window).width() - mainWith) / 2 - options.leftWidth - options.leftMargin;
                startLY = options.topMargin + 80;
                startRX = ($(window).width() - mainWith) / 2 + mainWith + options.rightMargin;
                startRY = options.topMargin + 80;

                window.stayTopLeft = stayTopLeft;

                floatLeftObject = function (a) {
                    a = $("#" + a).get(0);
                    a.sP = function (a, b) {
                        $(this).css("left", a + "px");
                        $(this).css("top", b + "px");
                    };
                    a.x = startLX;
                    a.y = startLY;
                    return a;
                }("divadleft");

                floatRightObject = function (a) {
                    a = $("#" + a).get(0);
                    a.sP = function (a, b) {
                        $(this).css("left", a + "px");
                        $(this).css("top", b + "px");
                    };
                    a.x = startRX;
                    a.y = startRY;
                    return a;
                }("divadright");

                stayTopLeft();
            };

            var showAds = function () {
                var a = $("#divadleft");
                var b = $("#divadright");

                //if ($(window).width() < 1000) {
                if ($(window).width() < (mainWith + options.leftMargin + options.rightMargin)) {
                    a.hide();
                    b.hide();
                }
                else {
                    a.show();
                    b.show();
                    floatTopDiv();
                };
            };

            var initPanelAds = function () {
                //Tìm leftDivElem và rightDivElem trên trang
                var leftDivElem = $("#divadleft");
                var rightDivElem = $("#divadright");

                //Nếu chưa có thì tạo động
                if (leftDivElem.length == 0) {
                    leftDiv = '<div id="divadleft" style="display: none; position: absolute; z-index:999;">';
                    leftDiv += '<div style="text-align: center;">';
                    leftDiv += '<a href="' + options.leftLink + '" target="_blank">';
                    leftDiv += options.leftHTML;
                    leftDiv += '</a>';
                    leftDiv += '</div>';
                    leftDiv += '</div>';

                    leftDivElem = $(leftDiv);
                    leftDivElem.width(options.rightWidth)
                    leftDivElem.appendTo("body");
                }

                
                //Nếu chưa có thì tạo ra
                if (rightDivElem.length == 0) {
                    var rightDiv = '<div id="divadright" style="display: none; position: absolute; z-index:999;">';
                    rightDiv += '<div style="text-align: center;">';
                    rightDiv += '<a href="' + options.rightLink + '" target="_blank">';
                    rightDiv += options.rightHTML;
                    rightDiv += '</a>';
                    rightDiv += '</div>';
                    rightDiv += '</div>';

                    rightDivElem = $(rightDiv);
                    rightDivElem.width(options.rightWidth)
                    rightDivElem.appendTo("body");
                }
            };

            //Nội dung chính
            return this.each(function () {
                //Tính chiều dài trang
                mainWith = $(this).width();

                //Khởi tạo 2 div chứa quảng cáo trái-phải
                initPanelAds();

                //Hiện quảng cáo
                showAds();

                //Cập nhật lại quảng cáo khi thay đổi kích cỡ trình duyệt
                $(window).resize(function () {
                    showAds();
                });
            });
        }
    });
})(jQuery);