$(function () {
    var vueData = {
        tools: [],
        pledge: 0,
        forDays: 0
    }
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    function getTools(){
        $.ajax({
            url: baseUrl + '/cart-tools',
            type: 'GET',
            success: function (data) {
                console.log(data)
                vueData.tools = data
                vueData.pledge = 0
                vueData.tools.forEach(function (tuple) {
                    vueData.pledge += tuple.first.pledge*tuple.second
                })
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }
    getTools()

    var vue = new Vue({
        el: '#main-container',
        data: vueData,
        computed:{
            total: function () {
                var totalTemp = 0
                vueData.tools.forEach(function (tuple) {
                    totalTemp += tuple.first.price*tuple.second*vueData.forDays
                })
                return totalTemp;
            }
        },
        methods: {
            addTool: function (id) {
                $.ajax({
                    url: baseUrl + '/tool/'+id,
                    type: 'POST',
                    success: function (data) {
                        raisePopup('Инструмент добавлен','success')
                        getTools()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            removeTool: function (id) {
                $.ajax({
                    url: baseUrl + '/tool/'+id,
                    type: 'DELETE',
                    success: function (data) {
                        raisePopup('Инструмент удалён','success')
                        getTools()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            }
        }
    })
})