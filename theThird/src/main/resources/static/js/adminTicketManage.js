//发布、修改退票策略
$(document).ready(function () {

    getRefundInfo();

    function getRefundInfo(){
        getRequest(
            '/ticket/refund/getInfo',
            function (res) {
                if (res.success) {
                    console.log(res);
                    $("#limitHours").text(res.content.limitHours);
                    $("#refundRate").text(res.content.rate);
                    $("#refund-limit-input").val(res.content.limitHours);
                    $("#refund-rate-input").val(res.content.rate);
                } else {
                    alert(res.content);
                }

            },
            function (error) {
                alert(error);
            });
    }

    $("#ticketRefund-form-btn").click(function () {
        var form = {
            limitHours:$("#refund-limit-input").val(),
            rate:$("#refund-rate-input").val()
        }

        console.log(form);

        if (!validateRefundEditForm(form)) {
            return;
        }

        postRequest(
            '/ticket/refund/update',
            form,
            function (res) {
                if (res.success) {
                    getRefundInfo();
                    $("#ticketRefundModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    })
    function validateRefundEditForm(data){
        var isValidate=true;
        function isAValidNum(f){
            var isValid=false;
            if(/^[0-9]+$/.test(f)||/^[0-9]+\.?[0-9]+?$/.test(f)){
                isValid=true;
            }
            return isValid;
        }
        if(!data.limitHours||data.limitHours<0||!(/^[0-9]+$/.test(data.limitHours))){
            //不存在或者为负数或者不是整数
            isValidate=false;
            $('#refund-limit-error').css("visibility", "visible");
            $("#refund-limit-error").text("请正确输入非负整数");
            //showErrorMessage()
        }
        if(!data.rate||data.rate<0||data.rate>1||!(/^[0-9]+\.?[0-9]+?$/.test(data.rate))){
            //不存在或者为负数或者不是小于一的小数
            isValidate=false;
            $('#refund-rate-error').css("visibility", "visible");
            $("#refund-rate-error").text("请正确输入0到1之间的小数");
            //showErrorMessage()
        }
        return isValidate;
    }


});
