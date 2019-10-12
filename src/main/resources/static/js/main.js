var baseUrl = window.location.origin;
function raisePopup(text, style){
    $('<div class="popup alert alert-'+style+'">\n' +
    '    <span class="close">&times;</span>\n' +
    '    <div>\n' +
    '       '+text+'\n' +
    '    </div>\n' +
    '</div>').appendTo('#popupContainer').hide().fadeIn(function () {
        $(this).find('.close').on('click', function (e) {
            $(e.target).parents('.popup').remove()
        })
        $(this).delay(1000).fadeOut(2000,function () {
            $(this).remove()
        })
    })
}
$(document).ready(function () {
    $(window).scroll(function(e) {
        var scroll = $(window).scrollTop();
        if (scroll >= 150) {
            $('.scrollFix').addClass("fixed-top");
        } else {
            $('.scrollFix').removeClass("fixed-top");
        }
    });

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('.addToCart').on('click', function (e) {
        var id = $(this).parents('[data-tool]').attr('data-tool')
        $.ajax({
            type: 'POST',
            url: baseUrl + '/tool/' + id,
            success: function (data) {
                $('span.cart').text(data)
                raisePopup("Инструмент добавлен в корзину","success")
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    })

    $('.quickBuy').on('click', function (e) {
        var id = $(this).parents('[data-tool]').attr('data-tool')
        $.ajax({
            type: 'POST',
            url: baseUrl + '/tool/' + id,
            success: function (data) {
                $('span.cart').text(data)
                window.location.href = baseUrl + '/order/cart'
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    })

    $('div.catalog').on('click',function () {
        $('#catalog').slideToggle()
    })
})