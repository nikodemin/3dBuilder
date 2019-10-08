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

    var liCount = 0;

    $('#main-banner div.wrapper ul li').each(function () {
        ++liCount;
        $('#main-banner + ul.banner-btns').append('<li></li>')
    });

    var currentIndex = 0,
        animating = false;

    function move(index) {
        if (animating)
            return;
        animating = true;

        if (index >= liCount)
            index = 0;
        $('#main-banner div.wrapper').animate({
            'margin-left': '-' +
                $('#main-banner').width() * index + "px"
        }, 700, function () {
            $('#main-banner + ul.banner-btns li').removeClass('active').eq(index).addClass('active');
            animating = false;
        })
        currentIndex = index;
    }

    setInterval(function () {
        move(++currentIndex)
    }, 3000);

    $('#main-banner + ul.banner-btns li').on('click', function (e) {
        move($('#main-banner + ul.banner-btns').find('li').index(this))
    }).first().addClass('active');

    // hooks
    $(window).on('resize', function (e) {
        var ratio = 2 / 4.8;
        $('#main-banner').height($('#main-banner div.wrapper').width() * ratio);
        $('#main-banner img').height($('#main-banner div.wrapper').width() * ratio)
            .width($('#main-banner div.wrapper').width());
    }).resize();

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