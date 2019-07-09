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

    //$('.addToCart')
})