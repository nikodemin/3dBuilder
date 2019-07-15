$(document).ready(function(){

    var liCount = 0;

    $('#main-banner div.wrapper ul li').each(function(){
        ++liCount;
        $('#main-banner + ul.banner-btns').append('<li></li>')
    });

    var currentIndex = 0,
        animating = false;

    function move(index)
    {
        if (animating)
            return;
        animating = true;

        if (index >= liCount)
            index = 0;
        $('#main-banner div.wrapper').animate({
            'margin-left': '-' + 
            $('#main-banner').width()*index + "px"
        }, 700, function(){
            $('#main-banner + ul.banner-btns li').removeClass('active').eq(index).addClass('active');
            animating = false;
        })
        currentIndex = index;
    }

    setInterval(function(){move(++currentIndex)},3000);

    $('#main-banner + ul.banner-btns li').on('click', function(e){move($('#main-banner + ul.banner-btns').find('li').index(this))}).first().addClass('active');    
    
    // hooks
    $(window).on('resize',function(e){
        var ratio = 2/4.8;
        $('#main-banner').height($('#main-banner div.wrapper').width()*ratio);
        $('#main-banner img').height($('#main-banner div.wrapper').width()*ratio)
        .width($('#main-banner div.wrapper').width());
    }).resize();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
    });

    $('.addToCart').on('click',function(e){
        var id = $(this).parents('[data-tool]').attr('data-tool')
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/addtool/'+id,
            success: function(data){
                $('span.cart').text(data)
            }
        })
    })

    $('.quickBuy').on('click',function(e){
        var id = $(this).parents('[data-tool]').attr('data-tool')
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/addtool/'+id,
            success: function(data){
                $('span.cart').text(data)
                window.location.href='http://localhost:8080/order/cart'
            }
        })
    })

    $('.date').datepicker($.datepicker.regional[ "ru" ])

    $('tr.tool button.btn-dec').on('click', function(e){
        var id = $(this).parents('tr').attr('data-tool')
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/deltool/'+id,
            success: function(data){
                $('span.cart').text(data)
            }
        })
        var quantity = $(this).parents('tr').find('span.quantity')
        if (quantity.text() > 0)
            quantity.text(quantity.text()-1)
    })
    $('tr.tool button.btn-inc').on('click', function(e){
            var id = $(this).parents('tr').attr('data-tool')
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/addtool/'+id,
                success: function(data){
                    $('span.cart').text(data)
                }
            })
            var quantity = $(this).parents('tr').find('span.quantity')
            quantity.text(parseInt(quantity.text(),10)+1)
    })
})