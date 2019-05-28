$(document).ready(function() {

    var canNum = 100;

    getCouponsNum();

    function getCouponsNum() {

        //该行需注释掉
        $("#send-num").text(canNum);

        //以下为与后端交互真方法
        // getRequest(
        //     '/coupon/getNum',
        //     function (res) {
        //         canNum = res.content;
        //         $("#send-num").text(canNum);
        //     },
        //     function (error) {
        //         alert(JSON.stringify(error));
        //     }
        // );
    }

    $('#send-modify-btn').click(function () {
        $("#send-modify-btn").hide();
        $("#send-set-input").val(canNum);
        $("#send-set-input").show();
        $("#send-confirm-btn").show();
    });

    $('#send-confirm-btn').click(function () {
        var dayNum = $("#send-set-input").val();

        // 以下需注释掉
        canNum = dayNum;
        getCouponsNum();
        $("#send-modify-btn").show();
        $("#send-set-input").hide();
        $("#send-confirm-btn").hide();

        //以下为与后端交互真方法
        // postRequest(
        //     '/coupon/change',
        //     {day:dayNum},
        //     function (res) {
        //         if(res.success){
        //             canNum = dayNum;
        //             getCouponsNum();
        //             $("#send-modify-btn").show();
        //             $("#send-set-input").hide();
        //             $("#send-confirm-btn").hide();
        //         } else{
        //             alert(res.message);
        //         }
        //     },
        //     function (error) {
        //         alert(JSON.stringify(error));
        //     }
        // );
    })
});